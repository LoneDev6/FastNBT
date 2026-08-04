#!/usr/bin/env bash
set -euo pipefail

script_dir="$(cd -- "$(dirname -- "${BASH_SOURCE[0]}")" && pwd)"
project_dir="$(cd -- "$script_dir/.." && pwd)"

cd "$project_dir"
./gradlew deployMavenCentral --no-daemon --no-configuration-cache "$@"

echo "Review and publish the validated deployment at https://central.sonatype.com/publishing/deployments"
