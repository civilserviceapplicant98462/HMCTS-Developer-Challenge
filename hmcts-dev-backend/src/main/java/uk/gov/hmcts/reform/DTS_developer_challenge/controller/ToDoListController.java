package uk.gov.hmcts.reform.DTS_developer_challenge.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Task;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.request.RequestTask;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.response.ResponseTask;
import uk.gov.hmcts.reform.DTS_developer_challenge.service.ToDoListService;
import uk.gov.hmcts.reform.DTS_developer_challenge.transformer.RequestTransformer;
import uk.gov.hmcts.reform.DTS_developer_challenge.transformer.ResponseTransformer;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ToDoListController {
    
    private final ToDoListService toDoListService;

    @Autowired
    public ToDoListController(ToDoListService toDoListService) {
        this.toDoListService = toDoListService;
    }

    @GetMapping("/tasks")
    ResponseEntity<List<Task>> getAllTasks() {

        return ResponseEntity.status(HttpStatus.OK).body(toDoListService.getAllTasks());
    }

    @GetMapping("/task/{id}")
    ResponseEntity<ResponseTask> getTask(@PathVariable Integer id) {

            Task task = toDoListService.getTask(id);

            ResponseTask responseTask = ResponseTransformer.toResponseTask(task);

            return ResponseEntity.status(HttpStatus.OK).body(responseTask);
    }

    @PostMapping("/task")
    ResponseEntity<ResponseTask> createTask(@RequestBody RequestTask requestTask) {

        requestTask.validate();

        Task task = toDoListService.createTask(RequestTransformer.toInternalTask(requestTask));

        ResponseTask responseTask = ResponseTransformer.toResponseTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseTask);
    }

    @PutMapping("/task")
    ResponseEntity<ResponseTask> updateTask(@RequestBody RequestTask requestTask) {

        requestTask.validate();

        Task task = toDoListService.updateTask(RequestTransformer.toInternalTask(requestTask));

        ResponseTask responseTask = ResponseTransformer.toResponseTask(task);

        return ResponseEntity.status(HttpStatus.OK).body(responseTask);
    }

    @DeleteMapping("/task/{id}")
    ResponseEntity<Map<String, String>> deleteTask(@PathVariable Integer id) {

        toDoListService.deleteTask(id);

        return ResponseEntity.ok(Map.of("message", "Task deleted"));
    }
}

