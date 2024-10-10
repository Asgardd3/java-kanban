import managers.InMemoryTaskManager;
import managers.ManagerSaveException;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class TaskManagerTest<T extends TaskManager> {
    T taskManager;

    abstract void setManager();

    @BeforeEach
    void beforeEach() {
        setManager();
    }

    @Test
    void addTask() throws ManagerSaveException, TaskOverloadException {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addTask(task);
        } catch (TaskOverloadException e) {
            fail();
        }
        int taskId = task.getId();
        Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");

        List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "Задачи не возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void getTaskById() throws ManagerSaveException, TaskOverloadException {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addTask(task);
        } catch (TaskOverloadException e) {
            fail();
        }
        assertNotNull(taskManager.getTaskById(task.getId()));
    }

    @Test
    void addSubTask() throws ManagerSaveException, TaskOverloadException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic1);
        SubTask subTask = new SubTask("Подзадача 1", "Описание 1", Status.NEW, epic1.getId(), LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addSubTask(subTask);
        } catch (TaskOverloadException e) {
            fail();
        }

        int SubTaskId = subTask.getId();
        Task savedSubTask = taskManager.getSubTaskById(SubTaskId);

        assertNotNull(savedSubTask, "Подзадача не найдена.");
        assertEquals(subTask, savedSubTask, "Подзадачи не совпадают.");

        List<SubTask> subTasks = taskManager.getAllSubTasks();

        assertNotNull(subTasks, "Подзадачи не возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество подзадач.");
        assertEquals(subTask, subTasks.get(0), "Подзадачи не совпадают.");
    }

    @Test
    void getSubTaskById() throws ManagerSaveException, TaskOverloadException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic1);
        SubTask subTask = new SubTask("Подзадача 1", "Описание 1", Status.NEW, epic1.getId(), LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addSubTask(subTask);
        } catch (TaskOverloadException e) {
            fail();
        }
        assertNotNull(taskManager.getSubTaskById(subTask.getId()));
    }

    @Test
    void addEpic() throws ManagerSaveException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic1);
        int epicId = epic1.getId();
        Epic savedEpic = taskManager.getEpicById(epicId);

        assertNotNull(savedEpic, "Эпик не найден.");
        assertEquals(epic1, savedEpic, "Эпики не совпадают.");

        List<Epic> epics = taskManager.getAllEpics();
        assertNotNull(epics, "Подзадачи не возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество подзадач.");
        assertEquals(epic1, epics.get(0), "Подзадачи не совпадают.");

    }

    @Test
    void getEpicById() throws ManagerSaveException {
        Epic epic = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic);
        assertNotNull(taskManager.getEpicById(epic.getId()));
    }

    @Test
    void shouldBeIncrIdNumber() throws ManagerSaveException, TaskOverloadException {
        try {
            Epic epic1 = new Epic("Эпик 1", "Описание 1");
            taskManager.addEpic(epic1);
            assertEquals(epic1.getId(), 1);
            Epic epic2 = new Epic("Эпик 2", "Описание 2");
            taskManager.addEpic(epic2);
            assertEquals(epic2.getId() - epic1.getId(), 1);
            //Создаем задачи
            Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now().plusHours(1), Duration.ofMinutes(30));
            taskManager.addTask(task1);
            assertEquals(task1.getId() - epic2.getId(), 1);
            Task task2 = new Task("Задача 2", "Описание 2", Status.NEW, LocalDateTime.now().plusHours(2), Duration.ofMinutes(30));
            taskManager.addTask(task2);
            assertEquals(task2.getId() - task1.getId(), 1);
            //Создаем подзадачи
            SubTask subTask1 = new SubTask("Подзадача 1", "Описание 1", Status.NEW, 2, LocalDateTime.now().plusHours(3), Duration.ofMinutes(30));
            taskManager.addSubTask(subTask1);
            assertEquals(subTask1.getId() - task2.getId(), 1);
            SubTask subTask2 = new SubTask("Подзадача 2", "Описание 2", Status.NEW, 2, LocalDateTime.now().plusHours(4), Duration.ofMinutes(30));
            taskManager.addSubTask(subTask2);
            assertEquals(subTask2.getId() - subTask1.getId(), 1);
        } catch (TaskOverloadException e) {
            fail();
        }

    }

    @Test
    void shouldBeFieldsTasksNotChangedWhenCreate() throws ManagerSaveException, TaskOverloadException {
        try {
            Epic epic1 = new Epic("Эпик 1", "Описание 1");
            taskManager.addEpic(epic1);
            assertTrue(epic1.getName().equals("Эпик 1") & epic1.getDescription().equals("Описание 1"));

            Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
            taskManager.addTask(task1);
            assertTrue(task1.getName().equals("Задача 1") & task1.getDescription().equals("Описание 1"));

            SubTask subTask1 = new SubTask("Подзадача 1", "Описание 1", Status.NEW, 2, LocalDateTime.now(), Duration.ofMinutes(30));
            taskManager.addSubTask(subTask1);
            assertTrue(subTask1.getName().equals("Подзадача 1") & subTask1.getDescription().equals("Описание 1"));
        } catch (TaskOverloadException e) {
            fail();
        }


    }

    //Удаляемые подзадачи не должны хранить внутри себя старые id.
    @Test
    void shouldSubtasksNotHaveOldId() throws ManagerSaveException, TaskOverloadException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic1);
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание 1", Status.NEW, epic1.getId(), LocalDateTime.now(), Duration.ofMinutes(30));

        try {
            taskManager.addSubTask(subTask1);
        } catch (TaskOverloadException e) {
            fail();
        }
        taskManager.deleteEpicById(epic1.getId());
        assertEquals(taskManager.getAllSubTasks().size(), 0);

    }

    @Test
    void shouldEpicsNotHaveUnusableId() throws ManagerSaveException, TaskOverloadException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic1);
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание 1", Status.NEW, epic1.getId(), LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addSubTask(subTask1);
        } catch (TaskOverloadException e) {
            fail();
        }
        taskManager.deleteSubTaskById(subTask1.getId());
        assertEquals(taskManager.getEpicById(epic1.getId()).getSubTasksIds().size(), 0);
    }

    @Test
    void shouldTasksChanged() throws ManagerSaveException, TaskOverloadException {
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addTask(task1);
        } catch (TaskOverloadException e) {
            fail();
        }
        task1.setId(10);
        assertEquals(task1.getId(), 10);
        task1.setName("Новое имя");
        assertEquals(task1.getName(), "Новое имя");
        task1.setDescription("Новое описание");
        assertEquals(task1.getDescription(), "Новое описание");
        task1.setStatus(Status.DONE);
        assertEquals(task1.getStatus(), Status.DONE);
    }

    @Test
    void shouldSubTasksHaveEpic() throws ManagerSaveException, TaskOverloadException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic1);
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание 1", Status.NEW, epic1.getId(), LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addSubTask(subTask1);
        } catch (TaskOverloadException e) {
            fail();
        }
        assertEquals(taskManager.getSubTaskById(subTask1.getId()).getEpicId(), epic1.getId());
    }

    @Test
    void shouldSubtasksNotOverlap() throws ManagerSaveException, TaskOverloadException {
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание 1", Status.NEW, 1, LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addSubTask(subTask1);
        } catch (TaskOverloadException e) {
            fail();
        }
        SubTask subTask2 = new SubTask("Подзадача 2", "Описание 2", Status.NEW, 1, LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addSubTask(subTask2);
        } catch (TaskOverloadException e) {
            fail();
        }
        assertEquals(taskManager.getAllSubTasks().size(), 1);
    }


}