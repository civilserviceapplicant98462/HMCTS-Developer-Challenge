package uk.gov.hmcts.reform.DTS_developer_challenge.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import uk.gov.hmcts.reform.DTS_developer_challenge.exception.TaskNotFoundException;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Importance;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Task;

class ToDoListRepositoryTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private ToDoListRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getTask_returnsTask() {
        Task mockTask = Task.builder()
            .id(1)
            .title("Test")
            .taskDescription("desc")
            .importance(Importance.HIGH)
            .due(new Timestamp(System.currentTimeMillis()))
            .completed(false)
            .build();

        when(jdbcTemplate.queryForObject(anyString(), any(SqlParameterSource.class), any(org.springframework.jdbc.core.RowMapper.class)))
            .thenReturn(mockTask);

        Task result = repository.getTask(1);
        assertEquals(mockTask, result);
    }

    @Test
    void getTask_throwsException_whenNotFound() {
        when(jdbcTemplate.queryForObject(anyString(), any(SqlParameterSource.class), any(org.springframework.jdbc.core.RowMapper.class)))
            .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(EmptyResultDataAccessException.class, () -> repository.getTask(999));
    }

    @Test
    void createTask_returnsCreatedTask() {
        Task inputTask = Task.builder()
            .title("Test")
            .taskDescription("desc")
            .importance(Importance.HIGH)
            .due(new Timestamp(System.currentTimeMillis()))
            .completed(false)
            .build();

        when(jdbcTemplate.queryForObject(contains("INSERT INTO task"), any(SqlParameterSource.class), eq(Integer.class)))
            .thenReturn(42);
        Task returnedTask = Task.builder().id(42).title("Test").taskDescription("desc")
            .importance(Importance.HIGH).due(inputTask.getDue()).completed(false).build();
        when(jdbcTemplate.queryForObject(contains("SELECT * FROM task"), any(SqlParameterSource.class), any(org.springframework.jdbc.core.RowMapper.class)))
            .thenReturn(returnedTask);

        Task result = repository.createTask(inputTask);
        assertEquals(42, result.getId());
        assertEquals("Test", result.getTitle());
    }

    @Test
    void deleteTask_deletesSuccessfully() {
        when(jdbcTemplate.update(anyString(), any(SqlParameterSource.class))).thenReturn(1);
        assertDoesNotThrow(() -> repository.deleteTask(1));
    }

    @Test
    void deleteTask_throwsException_whenNotFound() {
        when(jdbcTemplate.update(anyString(), any(SqlParameterSource.class))).thenReturn(0);
        assertThrows(TaskNotFoundException.class, () -> repository.deleteTask(999));
    }

    @Test
    void getAllTasks_returnsList() {
        Task mockTask = Task.builder()
            .id(1)
            .title("Test")
            .taskDescription("desc")
            .importance(Importance.HIGH)
            .due(new Timestamp(System.currentTimeMillis()))
            .completed(false)
            .build();

        when(jdbcTemplate.<Task>query(anyString(), any(org.springframework.jdbc.core.RowMapper.class)))
            .thenReturn(List.of(mockTask));

        List<Task> result = repository.getAllTasks();
        assertEquals(1, result.size());
        assertEquals("Test", result.get(0).getTitle());
    }
}