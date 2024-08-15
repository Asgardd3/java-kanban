import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");

        //Читаем из файл список зад
        FileBackedTaskManager taskManager = Managers.getDefault();

        // Старые прроверки
        allTestData(taskManager);
        Managers.printAllTasks(taskManager);

        //Пробуем просмотреть задачи, для проверки истории
        System.out.println("Проверяем запись в историю");
        Task taskN = taskManager.getTaskById(3);
        SubTask subTaskN = taskManager.getSubTaskById(5);
        Epic epicN = taskManager.getEpicById(1);
        Managers.printAllTasks(taskManager);

        System.out.println("Получаем задачу по идентификатору и печатаем ее");
        System.out.println(taskManager.getTaskById(3));
        System.out.println(taskManager.getSubTaskById(5));
        System.out.println(taskManager.getEpicById(1));

        System.out.println("Обновляем задачу по идентификатору и печатаем ее");
        Task task1 = taskManager.getTaskById(3);
        task1.setName("Задача 1_modify");
        taskManager.updateTask(task1);
        System.out.println(taskManager.getTaskById(3));

        Epic epic1 = taskManager.getEpicById(1);
        epic1.setName("Эпик 1_modify");
        taskManager.updateEpic(epic1);
        System.out.println(taskManager.getEpicById(1));

        SubTask subTask1 = taskManager.getSubTaskById(5);
        subTask1.setName("Подзадача 1_modify");
        taskManager.updateSubTask(subTask1);
        System.out.println(taskManager.getSubTaskById(5));

        //Удаление по индентификатору
        System.out.println("Удаляем по идентификатору");
        taskManager.deleteTaskById(3);
        taskManager.deleteSubTaskById(5);
        taskManager.deleteEpicById(1);
        Managers.printAllTasks(taskManager);

        //Добавляем подзадачу 3  и Эпик 3

        //SubTask subtask3 = new SubTask("Подзадача 3","Описание 3",taskManager.getId());
        //taskManager("Эпик 3","Описание 3",taskManager.getId());
        //taskManager.addSubTaskToEpic(epic3,subtask3);


        //Всё удаляем и проверяем
        System.out.println("Удаляем все и печатаем текущие");
        deleteAll(taskManager);
        Managers.printAllTasks(taskManager);

        //Тест из задания
        System.out.println("Теперь тестируем функционал по заданию");
        System.out.println("Добавляем 2 подзадачи и 1 эпик");

        taskManager.addEpic(new Epic("Эпик 4", "Описание 4"));
        taskManager.addSubTask(new SubTask("Подзадача 4", "Описание 4", Status.NEW, 7));
        taskManager.addSubTask(new SubTask("Подзадача 5", "Описание 5", Status.NEW, 7));
        Managers.printAllTasks(taskManager);
        //taskManager.addSubTaskToEpic(taskManager.getEpicById(9), taskManager.getSubTaskById(8));
        Managers.printAllTasks(taskManager);

        System.out.println("Меняем у 4 подзадачи описание и статус");
        SubTask subTask2 = taskManager.getSubTaskById(8);
        subTask2.setName("Подзадача 4 (update)");
        subTask2.setDescription("Описание 4 (update)");
        subTask2.setStatus(Status.DONE);
        taskManager.updateSubTask(subTask2);
        Managers.printAllTasks(taskManager);

        System.out.println("Меняем у 5 подзадачи описание и статус");
        SubTask subTask3 = taskManager.getSubTaskById(9);
        subTask3.setName("Подзадача 5 (update)");
        subTask3.setDescription("Описание 5 (update)");
        subTask3.setStatus(Status.DONE);
        taskManager.updateSubTask(subTask3);
        Managers.printAllTasks(taskManager);

        ArrayList<SubTask> subTasks = taskManager.getAllSubTasksByEpicId(7);
        //Проверяем getAllSubTasksByEpicId
/*
        //Удаляем задачу из эпика (DONE)
        System.out.println("Удаляем одну из подзадач (подзадача 4)");
        taskManager.deleteSubTaskById(8);
        Managers.printAllTasks(taskManager);
        System.out.println("Удаляем одну из подзадач (подзадача 5)");
        taskManager.deleteSubTaskById(9);
        Managers.printAllTasks(taskManager);
        System.out.println("Удаляем эпик 4");
        taskManager.deleteEpicById(7);
        Managers.printAllTasks(taskManager);
        */


    }

    public static void deleteAll(TaskManager taskManager) {
        taskManager.deleteAllTasks();
        //Удаляем все эпики из списка
        taskManager.deleteAllEpics();
        //Удаляем все подзадачи из списка
        taskManager.deleteAllSubTasks();
    }

    public static void allTestData(TaskManager taskManager) {
        //Создаем эпики
        taskManager.addEpic(new Epic("Эпик 1", "Описание 1"));
        taskManager.addEpic(new Epic("Эпик 1", "Описание 1"));
        //Создаем задачи
        taskManager.addTask(new Task("Задача 1", "Описание 1", Status.NEW));
        taskManager.addTask(new Task("Задача 2", "Описание 2", Status.NEW));
        //Создаем подзадачи
        taskManager.addSubTask(new SubTask("Подзадача 1", "Описание 1",  Status.NEW, 2));
        taskManager.addSubTask(new SubTask("Подзадача 2", "Описание 2",  Status.NEW, 2));
    }
}
