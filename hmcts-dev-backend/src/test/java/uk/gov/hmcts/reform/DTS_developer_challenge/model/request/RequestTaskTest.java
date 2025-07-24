package uk.gov.hmcts.reform.DTS_developer_challenge.model.request;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Importance;

class RequestTaskTest {

    @Test
    void validate_passesWithValidData() {
        RequestTask task = RequestTask.builder()
            .title("Test")
            .importance(Importance.HIGH)
            .due(new Timestamp(System.currentTimeMillis() + 10000))
            .completed(false)
            .taskDescription("A description")
            .id(1)
            .build();
        assertDoesNotThrow(task::validate);
    }

    @Test
    void validate_throwsExceptionForMissingFields() {
        RequestTask task = RequestTask.builder()
            .due(new Timestamp(System.currentTimeMillis() + 10000))
            .build();
        Exception ex = assertThrows(IllegalArgumentException.class, task::validate);
        assertTrue(ex.getMessage().contains("Title"));
        assertTrue(ex.getMessage().contains("Importance"));
        assertTrue(ex.getMessage().contains("Completed"));
    }

    @Test
    void validate_throwsExceptionForPastDueDate() {
        RequestTask task = RequestTask.builder()
            .title("Test")
            .importance(Importance.HIGH)
            .due(new Timestamp(System.currentTimeMillis() - 10000))
            .completed(false)
            .taskDescription("desc")
            .id(2)
            .build();
        Exception ex = assertThrows(IllegalArgumentException.class, task::validate);
        assertTrue(ex.getMessage().contains("Due date"));
    }
}