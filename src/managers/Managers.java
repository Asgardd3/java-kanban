package managers;

import tasks.TaskOverloadException;

import java.io.File;

public abstract class Managers {
    public static TaskManager getDefault() throws ManagerSaveException, TaskOverloadException {
        File file = new File("tasks.txt");
        if (file.exists() && !file.isDirectory()) {
            return FileBackedTaskManager.loadFromFile(file);
        } else {
            return new FileBackedTaskManager(file.getPath());
        }

    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }

    public static void printAllTasks(TaskManager manager) {
        System.out.println("");
        System.out.println("*************************");
        System.out.println("Задачи:");
        System.out.println("Задачи:");
        manager.getAllTasks()
                .forEach(task -> System.out.println(task));

        System.out.println("Эпики:");
        manager.getAllEpics()
                .forEach(epic -> {
                    System.out.println(epic);
                    manager.getAllSubTasksByEpicId(epic.getId()).stream()
                            .forEach(subtask -> System.out.println("--> " + subtask));
                });

        System.out.println("Подзадачи:");
        manager.getAllSubTasks()
                .forEach(subtask -> System.out.println(subtask));

        System.out.println("История:");
        manager.getHistory()
                .forEach(task -> System.out.println(task));


        System.out.println("*************************");
        System.out.println("");
    }
}
