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
import pnevsky.com.util.PersonValidator;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/people")
public class PeopleController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;
    private final BooksService booksService;

    @Autowired
    public PeopleController(PeopleService peopleService, PersonValidator personValidator, BooksService booksService) {
        this.peopleService = peopleService;
        this.personValidator = personValidator;
        this.booksService = booksService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("people", peopleService.findAll());
        return "people/index";
    }

    @PostMapping()
    public String create (@Valid Person person, BindingResult bindingResult, Model model){
       bindingResult.getModel().put("person", person);
       personValidator.validate(person, bindingResult);
       if (bindingResult.hasErrors())
           return "/people/new";

       model.addAttribute("person", person);
       peopleService.save(person);
       return "redirect:/people";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        Person person = peopleService.findOne(id);
        List<Book> books = booksService.getPersonBooks(person);
        person.setBooks(books);
        model.addAttribute("person",person);
        return "people/show";
    }

    @PatchMapping("/{id}")
    public String update(@PathVariable int id, Model model, @Valid Person person,
                         BindingResult bindingResult){
        if (bindingResult.hasErrors())
            return "/people/edit";

        model.addAttribute("person", person);
        peopleService.update(person, id);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable int id){
        peopleService.delete(id);
        return "redirect:/people";
    }

    @GetMapping ("/new")
    public String addPerson(Model model){
        Person person = new Person();
        model.addAttribute("person",person);
        return "people/new";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model){
        model.addAttribute("person", peopleService.findOne(id));
        return "people/edit";
    }

    @PatchMapping("/{id}/edit/{bookId}")
    public String returnBook(@PathVariable ("id") int id,@PathVariable ("bookId")
    int bookId, Model model){
        booksService.returnBook(bookId);
        model.addAttribute("person", peopleService.findOne(id));
        model.addAttribute("personBooks", peopleService.getPersonBooks(id));
        return "redirect: /people/{id}";
    }
}