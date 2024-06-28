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
        history.add(task);
    }
}
