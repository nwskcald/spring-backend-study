# Smart Locker Management System — Final Project Review

검증일: 2026-10-24 UTC. 현재 작업 트리의 구현을 대상으로 검증했다. 검증 시작 전에 존재하던 수정·미추적 파일은 그대로 보존했다. Java, 테스트, 설정, 빌드 파일 및 다른 Markdown은 수정하지 않았다. **import 추가·삭제도 없다.**

## 1. Executive Summary

**최종 결과: 빌드 및 기존 자동화 테스트 통과, 요청된 핵심 HTTP 기능 통과.** 현재 범위의 프로젝트는 정상적인 등록 → 이용 → 종료 → 재이용 흐름을 구현했다. 심각한 실행 장애나 핵심 기능 실패는 발견하지 못했다. 다만 테스트 통과가 모든 예외 분기와 트랜잭션 롤백의 검증을 의미하지는 않는다.

가장 좋은 부분은 도메인 메서드가 상태 변경을 통제하고, 서비스 트랜잭션에서 Locker와 Storage를 함께 변경하며, 통합 테스트가 flush/clear 이후 DB 상태를 재조회한다는 점이다. 종료된 기록도 새 이용 기록과 분리해 보존한다.

우선 개선 사항은 서비스 종료·조회·예외 흐름 및 MVC 계약의 자동화 테스트 보강이다. 오류 메시지에 프레임워크 내부 내용을 그대로 노출하는 점, 경로 타입 오류의 응답 형식 불일치도 개선할 수 있다. 현재 기능 완성도와 자동화 검증의 완성도는 구분해서 평가했다.

## 2. Build & Test Results

| 명령 | 실제 결과 | 테스트 전체 | 통과 | 실패 | 건너뜀 | import 변경 |
|---|---|---:|---:|---:|---:|---|
| `./gradlew clean test` | BUILD SUCCESSFUL, 39초 | 16 | 16 | 0 | 0 | 없음 |
| `./gradlew compileJava` | BUILD SUCCESSFUL, 5초, UP-TO-DATE | 0 (테스트 실행 태스크 아님) | 해당 없음 | 해당 없음 | 해당 없음 | 없음 |

첫 clean test 시도는 샌드박스에서 기본 Gradle 캐시의 `.zip.lck` 생성이 차단되어 종료 코드 1이었다. 소스 컴파일 실패가 아니라 실행 환경 권한 문제이며, 권한을 허용받아 동일 명령을 다시 실행해 성공했다. HTTP 클라이언트도 최초 로컬 소켓 접근이 차단되었으나 권한 허용 후 실행했다. Gradle의 JVM 클래스 공유 경고는 테스트 실패를 유발하지 않았다. 앱은 Java 21.0.12.1로 실행됐다.

근거: `build/test-results/test/TEST-*.xml`, `build/reports/tests/test/index.html` 및 명령 출력. 실패 테스트 이름과 코드 오류 재현 절차는 해당 없음.

### 테스트별 결과

모든 이름은 각 클래스의 메서드명이다.

| 클래스 | 테스트 | 결과 |
|---|---|---|
| DomainTest | lockerRegister_success | PASS |
| DomainTest | lockerOccupy_AVAILABLE_success | PASS |
| DomainTest | lockerOccupy_OCCUPIED_throwAlreadyOccupiedLockerException | PASS |
| DomainTest | storageEnd_ACTIVE_success | PASS |
| DomainTest | storageEnd_COMPLETED_throwEndCompletedStorageException | PASS |
| ServiceTest | lockerRegister_success | PASS |
| ServiceTest | lockerRegister_throwDuplicateLockerException | PASS |
| ServiceTest | lockerOccupy_success | PASS |
| ServiceTest | occupyLocker_throwLockerNotFoundException | PASS |
| IntegrationTest | lockerRegister_persistsToDataBase | PASS |
| IntegrationTest | getLockerList_success | PASS |
| IntegrationTest | lockerOccupy_persistsToDataBase | PASS |
| IntegrationTest | lockerTerminate_persistsToDataBase | PASS |
| IntegrationTest | storageCompleted_persistsToDataBase | PASS |
| IntegrationTest | occupyLockerOccupied_throwAlreadyOccupiedLockerException | PASS |
| SmartLockerSystemApplicationTests | contextLoads | PASS |

