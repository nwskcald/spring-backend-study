Key Business Logic
(1) 로봇 등록
(2) 작업 등록
(3) 로봇에게 작업 배정

Domain
(1) Robot
    - String number
    - String name
    - RobotStatus robotStatus(enum)
        - IDLE, BUSY
    - Task task(@OneToOne)

(2) Task
    - String number
    - String departure
    - String destination
    - TaskStatus taskStatus(enum)
        - PENDING, ASSIGNED
    - Robot robot(@OneToOne)


Service
(1) 로봇등록 - register(number, name)
    -> RobotRepository findByNumber해서 이미 등록된 로봇이 있나 조회
    -> RobotService 내부에서 Robot 객체 생성
        -> robotStatus = RobotStatus.IDLE 초기화
    -> 비영속 상태이므로 RobotRepository에 수동 저장

(2) 로봇조회 - findByNumber(number)
    -> RobotRepository에서 number로 robot 객체 조회
    -> Optional, orElseThrow 이용하여 리턴
    -> RobotService에서는 Robot타입으로 받는다

(3) 작업등록 - register(number, departure, destination)
    -> TaskService 내부에서 Task 객체 생성
        -> taskStatus = TaskStatus.PENDING 초기화
    -> 비영속 상태이므로 taskRepository에 수동 저장

(4) 작업 조회 - findByNumber(number)
    -> TaskRepository에서 number로 task 객체 조회
    -> Optional, orElseThrow 이용하여 리턴
    -> TaskService에서는 Task타입으로 받는다

(5) 로봇작업배정 - assignTask(number, number)
    -> RobotService에서 구현
    -> TaskRepository에서 findByNumber(number)
    -> RobotRepository에서 findByNumber(number)
    -> Domain호출
        -> Robot과 Task 내부 상태검사 (IDLE, PENDING)
    -> Domain 스스로 자기 상태변경
        -> robot.assignTask(task);
            -> 안에서 task 상태도변경
    -> Transactional로 묶고, DB저장은 Hibernate (영속상태)

DTO
(1) RobotRegisterRequest
(2) RobotResponse
    -> Robot전체 field 및 배정된 작업번호
(3) TaskRegisterRequest
(4) TaskResponse
    -> Task전체 field 및 배정된 로봇번호
(5) AssignTaskRequest
(6) ErrorResponse

touch src/main/java/com/example/fleet/dto/

Exception
(1) GlobalExceptionHandler
(2) RobotNotFoundException
(3) TaskNotFoundException
(4) DuplicateRobotException
(5) DuplicateTaskException
(6) RobotAlreadyAssignedException
(7) TaskAlreadyAssignedException

touch src/main/java/com/example/fleet/exception/


Controller
(1) RobotController
    - POST /robots (로봇등록)
        -> @RequestBody
    
    - GET /robots/R-001 (로봇 조회)
        -> @PathVariable

    - PATCH /robots/R-001/assignment (로봇 작업 배정)
        -> @PathVariable String robotId
        -> @RequestBody TaskRegisteRequest request

(2) TaskController
    - POST /tasks (작업등록)
        -> @RequestBody
    
    - GET /tasks/T-101 (작업 조회)
        -> @PathVariable
