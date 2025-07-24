package uk.gov.hmcts.reform.DTS_developer_challenge.integrationtests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import uk.gov.hmcts.reform.DTS_developer_challenge.integrationtests.testcontainers.PostgresTestcontainer;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class E2Etest {

    @LocalServerPort
    private int port;

    @Autowired
    public TestRestTemplate restTemplate;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public String baseUrl;

    public Endpoints endpoints;

    public static PostgreSQLContainer<PostgresTestcontainer> postgreSQLContainer = PostgresTestcontainer.getInstance();
    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }
    

    @BeforeEach
    void setUp() {
        baseUrl = "http://localhost:" + port;
        endpoints = new Endpoints(baseUrl, restTemplate);
    }

    @AfterEach
    void tearDown() {
        jdbcTemplate.execute("DELETE FROM task");
    }    
}
