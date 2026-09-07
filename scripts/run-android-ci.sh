#!/usr/bin/env bash

set -euo pipefail

PROFILE="${1:-}"
GATE="${2:-}"

if [ -z "$PROFILE" ] || [ -z "$GATE" ]; then
    echo "ERROR: profile and gate are required."
    exit 2
fi

case "$PROFILE" in
    smoke|regression)
        ;;
    *)
        echo "ERROR: unsupported Maven profile: $PROFILE"
        exit 2
        ;;
esac

echo "=========================================="
echo " ANDROID ${GATE} GATE"
echo "=========================================="

echo
echo "=== DEVICE ==="

adb devices -l

echo
echo "=== BOOTSTRAP TEST APP ==="

./scripts/bootstrap-android.sh

echo
echo "=== START APPIUM ==="

rm -f appium-server.log

appium     --address 127.0.0.1     --port 4723     > appium-server.log 2>&1 &

APPIUM_PID=$!

echo "Appium PID: ${APPIUM_PID}"

cleanup() {
    if kill -0 "$APPIUM_PID" >/dev/null 2>&1; then
        echo
        echo "=== STOP APPIUM ==="
        kill "$APPIUM_PID" || true
    fi
}

trap cleanup EXIT

echo
echo "=== WAITING FOR APPIUM ==="

READY="false"

for i in $(seq 1 45); do
    if curl -fsS         http://127.0.0.1:4723/status         >/dev/null 2>&1; then

        READY="true"
        echo "OK: Appium Server ready."
        break
    fi

    echo "Waiting for Appium... ${i}/45"
    sleep 1
done

if [ "$READY" != "true" ]; then
    echo
    echo "ERROR: Appium Server did not start."
    cat appium-server.log || true
    exit 1
fi

echo
echo "=== APPIUM STATUS ==="

curl -fsS http://127.0.0.1:4723/status

echo
echo
echo "=== MAVEN PROFILE ==="

echo "$PROFILE"

echo
echo "=== RUN TESTS ==="

mvn -B -ntp test "-P${PROFILE}"

echo
echo "=========================================="
echo " ANDROID ${GATE} GATE PASSED"
echo "=========================================="
