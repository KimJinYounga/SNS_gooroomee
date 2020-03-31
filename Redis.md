## Redis
- REmote DIctionary Server
- 대용량 처리 관련 기술

### Redis특징
1. 오픈 소스 소프트웨어
2. 디스크가 아닌 메모리 기반의 데이터 저장소(In-Memory data structure store)
3. NoSQL & Cache 솔루션
4. 영구적 보존 (disk에 저장하기 때문에 shut-down 후에도 데이터 불러올 수 있음)
5. 여러대 서버 구성 가능
6. Key/Value 저장소

> 인메모리 캐시(In-memory Cache)란?
- 서비스 요청이 증가하여 DB요청이 많아지면 DB 서버 부하 증가  
이 때 메모리 캐시 적용 -> 성능 및 처리속도 향상 => DB Read부하 감소

### 사용 가능한 데이터형
1. String
2. Lists
3. Sets
4. Sorted sets
5. Hashs