
package kz.iitu.springlab.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByAuthorAndYearGreaterThanEqual(
            String author, int year
    );
}
