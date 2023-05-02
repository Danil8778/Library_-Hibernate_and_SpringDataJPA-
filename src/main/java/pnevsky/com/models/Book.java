package pnevsky.com.models;


import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty (message = "The field should not be empty")
    @Size(max = 100, message = "The title should not be more than 100 characters" )
    private String title;

    @Column(name = "author")
    @NotEmpty (message = "The field should not be empty")
    @Size(max = 100, message = "The title should not be more than 100 characters" )
    private String author;

    @Column(name = "year")
    @Min(value = 1500, message = "The year should be more than 1500")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id")
    private Person owner;



    public Book() { }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getYear() {
        return year;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
    @Override
    public String toString() {
        return "id " + id + ", " + title + ", " + author + ", " + year;
    }
}
