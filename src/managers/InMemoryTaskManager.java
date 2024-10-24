package managers;

import tasks.*;

import java.util.*;

public class InMemoryTaskManager implements TaskManager {

    protected Map<Integer, Task> tasks = new HashMap<Integer, Task>();
    protected Map<Integer, SubTask> subTasks = new HashMap<Integer, SubTask>();
    protected Map<Integer, Epic> epics = new HashMap<>();
    private int id = 1;
    private HistoryManager historyManager = Managers.getDefaultHistory();
    private TreeSet<Task> prioritizedTasks = new TreeSet<>(Comparator.comparing(
                    Task::getStartTime,
                    Comparator.nullsLast(Comparator.naturalOrder())
            )
            .thenComparingInt(Task::getId));

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void addHistory(Task task) {
        historyManager.add(task);
    }

    private int getId() {
        return this.id++;
    }

    private void addInPrioritizedTasks(Task task) {
        //Если не задана startTime, то не добавляем в treeset
        if (task.getStartTime() == null) {
            return;
        }
        prioritizedTasks.add(task);
    }

    //Задачи
    @Override
    public void addTask(Task task) throws ManagerSaveException, TaskOverloadException {
        task.setId(getId());
        tasks.put(task.getId(), task);
        addInPrioritizedTasks(task);
        //Ищем по методу isOverlapTasks с помощью stream APi
        if (prioritizedTasks.stream().anyMatch(taskT -> isOverlapTasks(task, taskT))) {
            throw new TaskOverloadException("Ошибка: пересечение задач");
        }
    }

    public void printAllTasks() {
        tasks.values()
                .forEach(task -> System.out.println(task.toString()));
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public void deleteAllTasks() throws ManagerSaveException {
        tasks.values()
                .forEach(task -> {
                    historyManager.remove(task.getId());
                    prioritizedTasks.remove(task);
                });
        tasks.clear();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = tasks.get(id);
        addHistory(task);
        return task;
    }

    @Override
    public void updateTask(Task task) throws ManagerSaveException {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            prioritizedTasks.remove(task);
            addInPrioritizedTasks(task);
        } else {
            System.out.println("Задачи с такой ID(" + task.getId() + ") не существует");

        }
    }

    @Override
    public void deleteTaskById(int id) throws ManagerSaveException {
        Task removedTask = tasks.remove(id);
        prioritizedTasks.remove(removedTask);
        historyManager.remove(id);

    }

    //Подзадачи
    public void printAllSubTasks() {
        subTasks.values()
                .forEach(subTask -> System.out.println(subTask.toString()));

    }

    @Override
    public void addSubTask(SubTask subTask) throws ManagerSaveException, TaskOverloadException {
        subTask.setId(getId());
        if (epics.containsKey(subTask.getEpicId())) {
            subTasks.put(subTask.getId(), subTask);
            Epic epic = epics.get(subTask.getEpicId());
            epic.addSubTasksId(subTask.getId());
            updateEpicAdditProps(epic);
            addInPrioritizedTasks(subTask);
        }


        if (prioritizedTasks.stream().anyMatch(taskT -> isOverlapTasks(subTask, taskT)) && prioritizedTasks.size() > 1) {
            throw new TaskOverloadException("Ошибка: пересечение задач");
        }


    }

    @Override
    public ArrayList<SubTask> getAllSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void deleteAllSubTasks() throws ManagerSaveException {
        subTasks.values()
                .forEach(subTask -> {
                    historyManager.remove(subTask.getId());
                    prioritizedTasks.remove(subTask);
                });
        subTasks.clear();
        epics.values()
                .forEach(epic -> {
                    epic.removeAll();
                    updateEpicAdditProps(epic);
                });


    }

