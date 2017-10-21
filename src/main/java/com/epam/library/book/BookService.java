package com.epam.library.book;

import com.epam.library.borrow.Borrow;
import com.epam.library.borrow.BorrowRepository;
import com.epam.library.borrow.BorrowRequest;
import com.epam.library.user.User;
import com.epam.library.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

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

    //TODO Create REST Exception handling
    public Borrow extend(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId);
        if (book.getBorrow() == null) {
            throw new RuntimeException("You can not extend the borrow expiration because the book is not borrowed");
        }
        if (book.getBorrow() != null && book.getBorrow().getUser().getId() != userId) {
            throw new RuntimeException("You can not extend the borrow expiration because the book is not borrowed by you");
        }
        if (book.getBorrow().getExtended()) {
            throw new RuntimeException("You can not extend the borrow expiration because you had already extended it");
        }
        return extendExpirationDate(book);
    }

    public Page<Borrow> getBorrowsByUserId (Long userId, Pageable pageable) {
        User user = userRepository.findById(userId);
        Page<Borrow> borrows = borrowRepository.findByUser(user, pageable);
        return borrows;
    }

    public Boolean deleteBorrow(BorrowRequest request) {
        Book book = bookRepository.findById(request.getBookId());
        Borrow borrow = borrowRepository.findByBook(book);

        Set<User> subscribers = borrow.getBook().getSubscribers();
        sendEmailToSubscribers(subscribers);

        borrowRepository.delete(borrow);

        return Boolean.TRUE;
    }

    public Boolean subscribe(BorrowRequest request) {

        Book book = bookRepository.findById(request.getBookId());

        Set<User> subscribers = book.getSubscribers();
        User user = userRepository.findById(request.getUserId());
        subscribers.add(user);

        book.setSubscribers(subscribers);
        bookRepository.save(book);

        return Boolean.TRUE;
    }

    private void sendEmailToSubscribers(Set<User> subscribers) {
        subscribers.stream().map(User::getEmail).forEach(this::sendEmail);
    }

    private void sendEmail(String email) {
        logger.info("Send email to: {}", email);
    }

    private Borrow extendExpirationDate(Book book) {
        Borrow borrow = book.getBorrow();
        borrow.setExtended(true);
        borrow.setExpiration(borrow.getExpiration().plusDays(BORROW_DAYS));

        borrowRepository.save(borrow);
        return borrow;
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
