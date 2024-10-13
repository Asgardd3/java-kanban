import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import managers.*;
import tasks.*;


import static org.junit.jupiter.api.Assertions.*;

class FileBackendTaskManagerTest extends TaskManagerTest<FileBackedTaskManager> {
    @Override
    void setManager() {
        try {
            File f = File.createTempFile("test", ".txt");
            taskManager = new FileBackedTaskManager(f.toPath().toString());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void shouldBeOkSaveAndLoadEmptyFile() {
        assertDoesNotThrow(() -> {
            File nFile = new File(taskManager.getFilePath());
            TaskManager taskManagerN = FileBackedTaskManager.loadFromFile(nFile);
        }, "Ошибка при попытке загрузки пустого файла");
    }

    @Test
    void shouldBeOkSaveFewTasksInFile() throws ManagerSaveException, TaskOverloadException {
        assertDoesNotThrow(() -> {
            Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
            taskManager.addTask(task1);
            Task task2 = new Task("Задача 2", "Описание 2", Status.NEW, LocalDateTime.now().plusHours(1), Duration.ofMinutes(30));
            taskManager.addTask(task2);
            Task task3 = new Task("Задача 3", "Описание 3", Status.NEW, LocalDateTime.now().plusHours(2), Duration.ofMinutes(30));
            taskManager.addTask(task3);
            File nFile = new File(taskManager.getFilePath());
            FileReader fileReader = new FileReader(nFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line;
            int count = 0;
            while ((line = bufferedReader.readLine()) != null) {
                count++;
            }
            if (count != 4) {
                throw new Exception();
            }
            assertEquals(count, 4);
        }, "Сохранение нескольких задач");

    }

    @Test
    void shouldBeOkLoadFewTasksFromFile() throws ManagerSaveException, TaskOverloadException {
        assertDoesNotThrow(() -> {
            Task task1 = new Task("Задача 1", "Описание 1", Status.NEW, LocalDateTime.now(), Duration.ofMinutes(30));
            taskManager.addTask(task1);
            Task task2 = new Task("Задача 2", "Описание 2", Status.NEW, LocalDateTime.now().plusHours(1), Duration.ofMinutes(30));
            taskManager.addTask(task2);
            Task task3 = new Task("Задача 3", "Описание 3", Status.NEW, LocalDateTime.now().plusHours(2), Duration.ofMinutes(30));
            taskManager.addTask(task3);
            TaskManager taskManagerN = FileBackedTaskManager.loadFromFile(new File(taskManager.getFilePath()));
            System.out.println(taskManagerN.getAllTasks().size());
            if (taskManagerN.getAllTasks().size() != 3) {
                throw new Exception();
            }
        }, "Ошибка при загрузке нескольких задач из файла");
    }
}
//