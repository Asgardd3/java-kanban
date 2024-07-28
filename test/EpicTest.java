import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EpicTest {
    private TaskManager taskManager;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
    }

    @Test
    void shouldBeEqualsEpicsWithSameId() {
        Epic epic1 = new Epic("Эпик 1", "Описание 1"); //taskManager.addEpic();
        Epic epic2 = new Epic("Эпик 2", "Описание 1");
        this.taskManager.addEpic(epic1);
        epic2.setId(epic1.getId());
        assertEquals(epic1, epic2);
    }

    @Test
    void shouldBeNegativeAddEpicInHisSubtasks () {
        Epic epic1 = new Epic("Эпик 1", "Описание 1");
        //TaskManager taskManager = new InMemoryTaskManager();
        this.taskManager.addEpic(epic1);
        epic1.addSubTasksId(epic1.getId());
        assertFalse(taskManager.getAllSubTasksByEpicId(epic1.getId()).equals(epic1.getId()));
    }
}