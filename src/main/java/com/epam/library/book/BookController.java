package com.epam.library.book;

import com.epam.library.borrow.Borrow;
import com.epam.library.borrow.BorrowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<Book> getAllBooks(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Book getBook(@PathVariable("id") Long id) {
        return bookService.getBookById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Book createBook(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Book updateBook(@RequestBody Book book) {
        return bookService.create(book);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void deleteBook(@RequestBody Book book) {
        bookService.delete(book);
    }

    @RequestMapping(value = "/borrow", method = RequestMethod.POST)
    public Borrow borrow(@RequestBody BorrowRequest request) {
        return bookService.borrow(request.getBookId(), request.getUserId());
    }

    @RequestMapping(value = "/extend", method = RequestMethod.POST)
    public Borrow extend(@RequestBody BorrowRequest request) {
        return bookService.extend(request.getBookId(), request.getUserId());
    }

    @RequestMapping(value = "/borrows/{userId}", method = RequestMethod.GET)
    public Page<Borrow> getBorrowsByUserId(@PathVariable("userId") Long userId, Pageable pageable) {
        return bookService.getBorrowsByUserId(userId, pageable);
    }

    @RequestMapping(value = "/returnBook", method = RequestMethod.POST)
    public void returnBook(@RequestBody BorrowRequest request) {
        bookService.returnBook(request);
    }

    @RequestMapping(value = "/subscribe", method = RequestMethod.POST)
    public Boolean subscribe(@RequestBody BorrowRequest request) {
        return bookService.subscribe(request);
    }

    @RequestMapping(value = "/search/{searchText}", method = RequestMethod.GET)
    public Page<Book> search(@PathVariable("searchText") String searchText, Pageable pageable) {
        return bookService.search(searchText, pageable);
    }



}
