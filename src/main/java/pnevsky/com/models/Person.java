package pnevsky.com.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="people")
public class Person {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotEmpty(message = "The field should not be empty")
    @Size(max = 25, message = "The field should be less than 25 characters")
    private String name;

    @Column(name = "birthyear")
    @Min(value = 1900, message = "The year of birth should not me less then 1900")
    private int birthYear;

    @OneToMany(mappedBy = "owner")
    private List<Book> books = new ArrayList<>();

    public Person() {
    }

    public Person(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }


    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }


    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return "id " + id + ", " + name + ", " + birthYear;
    }
}