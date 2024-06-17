public class Main {

    public static void main(String[] args) {

        System.out.println("Поехали!");
        TaskManager taskManager = new TaskManager();

        allTestData(taskManager);
        printAll(taskManager);

        System.out.println("Получаем задачу по идентификатору и печатаем ее");
        System.out.println(taskManager.getTaskById(3));
        System.out.println(taskManager.getSubTaskById(5));
        System.out.println(taskManager.getEpicById(1));

        System.out.println("Обновляем задачу по идентификатору и печатаем ее");
        taskManager.updateTask(new Task("Задача 1_modify","Описание 1",3));
        System.out.println(taskManager.getTaskById(3));
        taskManager.updateEpic(new Epic("Эпик 1_modify","Описание 1",1));
        System.out.println(taskManager.getEpicById(1));
        taskManager.updateSubTask(new SubTask("Подзадача 1_modify","Описание 1",5));
        System.out.println(taskManager.getSubTaskById(5));

        //Удаление по индентификатору
        System.out.println("Удаляем по идентификатору");
        taskManager.deleteTaskById(3);
        taskManager.deleteSubTaskById(5);
        taskManager.deleteEpicById(1);
        printAll(taskManager);

        //Добавляем подзадачу 3  и Эпик 3

        //SubTask subtask3 = new SubTask("Подзадача 3","Описание 3",taskManager.getId());
        //taskManager("Эпик 3","Описание 3",taskManager.getId());
        //taskManager.addSubTaskToEpic(epic3,subtask3);


        //Всё удаляем и проверяем
        System.out.println("Удаляем все и печатаем текущие");
        deleteAll(taskManager);
        printAll(taskManager);

        //Тест из задания
        taskManager.addSubTask(new SubTask("Подзадача 4","Описание 4",taskManager.getId()));
        taskManager.addSubTask(new SubTask("Подзадача 5","Описание 5",taskManager.getId()));
        taskManager.addEpic(new Epic("Эпик 4","Описание 4",taskManager.getId()));
        printAll(taskManager);
        taskManager.addSubTaskToEpic(taskManager.getEpicById(9),taskManager.getSubTaskById(7));
        taskManager.addSubTaskToEpic(taskManager.getEpicById(9),taskManager.getSubTaskById(8));
        printAll(taskManager);
        taskManager.updateSubTask(new SubTask("Подзадача 4","Описание 4",7, Status.DONE));
        taskManager.updateSubTask(new SubTask("Подзадача 5","Описание 5",8, Status.NEW));
        printAll(taskManager);

        //Удаляем задачу из эпика (DONE)

        System.out.println("Удаляем одну из задач");
        taskManager.deleteSubTaskById(7);
        printAll(taskManager);
        taskManager.deleteSubTaskById(8);
        printAll(taskManager);
        taskManager.deleteEpicById(9);
        printAll(taskManager);







/*
        создание
        получение по идетификатор
                обновление
                удаление поидентификатору
  */
        /***********************/
        //Удаляем все задачи и подзадачи из списка

        //Проверяем после удаления
        /***********************/
        /*
        //Добавляем задачи в эпик
        epic1.addTask(task1);
        epic1.addTask(task2);
        //Добавляем подзадачи в задачу
        task1.addSubTask(subTask1);
        task1.addSubTask(subTask2);
        //О

         */

    }

    public static void printAll(TaskManager taskManager) {
        taskManager.printAllEpics();
        taskManager.printAllTasks();
        taskManager.printAllSubTasks();
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
        taskManager.addEpic(new Epic("Эпик 1","Описание 1",taskManager.getId()));
        taskManager.addEpic(new Epic("Эпик 1","Описание 1",taskManager.getId()));
        //Создаем задачи
        taskManager.addTask(new Task("Задача 1","Описание 1",taskManager.getId()));
        taskManager.addTask(new Task("Задача 2","Описание 2",taskManager.getId()));
        //Создаем подзадачи
        taskManager.addSubTask(new SubTask("Подзадача 1","Описание 1",taskManager.getId()));
        taskManager.addSubTask(new SubTask("Подзадача 2","Описание 2",taskManager.getId()));
    }
}


