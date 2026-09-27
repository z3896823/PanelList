#!/usr/bin/env bash
set -euo pipefail
cd "$(dirname "$0")/.."
source tools/android-env.sh

# Default to the only USB-connected physical device. Pass a serial for another device.
if [[ $# -gt 0 ]]; then
    device=(-s "$1")
else
    device=(-d)
fi
if ! adb "${device[@]}" get-state >/dev/null 2>&1; then
    echo 'Connect and unlock your phone, enable USB debugging, and accept its authorization prompt.' >&2
    echo 'Or specify a connected device: ./tools/run-demo.sh <serial>' >&2
    exit 1
fi
./gradlew :app:assembleDebug
adb "${device[@]}" install -r app/build/outputs/apk/debug/app-debug.apk
adb "${device[@]}" shell am start -W -n sysu.zyb.panellisttest/.MainActivity
