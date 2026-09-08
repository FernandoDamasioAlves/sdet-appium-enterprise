# SDET Appium Enterprise

[![Android Mobile CI](https://github.com/FernandoDamasioAlves/sdet-appium-enterprise/actions/workflows/android-ci.yml/badge.svg)](https://github.com/FernandoDamasioAlves/sdet-appium-enterprise/actions/workflows/android-ci.yml)

> 🇺🇸 English | [🇧🇷 Português](README.pt-BR.md)

Enterprise-style Android test automation framework built with **Java 21, Appium 3, UiAutomator2, Selenium and JUnit 5**.

The project demonstrates mobile quality engineering practices beyond basic UI automation: reusable Page Objects, native Android interactions, W3C gestures, automatic failure evidence, reproducible application bootstrap, Maven quality gates and Android emulator execution in GitHub Actions.

## What This Project Demonstrates

The suite currently covers four distinct mobile automation capabilities:

- Navigation through native Android screens
- W3C touch gestures and drag-and-drop
- Native form controls and state validation
- Native Android alert dialogs

The framework also includes:

- Page Object Model
- Thread-local Android driver lifecycle
- Explicit waits
- Configurable Appium capabilities
- Automatic failure evidence
- Smoke and regression Maven profiles
- Reproducible ApiDemos installation
- SHA-256 application integrity verification
- Android emulator execution in CI
- Separate CI quality gates
- Test reports and CI artifacts

## Technology Stack

| Technology | Version / Usage |
|---|---|
| Java | 21 |
| Maven | Build and test orchestration |
| Appium Server | 3.7.0 |
| Appium Java Client | 10.1.1 |
| UiAutomator2 Driver | 8.6.1 |
| Selenium | 4.43.0 |
| JUnit Jupiter | 5.14.4 |
| Android | Android 16 / API 36 |
| GitHub Actions | Android CI/CD |
| Android Emulator | Local and CI execution |

## Application Under Test

The framework uses the official Appium **ApiDemos v3.1.0** Android application.

Package:

```text
io.appium.android.apis
```

Launch activity:

```text
io.appium.android.apis.ApiDemos
```

The bootstrap process downloads the official APK and validates its SHA-256 checksum before installation.

Expected SHA-256:

```text
b13059630dfea8ec2828797911e4ce1893be3adac5cc9db61421434f7987f2d3
```

The APK is never committed to the repository.

## Architecture

The project separates driver management, reusable page behavior, application screens, test scenarios and failure evidence.

```text
Tests
  |
  v
Page Objects
  |
  v
BaseMobilePage
  |
  v
AndroidDriver
  |
  v
Appium Server
  |
  v
UiAutomator2
  |
  v
Android Emulator
```

Main responsibilities:

```text
ConfigManager
    Configuration and system-property overrides

MobileDriverFactory
    AndroidDriver creation and ThreadLocal lifecycle

BaseMobilePage
    Explicit waits and shared page behavior

Page Objects
    Locators and application interactions

BaseMobileTest
    Driver lifecycle for tests

MobileFailureEvidenceExtension
    Automatic evidence generation on failure

EvidenceManager
    Screenshot, page source and metadata persistence
```

## Test Coverage

### Smoke

`ApiDemosNavigationTest`

Validates that the application starts correctly and navigation from the ApiDemos home screen to the Views menu works.

### Regression

The regression gate includes the smoke scenario plus the functional scenarios below.

#### Navigation

`ApiDemosNavigationTest`

- Validate ApiDemos home screen
- Navigate to Views
- Validate Views menu

#### Native Gesture

`DragAndDropTest`

- Navigate to Views
- Open Drag and Drop
- Execute a W3C touch gesture
- Drag the first dot to the second dot
- Validate the `Dropped!` result

#### Native Controls

`ControlsInteractionTest`

Validates native Android components including:

- EditText
- CheckBox
- RadioButton
- ToggleButton
- Spinner
- Enabled button state
- Disabled button state

The scenario also validates a real Spinner selection using the `Earth` option.

#### Native Alert Dialogs

`AlertDialogsTest`

Contains two independent scenarios:

- Cancel a native OK/Cancel dialog
- Confirm a native OK/Cancel dialog

Both validate:

- Dialog content
- Positive button
- Negative button
- Dialog closure
- Return to the Alert Dialogs screen

## Current Test Count

```text
Smoke Gate       1 test
Regression Gate  5 tests
Default Suite    5 tests
```

## Project Structure

```text
.
├── .github
│   └── workflows
│       └── android-ci.yml
├── scripts
│   ├── bootstrap-android.sh
│   └── run-android-ci.sh
├── src
│   └── test
│       ├── java
│       │   └── com
│       │       └── fernandodamasio
│       │           └── sdet
│       │               └── mobile
│       │                   ├── config
│       │                   ├── driver
│       │                   ├── evidence
│       │                   ├── pages
│       │                   └── tests
│       └── resources
│           └── config
│               └── test.properties
├── .gitignore
├── pom.xml
└── README.md
```

## Requirements

For local execution:

- Java 21
- Maven
- Node.js
- Appium 3
- UiAutomator2 Appium driver
- Android SDK
- ADB
- Android Emulator
- Android API 36 emulator

Verify Java:

```bash
java -version
```

Verify Maven:

```bash
mvn -version
```

Verify ADB:

```bash
adb version
```

Verify Appium:

```bash
appium --version
```

Verify installed Appium drivers:

```bash
appium driver list --installed
```

## Local Android Configuration

The default configuration is stored in:

```text
src/test/resources/config/test.properties
```

Default Appium endpoint:

```text
http://127.0.0.1:4723
```

Default Android device configuration:

```text
deviceName=SDET_Pixel_Android_36
udid=emulator-5554
platformVersion=16
automationName=UiAutomator2
```

Configuration values can be overridden through Java system properties.

## Bootstrap the Android Application

With the emulator already running:

```bash
bash scripts/bootstrap-android.sh
```

The bootstrap script:

1. Verifies required tools
2. Verifies that the emulator is available
3. Downloads ApiDemos v3.1.0
4. Validates its SHA-256 checksum
5. Installs or updates the APK
6. Handles incompatible existing installations
7. Validates the installed package and version

Downloaded applications are stored under `target/` and are intentionally excluded from Git.

## Start Appium

Start the Appium server locally:

```bash
appium --address 127.0.0.1 --port 4723
```

The framework expects Appium at:

```text
http://127.0.0.1:4723
```

## Running Tests

### Full suite

```bash
mvn test
```

Expected:

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
```

### Smoke gate

```bash
mvn test -Psmoke
```

Expected:

```text
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
```

### Regression gate

```bash
mvn test -Pregression
```

Expected:

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
```

### Single test class

Example:

```bash
mvn -Dtest=ControlsInteractionTest test
```

## Failure Evidence

When a mobile test fails, the framework automatically creates evidence under:

```text
target/evidence/<TestClass>/<TestMethod>/<timestamp>/
```

Evidence may contain:

```text
screenshot.png
page-source.xml
metadata.txt
```

Evidence is generated on failures only.

This allows local debugging and CI troubleshooting without requiring manual evidence collection.

## CI/CD

GitHub Actions runs the Android suite on:

```text
ubuntu-latest
Android API 36
Google APIs
x86_64
Pixel 6 emulator profile
```

The workflow creates two independent quality gates:

```text
Android Smoke Gate
Android Regression Gate
```

The CI environment automatically:

1. Checks out the repository
2. Installs Java 21
3. Installs Node.js 24
4. Installs Appium 3.7.0
5. Installs UiAutomator2 8.6.1
6. Enables KVM
7. Starts an Android API 36 emulator
8. Bootstraps the official ApiDemos APK
9. Starts Appium
10. Waits for Appium readiness
11. Executes the selected Maven quality gate
12. Uploads reports, evidence and Appium logs

CI artifacts are retained for 14 days.

## CI Artifacts

Each quality gate publishes its own artifact:

```text
android-smoke-evidence
android-regression-evidence
```

Artifacts may include:

```text
Surefire reports
Failure evidence
Appium server log
```

On successful executions, the failure-evidence directory may be absent because evidence is generated only when a test fails.

## Design Decisions

### Stable Locators

The framework prefers stable native identifiers such as:

```text
resource-id
accessibility id
```

Coordinates are not used by production test scenarios.

Coordinates were used only during exploratory inspection while discovering the real ApiDemos hierarchy.

### Explicit Waits

Page Objects use explicit waits instead of arbitrary sleeps for normal test synchronization.

### Page Object Model

Locators and UI behavior remain inside Page Objects rather than test classes.

Tests describe behavior while Page Objects implement interaction details.

### W3C Gestures

Drag-and-drop uses Selenium W3C `PointerInput` actions instead of deprecated Appium touch APIs.

### Reproducible Test Application

The project does not depend on an unknown local APK.

The bootstrap process downloads a pinned official ApiDemos release and verifies its checksum before installation.

### Fail-Closed Integrity Validation

If the downloaded APK does not match the expected SHA-256 checksum, the bootstrap process stops instead of installing an unverified application.

### Independent Quality Gates

Smoke and regression execute independently in CI, making failures easier to identify and allowing different execution strategies as the suite grows.

## Known Limitations

- The current implementation focuses on Android.
- iOS execution is not part of this version.
- The framework uses ApiDemos as a deterministic demonstration application rather than a production backend-dependent application.
- Local execution requires an already running compatible Android emulator.
- Failure screenshots and page sources are intentionally generated only for failed tests.

## Engineering Goals

This repository is designed as an SDET portfolio project demonstrating:

- maintainable mobile automation architecture
- native Android automation
- test isolation
- deterministic environment provisioning
- CI quality gates
- failure diagnostics
- reproducible execution
- test automation design decisions

## Author

**Fernando Damasio**

QA Engineer / SDET

GitHub: [FernandoDamasioAlves](https://github.com/FernandoDamasioAlves)
