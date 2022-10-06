# AccessDecisionManager

- 인증 정보, 요청 정보, 권한 정보를 이용해서 사용자의 자원접근을 허용할 것인지 거부할 것인지를 최종 결정하는 주체이다.
- 여러 개의 Voter들을 가질 수 있으며 Voter 들로 부터 접근을 허용, 거부, 보류에 해당하는 각각의 값을 리턴받고 판단 및 결정한다.
- 최종 접근 거부 시 예외가 발생한다.

### 접근 결정의 세가지 유형

- AffirmativeBased: 여러 개의 Voter 클래스 중 **하나라도 접근 허가**로 결론을 내면 접근 허가로 판단한다.
- ConsensusBased
	- **다수표**(승인 및 거부)에 의해 최종 결정을 판단한다.
	- 동수일 경우 기본을 접근 허가이나 allowIfEqualGrantedDeniedDecisions를 false로 설정할 경우 접근 거부로 결정된다.
- UnanimousBased: 모든 Voter가 **만장일치**로 접근을 승인해야 하며, 그렇지 않은 경우 접근을 거부한다.

# AccessDecisionVoter

판단을 심사하는 것이다.

- Voter가 권한 부여 과정에서 판단하는 자료
	- Authentication - 인증 정보(User)
	- FilterInvocation - 요청 정보(antMatcher("/user"))
	- ConfigAttributes - 권한 정보(hasRole("USER"))
- 결정 방식
	- ACCESS_GRANTED: 접근 허용(1)
	- ACCESS_DENIED: 접근 거부(0)
	- ACCESS_ABSTAIN: 접근 보류(-1)