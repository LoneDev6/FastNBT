#!/usr/bin/env bash
set -euo pipefail

script_dir="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
project_dir="$(cd -- "$script_dir/.." && pwd)"
build_gradle="$project_dir/build.gradle.kts"

group="$(sed -n 's/^group = "\([^"]*\)"$/\1/p' "$build_gradle")"
version="$(sed -n 's/^version = "\([^"]*\)"$/\1/p' "$build_gradle")"

if [[ -z "$group" || -z "$version" ]]; then
  echo "Unable to read group/version from ${build_gradle}" >&2
  exit 1
fi

cd "$project_dir"
./gradlew :FastNbt-jar:publishToMavenLocal --no-daemon --no-configuration-cache "$@"

cat <<EOF
Installed: ${group}:FastNbt-jar:${version}

Gradle Kotlin DSL:
repositories {
    mavenLocal()
}
dependencies {
    compileOnly("${group}:FastNbt-jar:${version}")
}

Maven:
<dependency>
    <groupId>${group}</groupId>
    <artifactId>FastNbt-jar</artifactId>
    <version>${version}</version>
    <scope>provided</scope>
</dependency>
EOF
