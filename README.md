# Spring Security 저장소

## **Spring Security**

**:book: Contents**
1. [스프링 시큐리티 기본 API 및 Filter 이해](#1-spring_security_api_filter)
2. [스프링 시큐리티 주요 아키텍처 이해](#2-spring_security_core_architecture)
3. [스프링 시큐리티 실습](#3-spring_security_src)

## 1. Spring Security Basic API & Filter
* [X] [사용자 정의 보안 기능](/)
* [X] [Form Login 인증](/)
* [X] [Form Login 인증 필터](/)
* [X] [Logout 처리, LogoutFilter](/)
* [X] [Remember Me 인증 & 인증 필터 : RememberMeAuthenticationFilter](/Chapter01/remember_me.md)
* [X] [익명 사용자 인증 필터 : AnonymousAuthenticationFilter](/Chapter01/anonymous.md)
* [X] [동시 세션 제어, 세션 고정 보호, 세션 정책](/Chapter01/concurrent_session_control.md)
* [X] [예외 처리 및 요청 캐시 필터 : ExceptionTranslationFilter, RequestCacheAwareFilter](/Chapter01/exception_translation_filter.md)
* [X] [사이트 간 요청 위조 - CSRF, CsrfFilter](/Chapter01/csrf_filter.md) 

## 2. Spring Security Core Architecture
* [X] [위임 필터 및 필터 빈 초기화 - DelegatingProxyChain, FilterChainProxy](/Chapter02/delegating_filter_proxy_and_filter_chain_proxy.md)
* [X] [필터 초기화와 다중 보안 설정](/Chapter02/multi_security_config_class.md)
* [X] [인증 개념 이해 - Authentication](/Chapter02/authentication.md)
* [X] [인증 저장소 - SecurityContextHolder, SecurityContext](/Chapter02/securitycontextholder_securitycontext.md)
* [X] [인증 저장소 필터 - SecurityContextPersistenceFilter](/Chapter02/SecurityContextPersistenceFilter.md)
* [X] [인증 관리자 - AuthenticationManager](/Chapter02/AuthenticationManager.md)
* [X] [인증 처리자 - AuthenticationProvider](/)
* [X] [인가 개념 및 필터 이해 : Authorization, FilterSecurityInterceptor](/)
* [X] [인가 결정 심의자 - AccessDecisionManager, AccessDecisionVoter](/)
* [X] [스프링 시큐리티 필터 및 아키텍처 정리](/)

## 3. Spring Security Practice

* [X] [기본 시큐리티 실습](/corespringsecurity)
* [X] [스프링 시큐리티 - JWT](/spring-jwt)

### 출처

- [스프링 시큐리티 - Spring boot 기반으로 개발하는 Spring Security](https://www.inflearn.com/course/코어-스프링-시큐리티/dashboard)
