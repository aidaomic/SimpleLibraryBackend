package at.technikumwien.omic.libraryapp.repositories;

import at.technikumwien.omic.libraryapp.datamodels.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    Optional<Loan> findByIdloan(Long id);

    List<Loan> findAll();

    Loan save(Loan loan);

    void deleteByIdloan(long id);

}
