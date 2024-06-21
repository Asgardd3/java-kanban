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
        ArrayList<Task> tasksList = new ArrayList<Task>();
        for (Task task : tasks.values()) {
            tasksList.add(task);
        }
        return tasksList;
    }

    public void deleteAllTasks() {
        tasks.clear();
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.id)) {
            tasks.put(task.id, task);
        } else {
            System.out.println("Задачи с такой ID(" + task.id + ") не существует");

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
        subTasks.put(subTask.getId(), subTask);
        //this.id++;

    }

    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public void deleteAllSubTasks() {
        subTasks.clear();
        for (Epic epicLoop : epics.values()) {
            epicLoop.removeAll();
        }
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public void updateSubTask(SubTask subTask) {

        if (subTasks.containsKey(subTask.id)) {
            subTasks.put(subTask.id, subTask);
        } else {
            System.out.println("Подзадачи с такой ID(" + subTask.id + ") не существует");

        }

        if (subTask.getEpicId() != 0) {
            getEpicById(subTask.getEpicId()).removeSubTasksId(id);
        }

        subTasks.remove(id);
        subTasks.remove(null);
        if (getEpicById(subTask.getEpicId()) != null ) {
            evaluateEpicStatus(getEpicById(subTask.getEpicId()));
        }

    }
    //Ищем, в каком эпике есть данная подзадача и
    // обновляем его статус

    public void deleteSubTaskById(int id) {

        if (getSubTaskById(id).getEpicId() != 0) {
            getEpicById(getSubTaskById(id).getEpicId()).removeSubTasksId(id);
        }

        if (getEpicById(getSubTaskById(id).getEpicId()) != null ) {
            evaluateEpicStatus(getEpicById(getSubTaskById(id).getEpicId()));
        }

        subTasks.remove(id);
        subTasks.remove(null);
    }


    //Эпики
    public void printAllEpics() {
        for (Epic epic : epics.values()) {
            System.out.println(epic.toString());
        }
    }

    public void addEpic(Epic epic) {
        //epic.id = id;
        epic.setId(getId());
        epics.put(epic.getId(), epic);
        //this.id++;

    }

    public ArrayList<Epic> getAllEpics() {
        ArrayList<Epic> epicsList = new ArrayList<Epic>();
        for (Epic epic : epics.values()) {
            epicsList.add(epic);
        }
        return epicsList;
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
        this.epics.get(epic.id).setName(epic.getName());
        this.epics.get(epic.id).setDescription(epic.getDescription());
    }

    public void deleteEpicById(int id) {
        epics.remove(id);
        for (SubTask subTask  : subTasks.values())  {
            if (subTask.getEpicId() == id) {
                subTasks.remove(id);
            }
        }
    }

    public ArrayList<SubTask> getAllSubTasksByEpicId(int id) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        Epic epic = getEpicById(id);

        for (int o : epic.getSubTasksIds()) {

            subTasks.add(this.getSubTaskById(o));

        }
        return subTasks;
    }

    private void evaluateEpicStatus(Epic epic) {
        //Ели у эпика нет подзадач или они все NEW, то статус должен быть NEW
        //Проверить, есть ли задачи в статусе NEW, если нет, то статус должен стать NEW
        Boolean isActualHaveTasksNew = false;
        Boolean isActualHaveTasksDone = false;
        Boolean isActualHaveTasksInProgress = false;

        if (epic.getSubTasksIds() == null || epic.getSubTasksIds().size() == 0) { //epics.size() == 0 ||
            epic.setStatus(Status.NEW);
            return;
        }

        for (SubTask subTask : subTasks.values()) {
            if (subTask.getEpicId() == epic.getId()) {
                switch (subTask.getStatus()) {
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

