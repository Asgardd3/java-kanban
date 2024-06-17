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
        taskManager.updateTask(new Task("������ 1_modify","�������� 1",3));
        System.out.println(taskManager.getTaskById(3));
        taskManager.updateEpic(new Epic("���� 1_modify","�������� 1",1));
        System.out.println(taskManager.getEpicById(1));
        taskManager.updateSubTask(new SubTask("��������� 1_modify","�������� 1",5));
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
        taskManager.addSubTask(new SubTask("��������� 4","�������� 4",taskManager.getId()));
        taskManager.addSubTask(new SubTask("��������� 5","�������� 5",taskManager.getId()));
        taskManager.addEpic(new Epic("���� 4","�������� 4",taskManager.getId()));
        printAll(taskManager);
        taskManager.addSubTaskToEpic(taskManager.getEpicById(9),taskManager.getSubTaskById(7));
        taskManager.addSubTaskToEpic(taskManager.getEpicById(9),taskManager.getSubTaskById(8));
        printAll(taskManager);
        taskManager.updateSubTask(new SubTask("��������� 4","�������� 4",7, Status.DONE));
        taskManager.updateSubTask(new SubTask("��������� 5","�������� 5",8, Status.NEW));
        printAll(taskManager);

        //������� ������ �� ����� (DONE)

        System.out.println("������� ���� �� �����");
        taskManager.deleteSubTaskById(7);
        printAll(taskManager);
        taskManager.deleteSubTaskById(8);
        printAll(taskManager);
        taskManager.deleteEpicById(9);
        printAll(taskManager);







/*
        ��������
        ��������� �� ������������
                ����������
                �������� ����������������
  */
        /***********************/
        //������� ��� ������ � ��������� �� ������

        //��������� ����� ��������
        /***********************/
        /*
        //��������� ������ � ����
        epic1.addTask(task1);
        epic1.addTask(task2);
        //��������� ��������� � ������
        task1.addSubTask(subTask1);
        task1.addSubTask(subTask2);
        //�

         */

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
        taskManager.addEpic(new Epic("���� 1","�������� 1",taskManager.getId()));
        taskManager.addEpic(new Epic("���� 1","�������� 1",taskManager.getId()));
        //������� ������
        taskManager.addTask(new Task("������ 1","�������� 1",taskManager.getId()));
        taskManager.addTask(new Task("������ 2","�������� 2",taskManager.getId()));
        //������� ���������
        taskManager.addSubTask(new SubTask("��������� 1","�������� 1",taskManager.getId()));
        taskManager.addSubTask(new SubTask("��������� 2","�������� 2",taskManager.getId()));
    }
}


