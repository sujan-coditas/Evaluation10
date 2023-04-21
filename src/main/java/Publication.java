import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
public class Publication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pid;

    private String pubname;
@OneToOne
    private Book book;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPubname() {
        return pubname;
    }

    public void setPubname(String pubname) {
        this.pubname = pubname;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Publication() {
    }

    public Publication(String pubname, Book book) {
        this.pubname = pubname;
        this.book = book;
    }

    public Publication(int pid, String pubname, Book book) {
        this.pid = pid;
        this.pubname = pubname;
        this.book = book;
    }

    public Publication(String pubname) {
        this.pubname = pubname;
    }
}
