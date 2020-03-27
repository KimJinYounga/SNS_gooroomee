## WebSocket
websocket은 클라이언트와 서버간의 full-duplex(양방향), bi-direction(전이중적), persistent connection(지속적인 연결)의 특징을 갖는 프로토콜.

핵심 : 클라이언트와 서버간의 지속적인 연결을 한다는 점.

websocket이 나오기 전엔, Http Polling, Long Polling, Streaming을 사용하였음.
 -> 지속적으로 서버에 요청을 던지기 때문에 서버의 오버헤드 문제( + 중복적인 패킷전달로 인한 리소스 낭비 ).

==> websocket 프로토콜 탄생

    - TCP : binary 데이터만 주고받을 수 있음
    - Websocket : binary + text 데이터를 주고 받을 수 있음

## Spring Websocket
구현 방식(2가지)
- WebSocket 데이터 직접 처리
- Stomp 프로토콜을 사용하여 메세징 처리

## Stomp
simple text oriented messaging protocol, 텍스트 기반,  
메시징 전송을 효율적으로 하기 위해 나온 프로토콜.

기본적으로 pub/sub 구조로 되어있어 메세지를 발송하고 이를 받아 처리하는 부분이 확실히 정해져 있기 때문에  개발이 편리함.  
또한 통신 메시지의 헤더에 값을 세팅할 수 있어 이를 기반으로 인증처리 구현 가능

- 채팅방을 생성한다 - pub/sub 구현을 위한 Topic이 하나 생성된다.
- 채팅방에 입장한다 - Topic을 구독한다.
- 채팅방에서 메시지를 보내고 받는다 - 해당 Topic으로 메시지를 발송(pub)하거나 받는다(sub).

스프링 내부의 In Memory Broker를 통해 메세지를 처리

#### Broker
- 메세지 브로커는 Kafka, RabbitMQ, ActiveMQ 등의 오픈소스처럼 MQ이며, pub/sub모델을 따른다. 토픽에 따라 메세지를 전달해야 하는 사용자를 구분한다.
- 연결된 클라이언트의 세션을 관리한다.
- 특정 토픽과 메세지를 Mapping 하여, 토픽을 구독하는 세션에 존재하는 클라이언트에게 메세지를 전달한다.

## Problem
- 서버를 재시작 할때마다 채팅방 정보들이 리셋됨
- 채팅서버가 여러대이면 서버간 채팅방을 공유할 수 없음  

    => redis 사용
   