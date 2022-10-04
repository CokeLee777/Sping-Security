# SecurityContextPersistenceFilter

이 필터는 SecurityContext 객체의 생성, 저장, 조회하는 역할을 수행한다.

- 익명 사용자
	- 새로운 SecurityContext 객체를 생성하여 SecurityContextHolder에 저장한다.
	- `AnonymousAuthenticationFilter` 에서 AnonymousAuthenticationToken 객체를 **SecurityContext에 저장**한다.
- 인증 시
	- 새로운 SecurityContext 객체를 생성하여 SecurityContextHolder에 저장한다.
	- `UsernamePasswordAuthenticationFilter`에서 인증 성공 후 SecurityContext에서 `UsernamePasswordAuthentication` 객체를 **SecurityContext에 저장**한다.
	- **인증이 최종 완료되면 Session에 SecurityContext를 저장**한다.
- 인증 후
	- `Session` 에서 SecurityContext를 꺼내어 SecurityContextHolder에 저장한다.
	- **SecurityContext안에 Authentication 객체가 존재하면 계속 인증을 유지**한다.
- 최종 응답 시 공통
	- SecurityContextHolder.clearContext()

### 동작 흐름

![SecurityContextPersistenceFilter](./images/security_context_persistence_filter.pn)

1. 사용자가 인증 요청을 하면 SecurityContextPersistenceFilter가 동작한다.
2. 이미 인증된 사용자라면 세션에 저장되어있는 SecurityContext를 가져와서 SecurityContextHolder에 저장하고, 다음 필터로 넘어간다.
3. 인증이 필요한 사용자라면 새로운 SecurityContext를 생성해서 SecurityContextHolder에 저장하고, SecurityContext에 인증 객체를 넣는다.
4. 그리고 마지막으로 인증이 완료되면 세션에 SecurityContext를 저장하고, SecurityContext를 모두 지운다.