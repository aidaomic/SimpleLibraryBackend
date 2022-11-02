package at.technikumwien.omic.libraryapp.datamodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "loan")
public class Loan {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idloan;

        @ManyToOne(cascade = CascadeType.MERGE)   // EAGER
        @JoinColumn(name = "idbook")
        private Book book;

        public Loan(Book book) {
                this(null, book);
        }
}
