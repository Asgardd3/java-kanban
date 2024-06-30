import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EpicTest {
    TaskManager taskManager;
    TaskManager taskManager2;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
        taskManager2 = new InMemoryTaskManager();
    }

    @Test
    void shouldBeEqualsEpicsWithSameId() {
        Epic epic1 = new Epic("���� 1", "�������� 1"); //taskManager.addEpic();
        Epic epic2 = new Epic("���� 2", "�������� 1");
        this.taskManager.addEpic(epic1);
        this.taskManager2.addEpic(epic2);
        assertEquals(epic1, epic2);
    }

    @Test
    void shouldBeNegativeAddEpicInHisSubtasks () {
        Epic epic1 = new Epic("���� 1", "�������� 1");
        //TaskManager taskManager = new InMemoryTaskManager();
        this.taskManager.addEpic(epic1);
        epic1.addSubTasksId(epic1.getId());
        assertFalse(taskManager.getAllSubTasksByEpicId(epic1.getId()).equals(epic1.getId()));
    }
}

/*

���������, ��� ������ � �������� id � ��������������� id �� ����������� ������ ���������;
�������� ����, � ������� ����������� ������������ ������ (�� ���� �����) ��� ���������� ������ � ��������
���������, ��� ������, ����������� � HistoryManager, ��������� ���������� ������ ������ � � ������.

 */