package managers;

import tasks.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class InMemoryTaskManager implements TaskManager {

    private int id = 1;
    private Map<Integer, Task> tasks = new HashMap<Integer, Task>();
    private Map<Integer, SubTask> subTasks = new HashMap<Integer, SubTask>();
    private Map<Integer, Epic> epics = new HashMap<>();
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

    private void addHistory(Task task)  {
        historyManager.add(task);
    }

    private int getId() {
        return this.id++;
    }

    private void addInPrioinzedTasks(Task task) {
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
        addInPrioinzedTasks(task);
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
        Task task  = tasks.get(id);
        addHistory(task);
        return task;
    }

    @Override
    public void updateTask(Task task) throws ManagerSaveException {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
            prioritizedTasks.remove(task);
            addInPrioinzedTasks(task);
        } else {
            System.out.println("Задачи с такой ID(" + task.getId() + ") не существует");

        }
    }

    @Override
    public void deleteTaskById(int id) throws ManagerSaveException {
        prioritizedTasks.remove(getTaskById(id));
        tasks.remove(id);
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
            evaluateEpicStatus(epic);
            addInPrioinzedTasks(subTask);
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
                    evaluateEpicStatus(epic);
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
            addInPrioinzedTasks(subTask);
            if (epics.get(subTask.getEpicId()) != null) {
                evaluateEpicStatus(epics.get(subTask.getEpicId()));
            }
        } else {
            System.out.println("Подзадачи с такой ID(" + subTask.getId() + ") не существует");

        }



    }
    //Ищем, в каком эпике есть данная подзадача и
    // обновляем его статус

    @Override
    public void deleteSubTaskById(int id) throws ManagerSaveException {
        prioritizedTasks.remove(getSubTaskById(id));
        SubTask subTask = subTasks.remove(id);
        Epic epic = epics.get(subTask.getEpicId());
        epic.removeSubTasksId(subTask.getId());
        evaluateEpicStatus(epic);
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
        Epic epicUpd  = epics.get(epic.getId());
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


    private void evaluateEpicStatus(Epic epic) {

        //Ели у эпика нет подзадач или они все NEW, то статус должен быть NEW
        //Проверить, есть ли задачи в статусе NEW, если нет, то статус должен стать NEW
        AtomicBoolean isActualHaveTasksNew = new AtomicBoolean(false);
        AtomicBoolean isActualHaveTasksDone = new AtomicBoolean(false);
        AtomicBoolean isActualHaveTasksInProgress = new AtomicBoolean(false);

        if (epic.getSubTasksIds() == null || epic.getSubTasksIds().size() == 0) { //epics.size() == 0 ||
            epic.setStatus(Status.NEW);
            return;
        }

        epic.getSubTasksIds()
                .forEach(subTaskId -> {
                    SubTask subTask = subTasks.get(subTaskId);
                    switch (subTask.getStatus()) {
                        case NEW:
                            isActualHaveTasksNew.set(true);
                            break;
                        case DONE:
                            isActualHaveTasksDone.set(true);
                            break;
                        case IN_PROGRESS:
                            isActualHaveTasksInProgress.set(true);
                    }

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

        if (isActualHaveTasksNew.get() && !isActualHaveTasksDone.get() && !isActualHaveTasksInProgress.get()) { //epics.size() == 0 ||
            epic.setStatus(Status.NEW);
            return;
        }

        //Если все подзадачи DONE, то эпик считается завершённым
        if (!isActualHaveTasksNew.get() && isActualHaveTasksDone.get() && !isActualHaveTasksInProgress.get()) {
            epic.setStatus(Status.DONE);
            return;
        }
        epic.setStatus(Status.IN_PROGRESS);

        //Дата старта эпика

        //Продолжительность эпика — сумма продолжительностей всех его подзадач. Время начала — дата старта самой ранней подзадачи, а время завершения — время окончания самой поздней из задач
        //сумма продолжительностей всех подзадач
        //дата начала самой ранней подзадачи
        //дата окончания самой поздней подзадачи




    }
}

