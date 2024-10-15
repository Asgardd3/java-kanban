import managers.InMemoryTaskManager;

class InMemoryTaskManagerTest extends TaskManagerTest<InMemoryTaskManager> {
    @Override
    void setManager() {
        taskManager = new InMemoryTaskManager();
    }
}