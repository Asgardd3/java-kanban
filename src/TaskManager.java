import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private int id = 1;
    private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<Integer, SubTask>();
    private HashMap<Integer, Epic> epics = new HashMap<>();

    private int getId() {
        return this.id++;
    }

    //Задачи
    public void addTask(Task task) {
        task.setId(getId());
        tasks.put(task.getId(), task);
    }

    public void printAllTasks() {
        for (Task task : tasks.values()) {
            System.out.println(task.toString());
        }
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("Задачи с такой ID(" + task.getId() + ") не существует");

        }
    }

    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    //Подзадачи
    public void printAllSubTasks() {
        for (SubTask subTask : subTasks.values()) {
            System.out.println(subTask.toString());
        }
    }

    public void addSubTask(SubTask subTask) {
        subTask.setId(getId());
        if (epics.containsKey(subTask.getEpicId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            epic.addSubTasksId(subTask.getId());
            evaluateEpicStatus(epic);
        }
    }

    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
        for (Epic epicLoop : epics.values()) {
            epicLoop.removeAll();
            evaluateEpicStatus(epicLoop);
        }

    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public void updateSubTask(SubTask subTask) {

        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            if (epics.get(subTask.getEpicId()) != null) {
                evaluateEpicStatus(epics.get(subTask.getEpicId()));
            }
        } else {
            System.out.println("Подзадачи с такой ID(" + subTask.getId() + ") не существует");

        }



    }
    //Ищем, в каком эпике есть данная подзадача и
    // обновляем его статус

    public void deleteSubTaskById(int id) {
        SubTask subTask = subTasks.remove(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeSubTasksId(subTask.getId());
        evaluateEpicStatus(epic);
    }


    //Эпики
    public void printAllEpics() {
        for (Epic epic : epics.values()) {
            System.out.println(epic.toString());
        }
    }

    public void addEpic(Epic epic) {
        epic.setId(getId());
        epics.put(epic.getId(), epic);
    }

    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public void updateEpic(Epic epic) {
        //Пооользователь не может переопределить статус эпика
        Epic epicUpd  = epics.get(epic.getId());
        epicUpd.setName(epic.getName());
        epicUpd.setDescription(epic.getDescription());
    }

    public void deleteEpicById(int id) {
        Epic epic = epics.remove(id);
        for (int subtaskToRemove : epic.getSubTasksIds()) {
            subTasks.remove(subtaskToRemove);
        }
    }

    public ArrayList<SubTask> getAllSubTasksByEpicId(int id) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        Epic epic = getEpicById(id);

        for (int o : epic.getSubTasksIds()) {

            subTasks.add(this.subTasks.get(o));

        }
        return subTasks;
    }

    private void evaluateEpicStatus(Epic epic) {
        //Ели у эпика нет подзадач или они все NEW, то статус должен быть NEW
        //Проверить, есть ли задачи в статусе NEW, если нет, то статус должен стать NEW
        boolean isActualHaveTasksNew = false;
        boolean isActualHaveTasksDone = false;
        boolean isActualHaveTasksInProgress = false;

        if (epic.getSubTasksIds() == null || epic.getSubTasksIds().size() == 0) { //epics.size() == 0 ||
            epic.setStatus(Status.NEW);
            return;
        }

        for (int subTaskId : epic.getSubTasksIds()) {
            switch (subTasks.get(subTaskId).getStatus()) {
                case NEW:
                    isActualHaveTasksNew = true;
                    break;
                case DONE:
                    isActualHaveTasksDone = true;
                    break;
                case IN_PROGRESS:
                    isActualHaveTasksInProgress = true;

            }
        }

        if (isActualHaveTasksNew && !isActualHaveTasksDone && !isActualHaveTasksInProgress) { //epics.size() == 0 ||
            epic.setStatus(Status.NEW);
            return;
        }

        //Если все подзадачи DONE, то эпик считается завершённым
        if (!isActualHaveTasksNew && isActualHaveTasksDone && !isActualHaveTasksInProgress) {
            epic.setStatus(Status.DONE);
            return;
        }
        epic.setStatus(Status.IN_PROGRESS);
    }
}

