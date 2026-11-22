
package kz.iitu.springlab.persistence;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookJpaRepositoryTest {

    @Autowired
    private BookJpaRepository repository;

    @Test
    void findsBooksByAuthorAndMinimumYear() {
        repository.saveAndFlush(
                new BookEntity("Dune", "Frank Herbert", 1965)
        );

        repository.saveAndFlush(
                new BookEntity("Dune Messiah", "Frank Herbert", 1969)
        );

        List<BookEntity> results =
                repository.findByAuthorAndYearGreaterThanEqual(
                        "Frank Herbert", 1965
                );

        assertThat(results)
                .hasSize(2)
                .extracting(BookEntity::getTitle)
                .containsExactlyInAnyOrder(
                        "Dune",
                        "Dune Messiah"
                );
    }

    @Test
    void excludesBooksThatDoNotMatchCriteria() {
        repository.saveAndFlush(
                new BookEntity("Dune", "Frank Herbert", 1965)
        );

        repository.saveAndFlush(
                new BookEntity("Clean Code", "Robert Martin", 2008)
        );

        repository.saveAndFlush(
                new BookEntity("Old Novel", "Frank Herbert", 1950)
        );

        List<BookEntity> results =
                repository.findByAuthorAndYearGreaterThanEqual(
                        "Frank Herbert", 1960
                );

        assertThat(results)
                .hasSize(1)
                .extracting(BookEntity::getTitle)
                .containsExactly("Dune");
    }

}
