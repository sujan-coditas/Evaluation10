import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Function {

    public static BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));

    public static Configuration configuration = new Configuration().configure().addAnnotatedClass(Author.class).addAnnotatedClass(Book.class).addAnnotatedClass(Publication.class);
    public static SessionFactory sessionFactory = configuration.buildSessionFactory();

    public static void main(String[] args) throws Exception {

        boolean exit = true;
        int choice = 0;
        while (exit) {
            System.out.println("1. Add Author");
            System.out.println("2. Add publication");
            System.out.println("3. Add  Book");
            System.out.println("4. Query1 (nirali publication)");
            System.out.println("5. update Book");
            System.out.println("6. Delete Book");
            System.out.println("7. Query 2 (author Name with  A )");
            System.out.println("8. Query 3 (date query )");


            System.out.println("0. EXIT");

            System.out.println("choose a option :: ");
            choice = Integer.parseInt(bf.readLine());

            switch (choice) {
                case 0:
                    exit = false;
                    break;
                case 1:
                    addAuthor();
                    break;
                case 2:
                    addPublication();
                    break;
                case 3:
                    addBook();
                    break;
                case 4:
                    niralipublicationBook();
                    break;
                case 5:
                    updateBook();
                    break;
                case 6:
                    deleteBook();
                    break;
                case 7:
                    authorNameQuery();
                    break;

                case 8:
                    BookBydate();
                    break;

                case 9:
                    deletepublication();
                    break;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    private static void BookBydate() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        LocalDate date = LocalDate.of(2021, 1, 1);

        Date sqlDate = java.sql.Date.valueOf(date);

        Query query = session.createQuery("FROM Book WHERE date <= :date");
        query.setParameter("date", sqlDate);

        List<Book> bookList = query.getResultList();

        if (bookList.isEmpty()) {
            System.out.println("No books found");
        } else {
            for (Book book : bookList) {
                System.out.println("Book ID: " + book.getBid());
                System.out.println("Book Name: " + book.getBookname());
                System.out.println("Author Name: " + book.getAuthor().getAuthorName());
                System.out.println("Book Price: " + book.getPrice());
                System.out.println("----------------------");
            }
        }
        session.close();
    }


    private static void authorNameQuery() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("select b from Book b join b.author a where a.authorName like 'a%i'");
        List<Book> bookList = query.getResultList();

        if (bookList.isEmpty()) {
            System.out.println("No books found");
        } else {
            for (Book book : bookList) {
                System.out.println("Book ID: " + book.getBid());
                System.out.println("Book Name: " + book.getBookname());
                System.out.println("Author Name: " + book.getAuthor().getAuthorName());
                System.out.println("----------------------");
            }


        }
        session.close();
    }

    private static void deleteBook() throws IOException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("Enter Book ID:");
        int Bid = Integer.parseInt(bf.readLine());

        Book book = session.get(Book.class, Bid);

        if (book == null) {
            System.out.println("Book not found");
        } else {
            session.delete(book);
            transaction.commit();
            System.out.println("Book deleted successfully");
        }

        session.close();
    }

    private static void deletepublication() throws IOException {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        System.out.println("Enter publication ID:");
        int pid = Integer.parseInt(bf.readLine());

        Publication publication = session.get(Publication.class, pid);

        if (publication == null) {
            System.out.println("publisher not found");
        } else {
            session.delete(publication);
            transaction.commit();
            System.out.println("publisher deleted successfully");
        }

        session.close();
    }

    private static void updateBook() throws IOException {

        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        System.out.println("Enter Book id :");
        int Bid = Integer.parseInt(bf.readLine());

        Book book = session.get(Book.class, Bid);

        if (book == null) {
            System.out.println("book not found");
        } else {
            System.out.println("change Book name to :");
            String newBook = bf.readLine();
            book.setBookname(newBook);
        }
        session.update(book);
        tx.commit();
        System.out.println("book updated ");
    }

    private static void niralipublicationBook() {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        Query query = session.createQuery("from Book where publication.pubname = :pubname and price >= :price");
        query.setParameter("pubname", "nirali");
        query.setParameter("price", 500);
        List<Book> bookList = query.getResultList();

        if (bookList.isEmpty()) {
            System.out.println("No books found");
        } else {
            for (Book book : bookList) {
                System.out.println("Book ID: " + book.getBid());
                System.out.println("Book Name: " + book.getBookname());
                System.out.println("Author Name: " + book.getAuthor().getAuthorName());
                System.out.println("Book Price: " + book.getPrice());
                System.out.println("----------------------");
            }
        }
        session.close();

    }


    private static void addBook() throws Exception {
        System.out.println("Enter number of Book details you want to add ");
        int noOfBooks = Integer.parseInt(bf.readLine());

        List<Book> bookList = new ArrayList<>();
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        for (int i = 0; i < noOfBooks; i++) {
            System.out.println("Enter Book name");
            String bookname = bf.readLine();
            System.out.println("Enter author name");
            String authorName = bf.readLine();
            System.out.println("Enter publication name ");
            String publicationName = bf.readLine();
            System.out.println("Enter Book price ");
            int price = Integer.parseInt(bf.readLine());
            System.out.println("Enter Book date (yyyy-mm-dd)");
            String dateStr = bf.readLine();
            java.sql.Date date = java.sql.Date.valueOf(LocalDate.parse(dateStr));

            Query query = session.createQuery("from Author where authorName = :authorName");
            query.setParameter("authorName", authorName);
            List<Author> authorList = query.getResultList();

            Author author;
            if (authorList.isEmpty()) {
                author = new Author(authorName);
                session.persist(author);
            } else {
                author = authorList.get(0);
            }

            Query publicationQuery = session.createQuery("from Publication where pubname = :pubname");
            publicationQuery.setParameter("pubname", publicationName);
            List<Publication> publicationList = publicationQuery.getResultList();

            Publication publication;
            if (publicationList.isEmpty()) {
                publication = new Publication(publicationName);
                session.persist(publication);
            } else {
                publication = publicationList.get(0);
            }

            Book book = new Book(bookname, price, author, publication);
            book.setDate(date);
            session.persist(book);
            bookList.add(book);
            publication.setBook(book);
            System.out.println("Book added");
        }

        tx.commit();
        session.close();

    }

    private static void addPublication() throws Exception {
     
            System.out.println("Enter number of Publication details you want to add ");
            int noOfPublications = Integer.parseInt(bf.readLine());

            List<Publication> publicationList = new ArrayList<Publication>();
            Session session = sessionFactory.openSession();
            Transaction tx = session.beginTransaction();

             {
                for (int i = 0; i < noOfPublications; i++) {
                    System.out.println("Enter Publication name");
                    String publicationName = bf.readLine();

                    //check if the publication exists in the table
                    Query query = session.createQuery("from Publication where pubname = :pubname");
                    query.setParameter("pubname", publicationName);
                    List<Publication> publicationList1 = query.getResultList();

                    Publication publication = new Publication(publicationName);//create new entry

                    publicationList.add(publication);
                    System.out.println("Publication added");
                }

                tx.commit();
            } 
        }

        
    private static void addAuthor() throws IOException {
        Session session= sessionFactory.openSession();
        Transaction tx= session.getTransaction();

        System.out.println("Enter Author Name");
        String authorName=bf.readLine();

        Author author=new Author(authorName);
        session.beginTransaction();
        session.save(author);
        tx.commit();
        System.out.println("Author Added");

    }
}
/*
* Hibernate: create table Book (bid integer not null auto_increment, bookname varchar(255), date datetime, price integer not null, author_aid integer, primary key (bid)) engine=MyISAM
Hibernate: create table Publication (pid integer not null auto_increment, pubname varchar(255), book_bid integer, primary key (pid)) engine=MyISAM
Hibernate: alter table Book add constraint FK6gw5twjh1cd75ylobxjtbxsh6 foreign key (author_aid) references Author (aid)
Hibernate: alter table Publication add constraint FKabcs4sre9hkukj862buoifv6e foreign key (book_bid) references Book (bid)
1. Add Author
2. Add publication
3. Add  Book
4. Query1 (nirali publication)
5. update Book
6. Delete Book
7. Query 2 (author Name with  A )
8. Query 3 (date query )
0. EXIT
choose a option ::
3
Enter number of Book details you want to add
1
Enter Book name
secrets
Enter author name
aarti
Enter publication name
nirali
Enter Book price
600
Enter Book date (yyyy-mm-dd)
2019-01-01
Hibernate: select author0_.aid as aid1_0_, author0_.authorName as authorna2_0_ from Author author0_ where author0_.authorName=?
Hibernate: insert into Author (authorName) values (?)
Hibernate: select publicatio0_.pid as pid1_2_, publicatio0_.book_bid as book_bid3_2_, publicatio0_.pubname as pubname2_2_ from Publication publicatio0_ where publicatio0_.pubname=?
Hibernate: insert into Publication (book_bid, pubname) values (?, ?)
Hibernate: insert into Book (author_aid, bookname, date, price) values (?, ?, ?, ?)
Book added
Hibernate: update Publication set book_bid=?, pubname=? where pid=?
1. Add Author
2. Add publication
3. Add  Book
4. Query1 (nirali publication)
5. update Book
6. Delete Book
7. Query 2 (author Name with  A )
8. Query 3 (date query )
0. EXIT
choose a option ::
7
Hibernate: select book0_.bid as bid1_1_, book0_.author_aid as author_a5_1_, book0_.bookname as bookname2_1_, book0_.date as date3_1_, book0_.price as price4_1_ from Book book0_ inner join Author author1_ on book0_.author_aid=author1_.aid where author1_.authorName like 'a%i'
Hibernate: select publicatio0_.pid as pid1_2_1_, publicatio0_.book_bid as book_bid3_2_1_, publicatio0_.pubname as pubname2_2_1_, book1_.bid as bid1_1_0_, book1_.author_aid as author_a5_1_0_, book1_.bookname as bookname2_1_0_, book1_.date as date3_1_0_, book1_.price as price4_1_0_ from Publication publicatio0_ left outer join Book book1_ on publicatio0_.book_bid=book1_.bid where publicatio0_.book_bid=?
Book ID: 1
Book Name: secrets
Hibernate: select author0_.aid as aid1_0_0_, author0_.authorName as authorna2_0_0_ from Author author0_ where author0_.aid=?
Author Name: aarti
----------------------
1. Add Author
2. Add publication
3. Add  Book
4. Query1 (nirali publication)
5. update Book
6. Delete Book
7. Query 2 (author Name with  A )
8. Query 3 (date query )
0. EXIT
choose a option ::
8
Hibernate: select book0_.bid as bid1_1_, book0_.author_aid as author_a5_1_, book0_.bookname as bookname2_1_, book0_.date as date3_1_, book0_.price as price4_1_ from Book book0_ where book0_.date<=?
Hibernate: select publicatio0_.pid as pid1_2_1_, publicatio0_.book_bid as book_bid3_2_1_, publicatio0_.pubname as pubname2_2_1_, book1_.bid as bid1_1_0_, book1_.author_aid as author_a5_1_0_, book1_.bookname as bookname2_1_0_, book1_.date as date3_1_0_, book1_.price as price4_1_0_ from Publication publicatio0_ left outer join Book book1_ on publicatio0_.book_bid=book1_.bid where publicatio0_.book_bid=?
Book ID: 1
Book Name: secrets
Hibernate: select author0_.aid as aid1_0_0_, author0_.authorName as authorna2_0_0_ from Author author0_ where author0_.aid=?
Author Name: aarti
Book Price: 600
----------------------
1. Add Author
2. Add publication
3. Add  Book
4. Query1 (nirali publication)
5. update Book
6. Delete Book
7. Query 2 (author Name with  A )
8. Query 3 (date query )
0. EXIT
choose a option ::
0*/