# helloSpringMVC
Spring의 다양한 기능을 활용한 예제  
* * * 

### Spring MVC 기능
#### pom.xml 추가
~~~
		<!-- Spring MVC -->
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-webmvc</artifactId>
			<version>${org.springframework-version}</version>
		</dependency>
~~~
### Spring WebForm 기능
> DataBinding, DataBuffering, DataValidation기능

### Spring Security 5 기능
#### pom.xml 추가
~~~
		<!-- Spring Security 5 / core, web, config -->
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-core</artifactId>
			<version>${spring-security-version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-web</artifactId>
			<version>${spring-security-version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework.security</groupId>
			<artifactId>spring-security-config</artifactId>
			<version>${spring-security-version}</version>
		</dependency>
~~~
#### web.xml 추가
~~~
	<!-- Spring security - DelegatingFilterProxy  -->
	<filter>
		<filter-name>springSecurityFilterChain</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>springSecurityFilterChain</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
~~~
> 1. filter 기능 
>> TestFilter  : url이 바뀔때 console에 찍어준다. // 추후 log를 사용해서 사용   
<pre><code>
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
</code></pre>

> 2. Spring Authentication, Authorization 
>> 메모리상, db상에서 회원에 대해서 인증하도록 설정
>> 
#### security-context.xml      
~~~
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
~~~

### Spring SLF4J, Logback 사용
> 기존의 스프링에서 자동으로 만들어주는 log4j를 활용하지 않고, Logback을 사용하여 로그 기능을 활용한다.   
> 단순히 ip주소와, 홈페이지 url에 대해서만 찍어주도록 하였다.
#### pom.xml 수정
~~~
		<!-- Logging -->
			<!-- SLF4j -->
			<dependency>
				<groupId>org.slf4j</groupId>
				<artifactId>slf4j-api</artifactId>
				<version>${org.slf4j-version}</version>
			</dependency>
			<!-- 브리시 역할 -->
			<dependency>
				<groupId>org.slf4j</groupId>
				<artifactId>jcl-over-slf4j</artifactId>
				<version>${org.slf4j-version}</version>
				<scope>runtime</scope>
			</dependency>
			
			<!-- log4j를  logback으로 변경 -->
			<!-- logback-classic만 넣어주면, core도 들어감 -->
			<dependency>
				<groupId>ch.qos.logback</groupId>
				<artifactId>logback-classic</artifactId>
				<version>1.2.3</version>
				<scope>runtime</scope>
			</dependency>   
~~~

#### /helloSpringMVC/src/main/resources/logback.xml 추가    <br>
> consoleAppender, RollingFileAppener를 사용 

~~~
<?xml version="1.0" encoding="UTF-8"?>
    <configuration>
    	<!-- Appender -->
    	<!-- console Appender -->
    	<appender name="consoleAppender" class="ch.qos.logback.core.ConsoleAppender">
    		<encoder>
    			<Pattern>.%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg %n
    			</Pattern>
    		</encoder>
    		<filter class="ch.qos.logback.classic.filter.ThresholdFilter">
    			<level>TRACE</level>
    		</filter>
    	</appender>
    	
		<!-- dailyRollingFile Appender / 일별 단위로 rolling -->
      	<appender name="dailyRollingFileAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
    		<File>e:/tmp/rest-demo.log</File>
    		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
    		    <!-- daily rollover -->
    			<FileNamePattern>rest-demo.%d{yyyy-MM-dd}.log</FileNamePattern>

    			<!-- keep 30 days' worth of history -->
    			<!-- 30일 까지 유지한다. -->
    			<maxHistory>30</maxHistory>     		
    		</rollingPolicy>
    		<encoder>
    			<Pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{35} - %msg %n</Pattern>
    		</encoder>
      	</appender>
      	
      	<!-- minuteRollingFile Appender  / 분 단위로 rolling-->
      	<appender name="minuteRollingFileAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
    		<rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
    		    <!-- rollover every minute -->
    			<FileNamePattern>e:/tmp/minutes/rest-demo-minute.%d{yyyy-MM-dd_HH-mm}.log</FileNamePattern>

    			<!-- keep 30 minutes' worth of history -->
    			<maxHistory>30</maxHistory>
    		</rollingPolicy>

    		<encoder>
    			<Pattern>%-4relative [%thread] %-5level %logger{35} - %msg %n</Pattern>
    		</encoder>
      	</appender>
		
    	<logger name="kr.ac.hansung" additivity="false"><!-- additivity="false" 상속받지 않겠다. -->
    	    <level value="DEBUG" />
    		<appender-ref ref="dailyRollingFileAppender"/>
    		<appender-ref ref="minuteRollingFileAppender"/>
    		<appender-ref ref="consoleAppender" />
    	</logger>

    	<root>
    		<level value="INFO" />
    		<appender-ref ref="consoleAppender" />
    	</root>
    </configuration>
~~~

#### HomeController에서 로그 사용
~~~
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	// private static final Logger logger =
	// LoggerFactory.getLogger("kr.ac.hansung.controller.HomeController.java");

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {

		// Trace -> Debug -> Info -> Warn -> Error
		String url = request.getRequestURI().toString(); // url 찍기
		String clientIPaddr = request.getRemoteAddr(); // client ip 주소 찍기

		logger.info("Request URL : {}, Client IP: {}", url, clientIPaddr);
		
		return "home";
	}

}
~~~
