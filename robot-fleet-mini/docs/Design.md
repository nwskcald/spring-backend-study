# 로봇 작업 할당 해제: 다음 구현 과정

## 1. 현재까지 수정된 부분

작업 해제 API가 `DELETE /robots/{robotNumber}/assignment`로 변경되었다. 할당 API는 PATCH이므로 두 API의 매핑 충돌은 해결되었다.

Service의 불필요한 작업 번호 확인과 작업 재조회도 제거되었다. 이제 로봇을 조회한 뒤 `robot.unAssign()`을 호출한다. 이미 로봇이 연결된 작업을 알고 있으므로 작업을 Repository에서 다시 찾을 필요가 없다.

아래 내용은 현재 코드를 읽고 정리한 **다음 구현 안내**다. 문서의 예제 코드는 아직 실제 Java 파일에 반영하지 않았다. 이번 문서 수정에서는 서버 실행이나 API 호출 검증을 수행하지 않았다.

## 2. 해제 기능이 완료되었을 때의 모습

예를 들어 R001 로봇에 T001 작업이 할당되어 있다고 하자.

| 항목 | 해제 전 | 해제 후 |
| --- | --- | --- |
| 로봇 상태 | BUSY | IDLE |
| 작업 상태 | ASSIGNED | PENDING |
| 로봇이 참조하는 작업 | T001 | null |
| 작업이 참조하는 로봇 | R001 | null |

해제는 로봇이나 작업을 삭제하는 기능이 아니다. 둘 사이의 연결을 끊고 다시 할당할 수 있는 상태로 돌려놓는 기능이다.

성공 응답에는 어떤 로봇과 작업이 해제되었는지 함께 담는다.

```json
{
  "robotNumber": "R001",
  "robotStatus": "IDLE",
  "taskNumber": "T001",
  "taskStatus": "PENDING"
}
```

현재처럼 응답 본문을 반환한다면 HTTP 상태 코드는 200을 사용하면 된다.

## 3. 먼저 Service에서 응답까지 만들기

현재 Controller는 다음 순서로 처리한다.

```text
로봇 조회 → 작업 보관 → Service에 해제 요청 → 응답 생성
```

이렇게 하면 Controller와 Service가 로봇을 각각 조회하게 된다. 또한 Controller가 보관한 작업 객체와 Service가 변경한 작업 객체가 항상 같은 객체라고 보장하기 어렵다. 상황에 따라 실제 DB에서는 해제됐지만 응답에는 이전 작업 상태가 남을 수 있다.

해결 방법은 조회, 해제, 응답에 필요한 값 확보를 Service의 한 메서드에 모으는 것이다. `@Transactional`은 이 메서드의 DB 변경을 한 묶음으로 처리한다. 중간에 런타임 예외가 발생하면 변경은 롤백된다.

현재 프로젝트에서는 Service가 기존 `UnAssignTaskResponse`를 만들어 반환하는 방식으로 시작하면 이해하기 쉽다.

```java
// RobotService에 UnAssignTaskResponse import 추가
@Transactional
public UnAssignTaskResponse unAssignTask(String robotNumber) {
    Robot robot = robotRepository.findByNumber(robotNumber)
        .orElseThrow(() -> new RobotNotFoundException());

    // 해제 후에는 robot.getTask()가 null이므로 미리 보관한다.
    Task task = robot.getTask();

    // 해제 가능 여부 확인과 상태 변경은 도메인이 담당한다.
    robot.unAssign();

    return UnAssignTaskResponse.from(robot, task);
}
```

`Task task = robot.getTask()`는 작업을 복사하는 코드가 아니다. 같은 작업 객체를 가리키는 참조를 지역 변수에 보관한다. 따라서 `robot.unAssign()`이 그 작업을 PENDING으로 바꾸면 `task`로도 변경된 상태를 읽을 수 있다. 이후 `robot.task`를 null로 바꾸더라도 지역 변수 `task`는 작업 객체를 계속 가리킨다.

