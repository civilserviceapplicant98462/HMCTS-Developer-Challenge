package uk.gov.hmcts.reform.DTS_developer_challenge.integrationtests;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import uk.gov.hmcts.reform.DTS_developer_challenge.model.request.RequestTask;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.response.ResponseTask;

public class Endpoints {

    private String baseUrl;
    private TestRestTemplate restTemplate;

    public Endpoints (String baseUrl, TestRestTemplate restTemplate) {
        this.baseUrl = baseUrl;
        this.restTemplate = restTemplate;

    }

    public ResponseEntity<ResponseTask> getTask(Integer id) {
        try {
            return restTemplate.getForEntity(baseUrl + "/task/" + id, ResponseTask.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    public ResponseEntity<ResponseTask> createTask(RequestTask requestTask) {
        return restTemplate.postForEntity(baseUrl+"/task", new HttpEntity<>(requestTask), ResponseTask.class);
    }

    public ResponseEntity<ResponseTask> updateTask(RequestTask requestTask) {
        try {
            return restTemplate.exchange(baseUrl+"/task", HttpMethod.PUT, new HttpEntity<>(requestTask), ResponseTask.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }

    public ResponseEntity<String> deleteTask(Integer id) {
        try {
            return restTemplate.exchange(baseUrl+"/task/"+String.valueOf(id), HttpMethod.DELETE, new HttpEntity<>(null), String.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(e.getStatusCode()).build();
        }
    }
    
}