## 3. Functional Verification

실제 Controller, Service, Repository, Entity, DTO, ExceptionHandler를 모두 읽고, 아래 별도 메모리 DB로 앱을 실행해 실제 HTTP 요청을 보냈다. 설정 파일은 변경하지 않았다.

```bash
./gradlew bootRun --args='--server.port=18087 --spring.datasource.url=jdbc:h2:mem:review20261024;DB_CLOSE_DELAY=-1 --spring.jpa.hibernate.ddl-auto=create-drop'
```

요청은 `http://localhost:18087`로 보냈다. 등록 요청은 `{"number":"R-001","location":"1F","size":"MEDIUM"}`, 이용 요청은 `{"userId":"Peter"}`이다. **요청의 size와 응답의 lockerSize는 구분한다.** 공식 응답 필드 `lockerSize`, `lockerStatus`, `storageStatus`를 실제 확인했으며 필드명 차이는 결함이 아니다.

| 기능 | 결과 | 실제 근거 |
|---|---|---|
| 빈 Locker 목록 | PASS | 최초 GET /lockers → 200, [] |
| Locker 등록 | PASS | L 및 R 접두어의 002 → 001 → 003 순서 등록, 201, lockerSize=MEDIUM, lockerStatus=AVAILABLE |
| 중복 등록 방지 | PASS | 같은 번호 재등록 → 409, DUPLICATE_LOCKER |
| 목록 정렬 | PASS | GET /lockers → 200, L-001/002/003 및 R-001/002/003 문자열 오름차순 |
| 이용 시작 | PASS | POST /lockers/R-001/occupy → 201, storageStatus=ACTIVE, endTime=null; 별도 목록 GET에서 OCCUPIED |
| 중복 점유 방지 | PASS | 같은 Locker 재점유 → 409, LOCKER_OCCUPIED |
| 이용 종료 | PASS | PATCH /lockers/R-001/terminate → 200, COMPLETED 및 endTime; 별도 목록 GET에서 AVAILABLE |
| 잘못된 종료 | PASS | 반복 종료 및 미사용 R-002 종료 → 409, TERMINATE_AVAILABLE_LOCKER |
| Storage 조회 | PASS | GET /storages/3, /storages/4 → 200, 사용자와 상태 반환 |
| 없는 Storage | PASS | GET /storages/999999 → 404, STORAGE_NOT_FOUND |
| 없는 Locker | PASS | missing 번호 이용·종료 → 각각 404, LOCKER_NOT_FOUND |
| 종료 후 재이용 및 이력 보존 | PASS | Peter 기록 3 종료 후 James 기록 4 생성; 별도 GET에서 3=COMPLETED/Peter, 4=ACTIVE/James |
| 필수값·공백 검증 | PASS | 등록 {}, 공백 number/location, null size 및 이용 userId 누락/공백/null → 400, METHOD_ARGUMENT_NOT_VALID |
| 잘못된 Enum·JSON | PASS | size=HUGE, 미완성 JSON → 400, HTTP_MESSAGE_NOT_READABLE |
| 잘못된 경로 타입 | PASS (상태 코드) | GET /storages/abc → 400; 응답 스키마는 공통 ErrorResponse와 다름 |
| 불일치 데이터에서 종료 롤백 | NOT VERIFIED | 코드상 트랜잭션 롤백 근거는 있으나 DB 손상 상태를 구성한 실험은 하지 않음 |

