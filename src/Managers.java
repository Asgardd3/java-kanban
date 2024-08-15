import java.io.File;

public abstract class Managers {
    public static FileBackedTaskManager getDefault() {
        File file = new File("tasks.txt");
        if (file.exists() && !file.isDirectory()) {
            return  FileBackedTaskManager.loadFromFile(file);
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
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getAllSubTasksByEpicId(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : manager.getAllSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
        System.out.println("*************************");
        System.out.println("");
    }
}
