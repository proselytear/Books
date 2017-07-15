package com.skyblesoft.books.controller;

import com.skyblesoft.books.model.Book;
import com.skyblesoft.books.service.BookService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class BooksController {
    private BookService bookService;

    @Autowired(required = true)
    @Qualifier(value = "bookService")
    public void setBookService(BookService bs) {
        this.bookService = bs;
    }

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public String listBooks(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("listBooks", this.bookService.listBooks());

        return "books";
    }

    @RequestMapping(value = "/books/add", method = RequestMethod.POST)
    public String addBook(@ModelAttribute("book") Book book) {
        if (book.getId() == 0) {
            this.bookService.addBook(book);
        } else {
            this.bookService.updateBook(book);
        }

        return "redirect:/books";
    }

    @RequestMapping("/remove/{id}")
    public String removeBook(@PathVariable("id") int id) {
        this.bookService.removeBook(id);

        return "redirect:/books";
    }

    @RequestMapping("edit/{id}")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", this.bookService.getBooksById(id));
        model.addAttribute("listBooks", this.bookService.listBooks());

        return "books";
    }

    @RequestMapping("bookdata/{id}")
    public String bookData(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", this.bookService.getBooksById(id));

        return "bookdata";
    }
}
