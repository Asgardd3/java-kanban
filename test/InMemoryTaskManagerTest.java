import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest {
    private TaskManager taskManager;
    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void addTask() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.addTask(task);
        int taskId = task.getId();
        Task savedTask = taskManager.getTaskById(taskId);

        assertNotNull(savedTask, "������ �� �������.");
        assertEquals(task, savedTask, "������ �� ���������.");

        List<Task> tasks = taskManager.getAllTasks();

        assertNotNull(tasks, "������ �� ������������.");
        assertEquals(1, tasks.size(), "�������� ���������� �����.");
        assertEquals(task, tasks.get(0), "������ �� ���������.");
    }

    @Test
    void getTaskById() {
        Task task = new Task("Test addNewTask", "Test addNewTask description", Status.NEW);
        taskManager.addTask(task);
        assertNotNull(taskManager.getTaskById(task.getId()));
    }

    @Test
    void addSubTask() {
        Epic epic1 = new Epic("���� 1", "�������� 1");
        taskManager.addEpic(epic1);
        SubTask subTask = new SubTask("��������� 1", "�������� 1",  Status.NEW,epic1.getId());
        taskManager.addSubTask(subTask);
        int SubTaskId = subTask.getId();
        Task savedSubTask = taskManager.getSubTaskById(SubTaskId);

        assertNotNull(savedSubTask, "��������� �� �������.");
        assertEquals(subTask, savedSubTask, "��������� �� ���������.");

        List<SubTask> subTasks = taskManager.getAllSubTasks();

        assertNotNull(subTasks, "��������� �� ������������.");
        assertEquals(1, subTasks.size(), "�������� ���������� ��������.");
        assertEquals(subTask, subTasks.get(0), "��������� �� ���������.");
    }

    @Test
    void getSubTaskById() {
        Epic epic1 = new Epic("���� 1", "�������� 1");
        taskManager.addEpic(epic1);
        SubTask subTask = new SubTask("��������� 1", "�������� 1",  Status.NEW,epic1.getId());
        taskManager.addSubTask(subTask);
        assertNotNull(taskManager.getSubTaskById(subTask.getId()));
    }

    @Test
    void addEpic() {
        Epic epic1 = new Epic("���� 1", "�������� 1");
        taskManager.addEpic(epic1);
        int epicId = epic1.getId();
        Epic savedEpic = taskManager.getEpicById(epicId);

        assertNotNull(savedEpic, "���� �� ������.");
        assertEquals(epic1, savedEpic, "����� �� ���������.");

        List<Epic> epics = taskManager.getAllEpics();
        assertNotNull(epics, "��������� �� ������������.");
        assertEquals(1, epics.size(), "�������� ���������� ��������.");
        assertEquals(epic1, epics.get(0), "��������� �� ���������.");

    }

    @Test
    void getEpicById() {
        Epic epic = new Epic("���� 1", "�������� 1");
        taskManager.addEpic(epic);
        assertNotNull(taskManager.getEpicById(epic.getId()));
    }

    @Test
    void shouldBeIncrIdNumber()  {
        Epic epic1 = new Epic("���� 1", "�������� 1");
        taskManager.addEpic(epic1);
        assertEquals(epic1.getId(),1);
        Epic epic2 = new Epic("���� 2", "�������� 2");
        taskManager.addEpic(epic2);
        assertEquals(epic2.getId()-epic1.getId(),1);
        //������� ������
        Task task1 = new Task("������ 1", "�������� 1", Status.NEW);
        taskManager.addTask(task1);
        assertEquals(task1.getId()-epic2.getId(),1);
        Task task2 = new Task("������ 2", "�������� 2", Status.NEW);
        taskManager.addTask(task2);
        assertEquals(task2.getId()-task1.getId(),1);
        //������� ���������
        SubTask subTask1 = new SubTask("��������� 1", "�������� 1",  Status.NEW, 2);
        taskManager.addSubTask(subTask1);
        assertEquals(subTask1.getId()-task2.getId(),1);
        SubTask subTask2 = new SubTask("��������� 2", "�������� 2",  Status.NEW, 2);
        taskManager.addSubTask(subTask2);
        assertEquals(subTask2.getId()-subTask1.getId(),1);
    }

    @Test
    void shouldBeFieldsTasksNotChangedWhenCreate() {

        Epic epic1 = new Epic("���� 1", "�������� 1");
        taskManager.addEpic(epic1);
        assertTrue(epic1.getName().equals("���� 1") & epic1.getDescription().equals("�������� 1"));

        Task task1 = new Task("������ 1", "�������� 1", Status.NEW);
        taskManager.addTask(task1);
        assertTrue(task1.getName().equals("������ 1") & task1.getDescription().equals("�������� 1"));

        SubTask subTask1 = new SubTask("��������� 1", "�������� 1",  Status.NEW, 2);
        taskManager.addSubTask(subTask1);
        assertTrue(subTask1.getName().equals("��������� 1") & subTask1.getDescription().equals("�������� 1"));

    }
}