package uk.gov.hmcts.reform.DTS_developer_challenge.integrationtests;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import uk.gov.hmcts.reform.DTS_developer_challenge.model.request.RequestTask;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.response.ResponseTask;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Importance;

class TaskCrudTest extends E2Etest {
    
    @Test
    void createTaskHappyPath() {
        java.sql.Timestamp due = new java.sql.Timestamp(System.currentTimeMillis());
        due.setTime(due.getTime() + 1000 * 60 * 60 * 24); // make due date 1 day in the future
        ResponseEntity<ResponseTask> createTaskResponse = endpoints.createTask(RequestTask.builder()
            .title("Test Task")
            .taskDescription("This is a test task description")
            .importance(Importance.HIGH)
            .due(due)
            .completed(false)
            .build());
        assertTrue(createTaskResponse.getStatusCode().is2xxSuccessful());
        ResponseTask createTaskResponseBody = createTaskResponse.getBody();
        assertNotNull(createTaskResponseBody);
        assertEquals(createTaskResponseBody, ResponseTask.builder()
            .id(createTaskResponseBody.getId())
            .title("Test Task")
            .taskDescription("This is a test task description")
            .importance(Importance.HIGH)
            .due(due)
            .completed(false)
            .build());
    }

    @Test
    void updateTaskHappyPath() {
        java.sql.Timestamp due = new java.sql.Timestamp(System.currentTimeMillis());
        due.setTime(due.getTime() + 1000 * 60 * 60 * 24); // make due date 1 day in the future
        ResponseTask createTaskResponse = endpoints.createTask(RequestTask.builder()
            .title("Test Task")
            .taskDescription("This is a test task description")
            .importance(Importance.HIGH)
            .due(due)
            .completed(false)
            .build())
            .getBody();
        assertNotNull(createTaskResponse);
        java.sql.Timestamp updatedDue = new java.sql.Timestamp(due.getTime());
        updatedDue.setTime(updatedDue.getTime() + 1000 * 60 * 60 * 24); // add another day to the due date
        ResponseEntity<ResponseTask> updateTaskResponse = endpoints.updateTask(RequestTask.builder()
            .id(createTaskResponse.getId())
            .title("Test Task Updated")
            .taskDescription("This is an updated test task description")
            .importance(Importance.MEDIUM)
            .due(updatedDue)
            .completed(true)
            .build());
        assertTrue(updateTaskResponse.getStatusCode().is2xxSuccessful());
        ResponseTask updateTaskResponseBody = updateTaskResponse.getBody();
        assertNotNull(updateTaskResponseBody);
        assertEquals(updateTaskResponseBody, ResponseTask.builder()
            .id(createTaskResponse.getId())
            .title("Test Task Updated")
            .taskDescription("This is an updated test task description")
            .importance(Importance.MEDIUM)
            .due(updatedDue)
            .completed(true)
            .build());
    }

    @Test
    void getTaskHappyPath() {
        java.sql.Timestamp due = new java.sql.Timestamp(System.currentTimeMillis());
        due.setTime(due.getTime() + 1000 * 60 * 60 * 24); // make due date 1 day in the future
        ResponseTask createTaskResponse = endpoints.createTask(RequestTask.builder()
            .title("Test Task")
            .taskDescription("This is a test task description")
            .importance(Importance.HIGH)
            .due(due)
            .completed(false)
            .build())
            .getBody();
        assertNotNull(createTaskResponse);
        ResponseEntity<ResponseTask> getTaskResponse = endpoints.getTask(createTaskResponse.getId());
        assertTrue(getTaskResponse.getStatusCode().is2xxSuccessful());
        ResponseTask getTaskResponseBody = getTaskResponse.getBody();
        assertNotNull(getTaskResponseBody);
        assertEquals(getTaskResponseBody, ResponseTask.builder()
            .id(createTaskResponse.getId())
            .title("Test Task")
            .taskDescription("This is a test task description")
            .importance(Importance.HIGH)
            .due(due)
            .completed(false)
            .build());
    }

    @Test
    void deleteTaskHappyPath() {
        java.sql.Timestamp due = new java.sql.Timestamp(System.currentTimeMillis());
        due.setTime(due.getTime() + 1000 * 60 * 60 * 24); // make due date 1 day in the future
        ResponseTask createTaskResponse = endpoints.createTask(RequestTask.builder()
            .title("Test Task")
            .taskDescription("This is a test task description")
            .importance(Importance.HIGH)
            .due(due)
            .completed(false)
            .build())
            .getBody();
        assertNotNull(createTaskResponse);
        ResponseEntity<String> deleteTaskResponse = endpoints.deleteTask(createTaskResponse.getId());
        assertTrue(deleteTaskResponse.getStatusCode().is2xxSuccessful());
        ResponseEntity<ResponseTask> getTaskResponse = endpoints.getTask(createTaskResponse.getId());
        assertTrue(getTaskResponse.getStatusCode().is4xxClientError());
    }

    @Test
    void getTaskNotFound() {
        ResponseEntity<ResponseTask> response = endpoints.getTask(2);
        assertTrue(response.getStatusCode().is4xxClientError());
    }
    
    // I could add more tests, but I think these are enough to demonstrate the testing approach
}
