import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("�������!");
        TaskManager inMemoryTaskManager = Managers.getDefault();


        // ������ ���������
    /*
        allTestData(inMemoryTaskManager);
     //   printAll(inMemoryTaskManager);

        System.out.println("�������� ������ �� �������������� � �������� ��");
        System.out.println(inMemoryTaskManager.getTaskById(3));
        System.out.println(inMemoryTaskManager.getSubTaskById(5));
        System.out.println(inMemoryTaskManager.getEpicById(1));

        System.out.println("��������� ������ �� �������������� � �������� ��");
        Task task1 = inMemoryTaskManager.getTaskById(3);
        task1.setName("������ 1_modify");
        inMemoryTaskManager.updateTask(task1);
        System.out.println(inMemoryTaskManager.getTaskById(3));

        Epic epic1 = inMemoryTaskManager.getEpicById(1);
        epic1.setName("���� 1_modify");
        inMemoryTaskManager.updateEpic(epic1);
        System.out.println(inMemoryTaskManager.getEpicById(1));

        SubTask subTask1 = inMemoryTaskManager.getSubTaskById(5);
        subTask1.setName("��������� 1_modify");
        inMemoryTaskManager.updateSubTask(subTask1);
        System.out.println(inMemoryTaskManager.getSubTaskById(5));

        //�������� �� ���������������
        System.out.println("������� �� ��������������");
        inMemoryTaskManager.deleteTaskById(3);
        inMemoryTaskManager.deleteSubTaskById(5);
        inMemoryTaskManager.deleteEpicById(1);
    //    printAll(inMemoryTaskManager);

        //��������� ��������� 3  � ���� 3

        //SubTask subtask3 = new SubTask("��������� 3","�������� 3",taskManager.getId());
        //taskManager("���� 3","�������� 3",taskManager.getId());
        //taskManager.addSubTaskToEpic(epic3,subtask3);


        //�� ������� � ���������
        System.out.println("������� ��� � �������� �������");
        deleteAll(inMemoryTaskManager);
   //     printAll(inMemoryTaskManager);

        //���� �� �������
        System.out.println("������ ��������� ���������� �� �������");
        System.out.println("��������� 2 ��������� � 1 ����");

        inMemoryTaskManager.addEpic(new Epic("���� 4", "�������� 4"));
        inMemoryTaskManager.addSubTask(new SubTask("��������� 4", "�������� 4", Status.NEW, 7));
        inMemoryTaskManager.addSubTask(new SubTask("��������� 5", "�������� 5", Status.NEW, 7));
    //    printAll(inMemoryTaskManager);
        //taskManager.addSubTaskToEpic(taskManager.getEpicById(9), taskManager.getSubTaskById(8));
    //    printAll(inMemoryTaskManager);

        System.out.println("������ � 4 ��������� �������� � ������");
        SubTask subTask2 = inMemoryTaskManager.getSubTaskById(8);
        subTask2.setName("��������� 4 (update)");
        subTask2.setDescription("�������� 4 (update)");
        subTask2.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubTask(subTask2);
    //    printAll(inMemoryTaskManager);

        System.out.println("������ � 5 ��������� �������� � ������");
        SubTask subTask3 = inMemoryTaskManager.getSubTaskById(9);
        subTask3.setName("��������� 5 (update)");
        subTask3.setDescription("�������� 5 (update)");
        subTask3.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubTask(subTask3);
    //    printAll(inMemoryTaskManager);

        ArrayList<SubTask> subTasks = inMemoryTaskManager.getAllSubTasksByEpicId(7);
        //��������� getAllSubTasksByEpicId

        //������� ������ �� ����� (DONE)
        System.out.println("������� ���� �� �������� (��������� 4)");
        inMemoryTaskManager.deleteSubTaskById(8);
    //    printAll(inMemoryTaskManager);
        System.out.println("������� ���� �� �������� (��������� 5)");
        inMemoryTaskManager.deleteSubTaskById(9);
     //   printAll(inMemoryTaskManager);
        System.out.println("������� ���� 4");
        inMemoryTaskManager.deleteEpicById(7);
     //   printAll(inMemoryTaskManager);

    }

    public static void deleteAll(TaskManager inMemoryTaskManager) {
        inMemoryTaskManager.deleteAllTasks();
        //������� ��� ����� �� ������
        inMemoryTaskManager.deleteAllEpics();
        //������� ��� ��������� �� ������
        inMemoryTaskManager.deleteAllSubTasks();
    }

    public static void allTestData(TaskManager inMemoryTaskManager) {
        //������� �����
        inMemoryTaskManager.addEpic(new Epic("���� 1", "�������� 1"));
        inMemoryTaskManager.addEpic(new Epic("���� 1", "�������� 1"));
        //������� ������
        inMemoryTaskManager.addTask(new Task("������ 1", "�������� 1", Status.NEW));
        inMemoryTaskManager.addTask(new Task("������ 2", "�������� 2", Status.NEW));
        //������� ���������
        inMemoryTaskManager.addSubTask(new SubTask("��������� 1", "�������� 1",  Status.NEW, 2));
        inMemoryTaskManager.addSubTask(new SubTask("��������� 2", "�������� 2",  Status.NEW, 2));
    }*/
    }



}