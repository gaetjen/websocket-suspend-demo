## Demo project for bug on websockets with suspend on 4.5.0

test with suspending onOpen websocket method fails on master branch, but succeeds on success-earlier-micronautversio.

Culprit is combination of micronaut version and application property server.thread-selection