import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import managers.*;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    private TaskManager taskManager;
    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }
    @Test
    void shouldBeEqualsSubTasksWithSameId() throws ManagerSaveException, TaskOverloadException {
        //Создаем 2 задачи с одинаковыми id
        Epic epic1  = new Epic("Эпик  1",  "Описание  1");
        taskManager.addEpic(epic1);
        SubTask subTask1 = new SubTask("Задача  1  (jUnit)", "Описание  1", Status.NEW, 1, LocalDateTime.now(), Duration.ofMinutes(30));
        SubTask subTask2 = new SubTask("Задача  2  (jUnit)", "Описание  1", Status.NEW, 1, LocalDateTime.now().plus(Duration.ofMinutes(40)), Duration.ofMinutes(30));
        try {
            taskManager.addSubTask(subTask1);
            taskManager.addSubTask(subTask2);
        } catch (TaskOverloadException e) {
            fail();
        }

        subTask2.setId(subTask1.getId());
        assertEquals(subTask1, subTask2);
    }

    @Test
    void shouldBeNegativeMakeSubtaskHisEpic() throws ManagerSaveException, TaskOverloadException {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        taskManager.addEpic(epic1);
        SubTask subTask2 = new SubTask("Задача  2  (jUnit)", "Описание  1", Status.NEW, 1, LocalDateTime.now(), Duration.ofMinutes(30));
        try {
            taskManager.addSubTask(subTask2);
        } catch (TaskOverloadException e) {
            fail();
        }
        subTask2.setEpicId(subTask2.getId());
        assertFalse(subTask2.getEpicId() == subTask2.getId());
    }
}