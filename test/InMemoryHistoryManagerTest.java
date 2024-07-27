import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
    void shouldSaveHistoryPrevEpicsGet () {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic1);
        taskManager.getEpicById(epic1.getId());
        taskManager.getEpicById(epic1.getId());
        assertEquals(taskManager.getHistory().size(),1);
    }

    @Test
    void shouldSaveHistoryPrevTasksGet() {
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW);
        taskManager.addTask(task1);
        taskManager.getTaskById(task1.getId());
        taskManager.getTaskById(task1.getId());
        assertEquals(taskManager.getHistory().size(),1);

    }

    @Test
    void shouldSaveHistoryPrevSubTasksGet() {
        Epic epic2 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic2);
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание 1",  Status.NEW, epic2.getId());
        taskManager.addSubTask(subTask1);
        taskManager.getSubTaskById(subTask1.getId());
        taskManager.getSubTaskById(subTask1.getId());
        assertEquals(taskManager.getHistory().size(),1);

    }

    @Test
    void add() {

        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW);
        historyManager.add(task1);
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }
    @Test
    void remove() {

        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW);
        historyManager.add(task1);
        historyManager.remove(task1.getId());
        final List<Task> history = historyManager.getHistory();
        assertEquals(0, history.size(), "История пустая.");
    }


}
//