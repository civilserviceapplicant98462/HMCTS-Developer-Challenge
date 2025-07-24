package uk.gov.hmcts.reform.DTS_developer_challenge.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import uk.gov.hmcts.reform.DTS_developer_challenge.exception.TaskNotFoundException;

class ExceptionControllerTest {

    private final ExceptionController controller = new ExceptionController();

    @Test
    void handleEmptyResult_returnsNotFound() {
        ResponseEntity<Map<String, String>> response = controller.handleEmptyResult(new EmptyResultDataAccessException(1));
        assertEquals(404, response.getStatusCode().value());
        assertEquals("Task not found", response.getBody().get("error"));
    }

    @Test
    void handleTaskNotFound_returnsNotFound() {
        ResponseEntity<Map<String, String>> response = controller.handleTaskNotFound(new TaskNotFoundException(1));
        assertEquals(404, response.getStatusCode().value());
        assertEquals("Task not found", response.getBody().get("error"));
    }

    @Test
    void handleIllegalArgument_returnsBadRequest() {
        ResponseEntity<Map<String, String>> response = controller.handleIllegalArgument(new IllegalArgumentException("bad input"));
        assertEquals(400, response.getStatusCode().value());
        assertEquals("bad input", response.getBody().get("error"));
    }
}