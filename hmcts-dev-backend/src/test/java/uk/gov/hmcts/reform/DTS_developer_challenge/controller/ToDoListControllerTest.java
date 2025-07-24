package uk.gov.hmcts.reform.DTS_developer_challenge.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Importance;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Task;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.request.RequestTask;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.response.ResponseTask;
import uk.gov.hmcts.reform.DTS_developer_challenge.service.ToDoListService;

class ToDoListControllerTest {

    @Mock
    private ToDoListService service;

    @InjectMocks
    private ToDoListController controller;

    private Timestamp futureDue;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        futureDue = new Timestamp(System.currentTimeMillis() + 1000000);
    }

    @Test
    void getAllTasks_returnsList() {
        Task task = Task.builder()
            .id(1)
            .title("Test")
            .taskDescription("desc")
            .importance(Importance.HIGH)
            .due(futureDue)
            .completed(false)
            .build();
        when(service.getAllTasks()).thenReturn(List.of(task));

        ResponseEntity<List<Task>> response = controller.getAllTasks();
        assertEquals(OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Test", response.getBody().get(0).getTitle());
    }

    @Test
    void getTask_returnsResponseTask() {
        Task task = Task.builder()
            .id(1)
            .title("Test")
            .taskDescription("desc")
            .importance(Importance.HIGH)
            .due(futureDue)
            .completed(false)
            .build();
        when(service.getTask(1)).thenReturn(task);

        ResponseEntity<ResponseTask> response = controller.getTask(1);
        assertEquals(OK, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
        assertEquals("Test", response.getBody().getTitle());
    }

    @Test
    void createTask_returnsCreatedTask() {
        RequestTask request = RequestTask.builder()
            .title("Test")
            .taskDescription("desc")
            .importance(Importance.HIGH)
            .due(futureDue)
            .completed(false)
            .build();
        Task created = Task.builder()
            .id(1)
            .title("Test")
            .taskDescription("desc")
            .importance(Importance.HIGH)
            .due(futureDue)
            .completed(false)
            .build();
        when(service.createTask(any())).thenReturn(created);

        ResponseEntity<ResponseTask> response = controller.createTask(request);
        assertEquals(CREATED, response.getStatusCode());
        assertEquals(1, response.getBody().getId());
    }

    @Test
    void updateTask_returnsUpdatedTask() {
        RequestTask request = RequestTask.builder()
            .id(1)
            .title("Updated")
            .taskDescription("desc")
            .importance(Importance.MEDIUM)
            .due(futureDue)
            .completed(true)
            .build();
        Task updated = Task.builder()
            .id(1)
            .title("Updated")
            .taskDescription("desc")
            .importance(Importance.MEDIUM)
            .due(futureDue)
            .completed(true)
            .build();
        when(service.updateTask(any())).thenReturn(updated);

        ResponseEntity<ResponseTask> response = controller.updateTask(request);
        assertEquals(OK, response.getStatusCode());
        assertEquals("Updated", response.getBody().getTitle());
    }

    @Test
    void deleteTask_returnsOk() {
        doNothing().when(service).deleteTask(1);

        ResponseEntity<?> response = controller.deleteTask(1);
        assertEquals(OK, response.getStatusCode());
        assertTrue(response.getBody().toString().contains("Task deleted"));
    }
}