정렬은 String 자연 순서다. 숫자 부분을 자연수로 해석하는 정렬은 명시된 계약이 아니므로 요구하지 않았다.

검증용 임시 스크립트: `/tmp/locker_review_http.py`, 완료된 29개 요청 결과: `/tmp/locker_review_http_results.json`. 첫 시도에서는 빈 목록부터 L 계열 흐름을 검증한 뒤 응답 전체 동일성 assertion이 실패했다. 원인은 endTime의 나노초 값이 H2에 저장되며 마이크로초로 반올림된 것이다. 이후 R 계열로 상태·사용자·기록 ID·종료 시각 존재를 검증해 완료했다. 이는 기존 프로젝트 테스트의 실패가 아니며, 원래 비교 실패와 정밀도 차이를 숨기지 않고 기록한다. 핵심 이력 보존은 확인했지만 시각 문자열의 완전 동일성은 보장되지 않는다.

## 4. Architecture Review

- Controller는 HTTP 입력, DTO 매핑, 상태 코드에 집중하고 서비스에 업무 처리를 위임한다. Entity를 그대로 JSON 응답으로 내보내지 않는다.
- Service는 Repository 조회, 도메인 호출 및 트랜잭션을 조정한다. 단순한 현재 범위에서 적절한 구조다.
- Domain은 AVAILABLE/OCCUPIED, ACTIVE/COMPLETED 전이를 메서드로 통제하며 상태 setter가 없다. 불법 전이 전에 예외를 던진다.
- Repository는 필요한 파생 조회를 제공한다. `LockerRepository<Locker,String>`은 Locker.number의 String과, `StorageRepository<Storage,Long>`은 Storage.recordNumber의 Long과 정확히 일치한다.
- DTO에는 @NotBlank/@NotNull 및 Controller @Valid가 있다. 실제 Spring MVC 검증·역직렬화 예외 import가 명시되어 있어 같은 패키지의 동명 사용자 예외에 잘못 연결되지 않는다. 실제 HTTP 400 응답으로 확인했다.
- 단방향 Storage → Locker ManyToOne과 별도 기록 ID는 이력 모델에 적절하다. enum을 문자열로 저장하고 protected 기본 생성자로 JPA를 지원한다.
- 생성자 자체는 null/공백 입력이나 Locker와 Storage 사이의 결합 상태를 검증하지 않는다. 현재 API는 DTO와 서비스 순서로 보호되지만 내부 호출자는 이를 우회할 수 있다. 현재 HTTP 흐름의 장애로 판정하지 않았다.
- Controller 주입 필드의 final 부재, wildcard import, 불필요한 동명 예외 클래스, 다소 장황한 단순 상태 변경 helper는 가독성 개선 수준이다. 구조적 리팩터링이 필수인 규모는 아니다.

## 5. Data Consistency & Transaction Review

LockerService.register/occupy/terminate는 쓰기 트랜잭션이다. occupy는 조회한 관리 Entity의 상태를 변경하고 Storage를 저장한다. terminate는 Locker를 AVAILABLE로 변경한 뒤 ACTIVE Storage를 찾아 COMPLETED와 종료 시각을 기록한다. 별도 Locker save 또는 종료 시 Storage save가 없어도 관리 Entity의 Dirty Checking으로 반영된다. flush/clear 통합 테스트와 별도 HTTP 요청의 재조회가 정상 영속화를 뒷받침한다.

모든 업무 예외는 RuntimeException이므로 기본 Spring 트랜잭션 규칙상 롤백 대상이다. 특히 Locker를 변경한 뒤 ActiveStorageNotFoundException이 나더라도 서비스 프록시를 거친 호출은 DB 변경을 롤백한다. 이 예외 분기의 실제 롤백은 별도 검증하지 않았으므로 PASS로 표시하지 않았다. Java 객체의 메모리 상태까지 자동 복구되는 것은 아니다.

