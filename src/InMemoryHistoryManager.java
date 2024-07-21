import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {
    private List<Task> history  = new ArrayList<>();
    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(history);
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
