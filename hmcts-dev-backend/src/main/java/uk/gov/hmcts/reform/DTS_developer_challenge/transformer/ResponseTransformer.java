package uk.gov.hmcts.reform.DTS_developer_challenge.transformer;

import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Task;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.response.ResponseTask;

public class ResponseTransformer {

    private ResponseTransformer() {
    }
    
    public static ResponseTask toResponseTask(Task task) {
        return ResponseTask.builder()
        .id(task.getId())
        .title(task.getTitle())
        .taskDescription(task.getTaskDescription())
        .importance(task.getImportance())
        .due(task.getDue())
        .completed(task.getCompleted())
        .build();
    }
}
