package pnevsky.com.services;

import org.hibernate.secure.spi.JaccIntegrator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnevsky.com.models.Book;
import pnevsky.com.models.Person;
import pnevsky.com.repositories.BooksRepository;

import java.util.List;


@Service
@Transactional(readOnly = true)
public class BooksService {

    BooksRepository booksRepository;

    @Autowired
    public BooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    public void returnBook(int id){
        Book book = booksRepository.findById(id).get();
        book.setOwner(null);
    }

    public List<Book> indexFreeBooks() {
        return booksRepository.findAllByOwnerIsNull();
    }

    public List<Book> getPersonBooks(Person person) {
        return booksRepository.findByOwner(person);
    }



//    public List<Book> indexFreeBooks(){
//        List<Book> bookList = jdbcTemplate.query("SELECT * FROM Books " +
//                        "WHERE person_id is null",
//                new BeanPropertyRowMapper<>(Book.class));
//        return bookList;
//    }

    public List<Book> indexBusyBooks() {
        return booksRepository.findAllByOwnerIsNotNull();
    }



//    public List<Book> indexBusyBooks(){
//        List<Book> bookList = jdbcTemplate.query("SELECT * FROM Books " +
//                        "WHERE person_id is not null",
//                new BeanPropertyRowMapper<>(Book.class));
//        return bookList;
//    }


    public Book show(int id) {
        return booksRepository.findById(id).get();
    }


//    public Book show(int id){
//        Book book = jdbcTemplate.query("SELECT * FROM Books WHERE id=?",
//                        new Object[]{id},new BeanPropertyRowMapper<>(Book.class)).
//                stream().findAny().orElse(null);
//        return book;
//    }

    @Transactional
    public void update(Book book) {
        booksRepository.save(book);
    }



//    public void update(int id, Book book){
//        jdbcTemplate.update("UPDATE Books SET title=?, author=?, year=? WHERE id=?",
//                book.getTitle(), book.getAuthor(), book.getYear(), id);
//
//    }


    @Transactional
    public void create(Book book) {
        booksRepository.save(book);
    }




//    public void create(Book book){
//        jdbcTemplate.update("INSERT INTO Books (title, author, year) VALUES (?, ?, ?)",
//                book.getTitle(), book.getAuthor(), book.getYear());
//    }

    @Transactional
    public void delete(int id) {
        booksRepository.deleteById(id);
    }




//    public void delete(int id) {
//
//        jdbcTemplate.update("DELETE FROM Books WHERE id=?", id);
//    }
//

    public Person statusOfBook(int id) {
        return booksRepository.findById(id).get().getOwner();

//                map(Book::getOwner).orElse(null);
    }


//    public Person statusOfBook(int id){
//        Person person =
//                jdbcTemplate.query("SELECT p.id, name, birthyear FROM People p " +
//                                        "JOIN Books b on (p.id=b.person_id)\n" +
//                                        "         WHERE b.id =?;", new Object[]{id},
//                                new BeanPropertyRowMapper<>(Person.class)).
//                        stream().findAny().orElse(null);
//        return person;
//    }

    @Transactional
    public void assign(int id, Person selectedPerson) {
        Book book = booksRepository.findById(id).get();
        book.setOwner(selectedPerson);
        booksRepository.save(book);
    }


//
//


//    public void assign(int id, Person person){
//        jdbcTemplate.update("UPDATE Books SET person_id=? WHERE id=?",
//                person.getId(), id);
//    }
//





}
