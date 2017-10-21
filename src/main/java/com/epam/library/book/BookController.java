package com.epam.library.book;

import com.epam.library.borrow.Borrow;
import com.epam.library.borrow.BorrowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bookService")
public class BookController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/")
    public String hello() {
        return "BookService hello";
    }

    @RequestMapping(value = "/borrow", method = RequestMethod.POST)
    public Borrow borrow(@RequestBody BorrowRequest request) {
        return bookService.borrow(request.getBookId(), request.getUserId());
    }



}
