package at.technikumwien.omic.libraryapp.services;

import at.technikumwien.omic.libraryapp.datamodels.Book;
import at.technikumwien.omic.libraryapp.repositories.BookRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Transactional
@RestController
@RequestMapping("admin")
@CrossOrigin
@Log
public class BookCreationService {

    @Autowired
    private BookRepository bookRepository;

    private Book book;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody Book newBook) {

        log.info("admin create() >> book=" + newBook);

        newBook.setIdbook(0L);   // für alle fälle
        book = bookRepository.save(newBook);

        URI location = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(getClass()).retrieve(book.getIdbook())
        ).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public void update(@PathVariable long id, @RequestBody Book updatedBook) {
        log.info("admin update() >> id=" + id + ", book=" + updatedBook);

        updatedBook.setIdbook(id); // für alle fälle
        bookRepository.save(updatedBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> retrieve(@PathVariable long id) {

        log.info("admin retrieve() >> id=" + id);

        try{
            book = bookRepository.findByIdbook(id)
                    .orElseThrow(
                            () -> new EmptyResultDataAccessException("can't find book with id " + id, 1)
                    );

            return ResponseEntity.status(HttpStatus.FOUND).body(book);

        } catch (EmptyResultDataAccessException e) {

            log.warning(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id) {
        log.info("admin delete() >> id=" + id);
        bookRepository.deleteByIdbook(id);   // throw EmptyResultDataAccessException if book couldn't be found
        return ResponseEntity.status(HttpStatus.OK).body("Deleted book with id " + id);
    }

}
