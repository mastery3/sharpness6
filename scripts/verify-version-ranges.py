#!/usr/bin/env python3
"""Verify that every built jar's Minecraft dependency matches exactly one supported version.

Usage: python3 scripts/verify-version-ranges.py [dist-dir]
       default dist-dir: build/dist
"""
import json
import re
import sys
import zipfile
from pathlib import Path

SUPPORTED = [
    "1.12.2", "1.13.2", "1.14.4", "1.15.2", "1.16.5", "1.17.1", "1.18.2",
    "1.19.2", "1.19.4", "1.20.1", "1.20.4", "1.20.6", "1.21.1", "1.21.4",
    "1.21.8", "1.21.11", "26.1.2", "26.2", "26.3",
]


def parse_ver(v):
    # returns (tuple of ints, prerelease string or "")
    m = re.match(r"^(\d+(?:\.\d+)*)(.*)$", v.strip())
    if not m:
        raise ValueError(f"bad version: {v}")
    nums = tuple(int(x) for x in m.group(1).split("."))
    pre = m.group(2).lstrip("-").strip()
    return nums, pre


def cmp_ver(a, b):
    na, pa = parse_ver(a)
    nb, pb = parse_ver(b)
    # pad
    n = max(len(na), len(nb))
    na = na + (0,) * (n - len(na))
    nb = nb + (0,) * (n - len(nb))
    if na != nb:
        return -1 if na < nb else 1
    # prerelease: a version with prerelease < one without
    if pa and not pb:
        return -1
    if pb and not pa:
        return 1
    if pa != pb:
        return -1 if pa < pb else 1
    return 0


def satisfies(version, predicate):
    """Fabric-compatible version predicate evaluation (subset used by this project)."""
    for token in predicate.split():
        if not _token_ok(version, token):
            return False
    return True


def _token_ok(version, token):
    token = token.strip()
    if not token:
        return True
    # tilde: same major+minor, >= ref
    if token.startswith("~"):
        base = token[1:]
        if cmp_ver(version, base) < 0:
            return False
        na, _ = parse_ver(base)
        # next minor boundary: increment minor, reset patch
        if len(na) >= 2:
            boundary = f"{na[0]}.{na[1] + 1}.0"
        else:
            boundary = f"{na[0] + 1}.0.0"
        return cmp_ver(version, boundary) < 0
    # caret: compatible (next major)
    if token.startswith("^"):
        base = token[1:]
        if cmp_ver(version, base) < 0:
            return False
        na, _ = parse_ver(base)
        boundary = f"{na[0] + 1}.0.0"
        return cmp_ver(version, boundary) < 0
    # operators
    for op in (">=", "<=", ">", "<", "="):
        if token.startswith(op):
            base = token[len(op):]
            c = cmp_ver(version, base)
            if op == ">=":
                return c >= 0
            if op == "<=":
                return c <= 0
            if op == ">":
                return c > 0
            if op == "<":
                return c < 0
            if op == "=":
                return c == 0
    # wildcard
    if "*" in token:
        na, _ = parse_ver(version)
        parts = token.split(".")
        for i, p in enumerate(parts):
            if p == "*":
                continue
            if i >= len(na) or str(na[i]) != p:
                return False
        return True
    # exact
    return cmp_ver(version, token) == 0


def main():
    dist = Path(sys.argv[1]) if len(sys.argv) > 1 else Path("build/dist")
    jars = sorted(dist.glob("*.jar"))
    if not jars:
        print(f"No jars found in {dist}", file=sys.stderr)
        sys.exit(1)

    rows = []
    for j in jars:
        with zipfile.ZipFile(j) as z:
            if "fabric.mod.json" not in z.namelist():
                print(f"{j.name}: missing fabric.mod.json", file=sys.stderr)
                sys.exit(1)
            fm = json.loads(z.read("fabric.mod.json"))
        mc = fm["depends"]["minecraft"]
        # version from filename: sharpness6-2.0.0+mc<VERSION>.jar
        m = re.search(r"\+mc(.+)\.jar$", j.name)
        if not m:
            print(f"{j.name}: cannot parse version from filename", file=sys.stderr)
            sys.exit(1)
        ver = m.group(1)
        rows.append((j.name, ver, mc))

    errors = []
    # 1. count
    if len(rows) != len(SUPPORTED):
        errors.append(f"expected {len(SUPPORTED)} jars, found {len(rows)}")
    # 2. every supported version present exactly once
    found_versions = [r[1] for r in rows]
    for v in SUPPORTED:
        if found_versions.count(v) != 1:
            errors.append(f"version {v}: present {found_versions.count(v)} times (expected 1)")
    for v in found_versions:
        if v not in SUPPORTED:
            errors.append(f"unexpected version in matrix: {v}")
    # 3. exact match: each jar's mc_dep must equal its version
    for name, ver, mc in rows:
        if mc != ver:
            errors.append(f"{name}: mc_dep={mc!r} != version {ver!r}")
    # 4. predicate matrix: each version matches exactly its own jar
    matrix_fail = []
    for v in SUPPORTED:
        matches = [r[0] for r in rows if satisfies(v, r[2])]
        intended = [r[0] for r in rows if r[1] == v]
        if matches != intended:
            matrix_fail.append((v, matches, intended))
    if matrix_fail:
        for v, got, want in matrix_fail:
            errors.append(f"version {v}: matched {got}, expected {want}")

    # report
    print(f"{'version':10} {'jar':45} {'mc_dep':12} {'status'}")
    print("-" * 80)
    for name, ver, mc in sorted(rows, key=lambda r: r[1]):
        status = "OK" if (mc == ver and ver in SUPPORTED) else "FAIL"
        print(f"{ver:10} {name:45} {mc:12} {status}")

    if errors:
        print("\nERRORS:", file=sys.stderr)
        for e in errors:
            print("  - " + e, file=sys.stderr)
        sys.exit(1)
    print(f"\nAll {len(rows)} artifacts verified: exact, unique, no cross-version matches.")


if __name__ == "__main__":
    main()