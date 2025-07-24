package uk.gov.hmcts.reform.DTS_developer_challenge.transformer;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Importance;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Task;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.request.RequestTask;

class RequestTransformerTest {

    @Test
    void toInternalTask_mapsFieldsCorrectly() {
        Timestamp due = new Timestamp(System.currentTimeMillis());
        RequestTask request = RequestTask.builder()
            .id(5)
            .title("Test")
            .taskDescription("desc")
            .importance(Importance.HIGH)
            .due(due)
            .completed(false)
            .build();

        Task task = RequestTransformer.toInternalTask(request);

        assertEquals(request.getId(), task.getId());
        assertEquals(request.getTitle(), task.getTitle());
        assertEquals(request.getTaskDescription(), task.getTaskDescription());
        assertEquals(request.getImportance(), task.getImportance());
        assertEquals(request.getDue(), task.getDue());
        assertEquals(request.getCompleted(), task.getCompleted());
    }
}