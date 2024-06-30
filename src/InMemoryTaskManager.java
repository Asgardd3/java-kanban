import java.util.ArrayList;
import java.util.HashMap;

public class InMemoryTaskManager implements TaskManager {

    private int id = 1;
    private HashMap<Integer, Task> tasks = new HashMap<Integer, Task>();
    private HashMap<Integer, SubTask> subTasks = new HashMap<Integer, SubTask>();
    private HashMap<Integer, Epic> epics = new HashMap<>();
    private HistoryManager historyManager = Managers.getDefaultHistory();

    @Override
    public ArrayList<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public void clearHistory()  {
        historyManager.clear();
    }

    private void addHistory(Task task)  {
        historyManager.add(task);
    }

    private int getId() {
        return this.id++;
    }

    //������
    @Override
    public void addTask(Task task) {
        task.setId(getId());
        tasks.put(task.getId(), task);
    }

    public void printAllTasks() {
        for (Task task : tasks.values()) {
            System.out.println(task.toString());
        }
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() {
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task  = tasks.get(id);
        addHistory(tasks.get(id));
        return task;
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        } else {
            System.out.println("������ � ����� ID(" + task.getId() + ") �� ����������");

        }
    }

    @Override
    public void deleteTaskById(int id) {
        tasks.remove(id);
    }

    //���������
    public void printAllSubTasks() {
        for (SubTask subTask : subTasks.values()) {
            System.out.println(subTask.toString());
        }
    }

    @Override
    public void addSubTask(SubTask subTask) {
        subTask.setId(getId());
        if (epics.containsKey(subTask.getEpicId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            epic.addSubTasksId(subTask.getId());
            evaluateEpicStatus(epic);
        }
    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteAllSubTasks() {
        subTasks.clear();
        for (Epic epicLoop : epics.values()) {
            epicLoop.removeAll();
            evaluateEpicStatus(epicLoop);
        }

    }

    @Override
    public SubTask getSubTaskById(int id) {
        addHistory(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public void updateSubTask(SubTask subTask) {

        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            if (epics.get(subTask.getEpicId()) != null) {
                evaluateEpicStatus(epics.get(subTask.getEpicId()));
            }
        } else {
            System.out.println("��������� � ����� ID(" + subTask.getId() + ") �� ����������");

        }



    }
    //����, � ����� ����� ���� ������ ��������� �
    // ��������� ��� ������

    @Override
    public void deleteSubTaskById(int id) {
        SubTask subTask = subTasks.remove(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeSubTasksId(subTask.getId());
        evaluateEpicStatus(epic);
    }


    //�����
    public void printAllEpics() {
        for (Epic epic : epics.values()) {
            System.out.println(epic.toString());
        }
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(getId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpics() {
        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpicById(int id) {
        addHistory(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void updateEpic(Epic epic) {
        //�������������� �� ����� �������������� ������ �����
        Epic epicUpd  = epics.get(epic.getId());
        epicUpd.setName(epic.getName());
        epicUpd.setDescription(epic.getDescription());
    }

    @Override
    public void deleteEpicById(int id) {
        Epic epic = epics.remove(id);
        for (int subtaskToRemove : epic.getSubTasksIds()) {
            subTasks.remove(subtaskToRemove);
        }
    }

    @Override
    public ArrayList<SubTask> getAllSubTasksByEpicId(int id) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        Epic epic = epics.get(id);

        for (int o : epic.getSubTasksIds()) {

            subTasks.add(this.subTasks.get(o));

        }
        return subTasks;
    }

    private void evaluateEpicStatus(Epic epic) {
        //��� � ����� ��� �������� ��� ��� ��� NEW, �� ������ ������ ���� NEW
        //���������, ���� �� ������ � ������� NEW, ���� ���, �� ������ ������ ����� NEW
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

        //���� ��� ��������� DONE, �� ���� ��������� �����������
        if (!isActualHaveTasksNew && isActualHaveTasksDone && !isActualHaveTasksInProgress) {
            epic.setStatus(Status.DONE);
            return;
        }
        epic.setStatus(Status.IN_PROGRESS);
    }
}

