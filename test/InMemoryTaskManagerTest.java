import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import managers.*;
import tasks.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @Override
    void setManager() {
        taskManager = new InMemoryTaskManager();
    }
}