조회 서비스의 readOnly=true는 적절하다. Storage의 Locker FK는 nullable=false이고 Locker 기본키가 중복 번호를 DB 수준에서 보호한다. 사용자·상태 등에는 명시적 not-null 제약이 부족해 API 외 쓰기 경로까지 보호하지는 않는다. 현재 정상 서비스 흐름에서 이로 인한 데이터 오염은 관찰하지 못했다.

기록을 종료 시 삭제하거나 재사용하지 않고 새 Storage를 생성한다. 재이용 HTTP 검증에서 과거 사용자와 완료 상태가 유지됐다. ManyToOne 기본 EAGER 로딩으로 현재 DTO 변환은 동작했으며, 현재 요구 범위에서 성능 개선을 필수로 요구하지 않는다.

동시 요청에 대한 잠금·버전 제어는 없으며 동시 점유는 검증하지 않았다. 향후 다중 사용자 운영에서 검토할 사항이고 이번 점수의 필수 기준에는 포함하지 않았다.

## 6. Automated Test Quality

### Domain Test

5개 테스트가 Locker 생성 필드/초기 상태, 정상 점유, 중복 점유 예외와 OCCUPIED 상태 보존, Storage 정상 종료와 startTime 보존/endTime 존재, 재종료 예외와 기존 endTime 보존을 검증한다. 의미 있는 assertion이다.

공백: Locker 정상 종료와 AVAILABLE 종료 예외, 해당 예외 이후 상태 보존, Storage 생성 직후 ACTIVE/startTime/endTime=null/연결 객체 검증, 종료 시각의 시간 범위, 재종료 이후 COMPLETED 상태 명시 확인. 생성자 불변식 검증이 필요한 범위도 정리하면 좋다.

### Service Test

MockitoExtension, @Mock, @InjectMocks 및 Optional stub은 실제 호출과 맞는다. 중복 등록과 없는 Locker에서 저장하지 않는 검증은 유용하다. 점유 테스트는 실제 도메인 객체 상태도 검증한다.

`register_success`는 save를 stub하지 않아 mock 기본 반환 null을 받지만 서비스 반환값을 확인하지 않는다. `save(any(Locker.class))`만 검증하므로 잘못된 번호/위치/크기 객체를 저장해도 통과할 수 있다. occupy도 Storage 사용자·Locker 연결·초기 상태·반환값을 확인하지 않아 잘못된 기록 저장을 놓칠 수 있다. exists/find verify는 흐름 근거로는 유효하지만 그 자체로 업무 결과를 보장하지 않는다. 종료, 중복 점유 저장 방지, 목록, StorageService 및 여러 예외 분기가 빠져 있다.

### Integration Test

6개는 실제 @SpringBootTest, H2, JPA Repository/EntityManager를 사용한다. mock DB가 아니다. @Transactional 기본 롤백이 같은 번호를 사용하는 테스트를 분리하며 이번 전체 실행도 통과했다. flush는 SQL 반영, clear는 1차 캐시 제거에 적절하게 사용됐다. 목록 테스트의 매 등록마다 flush/clear는 다소 반복적이지만 잘못되지는 않았다.

점유·종료 테스트는 DB 재조회로 Dirty Checking을 검증하고 재이용 테스트는 사용자별 과거/신규 기록을 확인한다. 단, 점유/종료 테스트는 Storage가 의도한 Locker와 사용자에 연결됐는지 확인하지 않는다. 재이용 테스트도 전체 기록 수, 각 FK, Locker 최종 상태, 과거 종료 시각 보존을 확인하지 않아 일부 데이터 정합성 오류를 놓칠 수 있다.

테스트 전체 트랜잭션에 서비스가 참여하므로 서비스 자체 @Transactional을 제거해도 통과할 가능성이 있다. 따라서 실제 서비스 트랜잭션 경계와 commit/rollback을 완전히 입증하지 않는다. 이번 실제 HTTP 요청은 정상 흐름의 독립 트랜잭션 영속화를 추가 확인했지만 자동화 회귀 테스트를 대체하지 않는다. 중복 점유 예외 후 조회도 같은 테스트 트랜잭션에서 이루어져 독립 트랜잭션 롤백 검증은 아니다.

