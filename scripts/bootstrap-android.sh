#!/usr/bin/env bash

set -euo pipefail

APP_VERSION="3.1.0"
APP_NAME="ApiDemos-debug.apk"
APP_URL="https://github.com/appium/android-apidemos/releases/download/v${APP_VERSION}/${APP_NAME}"
APP_SHA256="b13059630dfea8ec2828797911e4ce1893be3adac5cc9db61421434f7987f2d3"

APP_DIR="target/apps"
APP_PATH="${APP_DIR}/${APP_NAME}"

PACKAGE_NAME="io.appium.android.apis"
DEVICE_SERIAL="${ANDROID_SERIAL:-emulator-5554}"

echo "=========================================="
echo " ANDROID TEST APP BOOTSTRAP"
echo "=========================================="

for command in curl adb; do
    if ! command -v "$command" >/dev/null 2>&1; then
        echo "ERRO: comando '$command' nao encontrado."
        exit 1
    fi
done

if command -v sha256sum >/dev/null 2>&1; then
    SHA256_COMMAND="sha256sum"
elif command -v shasum >/dev/null 2>&1; then
    SHA256_COMMAND="shasum -a 256"
else
    echo "ERRO: nenhum utilitario SHA-256 encontrado."
    exit 1
fi

echo
echo "=== DEVICE ==="

if ! adb devices |
    grep -q "^${DEVICE_SERIAL}[[:space:]]*device"; then
    echo "ERRO: dispositivo ${DEVICE_SERIAL} nao esta conectado."
    adb devices -l
    exit 1
fi

echo "OK: ${DEVICE_SERIAL} conectado."

mkdir -p "$APP_DIR"

verify_checksum() {
    local actual

    actual="$(
        $SHA256_COMMAND "$APP_PATH" |
            awk '{print $1}'
    )"

    [ "$actual" = "$APP_SHA256" ]
}

echo
echo "=== APIDEMOS v${APP_VERSION} ==="

if [ -f "$APP_PATH" ] && verify_checksum; then
    echo "OK: APK ja existe e checksum esta correto."
else
    echo "Baixando ${APP_NAME}..."

    rm -f "$APP_PATH"

    curl \
        --fail \
        --location \
        --retry 3 \
        --retry-delay 2 \
        --output "$APP_PATH" \
        "$APP_URL"

    if ! verify_checksum; then
        echo "ERRO: checksum SHA-256 invalido."
        rm -f "$APP_PATH"
        exit 1
    fi

    echo "OK: download concluido e checksum validado."
fi

echo
echo "=== SHA-256 ==="

$SHA256_COMMAND "$APP_PATH"

echo
echo "=== INSTALL ==="

set +e

INSTALL_OUTPUT="$(
    adb -s "$DEVICE_SERIAL" \
        install -r "$APP_PATH" 2>&1
)"

INSTALL_EXIT=$?

set -e

echo "$INSTALL_OUTPUT"

if [ "$INSTALL_EXIT" -ne 0 ]; then

    if echo "$INSTALL_OUTPUT" | \
        grep -q "INSTALL_FAILED_UPDATE_INCOMPATIBLE"; then

        echo
        echo "Assinatura diferente detectada."
        echo "Removendo somente ${PACKAGE_NAME}..."

        adb -s "$DEVICE_SERIAL" \
            uninstall "$PACKAGE_NAME"

        echo
        echo "Instalando APK oficial..."

        adb -s "$DEVICE_SERIAL" \
            install "$APP_PATH"

    else
        echo
        echo "ERRO: instalacao do APK falhou."
        exit "$INSTALL_EXIT"
    fi
fi

echo
echo "=== PACKAGE ==="

if ! adb -s "$DEVICE_SERIAL" \
    shell pm path "$PACKAGE_NAME" |
    grep -q "^package:"; then

    echo "ERRO: package ${PACKAGE_NAME} nao encontrado."
    exit 1
fi

echo "OK: ${PACKAGE_NAME} instalado."

echo
echo "=== VERSION ==="

adb -s "$DEVICE_SERIAL" \
    shell dumpsys package "$PACKAGE_NAME" |
    grep -E 'versionCode=|versionName=' |
    head -2

echo
echo "=========================================="
echo " ANDROID TEST APP READY"
echo "=========================================="
