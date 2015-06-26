# JSP 는 Spring boot 에서 사용하지 않길 권고함.
http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html#boot-features-spring-mvc-template-engines
여타의 템플릿 엔진들을 써보고 싶지만 spring-boot 에서 지원하는 것으로 결정해야 할 듯.
확실하지는 않지만 1.3.x 이상부터는 Mustache 도 지원하는 것 같음. 콧수염으로 결정!

# template cache
원래는 template cache 가 자동으로 설정되어있어 properties 에서 spring.thymeleaf.cache=false 를 설정해줘야했지만
1.3.x 이상부터 spring-boot-devtools 가 지원되면서 Quick Restart, LiveReload 및 Cache=false 등등을 지원한다.
java -jar 로 실행하면 "production application" 으로 고려되어 disabled 된다고 함. 
http://docs.spring.io/spring-boot/docs/current-SNAPSHOT/reference/htmlsingle/#using-boot-devtools
https://spring.io/blog/2015/06/17/devtools-in-spring-boot-1-3
