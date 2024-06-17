import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private  Integer id = 1;
    private HashMap<Integer,Task> tasks  = new HashMap<Integer,Task>();
    private HashMap<Integer,SubTask> subTasks  = new HashMap<Integer,SubTask>();
    private HashMap<Integer,Epic> epics  = new HashMap<>();
    public  Integer getId() {
        return this.id++;
    }
    //������
    public void addTask(Task task){
        //task.id = id;
        tasks.put(task.id,task);
        //this.id++;

    }

    public void printAllTasks(){    for  (Task task  : tasks.values()){
            System.out.println(task.toString());}}
    public ArrayList<Task> getAllTasks(){
        ArrayList<Task> tasksList = new ArrayList<Task>();
        for (Task task : tasks.values()){
            tasksList.add(task);
        }
        return tasksList;
    }

    public void deleteAllTasks(){ tasks.clear();}

    public Task getTaskById(Integer id){    return tasks.get(id);}

    public void updateTask(Task task) {
        if (tasks.containsKey(task.id)) {
            tasks.put(task.id, task);
        } else {
            System.out.println("������ � ����� ID(" + task.id + ") �� ����������");

        }
    }

    public void deleteTaskById(Integer id){ tasks.remove(id);}


    //���������
    public void printAllSubTasks(){
        for   (SubTask subTask   : subTasks.values())
        {   System.out.println(subTask.toString());}}
    public void addSubTask(SubTask subTask){
        //subTask.id = id;
        subTasks.put(subTask.id,subTask);
        //this.id++;

    }
    public ArrayList<SubTask> getAllSubTasks(){
        ArrayList<SubTask> subTasksList = new ArrayList<SubTask>();
        for (SubTask subTask : subTasks.values()){
            subTasksList.add(subTask);
        }
        return subTasksList;
    }

    public void deleteAllSubTasks(){ subTasks.clear();}

    public SubTask getSubTaskById(Integer id){    return subTasks.get(id);}

    public void updateSubTask(SubTask subTask) {

        if (subTasks.containsKey(subTask.id)) {
            subTasks.put(subTask.id, subTask);
        } else {
            System.out.println("��������� � ����� ID(" + subTask.id + ") �� ����������");

        }

        //���� ���� , � ������� ���� ������ ������
        //��������� �� ������ ����� � ���� ������
        for(Epic epic : epics.values()) {
            if (epic.getSubTasksId().contains(subTask.getId())) {
                evaluateEpicStatus(epic);
            }
        }

    }
        //����, � ����� ����� ���� ������ ��������� � ��������� ��� ������ evaluateEpicStatus(epic);

    public void deleteSubTaskById(Integer id){
        for(Epic epic : epics.values()) {
            if (epic.getSubTasksId().contains(id)) {
                subTasks.remove(id);
                subTasks.remove(null);
                epic.removeSubTasksId(id);
                evaluateEpicStatus(epic);
            }
        }

        //���� ���-�� ���� � ������, �� ������� � ��� + ������������� ������
    }




    //�����
    public void printAllEpics(){
        for   (Epic epic   : epics.values()){
        System.out.println(epic.toString());}}
    public void addEpic(Epic epic){
        //epic.id = id;
        epics.put(epic.id,epic);
        //this.id++;

    }
    public ArrayList<Epic> getAllEpics(){
        ArrayList<Epic> epicsList = new ArrayList<Epic>();
        for (Epic epic : epics.values()){
            epicsList.add(epic);
        }
        return epicsList;
    }

    public void deleteAllEpics(){ epics.clear();}

    public Epic getEpicById(Integer id){
        return epics.get(id);}

    public void updateEpic(Epic epic){
        //�������������� �� ����� �������������� ������ �����
        epic.setStatus(epics.get(epic.id).getStatus());
        epics.put(epic.id,epic);
        if (epics.containsKey(epic.id)) {
            epics.put(epic.id,epic);
        } else {
            System.out.println("����� � ����� ID(" + epic.id + ") �� ����������");

        }
    }

    public void deleteEpicById(Integer id){ epics.remove(id);}

    public ArrayList<SubTask> getAllTasksByEpic (Integer id) {
            ArrayList<SubTask> subTasks= new ArrayList<>();
            Epic epic = getEpicById(id);

            for (Integer o : epic.getSubTasksId()) {

                subTasks.add(this.getSubTaskById(o));

            }
            return subTasks;
        }
    public void addSubTaskToEpic(Epic epic,SubTask subTask) {
        Boolean isAlreadyInCollection = false;
       for (Epic epicLoop : epics.values()){
           if (epicLoop.getSubTasksId().contains(subTask.getId())) {

               isAlreadyInCollection = true;
           }
       }
       if (!isAlreadyInCollection) {
           epic.addSubTasksId(subTask.getId());
       }
        evaluateEpicStatus(epic);
    }

    public void evaluateEpicStatus(Epic epic) {
        //��� � ����� ��� �������� ��� ��� ��� NEW, �� ������ ������ ���� NEW
        //���������, ���� �� ������ � ������� NEW, ���� ���, �� ������ ������ ����� NEW
        Boolean isActualAllStatusNew = true;
        Boolean isActualALlTasksDone = true;

        if (epic.getSubTasksId().isEmpty()) { //epics.size() == 0 ||
            epic.setStatus(Status.NEW);
            return;
        }

        for (SubTask subTask : getAllTasksByEpic(epic.getId())) {
            if (!subTask.getStatus().equals(Status.NEW)) {
                isActualAllStatusNew = false;
            }
            if (!subTask.getStatus().equals(Status.DONE)) {
                isActualALlTasksDone = false;
            }
        }


        if (isActualAllStatusNew ) { //epics.size() == 0 ||
            epic.setStatus(Status.NEW);
            return;
        }


        //���� ��� ��������� DONE, �� ���� ��������� �����������
        if (isActualALlTasksDone) {
            epic.setStatus(Status.DONE);
            return;
        }
        epic.setStatus(Status.IN_PROGRESS);

        }
        }

