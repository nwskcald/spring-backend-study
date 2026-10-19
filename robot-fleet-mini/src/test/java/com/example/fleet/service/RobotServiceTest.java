package com.example.fleet.service;

import com.example.fleet.domain.Robot;
import com.example.fleet.domain.Task;
import com.example.fleet.domain.RobotStatus;
import com.example.fleet.domain.TaskStatus;
import com.example.fleet.repository.RobotRepository;
import com.example.fleet.repository.TaskRepository;
import com.example.fleet.exception.RobotAlreadyAssignedException;
import com.example.fleet.exception.RobotNotFoundException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
class RobotServiceTest{

    @Mock
    private RobotRepository robotRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private RobotService robotService;
    
    @Test
    void assignTask_robotBusy_throwsException(){
        // Given
        Robot robot = new Robot("R-001", "TestRobot");
        Task task = new Task("T-001", "ZONE_1", "ZONE_2");
        robot.assign(task);

        Task task_2 = new Task("T-002", "ZONE_7", "ZONE_8");

        when(robotRepository.findByNumber("R-001"))
            .thenReturn(Optional.of(robot));

        when(taskRepository.findByNumber("T-002"))
            .thenReturn(Optional.of(task_2));

        // When & Then
        assertThrows(
            RobotAlreadyAssignedException.class,
            () -> robotService.assignTask("R-001", "T-002")
        );

        assertEquals(RobotStatus.BUSY, robot.getRobotStatus());
        assertEquals(TaskStatus.ASSIGNED, task.getTaskStatus());
        assertEquals(TaskStatus.PENDING, task_2.getTaskStatus());
        assertSame(task, robot.getTask());
        assertSame(robot, task.getRobot());
        assertNull(task_2.getRobot());
    }

    @Test
    void assignTask_robotNotFound_throwsException(){
        // Given
        when(robotRepository.findByNumber("R-001"))
            .thenReturn(Optional.empty());

        // When & Then
        assertThrows(
            RobotNotFoundException.class,
            () -> robotService.assignTask("R-001", "T-001")
        );

        verifyNoInteractions(taskRepository);
    }
}