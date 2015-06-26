
# java.net.UnknownHostException: <hostname>: <hostname>: nodename nor servname provided, or not known
- 정확한 원인은 모름. 아래와 같이 /etc/hosts 에 넣어서 해결.

127.0.0.1   localhost localhost.localdomain localhost4 localhost4.localdomain4
127.0.1.1   <hostname>

http://stackoverflow.com/questions/19330334/hadoop-on-mac-pseudo-node-nodename-nor-servname-provided-or-not-known