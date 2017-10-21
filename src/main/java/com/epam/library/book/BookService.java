package com.epam.library.book;

import com.epam.library.borrow.Borrow;
import com.epam.library.borrow.BorrowRepository;
import com.epam.library.user.User;
import com.epam.library.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookService {

    //TODO move this to application.yml
    private static final int BORROW_DAYS = 30;

    private BorrowRepository borrowRepository;
    private UserRepository userRepository;
    private BookRepository bookRepository;

    private Logger logger = LoggerFactory.getLogger(BookService.class);

    @Autowired
    public BookService(BorrowRepository borrowRepository, UserRepository userRepository, BookRepository bookRepository) {
        this.borrowRepository = borrowRepository;
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public Borrow borrow(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId);
        if (book.getBorrow() != null) {
            throw new RuntimeException("The book is currently borrowed");
        }
        User user = userRepository.findById(userId);

        return borrowBook(book, user);
    }

    private Borrow borrowBook(Book book, User user) {
        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setUser(user);
        borrow.setExtended(false);
        borrow.setExpiration(LocalDate.now().plusDays(BORROW_DAYS));

        borrowRepository.save(borrow);

        return borrow;
    }


}