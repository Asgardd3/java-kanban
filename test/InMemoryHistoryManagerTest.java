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
        Epic epic1 = new Epic("���� 1", "�������� 1");
        taskManager.addEpic(epic1);

        //��������� � �������
        taskManager.getEpicById(epic1.getId());
        //������ �������� � �������� �����
        epic1.setName("���� 1 (new)");
        taskManager.getEpicById(epic1.getId());
        assertFalse(taskManager.getHistory().get(0).getName().equals(taskManager.getHistory().get(1).getName()));
        //������� �������
        taskManager.clearHistory();

        //��������� � �������
        Task task1 = new Task("������ 1", "�������� 1", Status.NEW);
        taskManager.addTask(task1);
        taskManager.getTaskById(task1.getId());
        //������ �������� � �������� �����
        task1.setName("������ 1 (new)");
        taskManager.getTaskById(task1.getId());
        assertFalse(taskManager.getHistory().get(0).getName().equals(taskManager.getHistory().get(1).getName()));
        //������� �������
        taskManager.clearHistory();

        Epic epic2 = new Epic("���� 1", "�������� 1");
        taskManager.addEpic(epic2);
        SubTask subTask1 = new SubTask("��������� 1", "�������� 1",  Status.NEW, epic2.getId());
        taskManager.addSubTask(subTask1);
        taskManager.getSubTaskById(subTask1.getId());
        //������ �������� � �������� �����
        subTask1.setName("��������� 1 (new)");
        taskManager.getSubTaskById(subTask1.getId());
        assertFalse(taskManager.getHistory().get(0).getName().equals(taskManager.getHistory().get(1).getName()));
        //������� �������
        taskManager.clearHistory();
    }

    @Test
    void add() {

        Task task1 = new Task("������ 1", "�������� 1", Status.NEW);
        historyManager.add(task1);
        final ArrayList<Task> history = historyManager.getHistory();
        assertNotNull(history, "������� �� ������.");
        assertEquals(1, history.size(), "������� �� ������.");
    }

}