작업이 없는 경우 지역 변수에 null을 담는 것 자체는 오류가 아니다. 중요한 것은 `task.getNumber()` 같은 호출보다 `robot.unAssign()`의 검증이 먼저 실행되어야 한다는 점이다.

Controller는 서비스 호출과 HTTP 응답 반환만 담당하도록 바꾼다.

```java
@DeleteMapping("/{robotNumber}/assignment")
public ResponseEntity<UnAssignTaskResponse> unAssignTask(
    @PathVariable String robotNumber
) {
    UnAssignTaskResponse response = robotService.unAssignTask(robotNumber);
    return ResponseEntity.ok(response);
}
```

이제 Controller에서 미리 작업을 조회하던 코드를 제거할 수 있다. Controller의 사용하지 않는 `TaskService` 필드와 import도 함께 정리하면 좋다.

나중에 서비스와 HTTP 응답 형식을 분리하고 싶다면 서비스 전용 결과 객체를 만들 수 있다. 지금은 위 방식으로 흐름을 완성한 뒤 확장해도 충분하다.

## 4. Robot에서 작업이 없는 경우도 확인하기

현재 `Robot.unAssign()`은 로봇 상태가 BUSY인지 먼저 검사한다. 따라서 정상적인 미할당 로봇(IDLE)은 이제 `RobotNotAssignedException`으로 처리된다. 이전 Service에서 발생하던 작업 번호 접근에 의한 null 오류는 제거된 상태다.

다만 로봇 상태는 BUSY인데 작업 참조가 null인 비정상 데이터가 있다면 여전히 null 오류가 발생할 수 있다. 작업의 메서드를 호출하기 전에 작업 존재 여부도 확인한다.

```java
public void unAssign() {
    if (!isRobotAvailableToUnAssign() || task == null) {
        throw new RobotNotAssignedException();
    }

    if (!task.isTaskAvailableToBeUnAssigned()) {
        throw new TaskNotAssignedException();
    }

    task.unAssign();
    robotStatus = RobotStatus.IDLE;
    this.task = null;
}
```

이 예제는 기존 예외를 활용하는 간단한 방식이다. BUSY인데 작업이 없는 상황이 실제로 발생한다면 응답 처리와 별개로, 어떤 코드가 잘못된 상태를 만들었는지도 조사해야 한다.

기존 `Task.unAssign()`의 역할은 유지한다.

```text
작업 상태를 PENDING으로 변경
작업의 robot 참조를 null로 변경
```

현재 매핑에서 DB의 로봇 외래 키를 관리하는 쪽은 `Task.robot`이다. 따라서 `Robot.task`뿐 아니라 `Task.robot`도 null로 만들어야 DB 연결까지 해제된다. 현재처럼 두 방향을 함께 정리하는 구조는 적절하다.

서비스에서 조회한 객체는 트랜잭션 안에서 JPA가 관리한다. JPA가 변경 사항을 감지해 DB에 반영하므로 이 흐름에서는 별도의 `save()`가 필요하지 않다. 이를 변경 감지라고 한다.

## 5. 해제 예외를 HTTP 응답으로 바꾸기

현재 `GlobalExceptionHandler`에는 해제 예외 전용 처리가 없다. 그래서 `RobotNotAssignedException`이나 `TaskNotAssignedException`이 발생하면 일반 예외 처리로 넘어가 500이 된다.

500은 서버 내부 오류를 나타낸다. 이미 미할당 상태여서 요청을 처리할 수 없는 경우에는 현재 상태와 요청이 충돌한다는 의미로 409를 반환하도록 정리하자.

아래 메서드를 기존 `GlobalExceptionHandler`에 추가한다. 두 예외는 핸들러와 같은 패키지에 있으므로 별도 import는 필요 없다.

