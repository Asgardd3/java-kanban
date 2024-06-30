import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {
    TaskManager taskManager;
    HistoryManager historyManager;
    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void shouldSaveHistoryPrevTasksStates () {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic1);

        //Добавляем в историю
        taskManager.getEpicById(epic1.getId());
        //Меняем название и описание эпика
        epic1.setName("Эпик 1 (new)");
        taskManager.getEpicById(epic1.getId());
        assertFalse(taskManager.getHistory().get(0).getName().equals(taskManager.getHistory().get(1).getName()));
        //Очищаем историю
        taskManager.clearHistory();

        //Добавляем в историю
        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW);
        taskManager.addTask(task1);
        taskManager.getTaskById(task1.getId());
        //Меняем название и описание эпика
        task1.setName("Задача 1 (new)");
        taskManager.getTaskById(task1.getId());
        assertFalse(taskManager.getHistory().get(0).getName().equals(taskManager.getHistory().get(1).getName()));
        //Очищаем историю
        taskManager.clearHistory();

        Epic epic2 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic2);
        SubTask subTask1 = new SubTask("Подзадача 1", "Описание 1",  Status.NEW, epic2.getId());
        taskManager.addSubTask(subTask1);
        taskManager.getSubTaskById(subTask1.getId());
        //Меняем название и описание эпика
        subTask1.setName("Подзадача 1 (new)");
        taskManager.getSubTaskById(subTask1.getId());
        assertFalse(taskManager.getHistory().get(0).getName().equals(taskManager.getHistory().get(1).getName()));
        //Очищаем историю
        taskManager.clearHistory();
    }

    @Test
    void add() {

        Task task1 = new Task("Задача 1", "Описание 1", Status.NEW);
        historyManager.add(task1);
        final ArrayList<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

}