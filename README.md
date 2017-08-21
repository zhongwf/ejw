# EJW
Easy Java Web Framework built upon spring-boot, spring-framework and spring-security.

## FEATURES
- Fully customizable property management in accordance with spring-boot's [Externalized Configuration](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-external-config.html)
- Out-of-the-box i18n support with a LocaleChangeInterceptor, a CookieLocaleResolver, and customizable locales
- Message sources are served in JSON format via a cached endpoint to become javascript-friendly.
- A convenient starter project made with Maven Overlay
- Production-ready examples of common Web development scenarios, including:
    - Form validation

## TODO
- Transaction nesting
- profile-aware static resources (e.g.: react.min.js)
- remove url prefixes (/f/\*\*, /b/\*\*, /a/\*\*)
- filter test sources

##### Web Admin
- biz modules
  - security
  - sysconfig
  - properties?
- dev components
  - data table
  - search
  - sorting

##### MVC
- find a replacement for JSP?
- revamp ControllerResult

##### Logging
- multiple logging targets according to level/format
- async logging


