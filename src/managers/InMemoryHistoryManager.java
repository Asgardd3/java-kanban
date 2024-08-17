package managers;

import tasks.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager {

    private HashMap<Integer, Node> tasks = new HashMap<>();
    private Node tail;
    private Node head;

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        if (tasks.get(id) != null) {
            removeNode(tasks.get(id));
            tasks.remove(id);
        }

    }

    @Override
    public void add(Task task)  {
        if (tasks.containsKey(task.getId())) {
            removeNode(tasks.get(task.getId()));
            tasks.remove(task.getId());
        }
        linkLast(task);
    }

    private void linkLast(Task task) {
        Node tailOld = tail;
        Node node = new Node(task);
        node.prev = tailOld;
        tail = node;
        if (tailOld == null) {
            head = node;
        } else {
            tailOld.next = node;
        }
        tasks.put(task.getId(), node);
    }

    private List<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<>();
        Node element = head;
        while (element != null) {
            tasks.add(element.data);
            element = element.next;
        }
        return tasks;
    }

    private void removeNode(Node node) {
        if (node.next != null) {
            node.next.prev = node.prev;
        } else tail = node.prev;
        if (node.prev != null) {
            node.prev.next = node.next;
        } else head = node.next;
    }

}