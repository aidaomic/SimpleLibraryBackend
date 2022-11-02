package at.technikumwien.omic.libraryapp.datamodels;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "book")
public class Book {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long idbook;

        @Column(nullable = false, length = 255)
        private String title;

        @Column(nullable = false, length = 255)
        private String author;

        @Column(length = 500)
        private String description;

        @Column(nullable = false)
        private Long pagecount;

        public Book(String title, String author, String description, Long pagecount) {
                this(null, title, author, description, pagecount);
        }
}
