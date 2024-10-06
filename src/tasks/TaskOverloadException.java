package tasks;

public class TaskOverloadException extends Exception {
    public TaskOverloadException(String message) {
        super(message);
        System.out.println(message);
    }

    public TaskOverloadException() {
    }
}
