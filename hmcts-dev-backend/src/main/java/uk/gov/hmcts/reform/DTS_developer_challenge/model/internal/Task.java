package uk.gov.hmcts.reform.DTS_developer_challenge.model.internal;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Task {

    private Integer id;
    private String title;
    private String taskDescription;
    private Importance importance;    
    private Timestamp due;
    private Boolean completed;
}