contextLoads는 컨텍스트 시작만 확인한다. HTTP 상태·JSON 필드·검증 예외 계약의 기존 자동화 테스트는 없다. 테스트 개수보다 위 공백의 영향이 크다.

## 7. Findings

경로는 프로젝트 루트 기준이며 줄 번호는 검토 당시 기준이다. CRITICAL/HIGH 수준의 재현된 핵심 기능 결함은 없다.

### F1 — 저장 객체와 서비스 반환값 검증 부족

- 심각도: **MEDIUM**
- 위치: `src/test/java/com/example/locker/service/ServiceTest.java:36`, `:71`
- 원인: save(any(...)) 호출과 Locker 상태만 확인하고 저장 인자 및 반환 계약을 검증하지 않는다.
- 영향: 등록 속성이나 사용자/FK가 잘못되어도 테스트가 통과하는 False Positive 가능성이 있다.
- 근거/재현: register의 save stub 없이 반환값을 사용하지 않으며, occupy 테스트에도 Storage 필드 assertion이 없다. 구현 변형 실험은 하지 않았다.
- 권장 방향: ArgumentCaptor 등으로 번호/위치/크기와 Storage 사용자/연결/상태를 확인하고 저장 반환값 및 서비스 반환 계약을 검증한다.

### F2 — 핵심 예외·MVC·트랜잭션 회귀 테스트 공백

- 심각도: **MEDIUM**
- 위치: `src/test/java/com/example/locker/domain/DomainTest.java:10`, `service/ServiceTest.java:24`, `integration/IntegrationTest.java:23`
- 원인: Domain 종료 일부와 서비스 종료·조회 테스트가 없고, 통합 테스트가 바깥 테스트 트랜잭션에서 실행된다. MVC 테스트도 없다.
- 영향: 종료/저장 방지/400 응답 회귀나 서비스 트랜잭션 선언 누락을 자동화 검증에서 놓칠 수 있다.
- 근거/재현: 테스트 16개 전수 검토 및 위 6절. ActiveStorageNotFoundException 경로의 독립 롤백 테스트는 없다.
- 권장 방향: 빠진 상태 전이와 서비스 흐름, 실제 MVC 예외/응답 필드, 독립 트랜잭션의 commit/rollback 재조회 테스트를 보강한다. 이번 HTTP 검증은 현재 동작 근거이며 지속적 회귀 방어는 아니다.

### F3 — 검증 오류에 내부 예외 메시지를 그대로 반환

- 심각도: **LOW**
- 위치: `src/main/java/com/example/locker/exception/GlobalExceptionHandler.java:84`, `:94`
- 원인: e.getMessage()를 API message로 그대로 사용한다.
- 영향: 패키지/메서드 시그니처/검증 내부 코드가 노출되며 클라이언트가 읽기 어렵고 프레임워크 변화에 따라 메시지가 달라진다.
- 재현: POST /lockers에 {} → 400 METHOD_ARGUMENT_NOT_VALID와 긴 메서드 시그니처·Field error 메시지. size=HUGE에서도 Java enum 타입명이 반환된다.
- 권장 방향: 외부용 안정적 메시지 및 필드별 오류를 구성하고 상세 내부 예외는 서버 로그로 남긴다.

### F4 — 경로 타입 오류의 공통 오류 스키마 불일치

