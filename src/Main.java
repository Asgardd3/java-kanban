import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("�������!");
        TaskManager taskManager = new TaskManager();

        allTestData(taskManager);
        printAll(taskManager);

        System.out.println("�������� ������ �� �������������� � �������� ��");
        System.out.println(taskManager.getTaskById(3));
        System.out.println(taskManager.getSubTaskById(5));
        System.out.println(taskManager.getEpicById(1));

        System.out.println("��������� ������ �� �������������� � �������� ��");
        Task task1 = taskManager.getTaskById(3);
        task1.setName("������ 1_modify");
        taskManager.updateTask(task1);
        System.out.println(taskManager.getTaskById(3));

        Epic epic1 = taskManager.getEpicById(1);
        epic1.setName("���� 1_modify");
        taskManager.updateEpic(epic1);
        System.out.println(taskManager.getEpicById(1));

        SubTask subTask1 = taskManager.getSubTaskById(5);
        subTask1.setName("��������� 1_modify");
        taskManager.updateSubTask(subTask1);
        System.out.println(taskManager.getSubTaskById(5));

        //�������� �� ���������������
        System.out.println("������� �� ��������������");
        taskManager.deleteTaskById(3);
        taskManager.deleteSubTaskById(5);
        taskManager.deleteEpicById(1);
        printAll(taskManager);

        //��������� ��������� 3  � ���� 3

        //SubTask subtask3 = new SubTask("��������� 3","�������� 3",taskManager.getId());
        //taskManager("���� 3","�������� 3",taskManager.getId());
        //taskManager.addSubTaskToEpic(epic3,subtask3);


        //�� ������� � ���������
        System.out.println("������� ��� � �������� �������");
        deleteAll(taskManager);
        printAll(taskManager);

        //���� �� �������
        System.out.println("������ ��������� ���������� �� �������");
        System.out.println("��������� 2 ��������� � 1 ����");

        taskManager.addEpic(new Epic("���� 4", "�������� 4"));
        taskManager.addSubTask(new SubTask("��������� 4", "�������� 4", Status.NEW, 7));
        taskManager.addSubTask(new SubTask("��������� 5", "�������� 5", Status.NEW, 7));
        printAll(taskManager);
        //taskManager.addSubTaskToEpic(taskManager.getEpicById(9), taskManager.getSubTaskById(8));
        printAll(taskManager);

        System.out.println("������ � 4 ��������� �������� � ������");
        SubTask subTask2 = taskManager.getSubTaskById(8);
        subTask2.setName("��������� 4 (update)");
        subTask2.setDescription("�������� 4 (update)");
        subTask2.setStatus(Status.DONE);
        taskManager.updateSubTask(subTask2);
        printAll(taskManager);

        System.out.println("������ � 5 ��������� �������� � ������");
        SubTask subTask3 = taskManager.getSubTaskById(9);
        subTask3.setName("��������� 5 (update)");
        subTask3.setDescription("�������� 5 (update)");
        subTask3.setStatus(Status.DONE);
        taskManager.updateSubTask(subTask3);
        printAll(taskManager);

        ArrayList<SubTask> subTasks = taskManager.getAllSubTasksByEpicId(7);
        //��������� getAllSubTasksByEpicId

        //������� ������ �� ����� (DONE)
        System.out.println("������� ���� �� �������� (��������� 4)");
        taskManager.deleteSubTaskById(8);
        printAll(taskManager);
        System.out.println("������� ���� �� �������� (��������� 5)");
        taskManager.deleteSubTaskById(9);
        printAll(taskManager);
        System.out.println("������� ���� 4");
        taskManager.deleteEpicById(7);
        printAll(taskManager);

    }

    public static void printAll(TaskManager taskManager) {
        taskManager.printAllEpics();
        taskManager.printAllTasks();
        taskManager.printAllSubTasks();
    }

    public static void deleteAll(TaskManager taskManager) {
        taskManager.deleteAllTasks();
        //������� ��� ����� �� ������
        taskManager.deleteAllEpics();
        //������� ��� ��������� �� ������
        taskManager.deleteAllSubTasks();
    }

    public static void allTestData(TaskManager taskManager) {
        //������� �����
        taskManager.addEpic(new Epic("���� 1", "�������� 1"));
        taskManager.addEpic(new Epic("���� 1", "�������� 1"));
        //������� ������
        taskManager.addTask(new Task("������ 1", "�������� 1", Status.NEW));
        taskManager.addTask(new Task("������ 2", "�������� 2", Status.NEW));
        //������� ���������
        taskManager.addSubTask(new SubTask("��������� 1", "�������� 1",  Status.NEW, 2));
        taskManager.addSubTask(new SubTask("��������� 2", "�������� 2",  Status.NEW, 2));
    }


}