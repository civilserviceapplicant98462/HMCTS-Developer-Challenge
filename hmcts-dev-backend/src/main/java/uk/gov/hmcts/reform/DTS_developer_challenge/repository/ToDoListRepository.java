package uk.gov.hmcts.reform.DTS_developer_challenge.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import uk.gov.hmcts.reform.DTS_developer_challenge.exception.TaskNotFoundException;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Importance;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Task;

@Repository
public class ToDoListRepository {

    private static final String ID_COLUMN = "id";
    private static final String TITLE_COLUMN = "title";
    private static final String IMPORTANCE_COLUMN = "importance";
    private static final String TASK_DESCRIPTION_COLUMN = "task_description";
    private static final String DUE_COLUMN = "due";
    private static final String COMPLETED_COLUMN = "completed";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public ToDoListRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Task> getAllTasks() {

        String getAllTasksSql = "SELECT * FROM task LIMIT 1000;";

        return jdbcTemplate.query(getAllTasksSql, (rs, rowNum) -> {
           return Task.builder()
            .id(rs.getInt(ID_COLUMN))
            .title(rs.getString(TITLE_COLUMN))
            .taskDescription(rs.getString(TASK_DESCRIPTION_COLUMN))
            .importance(Importance.valueOf(rs.getString(IMPORTANCE_COLUMN)))
            .due(Timestamp.valueOf(rs.getString(DUE_COLUMN)))
            .completed(rs.getBoolean(COMPLETED_COLUMN))
            .build();
        });
    }

    public Task getTask(Integer id) throws EmptyResultDataAccessException {

        String getTaskSql = "SELECT * FROM task WHERE id=:id;";

        SqlParameterSource paramSource = new MapSqlParameterSource()
            .addValue(ID_COLUMN, id);
        
        return jdbcTemplate.queryForObject(getTaskSql, paramSource, (rs, rowNum) -> {
            return Task.builder()
            .id(rs.getInt(ID_COLUMN))
            .title(rs.getString(TITLE_COLUMN))
            .taskDescription(rs.getString(TASK_DESCRIPTION_COLUMN))
            .importance(Importance.valueOf(rs.getString(IMPORTANCE_COLUMN)))
            .due(Timestamp.valueOf(rs.getString(DUE_COLUMN)))
            .completed(rs.getBoolean(COMPLETED_COLUMN))
            .build();
        }); 
    }

    public Task createTask(Task task) {

        String createTaskSql = "INSERT INTO task (title, task_description, importance, due, completed) VALUES (:title, :task_description, :importance, :due, :completed) RETURNING id;";

        SqlParameterSource paramSource = new MapSqlParameterSource()
            .addValue(TITLE_COLUMN, task.getTitle())
            .addValue(TASK_DESCRIPTION_COLUMN, task.getTaskDescription())
            .addValue(IMPORTANCE_COLUMN, task.getImportance().name())
            .addValue(DUE_COLUMN, task.getDue())
            .addValue(COMPLETED_COLUMN, task.getCompleted());

        Integer taskId = jdbcTemplate.queryForObject(createTaskSql, paramSource, Integer.class);

        return getTask(taskId);
    }

    public Task updateTask(Task task) throws TaskNotFoundException {

        String updateTaskSql = "UPDATE task SET (title, task_description, importance, due, completed) = (:title, :task_description, :importance, :due, :completed) WHERE id = :id;";

        SqlParameterSource paramSource = new MapSqlParameterSource()
            .addValue(ID_COLUMN, task.getId())
            .addValue(TITLE_COLUMN, task.getTitle())
            .addValue(TASK_DESCRIPTION_COLUMN, task.getTaskDescription())
            .addValue(IMPORTANCE_COLUMN, task.getImportance().name())
            .addValue(DUE_COLUMN, task.getDue())
            .addValue(COMPLETED_COLUMN, task.getCompleted());

        jdbcTemplate.update(updateTaskSql, paramSource);

        try {
            return getTask(task.getId());
        } catch (EmptyResultDataAccessException e) {
            throw new TaskNotFoundException(task.getId());
        }
    }

    public void deleteTask(Integer id) throws TaskNotFoundException {

        String deleteTaskSql = "DELETE FROM task WHERE id = :id";

        SqlParameterSource paramSource = new MapSqlParameterSource()
            .addValue(ID_COLUMN, id);

        int rowsAffected = jdbcTemplate.update(deleteTaskSql, paramSource);

        if (rowsAffected == 0) {
        throw new TaskNotFoundException(id);
        }    
    }

}
