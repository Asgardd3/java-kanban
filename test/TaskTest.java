import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import managers.*;
import tasks.*;


import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    private TaskManager taskManager;
    private TaskManager taskManager2;

    @BeforeEach
    void beforeEach(){
        taskManager = new InMemoryTaskManager();
        taskManager2 = new InMemoryTaskManager();
    }//проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void shouldBeTwoTasksInListAfterAddTwoTasks() throws ManagerSaveException {
        //Создаем 2 задачи
        taskManager.addTask(new Task("Задача 1 (jUnit)", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30)));
        taskManager.addTask(new Task("Задача 2 (jUnit)", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30)));
        assertEquals(taskManager.getAllTasks().size(), 2);
    }
    @Test
    void shouldBeEqualsTasksWithSameId() throws ManagerSaveException {
        //Создаем 2 задачи с одинаковыми id
        Task task1 = new Task("Задача  1 (jUnit)", "Описание  1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        Task task2 = new Task("Задача  2 (jUnit)", "Описание  1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
        taskManager.addTask(task1);
        taskManager2.addTask(task2);
        assertEquals(task1, task2);
    }
}
