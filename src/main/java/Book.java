import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bid;

    private String bookname;
    private int price;
    private Date date;

    public Date getDate() {
        return date;
    }

    public Book(String bookname, int price, Date date, Author author, Publication publication) {
        this.bookname = bookname;
        this.price = price;
        this.date = date;
        this.author = author;
        this.publication = publication;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    private Author author;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private Publication publication;

    public Book() {
    }

    public Book(String bookname, Author author) {
        this.bookname = bookname;
        this.author = author;
    }

    public Book(String bookname, int price, Author author, Publication publication) {
        this.bookname = bookname;
        this.price = price;
        this.author = author;
        this.publication = publication;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Publication getPublication() {
        return publication;
    }

    public void setPublication(Publication publication) {
        this.publication = publication;
    }

}
