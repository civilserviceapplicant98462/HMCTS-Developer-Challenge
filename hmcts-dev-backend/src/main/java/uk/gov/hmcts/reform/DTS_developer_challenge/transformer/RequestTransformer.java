package uk.gov.hmcts.reform.DTS_developer_challenge.transformer;

import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Task;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.request.RequestTask;

public class RequestTransformer {

    private RequestTransformer() {
    }
    
    public static Task toInternalTask(RequestTask task) {
        return Task.builder()
        .id(task.getId())
        .title(task.getTitle())
        .taskDescription(task.getTaskDescription())
        .importance(task.getImportance())
        .due(task.getDue())
        .completed(task.getCompleted())
        .build();
    }
}
