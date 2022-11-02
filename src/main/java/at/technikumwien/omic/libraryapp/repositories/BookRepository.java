package at.technikumwien.omic.libraryapp.repositories;


import at.technikumwien.omic.libraryapp.datamodels.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Optional<Book> findByIdbook(Long id);

    @Override
    List<Book> findAll();

    Book save(Book books);

    void deleteByIdbook(long id);

}
