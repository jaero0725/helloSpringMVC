
로그인을 해서, 제안을 남기는 간단한 로직을 스프링으로 구현.

#Spring MVC

#Spring WebForm
DataBinding, DataBuffering, DataValidation기능 

#Spring Security 5
1. filter 기능 
TestFilter  : url이 바뀔때 console에 찍어준다. 

2. Spring Authentication, Authorization 
- login, login error, logout
Show current     
-> 누구나 접근이 가능 
Add a new offer   
->  인증 받은 사람만 제안을 할 수 있음 -> 인증을 받지 않은 경우 로그인 창을 띄어준다.

/	   	permitAll
/offers	   	permitAll
/createoffer 	isAuthenticated()
/resources/** 	permitAll
/** 		denyAll