- 심각도: **LOW**
- 위치: `src/main/java/com/example/locker/controller/StorageController.java:27`, `exception/GlobalExceptionHandler.java:12`
- 원인: Long 경로 변환 실패에 대한 공통 핸들링이 없다.
- 영향: 클라이언트가 오류 응답을 하나의 code/message 계약으로 처리하기 어렵다. HTTP 400 자체는 정상이다.
- 재현: GET /storages/abc → {timestamp,status,error,path}; 없는 숫자 기록 → {code,message}.
- 권장 방향: 경로 타입 변환 오류도 공통 ErrorResponse로 매핑한다.

### F5 — 혼동을 주는 동명 예외와 종료 메시지

- 심각도: **LOW**
- 위치: `src/main/java/com/example/locker/exception/HttpMessageNotReadableException.java:6`, `MethodArgumentNotValidException.java:6`, `TerminateAvailableLockerException.java:6`
- 원인: 실제 Spring MVC 예외와 같은 이름의 미사용 사용자 예외가 존재하며 AVAILABLE 종료 메시지가 “이미 활성화된 Locker”라고 표현된다.
- 영향: import 변경 시 혼동과 API 사용자 오해 가능성. 현재 GlobalExceptionHandler는 명시적 Spring import를 사용하므로 400 처리 결함은 아니다.
- 재현: 미사용 클래스 참조 검토; 미사용 Locker 종료 → 409 및 위 메시지.
- 권장 방향: 동명 사용자 예외를 정리하고 종료 메시지를 “이용 중이 아닌 Locker” 등 실제 상태에 맞춘다.

### F6 — 생성/종료 직후와 재조회 시각 정밀도 차이

- 심각도: **LOW**
- 위치: `src/main/java/com/example/locker/domain/Storage.java:42`, `:74`, `dto/StorageResponse.java:36`
- 원인: LocalDateTime.now() 나노초 값이 응답에 사용되는 반면 관찰된 H2 저장값은 마이크로초 정밀도로 반올림된다.
- 영향: 같은 기록의 시각 문자열 전체 비교가 실패할 수 있다. 상태 전이와 이력 보존 실패는 아니다.
- 재현: 기록 3 종료 응답 endTime=`2026-10-24T14:40:22.455450828`, 이후 GET은 `2026-10-24T14:40:22.455451`. startTime에서도 같은 현상을 확인했다.
- 권장 방향: API 시간 정밀도 계약을 정하고 DB와 응답 정밀도를 맞춘다. 시간 검증은 계약에 맞는 허용 정밀도를 적용한다.

## 8. Scorecard

사용자 요청의 점수 항목 목록이 마지막 `-`에서 끝나 있어, 지정된 검토 범주를 기준으로 항목을 구성했다. 각 항목은 100점 만점이며 고급 기능 부재는 감점하지 않았다. 종합 점수는 아래 8개 항목의 단순 평균을 반올림한 것이다.

| 항목 | 점수 | 근거 |
|---|---:|---|
| 빌드·실행 가능성 | 100 | 두 명령 성공, 실제 앱 실행 성공 |
| 현재 기능·API 계약 | 95 | 핵심 정상/실패 HTTP 확인, 작은 오류 표현 문제 |
| 계층 분리·DTO 설계 | 90 | 역할 분리 명확, 동명 예외 및 입력 accessor 혼동 여지 |
| 도메인 상태·불변식 | 85 | 전이 방어 적절, 내부 생성자 입력·연결 상태 방어 제한 |
| JPA·트랜잭션·이력 정합성 | 90 | Dirty Checking 및 이력 확인, 예외 롤백 실험 미수행 |
| 입력 검증·예외 응답 | 85 | 실제 MVC 400/업무 409/404 정상, 메시지·스키마 개선 필요 |
| 자동화 테스트 품질 | 72 | DB 재조회 강점, 서비스 검증 깊이·예외·MVC·경계 공백 |
| 가독성·유지보수·범위 적절성 | 88 | 단순하고 요구 범위에 적절, 작은 중복·네이밍 정리 여지 |
| **종합** | **88** | **핵심 기능 완성, 회귀 테스트 보강 권장** |
