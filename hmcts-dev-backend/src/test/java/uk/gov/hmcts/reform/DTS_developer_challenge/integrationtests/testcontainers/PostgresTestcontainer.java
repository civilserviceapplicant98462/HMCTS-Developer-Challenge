package uk.gov.hmcts.reform.DTS_developer_challenge.integrationtests.testcontainers;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgresTestcontainer extends PostgreSQLContainer<PostgresTestcontainer> {
    
    private static PostgresTestcontainer container;

    private static final String IMAGE_VERSION = "postgres:15.3";

    private PostgresTestcontainer() {
        super(IMAGE_VERSION);
    }

    public static PostgresTestcontainer getInstance() {
        if (container == null) {
            container = new PostgresTestcontainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

}
