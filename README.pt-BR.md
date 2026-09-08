# SDET Appium Enterprise

[![Android Mobile CI](https://github.com/FernandoDamasioAlves/sdet-appium-enterprise/actions/workflows/android-ci.yml/badge.svg)](https://github.com/FernandoDamasioAlves/sdet-appium-enterprise/actions/workflows/android-ci.yml)

> 🇧🇷 Português | [🇺🇸 English](README.md)

Framework de automação de testes Android em estilo enterprise, construído com **Java 21, Appium 3, UiAutomator2, Selenium e JUnit 5**.

O projeto demonstra práticas de engenharia de qualidade mobile além da automação básica de interface: Page Objects reutilizáveis, interações nativas Android, gestos W3C, coleta automática de evidências em falhas, bootstrap reproduzível da aplicação, quality gates Maven e execução de emulador Android no GitHub Actions.

## O que este projeto demonstra

A suíte atualmente cobre quatro capacidades distintas de automação mobile:

- Navegação entre telas nativas Android
- Gestos touch W3C e drag-and-drop
- Controles nativos e validação de estados
- Alert dialogs nativos do Android

O framework também inclui:

- Page Object Model
- Ciclo de vida do AndroidDriver com ThreadLocal
- Explicit waits
- Capabilities Appium configuráveis
- Evidências automáticas em caso de falha
- Perfis Maven de smoke e regression
- Instalação reproduzível do ApiDemos
- Verificação de integridade SHA-256 da aplicação
- Execução de emulador Android em CI
- Quality gates independentes
- Relatórios de testes e artefatos de CI

## Stack tecnológica

| Tecnologia | Versão / Uso |
|---|---|
| Java | 21 |
| Maven | Build e orquestração dos testes |
| Appium Server | 3.7.0 |
| Appium Java Client | 10.1.1 |
| UiAutomator2 Driver | 8.6.1 |
| Selenium | 4.43.0 |
| JUnit Jupiter | 5.14.4 |
| Android | Android 16 / API 36 |
| GitHub Actions | CI/CD Android |
| Android Emulator | Execução local e em CI |

## Aplicação sob teste

O framework utiliza a aplicação Android oficial **ApiDemos v3.1.0**, mantida pelo projeto Appium.

Pacote:

```text
io.appium.android.apis
```

Activity inicial:

```text
io.appium.android.apis.ApiDemos
```

O processo de bootstrap baixa o APK oficial e valida seu checksum SHA-256 antes da instalação.

SHA-256 esperado:

```text
b13059630dfea8ec2828797911e4ce1893be3adac5cc9db61421434f7987f2d3
```

O APK nunca é versionado no repositório.

## Arquitetura

O projeto separa gerenciamento de driver, comportamento reutilizável de páginas, telas da aplicação, cenários de teste e evidências de falha.

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

Principais responsabilidades:

```text
ConfigManager
    Configuração e sobrescritas via system properties

MobileDriverFactory
    Criação do AndroidDriver e ciclo de vida com ThreadLocal

BaseMobilePage
    Explicit waits e comportamento compartilhado entre páginas

Page Objects
    Locators e interações com a aplicação

BaseMobileTest
    Ciclo de vida do driver nos testes

MobileFailureEvidenceExtension
    Geração automática de evidências quando um teste falha

EvidenceManager
    Persistência de screenshot, page source e metadata
```

## Cobertura de testes

### Smoke

`ApiDemosNavigationTest`

Valida que a aplicação inicia corretamente e que a navegação da tela inicial do ApiDemos para o menu Views funciona.

### Regression

O gate de regression inclui o cenário de smoke e os cenários funcionais abaixo.

#### Navegação

`ApiDemosNavigationTest`

- Validar a tela inicial do ApiDemos
- Navegar para Views
- Validar o menu Views

#### Gesto nativo

`DragAndDropTest`

- Navegar para Views
- Abrir Drag and Drop
- Executar gesto touch com W3C Actions
- Arrastar o primeiro ponto até o segundo
- Validar o resultado `Dropped!`

#### Controles nativos

`ControlsInteractionTest`

Valida componentes nativos Android, incluindo:

- EditText
- CheckBox
- RadioButton
- ToggleButton
- Spinner
- Estado de botão habilitado
- Estado de botão desabilitado

O cenário também valida uma seleção real no Spinner utilizando a opção `Earth`.

#### Alert dialogs nativos

`AlertDialogsTest`

Contém dois cenários independentes:

- Cancelar um diálogo nativo OK/Cancel
- Confirmar um diálogo nativo OK/Cancel

Ambos validam:

- Conteúdo do diálogo
- Botão positivo
- Botão negativo
- Fechamento do diálogo
- Retorno para a tela Alert Dialogs

## Quantidade atual de testes

```text
Smoke Gate       1 teste
Regression Gate  5 testes
Default Suite    5 testes
```

## Estrutura do projeto

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
├── README.md
└── README.pt-BR.md
```

## Requisitos

Para execução local:

- Java 21
- Maven
- Node.js
- Appium 3
- Driver UiAutomator2 do Appium
- Android SDK
- ADB
- Android Emulator
- Emulador Android API 36

Verifique o Java:

```bash
java -version
```

Verifique o Maven:

```bash
mvn -version
```

Verifique o ADB:

```bash
adb version
```

Verifique o Appium:

```bash
appium --version
```

Verifique os drivers Appium instalados:

```bash
appium driver list --installed
```

## Configuração Android local

A configuração padrão está em:

```text
src/test/resources/config/test.properties
```

Endpoint Appium padrão:

```text
http://127.0.0.1:4723
```

Configuração Android padrão:

```text
deviceName=SDET_Pixel_Android_36
udid=emulator-5554
platformVersion=16
automationName=UiAutomator2
```

Os valores de configuração podem ser sobrescritos por Java system properties.

## Bootstrap da aplicação Android

Com o emulador já em execução:

```bash
bash scripts/bootstrap-android.sh
```

O script de bootstrap:

1. Verifica as ferramentas necessárias
2. Verifica se o emulador está disponível
3. Baixa o ApiDemos v3.1.0
4. Valida o checksum SHA-256
5. Instala ou atualiza o APK
6. Trata instalações existentes incompatíveis
7. Valida o pacote e a versão instalada

As aplicações baixadas ficam dentro de `target/` e são intencionalmente excluídas do Git.

## Iniciando o Appium

Inicie o servidor Appium localmente:

```bash
appium --address 127.0.0.1 --port 4723
```

O framework espera o Appium em:

```text
http://127.0.0.1:4723
```

## Executando os testes

### Suíte completa

```bash
mvn test
```

Esperado:

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
```

### Smoke gate

```bash
mvn test -Psmoke
```

Esperado:

```text
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
```

### Regression gate

```bash
mvn test -Pregression
```

Esperado:

```text
Tests run: 5, Failures: 0, Errors: 0, Skipped: 0
```

### Classe de teste individual

Exemplo:

```bash
mvn -Dtest=ControlsInteractionTest test
```

## Evidências de falha

Quando um teste mobile falha, o framework cria automaticamente evidências em:

```text
target/evidence/<TestClass>/<TestMethod>/<timestamp>/
```

As evidências podem conter:

```text
screenshot.png
page-source.xml
metadata.txt
```

As evidências são geradas somente em caso de falha.

Isso facilita a depuração local e a análise de problemas no CI sem depender de coleta manual.

## CI/CD

O GitHub Actions executa a suíte Android em:

```text
ubuntu-latest
Android API 36
Google APIs
x86_64
Pixel 6 emulator profile
```

O workflow cria dois quality gates independentes:

```text
Android Smoke Gate
Android Regression Gate
```

O ambiente de CI:

1. Faz checkout do repositório
2. Instala Java 21
3. Instala Node.js 24
4. Instala Appium 3.7.0
5. Instala UiAutomator2 8.6.1
6. Habilita KVM
7. Inicia um emulador Android API 36
8. Faz bootstrap do APK oficial do ApiDemos
9. Inicia o Appium
10. Aguarda o Appium ficar pronto
11. Executa o quality gate Maven selecionado
12. Publica relatórios, evidências e logs do Appium

Os artefatos do CI são mantidos por 14 dias.

## Artefatos do CI

Cada quality gate publica seu próprio artefato:

```text
android-smoke-evidence
android-regression-evidence
```

Os artefatos podem incluir:

```text
Surefire reports
Failure evidence
Appium server log
```

Em execuções bem-sucedidas, o diretório de evidências de falha pode não existir, pois as evidências são geradas apenas quando um teste falha.

## Decisões de engenharia

### Locators estáveis

O framework prioriza identificadores nativos estáveis como:

```text
resource-id
accessibility id
```

Coordenadas não são utilizadas nos cenários de teste de produção.

Coordenadas foram utilizadas somente durante a inspeção exploratória para descobrir a hierarquia real do ApiDemos.

### Explicit waits

Os Page Objects utilizam explicit waits em vez de sleeps arbitrários para a sincronização normal dos testes.

### Page Object Model

Locators e comportamento da interface permanecem dentro dos Page Objects, e não nas classes de teste.

Os testes descrevem comportamento, enquanto os Page Objects implementam os detalhes de interação.

### Gestos W3C

O drag-and-drop utiliza `PointerInput` do padrão W3C do Selenium em vez das APIs antigas de touch do Appium.

### Aplicação de teste reproduzível

O projeto não depende de um APK local desconhecido.

O bootstrap baixa uma versão oficial e fixa do ApiDemos e valida seu checksum antes da instalação.

### Validação de integridade fail-closed

Se o APK baixado não corresponder ao SHA-256 esperado, o bootstrap é interrompido em vez de instalar uma aplicação não verificada.

### Quality gates independentes

Smoke e regression executam de forma independente no CI, facilitando a identificação de falhas e permitindo estratégias de execução diferentes conforme a suíte cresce.

## Limitações conhecidas

- A implementação atual é focada em Android.
- Execução iOS não faz parte desta versão.
- O framework utiliza o ApiDemos como aplicação determinística de demonstração, e não uma aplicação de produção dependente de backend.
- A execução local exige um emulador Android compatível já em execução.
- Screenshots e page sources de falhas são gerados somente quando algum teste falha.

## Objetivos de engenharia

Este repositório foi desenvolvido como um projeto de portfólio SDET para demonstrar:

- arquitetura de automação mobile sustentável
- automação Android nativa
- isolamento de testes
- provisionamento determinístico de ambiente
- quality gates em CI
- diagnóstico de falhas
- execução reproduzível
- decisões de design para automação de testes

## Autor

**Fernando Damasio**

QA Engineer / SDET

GitHub: [FernandoDamasioAlves](https://github.com/FernandoDamasioAlves)
