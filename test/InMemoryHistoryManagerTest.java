import managers.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    private TaskManager taskManager;
    private HistoryManager historyManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void shouldSaveHistoryPrevEpicsGet() throws ManagerSaveException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic1);
        taskManager.getEpicById(epic1.getId());
        taskManager.getEpicById(epic1.getId());
        assertEquals(taskManager.getHistory().size(), 1);
    }

    @Test
    void shouldSaveHistoryPrevTasksGet() throws ManagerSaveException, TaskOverloadException {
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addTask(task1);
        } catch (TaskOverloadException e) {
            //System.out.println("");
            fail();
        }
        //taskManager.addTask(task1);
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task1.getId());
        assertEquals(taskManager.getHistory().size(), 1);

    }

    @Test
    void shouldSaveHistoryPrevSubTasksGet() throws ManagerSaveException, TaskOverloadException {
        Epic epic2 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic2);
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание 1", Status.NEW, epic2.getId(), LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addSubTask(subTask1);
        } catch (
                TaskOverloadException e) {
            fail();
        }

        taskManager.getSubTaskById(subTask1.getId());
        taskManager.getSubTaskById(subTask1.getId());
        assertEquals(taskManager.getHistory().size(), 1);

    }

    @Test
    void add() {

        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void remove() {

        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        historyManager.add(task1);
        historyManager.remove(task1.getId());
        final List<Task> history = historyManager.getHistory();
        assertEquals(0, history.size(), "История пустая.");
    }

    @Test
    void getHistory() {

        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void shouldBeEmptyHistory() {
        final List<Task> history = historyManager.getHistory();
        assertEquals(0, history.size(), "История пустая.");
    }

    @Test
    void shouldBeDuplicateHistory() {
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        historyManager.add(task1);
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size());
    }

    @Test
    void deleteFirstTaskFromHistoryWithThreeTasks() throws TaskOverloadException, ManagerSaveException {
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        Task task2 = new Task("Задача 2", "Описание 2", Status.NEW, LocalDateTime.now().plusHours(1), Duration.ofMinutes(30));
        Task task3 = new Task("Задача 3", "Описание 3", Status.NEW, LocalDateTime.now().plusHours(2), Duration.ofMinutes(30));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task1.getId());
        final List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
    }

    @Test
    void deleteSecondTaskFromHistoryWithThreeTasks() throws TaskOverloadException, ManagerSaveException {
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        Task task2 = new Task("Задача 2", "Описание 2", Status.NEW, LocalDateTime.now().plusHours(1), Duration.ofMinutes(30));
        Task task3 = new Task("Задача 3", "Описание 3", Status.NEW, LocalDateTime.now().plusHours(2), Duration.ofMinutes(30));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task2.getId());
        final List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
    }

    @Test
    void deleteThirdTaskFromHistoryWithThreeTasks() throws TaskOverloadException, ManagerSaveException {
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        Task task2 = new Task("Задача 2", "Описание 2", Status.NEW, LocalDateTime.now().plusHours(1), Duration.ofMinutes(30));
        Task task3 = new Task("Задача 3", "Описание 3", Status.NEW, LocalDateTime.now().plusHours(2), Duration.ofMinutes(30));
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(task3.getId());
        final List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size());
    }
}
//