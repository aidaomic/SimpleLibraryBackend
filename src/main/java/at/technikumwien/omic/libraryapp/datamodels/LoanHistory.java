package at.technikumwien.omic.libraryapp.datamodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "loanhistory")
public class LoanHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idloanhistory;

    @ManyToOne(cascade = CascadeType.MERGE)   // EAGER
    @JoinColumn(name = "idbook")
    private Book book;

    public LoanHistory(Book book) {
        this(null, book);
    }
}
