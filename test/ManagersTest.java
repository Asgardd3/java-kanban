import managers.ManagerSaveException;
import managers.Managers;
import managers.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tasks.TaskOverloadException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagersTest {
    private TaskManager taskManager;

    @BeforeEach
    void beforeEach() throws ManagerSaveException, TaskOverloadException {
        taskManager = Managers.getDefault();
    }

    @Test
    void shouldPositiveReturnFullInitTaskManager() {
        assertEquals(taskManager.getAllTasks().size() + taskManager.getAllSubTasks().size() + taskManager.getAllEpics().size() +
                taskManager.getHistory().size(), 0);

    }


}