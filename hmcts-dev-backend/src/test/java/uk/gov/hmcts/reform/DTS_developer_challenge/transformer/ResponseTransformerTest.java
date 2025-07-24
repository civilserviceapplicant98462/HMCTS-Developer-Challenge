package uk.gov.hmcts.reform.DTS_developer_challenge.transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Importance;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Task;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.response.ResponseTask;

class ResponseTransformerTest {

    @Test
    void toResponseTask_mapsFieldsCorrectly() {
        Timestamp due = new Timestamp(System.currentTimeMillis());
        Task task = Task.builder()
            .id(10)
            .title("Test")
            .taskDescription("desc")
            .importance(Importance.LOW)
            .due(due)
            .completed(true)
            .build();

        ResponseTask response = ResponseTransformer.toResponseTask(task);

        assertEquals(task.getId(), response.getId());
        assertEquals(task.getTitle(), response.getTitle());
        assertEquals(task.getTaskDescription(), response.getTaskDescription());
        assertEquals(task.getImportance(), response.getImportance());
        assertEquals(task.getDue(), response.getDue());
        assertEquals(task.getCompleted(), response.getCompleted());
    }
}