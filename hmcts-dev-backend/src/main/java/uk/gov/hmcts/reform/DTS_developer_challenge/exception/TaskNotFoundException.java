package uk.gov.hmcts.reform.DTS_developer_challenge.exception;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(Integer id) {
        super("Task with id " + id + " not found.");
    }
}
