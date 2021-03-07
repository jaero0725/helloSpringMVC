# helloSpringMVC
Spring의 다양한 기능을 활용한 예제  
* * * 

### Spring MVC 기능
>

### Spring WebForm 기능
> DataBinding, DataBuffering, DataValidation기능

### Spring Security 5 기능
> 1. filter 기능 
>> TestFilter  : url이 바뀔때 console에 찍어준다. // 추후 log를 사용해서 사용

'''
//Servlet 앞단에서 처리해줌.
@WebFilter("/*")
public class TestFilter implements Filter {

    public TestFilter() {
    	
    }
	public void destroy() {
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//방문 url 찍는 기능 
		System.out.println(((HttpServletRequest) request).getRequestURL());
	
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}
}
'''
> 2. Spring Authentication, Authorization 
>> 메모리상, db상에서 회원에 대해서 인증하도록 설정
>> 
#### security-context.xml
'''
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://www.springframework.org/schema/security"
	xmlns:beans="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans
           http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
           http://www.springframework.org/schema/security
           http://www.springframework.org/schema/security/spring-security.xsd">
           
    <authentication-manager>
    	<!-- In Memory-->
		<!-- 
		<authentication-provider>
			<user-service>
				<user name="zeroco" authorities="ROLE_ADMIN"
					password="{noop}letmein" />
				<user name="alice" authorities="ROLE_USER"
					password="{noop}letmein" />
			</user-service>
		</authentication-provider> 
		-->
		
		<!-- In Database-->
		<authentication-provider>
			<jdbc-user-service data-source-ref="dataSource"
				users-by-username-query="select username, password, enabled from users where username =?" 
				authorities-by-username-query="select username, authority from authorities where username =?"/>
		</authentication-provider>
    </authentication-manager>


    <http auto-config="true" use-expressions="true">
		<intercept-url pattern="/" access="permitAll" />
		<intercept-url pattern="/login" access="permitAll" />
		<intercept-url pattern="/offers" access="permitAll" />
		<intercept-url pattern="/createoffer" access="hasRole('ROLE_USER')" />
		<intercept-url pattern="/resources/**" access="permitAll" />
		<intercept-url pattern="/**" access="denyAll" />
		
		<!-- login page -->
		<form-login login-page="/login"
			authentication-failure-url="/login?error" />
		<!-- logout page -->
		<logout/>
    </http>
</beans:beans>
'''

### Spring SLF4J, Logback 사용
> 기존의 스프링에서 자동으로 만들어주는 log4j를 활용하지 않고, Logback을 사용하여 로그 기능을 활용한다.
