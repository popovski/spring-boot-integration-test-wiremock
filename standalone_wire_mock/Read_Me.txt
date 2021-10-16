1. How to start wiremock as a standalone server
java -jar wiremock-jre8-standalone-2.31.0.jar --port 9999
-- use this flag to enable response templating so we can generate uuid or dates dynamically
java -jar wiremock-jre8-standalone-2.31.0.jar --port 9999 --global-response-templating


Getting started
http://wiremock.org/docs/getting-started/
http://wiremock.org/docs/running-standalone/ - Command line options

2. If we want the rest api to return respons from a file, then we add the file into the _files