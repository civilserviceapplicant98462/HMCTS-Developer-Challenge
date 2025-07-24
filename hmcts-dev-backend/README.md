# Requirements
- Java 21
- Docker
- Docker Compose


## To Run Locally
1. Start the database: 
``` 
docker compose up 
``` 
For subsequent runs, you can use:
```
docker compose start
```
2. Start the application:
```
./mvnw spring-boot:run
```
3. The application will run on `http://localhost:8090`, an actuator info endpoint is exposed for the purpose of health checks, at `http://localhost:8090/actuator/info`

## Running tests
Simply run 
```
./mvnw test
```
This will run both integration and unit tests. There is no need to run the app or the database locally to run the tests - a testcontainer database is created upon running the tests, that is cleared of data between individual test runs. 
This ensures the tests are entirely isolated, and do not rely on external dependencies. 

# About the app
The app provides a simple CRUD interface for a database, allowing the management of 'tasks' which are objects of the form 
```json
{
    "id": 1,
    "title":"title1",
    "taskDescription":"description1",
    "importance":"HIGH",
    "due":"2024-06-02T15:04:05Z"
} 
```

the `id` starts at `1` and auto-increments, so it does not need to be provided for POST requests. The `id` must be provided for PUT requests, otherwise a 400 is returned.

