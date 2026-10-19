package com.example.fleet.service;

import com.example.fleet.domain.Robot;
import com.example.fleet.domain.Task;
import com.example.fleet.domain.RobotStatus;
import com.example.fleet.domain.TaskStatus;
import com.example.fleet.repository.RobotRepository;
import com.example.fleet.repository.TaskRepository;
import com.example.fleet.service.RobotService;

import org.junit.jupiter.api.Test;
import jakarta.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@Transactional
public class RobotIntegrationTest{

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RobotRepository robotRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private RobotService robotService;

    @Test
    void assignTask_persistsToDatabase(){
        // Given
        Robot robot = new Robot("R-001", "TestRobot");
        robotRepository.save(robot);
        Task task = new Task("T-001", "ZONE_1", "ZONE_2");
        taskRepository.save(task);

        // When
        robotService.assignTask("R-001", "T-001");
        entityManager.flush(); // DB값에 변경값 반영
        entityManager.clear(); // 영속성 컨텍스트 초기화

        // Then
        Robot robot_test = robotRepository
            .findByNumber("R-001")
            .orElseThrow();

        Task task_test = taskRepository
            .findByNumber("T-001")
            .orElseThrow();

        assertEquals(
            RobotStatus.BUSY, 
            robot_test.getRobotStatus()
        );

        assertEquals(
            TaskStatus.ASSIGNED,
            task_test.getTaskStatus()
        );

        // Then_2
        assertEquals(task_test.getNumber(), robot_test.getTask().getNumber());
        assertEquals(robot_test.getNumber(), task_test.getRobot().getNumber());

    }

    @Test
    void unAssignTask_persistsToDatabase(){
        // Given
        Robot robot = new Robot("R-001", "TestRobot");
        Task task = new Task("T-001", "ZONE_1", "ZONE_2");

        robot = robotRepository.save(robot);
        task = taskRepository.save(task);
        robotService.assignTask("R-001", "T-001");
        entityManager.flush(); // DB에 변경값 반영

        assertEquals(RobotStatus.BUSY, robot.getRobotStatus());
        assertEquals(TaskStatus.ASSIGNED, task.getTaskStatus());

        // When
        entityManager.clear();
        robotService.unAssignTask("R-001");
        entityManager.flush();
        entityManager.clear();

        // Then
        robot = robotRepository
            .findByNumber("R-001")
            .orElseThrow();

        task = taskRepository
            .findByNumber("T-001")
            .orElseThrow();

        assertEquals(RobotStatus.IDLE, robot.getRobotStatus());
        assertEquals(TaskStatus.PENDING, task.getTaskStatus());

        assertNull(robot.getTask());
        assertNull(task.getRobot());
    }
}