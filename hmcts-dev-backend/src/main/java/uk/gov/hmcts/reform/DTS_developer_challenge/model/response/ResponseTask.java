package uk.gov.hmcts.reform.DTS_developer_challenge.model.response;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Importance;

@Builder
@Data
public class ResponseTask {

    private Integer id;
    private String title;
    private String taskDescription;
    private Importance importance;
    private Timestamp due;
    private Boolean completed;
}

