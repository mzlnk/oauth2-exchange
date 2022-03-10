# OAuth2 Exchange Library for Java

[![Licence: MIT](https://img.shields.io/badge/Licence-MIT-blue.svg)](https://shields.io/)
[![Version: 0.1.0](https://img.shields.io/badge/version-0.1.0-blue.svg)](https://shields.io/)
[![Java : 17](https://img.shields.io/badge/Java-17-orange.svg)](https://jdk.java.net/17/)
[![Open Source](https://badges.frapsoft.com/os/v2/open-source.svg?v=103)](https://github.com/ellerbrock/open-source-badges/)

## About
OAuth2 Exchange is a Java library that provides a bunch of functionalities for exchanging authorization code for an access token
in OAuth2 Authorization Code Flow. It consists of templates and ready-to-use implementations for most common authorization providers 
(like Google or Facebook). The mentioned templates make a proper HTTP call to issue an access token based on provided authorization code. 
Therefore, you can save your time as you do not need to search through plenty of API docs or to implement a custom HTTP client just to 
exchange a code for a token - the library do it under the hood via a single method call ;)

According to the [OAuth2 overview](https://www.digitalocean.com/community/tutorials/an-introduction-to-oauth-2) found on DigitalOcen, 
the library manages the step 4 (issuing a token from authorization code) and step 5 (receiving token response):

![OAuth2 Authorization Code Flow diagram](https://assets.digitalocean.com/articles/oauth/auth_code_flow.png)

Apart from the core library which can be used in any Java project, there is also a dedicated starter for Spring Boot provided. It wraps the core library
and automatically configures proper templates based on provided credentials via application properties. Therefore, you do not need to bother about declaring 
necessary beans and other related stuff in your Spring Boot application ;)

You can find out more about implementation details and how to get started with a library on [GitHub wiki pages](https://github.com/mzlnk/oauth2-exchange/wiki).

## Development

The whole project is currently in development, so you can expect plenty of updates over time. 

| type   | current version |
| ------ | --------------- |
| stable | -               |
| latest | 0.1.0-SNAPSHOT  |


### Supported auth providers

Currently, the oauth2-exchange library provides *ready-to-use* support for the following auth providers:
- [Facebook](https://developers.facebook.com/docs/facebook-login/manually-build-a-login-flow/)
- [GitHub](https://docs.github.com/en/developers/apps/building-oauth-apps/authorizing-oauth-apps)
- [Google](https://developers.google.com/identity/protocols/oauth2/web-server)
- [Keycloak](https://www.keycloak.org/docs/latest/server_admin/)
- [Microsoft](https://docs.microsoft.com/en-us/azure/active-directory/develop/v2-oauth2-auth-code-flow)
- [Okta](https://developer.okta.com/docs/reference/api/oidc/#_2-okta-as-the-identity-platform-for-your-app-or-api)

### See a bug?

Have noticed a bug in a library? Do not hesitate to report it by creating an GitHub issue!

### Want to contribute?

If you want to contribute to the source code:
- find an issue you want to work on (or create a new one if you want to create something new)
- fork the project
- implement the feature/fix (do not forget about test coverage!)
- create a pull request to `develop` branch

## How to install

### Standalone library

#### Using Maven

If you want to use the core library itself in your Maven project - just include the dependency in your `pom.xml` file:
```xml
<dependency>
    <groupId>io.mzlnk.oauth2.exchange</groupId>
    <artifactId>oauth2-exchange-core</artifactId>
    <version>0.1.0</version>
</dependency>
```

#### Using Gradle

If you want to use the core library itself in your Gradle project - just include the dependency `build.gradle` file:
```text
implementation 'io.mzlnk.oauth2.exchange:oauth2-exchange-core:0.1.0'
```

#### Using standalone JAR file

If you want to manually add the core library in your Java project - you can always download the proper JAR file from [Releases](https://github.com/mzlnk/oauth2-exchange/releases) page.


### Starter for Spring Boot

#### Using Maven

If you want to use the Spring Boot starter in your Maven project - just include the dependency in your `pom.xml` file:
```xml
<dependency>
    <groupId>io.mzlnk.oauth2.exchange</groupId>
    <artifactId>oauth2-exchange-spring-boot-starter</artifactId>
    <version>0.1.0</version>
</dependency>
```

#### Using Gradle

If you want to use the core library itself in your Gradle project - just include the dependency `build.gradle` file:
```text
implementation 'io.mzlnk.oauth2.exchange:oauth2-exchange-spring-boot-starter:0.1.0'
```

## Documentation

If you want to get to know how the whole library works and how to use it in your project - you can check out [GitHub wiki pages](https://github.com/mzlnk/oauth2-exchange/wiki)
which include guides and tutorials. There are also official [Java docs](https://www.javadoc.io/doc/io.mzlnk.oauth2.exchange/oauth2-exchange-core) for core library.

## Credits

This starter is under MIT licence so feel free to use it for your personal or commercial use ;)

Created by Marcin Zielonka
