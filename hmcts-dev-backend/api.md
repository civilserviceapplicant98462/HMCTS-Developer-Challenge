# To-Do List API

Base URL: `http://localhost:8080`

## Endpoints

### Get All Tasks

- **GET** `/tasks`
- **Description:** Retrieve a list of all tasks.
- **Response:**  
  `200 OK`  
  ```json
  [
    {
      "id": 1,
      "title": "Task title",
      "taskDescription": "Description",
      "importance": "HIGH",
      "due": "2025-09-01T12:00:00.000+00:00",
      "completed": false
    }
  ]
  ```

---

### Get Task by ID

- **GET** `/task/{id}`
- **Description:** Retrieve a single task by its ID.
- **Response:**  
  `200 OK`  
  ```json
  {
    "id": 1,
    "title": "Task title",
    "taskDescription": "Description",
    "importance": "HIGH",
    "due": "2025-09-01T12:00:00.000+00:00",
    "completed": false
  }
  ```
- **Error:**  
  `404 Not Found`  
  ```json
  { "error": "Task not found" }
  ```

---

### Create Task

- **POST** `/task`
- **Description:** Create a new task.
- **Request Body:**
  ```json
  {
    "title": "Task title",
    "taskDescription": "Description",
    "importance": "HIGH",
    "due": "2025-09-01T12:00:00.000+00:00",
    "completed": false
  }
  ```
- **Response:**  
  `201 Created`  
  ```json
  {
    "id": 2,
    "title": "Task title",
    "taskDescription": "Description",
    "importance": "HIGH",
    "due": "2025-09-01T12:00:00.000+00:00",
    "completed": false
  }
  ```
- **Error:**  
  `400 Bad Request`  
  ```json
  { "error": "Title cannot be empty. Importance cannot be null. Due date cannot be in the past. Completed status cannot be null." }
  ```

---

### Update Task

- **PUT** `/task`
- **Description:** Update an existing task.
- **Request Body:** (same as Create Task, but must include `id`)
  ```json
  {
    "id": 2,
    "title": "Updated title",
    "taskDescription": "Updated description",
    "importance": "MEDIUM",
    "due": "2025-09-02T12:00:00.000+00:00",
    "completed": true
  }
  ```
- **Response:**  
  `200 OK`  
  ```json
  {
    "id": 2,
    "title": "Updated title",
    "taskDescription": "Updated description",
    "importance": "MEDIUM",
    "due": "2025-09-02T12:00:00.000+00:00",
    "completed": true
  }
  ```
- **Error:**  
  `404 Not Found`  
  ```json
  { "error": "Task not found" }
  ```

---

### Delete Task

- **DELETE** `/task/{id}`
- **Description:** Delete a task by its ID.
- **Response:**  
  `200 OK`  
  ```json
  { "message": "Task deleted" }
  ```
- **Error:**  
  `404 Not Found`  
  ```json
  { "error": "Task not found" }
  ```

---

## Task Object

| Field           | Type      | Description                |
|-----------------|-----------|----------------------------|
| id              | Integer   | Unique identifier          |
| title           | String    | Task title                 |
| taskDescription | String    | Task description           |
| importance      | String    | Importance: HIGH/MEDIUM/LOW|
| due             | Timestamp | Due date/time              |
| completed       | Boolean   | Completion status          |

---

## Error Responses

| Status Code | Example Body                        | When                                      |
|-------------|-------------------------------------|--------------------------------------------|
| 400         | `{ "error": "..." }`               | Validation errors (bad input)              |
| 404         | `{ "error": "Task not found" }`     | Task does not exist                        |
