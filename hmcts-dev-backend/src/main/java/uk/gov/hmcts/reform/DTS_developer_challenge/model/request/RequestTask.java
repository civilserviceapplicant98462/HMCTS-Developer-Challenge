package uk.gov.hmcts.reform.DTS_developer_challenge.model.request;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Importance;

@Builder
@Data
public class RequestTask {

    private Integer id;
    private String title;
    private String taskDescription;
    private Importance importance;    
    private Timestamp due;
    private Boolean completed;

    public void validate() {
        String errorString = "";

        if (title == null || title.isEmpty()) {
            errorString += "Title cannot be empty. ";
        }

        if (importance == null) {
            errorString += "Importance cannot be null. ";
        }

        if (due.before(new Timestamp(System.currentTimeMillis()))) {
            errorString += "Due date cannot be in the past. ";
        }

        if (completed == null) {
            errorString += "Completed status cannot be null. ";
        }

        if (!errorString.isEmpty()) {
            throw new IllegalArgumentException(errorString);
        }
    }
}