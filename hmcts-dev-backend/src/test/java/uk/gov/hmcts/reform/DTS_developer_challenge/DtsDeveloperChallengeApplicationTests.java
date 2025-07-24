package uk.gov.hmcts.reform.DTS_developer_challenge;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import uk.gov.hmcts.reform.DTS_developer_challenge.integrationtests.testcontainers.PostgresTestcontainer;

@ActiveProfiles("test")
@Testcontainers
@SpringBootTest
class DtsDeveloperChallengeApplicationTests {

	public static PostgresTestcontainer postgreSQLContainer = PostgresTestcontainer.getInstance();
    static {
        postgreSQLContainer.start();
    }

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

	@Test
	void contextLoads() {
	}

}
