package uk.gov.hmcts.reform.DTS_developer_challenge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import uk.gov.hmcts.reform.DTS_developer_challenge.exception.TaskNotFoundException;
import uk.gov.hmcts.reform.DTS_developer_challenge.model.internal.Task;
import uk.gov.hmcts.reform.DTS_developer_challenge.repository.ToDoListRepository;

@Service
public class ToDoListService {

    private final ToDoListRepository toDoListRepository;

    @Autowired
    public ToDoListService(ToDoListRepository toDoListRepository) {
        this.toDoListRepository = toDoListRepository;
    }

    public List<Task> getAllTasks()  {
        return toDoListRepository.getAllTasks();
    }

    public Task createTask(Task task) {
        return toDoListRepository.createTask(task);
    }

    public Task getTask(Integer id) throws EmptyResultDataAccessException {
        return toDoListRepository.getTask(id);
    }

    public Task updateTask(Task task) throws TaskNotFoundException {
        return toDoListRepository.updateTask(task);
    }

    public void deleteTask(Integer id) throws TaskNotFoundException {
        toDoListRepository.deleteTask(id);
    }

}