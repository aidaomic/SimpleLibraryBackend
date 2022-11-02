package at.technikumwien.omic.libraryapp.services;

import at.technikumwien.omic.libraryapp.datamodels.*;
import at.technikumwien.omic.libraryapp.repositories.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Transactional
@RestController
@RequestMapping("loanservice")
@CrossOrigin
@Log
public class BookLoaningService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanHistoryRepository loanHistoryRepository;

    private Book book;
    private List<Book> booksList = new ArrayList<>();
    private Loan loan;
    private List<Loan> loanList = new ArrayList<>();
    private LoanHistory loanHistory;
    private List<LoanHistory> loanHistoryList = new ArrayList<>();

    @PostMapping
    public ResponseEntity<?> createLoan(@RequestBody Loan newLoan) {

        log.info("create() >> loan=" + newLoan);

        try {

            newLoan.setIdloan(0L);   // für alle fälle
            loan = loanRepository.save(newLoan);

            URI location = WebMvcLinkBuilder.linkTo(
                    WebMvcLinkBuilder.methodOn(getClass()).retrieveLoan(loan.getIdloan())
            ).toUri();
            return ResponseEntity.created(location).build();

        } catch (Exception e) {

            log.warning("Could not create new Loan, Book not given back yet");
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Book not given Back yet!");

        }
    }

    @PostMapping("/history")
    public ResponseEntity<?> createHistory(@RequestBody LoanHistory newLoanHistory) {

        log.info("create() >> loan=" + newLoanHistory);

        newLoanHistory.setIdloanhistory(0L);   // für alle fälle
        loanHistory = loanHistoryRepository.save(newLoanHistory);

        URI location = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(getClass()).retrieveLoanHistory(loanHistory.getIdloanhistory())
        ).toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("/books")
    public List<Book> retrieveAllBooks() {

        log.info("retrieve() >> all books");

        booksList = bookRepository.findAll();

        return booksList;
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<?> retrieveBook(@PathVariable long id) {

        log.info("retrieve() >> id=" + id);

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

    @GetMapping("/loans/{id}")
    public ResponseEntity<?> retrieveLoan(@PathVariable long id) {

        log.info("retrieve() >> id=" + id);

        try {
            loan = loanRepository.findByIdloan(id)
                    .orElseThrow(
                            () -> new EmptyResultDataAccessException("can't find loan with id " + id, 1)
                    );

            return ResponseEntity.status(HttpStatus.FOUND).body(loan);

        } catch (EmptyResultDataAccessException e) {

            log.warning(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    @GetMapping("/history/{id}")
    public ResponseEntity<?> retrieveLoanHistory(@PathVariable long id) {

        log.info("retrieveLoanHistory() >> id=" + id);

        try {
            loanHistory = loanHistoryRepository.findByIdloanhistory(id)
                    .orElseThrow(
                            () -> new EmptyResultDataAccessException("can't find loan history with id " + id, 1)
                    );

            return ResponseEntity.status(HttpStatus.FOUND).body(book);

        } catch (EmptyResultDataAccessException e) {

            log.warning(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    @GetMapping("/loans")
    public List<Loan> retrieveAllLoans() {

        log.info("retrieve() >> all loans");

        loanList = loanRepository.findAll();

        return loanList;
    }

    @GetMapping("/history")
    public List<LoanHistory> retrieveAllOfLoanHistory() {

        log.info("retrieveLoanHistory() >> all of loan history");

        loanHistoryList = loanHistoryRepository.findAll();

        return loanHistoryList;
    }

    @DeleteMapping("/loans/{id}")
    public ResponseEntity<String> deleteLoan(@PathVariable long id) {
        log.info("delete() >> id=" + id);

        try {

            loan = loanRepository.findByIdloan(id)
                    .orElseThrow(
                            () -> new EmptyResultDataAccessException("can't find book with id " + id, 1)
                    );

            log.info("history create() call");
            loanHistory = new LoanHistory(loan.getBook());
            createHistory(loanHistory);

            log.info("delete call");
            loanRepository.deleteByIdloan(id);   // throw EmptyResultDataAccessException if loan couldn't be found

            return ResponseEntity.status(HttpStatus.OK).body("Deleted loan with id " + id);

        } catch (EmptyResultDataAccessException e) {

            log.warning(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());

        }
    }

    @DeleteMapping("/history/{id}")
    public ResponseEntity<String> deleteHistory(@PathVariable long id) {

        log.info("delete() >> idHistory=" + id);
        loanHistoryRepository.deleteByIdloanhistory(id);   // throw EmptyResultDataAccessException if loanhistory couldn't be found
        return ResponseEntity.status(HttpStatus.OK).body("Deleted loan history with id " + id);

    }

}
