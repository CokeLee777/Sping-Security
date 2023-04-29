# 인증 API - 사용자 정의 보안 기능 구현

스프링이 기본으로 정의해주는 보안기능이 아닌 사용자 정의 보안기능을 구현하려면 다음과 같이 구현 클래스를 작성해야 한다. `@EnableWebSecurity` 가 붙은 스프링 부트 설정정보(Configuration) 클래스에서는 스프링 시큐리티를 정의할 수 있도록 허용해준다.

`securityFilterChain Bean`의 파라미터인 `HttpSecurity`는 세부적인 보안 기능을 설정할 수 있는 API를 제공한다.

따라서 HttpSecurity의 파라미터를 받아서 빌더패턴으로 세부적인 보안을 정의하고 반환으로 `HttpSecurity` 객체를 반환해주면 적용이 된다.

`HttpSecurity` 객체의 세부적인 설정 정보들에는 인증 및 인가 API를 모두 사용자 정의가 가능하다.

```java
@Configuration
@EnableWebSecurity
public class MyWebSecurityConfiguration {

      @Bean
      public WebSecurityCustomizer webSecurityCustomizer() {
              return (web) -> web.ignoring()
              // Spring Security should completely ignore URLs starting with /resources/
                              .requestMatchers("/resources/**");
      }

      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
              http.authorizeRequests().requestMatchers("/public/**").permitAll().anyRequest()
                              .hasRole("USER").and()
                              // Possibly more configuration ...
                              .formLogin() // enable form based log in
                              // set permitAll for all URLs associated with Form Login
                              .permitAll();
              return http.build();
      }

      @Bean
      public UserDetailsService userDetailsService() {
              UserDetails user = User.withDefaultPasswordEncoder()
                      .username("user")
                      .password("password")
                      .roles("USER")
                      .build();
              UserDetails admin = User.withDefaultPasswordEncoder()
                      .username("admin")
                      .password("password")
                      .roles("ADMIN", "USER")
                      .build();
              return new InMemoryUserDetailsManager(user, admin);
      }

      // Possibly more bean methods ...
}
```