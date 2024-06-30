import java.sql.SQLOutput;

public abstract class Managers {
    public static TaskManager getDefault() {
        TaskManager taskManager = new InMemoryTaskManager();
        return taskManager;
    }

    public static HistoryManager getDefaultHistory() {
        HistoryManager historyManager = new InMemoryHistoryManager();
        return historyManager;
    }
    public static void printAllTasks(TaskManager manager) {
        System.out.println("");
        System.out.println("*************************");
        System.out.println("������:");
        for (Task task : manager.getAllTasks()) {
            System.out.println(task);
        }
        System.out.println("�����:");
        for (Task epic : manager.getAllEpics()) {
            System.out.println(epic);

            for (Task task : manager.getAllSubTasksByEpicId(epic.getId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("���������:");
        for (Task subtask : manager.getAllSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("�������:");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
        System.out.println("*************************");
        System.out.println("");
    }
}
