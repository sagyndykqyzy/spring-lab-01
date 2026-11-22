
package kz.iitu.springlab.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.postgresql.PostgreSQLContainer;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
@Testcontainers(disabledWithoutDocker = true)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

@TestPropertySource(properties = {
        "spring.flyway.enabled=true",
        "spring.jpa.hibernate.ddl-auto=validate"
})
class BookPostgresIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer postgres =
            new PostgreSQLContainer("postgres:16-alpine")
                    .withDatabaseName("lab12")
                    .withUsername("test")
                    .withPassword("test");

    @Autowired
    private BookJpaRepository repository;

    @Test
    void savesAndFindsBookInPostgres() {
        BookEntity saved = repository.saveAndFlush(
                new BookEntity("Dune", "Frank Herbert", 1965)
        );

        assertThat(repository.findById(saved.getId()))
                .isPresent()
                .get()
                .extracting(BookEntity::getTitle)
                .isEqualTo("Dune");
    }
}
