package pnevsky.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pnevsky.com.models.Book;
import pnevsky.com.models.Person;
import pnevsky.com.services.BooksService;
import pnevsky.com.services.PeopleService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


@Controller
@RequestMapping("/books")
public class BookController {

    BooksService booksService;
    PeopleService peopleService;


    @Autowired
    public BookController(BooksService booksService, PeopleService peopleService) {
        this.booksService = booksService;
        this.peopleService = peopleService;
    }





    @GetMapping()
    public String index(Model model){
        model.addAttribute("freeBooks",booksService.indexFreeBooks());
        model.addAttribute("busyBooks",booksService.indexBusyBooks());

        return "books/index";
    }

    @PostMapping()
    public String create(@Valid Book book, Model model, BindingResult bindingResult){
        bindingResult.getModel().put("book", book);
        if(bindingResult.hasErrors())
            return "books/new";

        model.addAttribute("book", book);
        booksService.create(book);
        return "redirect:/books";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable ("id") int id, Person person, Model model){
        model.addAttribute("person", person);
        model.addAttribute("book",booksService.show(id));
        Person owner = booksService.statusOfBook(id);
        List<Person> people = peopleService.findAll();

        if (owner != null)
            model.addAttribute("owner", owner);
        else {
            model.addAttribute("people",people);
        }
        return "books/show";
    }


    @PatchMapping("/{id}")
    public String update(@PathVariable ("id") int id, Book book, Model model,
                         BindingResult bindingResult){
        bindingResult.getModel().put("book", book);
        if(bindingResult.hasErrors())
            return "books/new";

        model.addAttribute("book", book);
        booksService.update(book);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable ("id") int id){
        booksService.delete(id);
        return "redirect:/books";
    }

    @GetMapping("/new")
    public String addBook(Model model){
        Book book = new Book();
        model.addAttribute(book);
        return "books/new";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable ("id") int id, Model model){
        model.addAttribute("book",booksService.show(id));
        return "books/edit";
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, Person selectedPerson, Model model) {
        model.addAttribute("person", selectedPerson);
        booksService.assign(id, selectedPerson);
        return "redirect:/books";
    }


}