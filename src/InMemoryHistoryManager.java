import java.util.ArrayList;

public class InMemoryHistoryManager implements HistoryManager {
    private ArrayList<Task> history  = new ArrayList<>();
    @Override
    public ArrayList<Task> getHistory() {
        return history;
    }
    @Override
    public void add(Task task)  {
        //Добавить задачу в histo
        if (history.size() == 10) {
            history.remove(0);
        }
        //скопировать обьект task
        Task taskCopy = new Task(task.getName(),task.getDescription(),task.getStatus());
        taskCopy.setId(task.getId());
        history.add(taskCopy);
    }

    @Override
    public void clear(){
        history.clear();
    }
}
