import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        TaskManager inMemoryTaskManager = Managers.getDefault();


        // Старые прроверки
    /*
        allTestData(inMemoryTaskManager);
     //   printAll(inMemoryTaskManager);

        System.out.println("Получаем задачу по идентификатору и печатаем ее");
        System.out.println(inMemoryTaskManager.getTaskById(3));
        System.out.println(inMemoryTaskManager.getSubTaskById(5));
        System.out.println(inMemoryTaskManager.getEpicById(1));

        System.out.println("Обновляем задачу по идентификатору и печатаем ее");
        Task task1 = inMemoryTaskManager.getTaskById(3);
        task1.setName("Задача 1_modify");
        inMemoryTaskManager.updateTask(task1);
        System.out.println(inMemoryTaskManager.getTaskById(3));

        Epic epic1 = inMemoryTaskManager.getEpicById(1);
        epic1.setName("Эпик 1_modify");
        inMemoryTaskManager.updateEpic(epic1);
        System.out.println(inMemoryTaskManager.getEpicById(1));

        SubTask subTask1 = inMemoryTaskManager.getSubTaskById(5);
        subTask1.setName("Подзадача 1_modify");
        inMemoryTaskManager.updateSubTask(subTask1);
        System.out.println(inMemoryTaskManager.getSubTaskById(5));

        //Удаление по индентификатору
        System.out.println("Удаляем по идентификатору");
        inMemoryTaskManager.deleteTaskById(3);
        inMemoryTaskManager.deleteSubTaskById(5);
        inMemoryTaskManager.deleteEpicById(1);
    //    printAll(inMemoryTaskManager);

        //Добавляем подзадачу 3  и Эпик 3

        //SubTask subtask3 = new SubTask("Подзадача 3","Описание 3",taskManager.getId());
        //taskManager("Эпик 3","Описание 3",taskManager.getId());
        //taskManager.addSubTaskToEpic(epic3,subtask3);


        //Всё удаляем и проверяем
        System.out.println("Удаляем все и печатаем текущие");
        deleteAll(inMemoryTaskManager);
   //     printAll(inMemoryTaskManager);

        //Тест из задания
        System.out.println("Теперь тестируем функционал по заданию");
        System.out.println("Добавляем 2 подзадачи и 1 эпик");

        inMemoryTaskManager.addEpic(new Epic("Эпик 4", "Описание 4"));
        inMemoryTaskManager.addSubTask(new SubTask("Подзадача 4", "Описание 4", Status.NEW, 7));
        inMemoryTaskManager.addSubTask(new SubTask("Подзадача 5", "Описание 5", Status.NEW, 7));
    //    printAll(inMemoryTaskManager);
        //taskManager.addSubTaskToEpic(taskManager.getEpicById(9), taskManager.getSubTaskById(8));
    //    printAll(inMemoryTaskManager);

        System.out.println("Меняем у 4 подзадачи описание и статус");
        SubTask subTask2 = inMemoryTaskManager.getSubTaskById(8);
        subTask2.setName("Подзадача 4 (update)");
        subTask2.setDescription("Описание 4 (update)");
        subTask2.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubTask(subTask2);
    //    printAll(inMemoryTaskManager);

        System.out.println("Меняем у 5 подзадачи описание и статус");
        SubTask subTask3 = inMemoryTaskManager.getSubTaskById(9);
        subTask3.setName("Подзадача 5 (update)");
        subTask3.setDescription("Описание 5 (update)");
        subTask3.setStatus(Status.DONE);
        inMemoryTaskManager.updateSubTask(subTask3);
    //    printAll(inMemoryTaskManager);

        ArrayList<SubTask> subTasks = inMemoryTaskManager.getAllSubTasksByEpicId(7);
        //Проверяем getAllSubTasksByEpicId

        //Удаляем задачу из эпика (DONE)
        System.out.println("Удаляем одну из подзадач (подзадача 4)");
        inMemoryTaskManager.deleteSubTaskById(8);
    //    printAll(inMemoryTaskManager);
        System.out.println("Удаляем одну из подзадач (подзадача 5)");
        inMemoryTaskManager.deleteSubTaskById(9);
     //   printAll(inMemoryTaskManager);
        System.out.println("Удаляем эпик 4");
        inMemoryTaskManager.deleteEpicById(7);
     //   printAll(inMemoryTaskManager);

    }

    public static void deleteAll(TaskManager inMemoryTaskManager) {
        inMemoryTaskManager.deleteAllTasks();
        //Удаляем все эпики из списка
        inMemoryTaskManager.deleteAllEpics();
        //Удаляем все подзадачи из списка
        inMemoryTaskManager.deleteAllSubTasks();
    }

    public static void allTestData(TaskManager inMemoryTaskManager) {
        //Создаем эпики
        inMemoryTaskManager.addEpic(new Epic("Эпик 1", "Описание 1"));
        inMemoryTaskManager.addEpic(new Epic("Эпик 1", "Описание 1"));
        //Создаем задачи
        inMemoryTaskManager.addTask(new Task("Задача 1", "Описание 1", Status.NEW));
        inMemoryTaskManager.addTask(new Task("Задача 2", "Описание 2", Status.NEW));
        //Создаем подзадачи
        inMemoryTaskManager.addSubTask(new SubTask("Подзадача 1", "Описание 1",  Status.NEW, 2));
        inMemoryTaskManager.addSubTask(new SubTask("Подзадача 2", "Описание 2",  Status.NEW, 2));
    }*/
    }



}