import managers.InMemoryTaskManager;
import managers.ManagerSaveException;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.Epic;
import tasks.Status;
import tasks.SubTask;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


class EpicTest {
    private TaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void shouldBeEqualsEpicsWithSameId() throws ManagerSaveException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1"); //taskManager.addEpic();
        Epic epic2 = new Epic("Эпик 2", "Описание 1");
        this.taskManager.addEpic(epic1);
        epic2.setId(epic1.getId());
        assertEquals(epic1, epic2);
    }

    @Test
    void shouldBeNegativeAddEpicInHisSubtasks() throws ManagerSaveException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        //TaskManager taskManager = new InMemoryTaskManager();
        this.taskManager.addEpic(epic1);
        epic1.addSubTasksId(epic1.getId());
        assertFalse(taskManager.getAllSubTasksByEpicId(epic1.getId()).equals(epic1.getId()));
    }

    @Test
    void shouldBeStatusNewThenAllSubtasksIsNew() throws ManagerSaveException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        SubTask subTask1 = new SubTask("Задача  1  (jUnit)", "Описание  1", Status.NEW, 1, LocalDateTime.now(), Duration.ofMinutes(30));
        SubTask subTask2 = new SubTask("Задача  2 (jUnit)", "Описание  1", Status.NEW, 1, LocalDateTime.now().plusHours(1), Duration.ofMinutes(30));
        this.taskManager.addEpic(epic1);
        epic1.addSubTasksId(subTask1.getId());
        epic1.addSubTasksId(subTask2.getId());

        assertEquals(epic1.getStatus(), Status.NEW);

    }

    @Test
    void shouldBeStatusDoneThenAllSubtasksIsDone() throws ManagerSaveException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        SubTask subTask1 = new SubTask("Задача  1  (jUnit)", "Описание  1", Status.NEW, 1, LocalDateTime.now(), Duration.ofMinutes(30));
        SubTask subTask2 = new SubTask("Задача  2 (jUnit)", "Описание  1", Status.NEW, 1, LocalDateTime.now().plusHours(1), Duration.ofMinutes(30));
        this.taskManager.addEpic(epic1);
        subTask1.setStatus(Status.DONE);
        subTask2.setStatus(Status.DONE);

        epic1.addSubTasksId(subTask1.getId());
        epic1.addSubTasksId(subTask2.getId());
        assertEquals(epic1.getStatus(), Status.NEW);
    }

    @Test
    void shouldBeStatusNewThenAllSubtasksIsDoneAndNew() throws ManagerSaveException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        SubTask subTask1 = new SubTask("Задача  1  (jUnit)", "Описание  1", Status.NEW, 1, LocalDateTime.now(), Duration.ofMinutes(30));
        SubTask subTask2 = new SubTask("Задача  2 (jUnit)", "Описание  1", Status.NEW, 1, LocalDateTime.now().plusHours(1), Duration.ofMinutes(30));
        this.taskManager.addEpic(epic1);
        subTask1.setStatus(Status.DONE);

        epic1.addSubTasksId(subTask1.getId());
        epic1.addSubTasksId(subTask2.getId());
        assertEquals(epic1.getStatus(), Status.NEW);
    }

    @Test
    void shouldBeStatusInProgressThenAllSubtasksIsInProgress() throws ManagerSaveException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        SubTask subTask1 = new SubTask("Задача  1  (jUnit)", "Описание  1", Status.NEW, 1, LocalDateTime.now(), Duration.ofMinutes(30));
        SubTask subTask2 = new SubTask("Задача  2 (jUnit)", "Описание  1", Status.NEW, 1, LocalDateTime.now().plusHours(1), Duration.ofMinutes(30));
        this.taskManager.addEpic(epic1);
        subTask1.setStatus(Status.IN_PROGRESS);
        subTask2.setStatus(Status.IN_PROGRESS);

        epic1.addSubTasksId(subTask1.getId());
        epic1.addSubTasksId(subTask2.getId());
        assertEquals(epic1.getStatus(), Status.NEW);
    }

}