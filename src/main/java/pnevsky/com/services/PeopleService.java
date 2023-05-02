package pnevsky.com.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pnevsky.com.models.Book;
import pnevsky.com.models.Person;
import pnevsky.com.repositories.PeopleRepository;

import java.util.List;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
public class PeopleService {

    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }

    public Person findOne(int id){
        return peopleRepository.findById(id).orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(Person person, int id){
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id){
        peopleRepository.deleteById(id);
    }

    public Optional<Person> getPersonByName(String name){
        return peopleRepository.findByName(name);
    }

    public List<Book> getPersonBooks(int id) {
        return peopleRepository.findById(id).get().getBooks();
    }
}
//    public void returnBook(int id) {
//        jdbcTemplate.update("update books set person_id=null where id=?", id);
//    }

//    public List<Book> getBooksByPersonId(int id) {
//        Optional<Person> person = peopleRepository.findById(id);
//
//        if (person.isPresent()) {
//            Hibernate.initialize(person.get().getBooks());
//            // Мы внизу итерируемся по книгам, поэтому они точно будут загружены, но на всякий случай
//            // не мешает всегда вызывать Hibernate.initialize()
//            // (на случай, например, если код в дальнейшем поменяется и итерация по книгам удалится)
//
//            // Проверка просроченности книг
//            person.get().getBooks().forEach(book -> {
//                long diffInMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
//                // 864000000 милисекунд = 10 суток
//                if (diffInMillies > 864000000)
//                    book.setExpired(true); // книга просрочена
//            });
//
//            return person.get().getBooks();
//        }
//        else {
//            return Collections.emptyList();
//        }
//    }