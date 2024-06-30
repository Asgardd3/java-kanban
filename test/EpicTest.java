import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class EpicTest {
    TaskManager taskManager;
    TaskManager taskManager2;

    @BeforeEach
    void beforeEach() {
        taskManager = new InMemoryTaskManager();
        taskManager2 = new InMemoryTaskManager();
    }

    @Test
    void shouldBeEqualsEpicsWithSameId() {
        Epic epic1 = new Epic("Ёпик 1", "ќписание 1"); //taskManager.addEpic();
        Epic epic2 = new Epic("Ёпик 2", "ќписание 1");
        this.taskManager.addEpic(epic1);
        this.taskManager2.addEpic(epic2);
        assertEquals(epic1, epic2);
    }

    @Test
    void shouldBeNegativeAddEpicInHisSubtasks () {
        Epic epic1 = new Epic("Ёпик 1", "ќписание 1");
        //TaskManager taskManager = new InMemoryTaskManager();
        this.taskManager.addEpic(epic1);
        epic1.addSubTasksId(epic1.getId());
        assertFalse(taskManager.getAllSubTasksByEpicId(epic1.getId()).equals(epic1.getId()));
    }
}

/*

проверьте, что задачи с заданным id и сгенерированным id не конфликтуют внутри менеджера;
создайте тест, в котором провер€етс€ неизменность задачи (по всем пол€м) при добавлении задачи в менеджер
убедитесь, что задачи, добавл€емые в HistoryManager, сохран€ют предыдущую версию задачи и еЄ данных.

 */