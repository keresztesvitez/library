package com.epam.library.book;

import com.epam.library.borrow.Borrow;
import com.epam.library.borrow.BorrowRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/extend", method = RequestMethod.POST)
    public Borrow extend(@RequestBody BorrowRequest request) {
        return bookService.extend(request.getBookId(), request.getUserId());
    }

    @RequestMapping(value = "/borrows/{userId}", method = RequestMethod.GET)
    public Page<Borrow> getBorrowsByUserId(@PathVariable("userId") Long userId, Pageable pageable) {
        return bookService.getBorrowsByUserId(userId, pageable);
    }

    @RequestMapping(value = "/returnBook", method = RequestMethod.POST)
    public Boolean bookReturned(@RequestBody BorrowRequest request) {
        return bookService.deleteBorrow(request);
    }


}