    @Override
    public SubTask getSubTaskById(int id) {
        addHistory(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public void updateSubTask(SubTask subTask) throws ManagerSaveException {

        if (subTasks.containsKey(subTask.getId())) {
            subTasks.put(subTask.getId(), subTask);
            prioritizedTasks.remove(subTask);
            addInPrioritizedTasks(subTask);
            if (epics.get(subTask.getEpicId()) != null) {
                updateEpicAdditProps(epics.get(subTask.getEpicId()));
            }
        } else {
            System.out.println("Подзадачи с такой ID(" + subTask.getId() + ") не существует");

        }


    }
    //Ищем, в каком эпике есть данная подзадача и
    // обновляем его статус

    @Override
    public void deleteSubTaskById(int id) throws ManagerSaveException {
        SubTask removedSubTask = subTasks.remove(id);
        prioritizedTasks.remove(removedSubTask);
        Epic epic = epics.get(removedSubTask.getEpicId());
        epic.removeSubTasksId(removedSubTask.getId());
        updateEpicAdditProps(epic);
        historyManager.remove(id);

    }


    //Эпики
    public void printAllEpics() {
        epics.values()
                .forEach(epic -> System.out.println(epic.toString()));
    }

    @Override
    public void addEpic(Epic epic) throws ManagerSaveException {
        epic.setId(getId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public ArrayList<Epic> getAllEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public void deleteAllEpics() throws ManagerSaveException {
        epics.values()
                .forEach(epic -> {
                    historyManager.remove(epic.getId());
                });


        subTasks.values()
                .forEach(subTask -> {
                    historyManager.remove(subTask.getId());
                    prioritizedTasks.remove(subTask.getId());
                });

        epics.clear();
        subTasks.clear();
    }

    @Override
    public Epic getEpicById(int id) {
        addHistory(epics.get(id));
        return epics.get(id);
    }

    @Override
    public void updateEpic(Epic epic) throws ManagerSaveException {
        //Пооользователь не может переопределить статус эпика
        Epic epicUpd = epics.get(epic.getId());
        epicUpd.setName(epic.getName());
        epicUpd.setDescription(epic.getDescription());
    }

    @Override
    public void deleteEpicById(int id) throws ManagerSaveException {
        Epic epic = epics.remove(id);
        epic.getSubTasksIds()
                .forEach(subtaskToRemove -> {
                    prioritizedTasks.remove(getSubTaskById(subtaskToRemove));
                    subTasks.remove(subtaskToRemove);
                    historyManager.remove(subtaskToRemove);
                });


        historyManager.remove(id);
    }

    @Override
    public ArrayList<SubTask> getAllSubTasksByEpicId(int id) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        Epic epic = epics.get(id);
        epic.getSubTasksIds()
                .forEach(subtaskId -> subTasks.add(this.subTasks.get(subtaskId)));
        return subTasks;
    }

    public Boolean isOverlapTasks(Task taskF, Task taskS) {
        return taskF != taskS && (taskF.getEndTime().isAfter(taskS.getStartTime()) &&
                taskS.getEndTime().isAfter(taskF.getStartTime()));
    }


    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
    }

    public void updateEpicAdditProps(Epic epic) {
        evaluateEpicStatus(epic);
        evaluateEpicSetTime(epic);
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

    private void evaluateEpicSetTime(Epic epic) {

        epic.getSubTasksIds()
                .forEach(subTaskId -> {
                    SubTask subTask = subTasks.get(subTaskId);

                    if (epic.getStartTime() == null || subTask.getStartTime() != null) {
                        epic.setStartTime(subTask.getStartTime());
                    }

                    if (subTask.getStartTime() != null) {
                        if (subTask.getStartTime().compareTo(epic.getStartTime()) > 0) {
                            epic.setStartTime(subTask.getStartTime());
                        }
                    }

                    if (epic.getEndTime() == null || subTask.getEndTime() != null) {
                        epic.setEndTime(subTask.getEndTime());
                    }

                    if (subTask.getEndTime() != null) {
                        if (subTask.getEndTime().compareTo(epic.getEndTime()) > 0) {
                            epic.setEndTime(subTask.getEndTime());
                        }
                    }

                    if (epic.getDuration() == null || subTask.getDuration() != null) {
                        epic.setDuration(subTask.getDuration());
                    }

                    if (subTask.getDuration() != null) {
                        epic.setDuration(epic.getDuration().plus(subTask.getDuration()));
                    }
                });
    }
}