```java
@ExceptionHandler(RobotNotAssignedException.class)
public ResponseEntity<ErrorResponse> robotNotAssignedError(
    RobotNotAssignedException e
) {
    ErrorResponse response = new ErrorResponse(
        "ROBOT_NOT_ASSIGNED", e.getMessage()
    );
    return ResponseEntity.status(409).body(response);
}

@ExceptionHandler(TaskNotAssignedException.class)
public ResponseEntity<ErrorResponse> taskNotAssignedError(
    TaskNotAssignedException e
) {
    ErrorResponse response = new ErrorResponse(
        "TASK_NOT_ASSIGNED", e.getMessage()
    );
    return ResponseEntity.status(409).body(response);
}
```

존재하지 않는 로봇은 기존 `RobotNotFoundException` 처리에 따라 404를 유지한다.

이 문서에서는 이미 해제된 로봇에 다시 해제를 요청하면 409를 반환하는 정책을 사용한다. 반복 요청을 성공으로 처리하는 정책도 가능하지만, 우선 기존 도메인 예외 흐름에 맞춰 구현하면 된다.

## 6. 수정 후 확인할 순서

먼저 서버가 시작되는지 확인하고, 로봇과 작업을 등록한 뒤 할당·해제 순서로 요청한다.

| 순서 | 확인할 상황 | 기대 결과 |
| --- | --- | --- |
| 1 | 서버 시작 | 할당·해제 매핑 충돌 없이 시작 |
| 2 | 할당된 로봇 해제 | 200, 응답의 로봇 IDLE·작업 PENDING |
| 3 | 해제 후 DB 재조회 | 로봇 IDLE, 작업 PENDING, 작업의 로봇 외래 키 null |
| 4 | 같은 로봇에 다시 해제 요청 | 409, ROBOT_NOT_ASSIGNED |
| 5 | 존재하지 않는 로봇 해제 | 404, ROBOT_NOT_FOUND |
| 6 | 해제한 작업을 다시 할당 | 정상 할당 가능 |

응답만 확인하면 DB 저장 여부를 놓칠 수 있다. 반드시 해제 요청이 끝난 뒤 다시 조회해서 확인한다. 자동화 테스트에서는 기존에 읽은 객체를 그대로 확인하지 말고, 영속성 컨텍스트를 비운 뒤 DB에서 다시 읽는 방식으로 검증한다.

도메인 테스트로는 정상 해제, 미할당 로봇, BUSY인데 작업이 없는 경우, 작업 상태가 ASSIGNED가 아닌 경우를 확인한다. 검증에 실패했을 때 기존 상태와 연결이 바뀌지 않는지도 함께 확인한다.

여유가 있다면 `spring.jpa.open-in-view=false` 설정에서도 확인한다. 이 설정은 웹 요청 동안 DB 객체 관리 범위를 길게 유지하는 기능을 끈다. 위에서 제안한 Service 방식은 트랜잭션 안에서 응답 값을 만들기 때문에 이 기능에 의존하지 않는다.

## 7. 기본 기능 완성 후 생각할 부분

현재는 Robot을 통해 Task를 변경하는 규칙으로 양쪽 연결을 맞추고 있다. 다른 코드가 `Task.unAssign()`만 직접 호출하면 작업만 해제되고 로봇 상태는 남을 수 있다. Task의 보조 변경 메서드를 같은 패키지 안에서만 호출하도록 접근 범위를 줄이는 방법 등을 검토할 수 있다.

또한 `@Transactional`이 있다고 해서 여러 요청이 자동으로 한 줄씩 처리되는 것은 아니다. 여러 사용자가 동시에 같은 로봇에 할당·해제를 요청해야 하는 단계에서는 `@Version`을 이용한 변경 충돌 감지나 DB 잠금을 추가로 학습하고 적용한다. 우선은 3~6번 과정을 통해 단일 요청의 성공과 실패 흐름을 완성한다.
