package com.example.fleet.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class RobotDomainTest{

    @Test
    void assignTask_success(){    
        // Given
        Robot robot = new Robot("R-001", "TestRobot");
        Task task = new Task("T-001", "ZONE_1", "ZONE_2");

        // When
        robot.assign(task);

        // Then
        assertEquals(RobotStatus.BUSY, robot.getRobotStatus());
        assertEquals(TaskStatus.ASSIGNED, task.getTaskStatus());

        // Then_2
        assertSame(task, robot.getTask());
        assertSame(robot, task.getRobot());
    }

    @Test
    void unAssignTask_success(){
        // Given
        Robot robot = new Robot("R-001", "TestRobot");
        Task task = new Task("T-001", "ZONE_1", "ZONE_2");
        robot.assign(task);

        // When
        robot.unAssign();

        // Then
        assertEquals(RobotStatus.IDLE, robot.getRobotStatus());
        assertEquals(TaskStatus.PENDING, task.getTaskStatus());

        // Then_2
        assertNull(robot.getTask());
        assertNull(task.getRobot());
    }
}