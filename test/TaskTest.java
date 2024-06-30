import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    TaskManager taskManager;
    TaskManager taskManager2;

    @BeforeEach
    void beforeEach(){
        taskManager = new InMemoryTaskManager();
        taskManager2 = new InMemoryTaskManager();
    }//проверьте, что экземпляры класса Task равны друг другу, если равен их id;
    @Test
    void shouldBeTwoTasksInListAfterAddTwoTasks() {
        //Создаем 2 задачи
        taskManager.addTask(new Task("Задача 1 (jUnit)", "Описание 1", Status.NEW));
        taskManager.addTask(new Task("Задача 2 (jUnit)", "Описание 1", Status.NEW));
        assertEquals(taskManager.getAllTasks().size(), 2);
    }
    @Test
    void shouldBeEqualsTasksWithSameId() {
        //Создаем 2 задачи с одинаковыми id
        Task task1 = new Task("Задача  1 (jUnit)",  "Описание  1", Status.NEW);
        Task task2 = new Task("Задача  2 (jUnit)",   "Описание  1", Status.NEW);
        taskManager.addTask(task1);
        taskManager2.addTask(task2);
        assertEquals(task1, task2);
    }
}
