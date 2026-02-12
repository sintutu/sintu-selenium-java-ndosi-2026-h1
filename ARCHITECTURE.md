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
* Tests specify the browsers used.
* Perform assertions explicitly.
* Never reference Selenium APIs.
* Never create WebDriver.
* Never use locators.
* Never perform waits.

Example:
```java
@Test
public void logIntoNdosiDevSite() {
    app.loginAsOrdinaryUser();
    assertThat(app.isOnDashboard()).isTrue();
}
```

Tests are executable specifications.

They assert outcomes.
They do not implement behaviour.

Tests trigger the start and end of the lifecycle as a user would.

This is via the `TestBase` class that only calls the lifecycle owner before and after tests.

### 2. Application Facade (Harness)

The `Harness` class represents:

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

One `Harness` instance represents one scenario execution.

It implements `AutoCloseable`. This signals that the harness is responsible to close.
That said, since the implementation of TestNG lifecycle hook `@AfterMethod` this closing is happening there. 
This signal says, whichever test runner is used, closing the lifecycle must happen in the harness. 
Reverting to try-with-resources is available.

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
> Harness  
> ↓  
> Page Objects  
> ↓  
> Selenium WebDriver

Dependencies flow downward only.

Selenium never leaks upward.

## Lifecycle Ownership

`Harness` owns:
* Driver instantiation via DriverFactory
* Wait creation
* Navigation to base URI
* (Optionally) Browser teardown

```java
@Override
public void close() {
    if (driver != null){
        driver.quit();
    }
}
```

Tests may use try-with-resources to ensure cleanup:
```java
try (Harness app = new Harness()) {
...
}
```
This allows deterministic teardown. Though this is already available in TestBase.

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
* No dependency injection framework (intentionally avoided for clarity).

* This architecture favours explicit ownership over abstraction.

## Extension Points

Future improvements may include:
* Environment configuration
* Test data injection
* Role-based login strategies
* Parallel execution support
* CI-friendly headless configuration

These can be added without breaking the core separation model.