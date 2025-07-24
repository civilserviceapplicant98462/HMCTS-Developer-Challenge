package uk.gov.hmcts.reform.DTS_developer_challenge.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import uk.gov.hmcts.reform.DTS_developer_challenge.exception.TaskNotFoundException;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Importance;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Task;
import uk.gov.hmcts.reform.DTS_developer_challenge.repository.ToDoListRepository;

class ToDoListServiceTest {

    @Mock
    private ToDoListRepository repository;

    @InjectMocks
    private ToDoListService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllTasks_returnsTasks() {
        Task task = Task.builder()
            .id(1)
            .title("Test")
            .taskDescription("desc")
            .importance(Importance.HIGH)
            .due(new Timestamp(System.currentTimeMillis()))
            .completed(false)
            .build();
        when(repository.getAllTasks()).thenReturn(List.of(task));

        List<Task> result = service.getAllTasks();
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getTitle());
    }

    @Test
    void getTask_returnsTask() {
        Task task = Task.builder().id(1).title("Test").build();
        when(repository.getTask(1)).thenReturn(task);

        Task result = service.getTask(1);
        assertEquals(1, result.getId());
    }

    @Test
    void getTask_throwsException_whenNotFound() {
        when(repository.getTask(99)).thenThrow(new TaskNotFoundException(99));
        assertThrows(TaskNotFoundException.class, () -> service.getTask(99));
    }

    @Test
    void createTask_returnsCreatedTask() {
        Task input = Task.builder().title("Test").build();
        Task created = Task.builder().id(1).title("Test").build();
        when(repository.createTask(input)).thenReturn(created);

        Task result = service.createTask(input);
        assertEquals(1, result.getId());
        assertEquals("Test", result.getTitle());
    }

    @Test
    void updateTask_returnsUpdatedTask() {
        Task input = Task.builder().id(1).title("Updated").build();
        Task updated = Task.builder().id(1).title("Updated").build();
        when(repository.updateTask(input)).thenReturn(updated);

        Task result = service.updateTask(input);
        assertEquals("Updated", result.getTitle());
    }

    @Test
    void deleteTask_delegatesToRepository() {
        doNothing().when(repository).deleteTask(1);
        assertDoesNotThrow(() -> service.deleteTask(1));
        verify(repository, times(1)).deleteTask(1);
    }
}