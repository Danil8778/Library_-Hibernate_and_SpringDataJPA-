package pnevsky.com.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pnevsky.com.models.Book;
import pnevsky.com.models.Person;

import java.util.List;

@Repository
public interface BooksRepository extends JpaRepository<Book, Integer> {
   List<Book> findAllByOwnerIsNull();
   List<Book> findAllByOwnerIsNotNull();

   List<Book> findByOwner(Person person);
}
