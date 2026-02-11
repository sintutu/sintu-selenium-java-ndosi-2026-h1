# UI Automation Architecture

## Overview

This project follows a layered behavioural architecture for Selenium UI automation.

The goal is to separate:
* Business intent (what the user does)
* Behaviour orchestration (how a scenario flows)
* DOM mechanics (how elements are located and interacted with)
* Assertions (verification)

Each layer has a strict responsibility.

## Architectural Principles

### 1. Tests Express Behaviour, Not Mechanics

Tests:
* Describe user-visible scenarios.
* Call high-level behaviour methods.
* Perform assertions explicitly.
* Never reference Selenium APIs.
* Never create WebDriver.
* Never use locators.
* Never perform waits.

Example:
```java
@Test
public void logIntoNdosiDevSite() {
    try (NdosiDev app = new NdosiDev()) {
        app.loginAsOrdinaryUser();
        assertThat(app.isOnDashboard()).isTrue();
    }
}
```

Tests are executable specifications.

They assert outcomes.
They do not implement behaviour.

### 2. Application Facade (NdosiDev)

The `NdosiDev` class represents:

> A running instance of the application as experienced by a user.

It owns:
* WebDriver lifecycle
* WebDriverWait lifecycle
* Behaviour orchestration
* Page sequencing

It does not:
* Expose WebDriver
* Expose page objects
* Contain raw locators
* Perform low-level Selenium interactions

It translates:

Page actions → User-level behaviours  
Page observations → Domain-level answers

Example:

```java
public void loginAsOrdinaryUser(){
    HomePage homePage = new HomePage(driver, wait);
    homePage.clickLogin();

    PracticePage practicePage = new PracticePage(driver, wait);
    practicePage.enterUsername();
    practicePage.enterPassword();
    practicePage.clickLogin();
}
```

One `NdosiDev` instance represents one scenario execution.

It implements `AutoCloseable` to guarantee browser cleanup.

### 3. Page Objects (Mechanics Layer)

Each page:
* Encapsulates locators
* Encapsulates Selenium calls
* Encapsulates waits for its own elements
* Reports page-level facts

Pages:
* Do not assert
* Do not coordinate cross-page flows
* Do not manage driver lifecycle
* Do not interpret business meaning

Example:

```java
public boolean isVisible(){
    return wait.until(
        ExpectedConditions.visibilityOfElementLocated(welcomeMessage)
    ).isDisplayed();
}
```


Pages report UI state.

They do not decide what that state means.

## Dependency Flow

Direction of dependency:

> Test  
> ↓  
> Facade (NdosiDev)  
> ↓  
> Page Objects  
> ↓  
> Selenium WebDriver

Dependencies flow downward only.

Selenium never leaks upward.

## Lifecycle Ownership

`NdosiDev` owns:
* Driver creation
* Wait creation
* Navigation to base URI
* Browser teardown

```java
@Override
public void close() {
    if (driver != null){
        driver.quit();
    }
}
```

Tests use try-with-resources to ensure cleanup:
```java
try (NdosiDev app = new NdosiDev()) {
...
}
```
This guarantees deterministic teardown.

## Design Goals

This structure optimises for:
* Readability
* Maintainability
* Test clarity
* Reduced Selenium coupling
* Explicit lifecycle ownership
* Interview-ready architectural clarity

## Tradeoffs

Current intentional tradeoffs:
* Page objects are instantiated per call (simple, stateless model).
* Credentials are embedded in page object (acceptable for demo, not production).
* ChromeDriver is created directly (can be abstracted later).
* No dependency injection framework (intentionally avoided for clarity).

* This architecture favours explicit ownership over abstraction.

## Extension Points

Future improvements may include:
* Driver factory abstraction
* Environment configuration
* Test data injection
* Role-based login strategies
* Parallel execution support
* CI-friendly headless configuration

These can be added without breaking the core separation model.