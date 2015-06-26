
# java.net.UnknownHostException: <hostname>: <hostname>: nodename nor servname provided, or not known
- 정확한 원인은 모름. 아래와 같이 /etc/hosts 에 넣어서 해결.

127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4
127.0.1.1   <hostname>

http://stackoverflow.com/questions/19330334/hadoop-on-mac-pseudo-node-nodename-nor-servname-provided-or-not-known


# IntelliJ 14.1 이하에서 spring-boot-devtools 의 reload 가 먹지 않는 문제
14.2 이상을 사용해야하는듯. 이 기능은 인텔리J Stable 업데이트 되면 사용해보자.  
https://youtrack.jetbrains.com/issue/IDEA-141638


# mustache 에서 한글이 안됨.
이유는 모르겠음. mustache 에서만 안됨.
중국인도 문제를 겪는 듯. 좀 시간이 지난 다음에나 써봐야할 것 같다.
http://stackoverflow.com/questions/30634661/spring-boot-mustache-doesnt-render-utf-8-correctly