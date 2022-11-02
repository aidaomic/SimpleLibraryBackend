package at.technikumwien.omic.libraryapp.repositories;

import at.technikumwien.omic.libraryapp.datamodels.LoanHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoanHistoryRepository extends JpaRepository<LoanHistory, Long> {

    Optional<LoanHistory> findByIdloanhistory(Long id);

    List<LoanHistory> findAll();

    LoanHistory save(LoanHistory loanHistory);

    void deleteByIdloanhistory(long id);

}
