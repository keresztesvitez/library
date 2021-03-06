package com.epam.library.configuration;

import com.epam.library.book.Book;
import com.epam.library.book.BookRepository;
import com.epam.library.borrow.Borrow;
import com.epam.library.borrow.BorrowRepository;
import com.epam.library.user.Role;
import com.epam.library.user.User;
import com.epam.library.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer {

    private static final int BORROW_DAYS = 30;
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BorrowRepository borrowRepository;

    private Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    public DataInitializer(BookRepository bookRepository, UserRepository userRepository, BorrowRepository borrowRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowRepository = borrowRepository;

        initRepos();
    }

    private void initRepos() {
        initBooks();
        initUsers();
        initBorrows();
    }

    private void initBorrows() {
        initBorrow(1L, 2L);
        initBorrow(2L, 3L);
    }
    private void initBorrow(Long bookId, Long userId) {
        Book book = this.bookRepository.findById(bookId);
        User user = this.userRepository.findById(userId);

        Borrow borrow = new Borrow();
        borrow.setBook(book);
        borrow.setUser(user);
        borrow.setExtended(false);
        borrow.setExpiration(LocalDate.now().minusDays(1));
        borrow.setNotified(false);

        this.borrowRepository.save(borrow);
    }

    private void initUsers() {
        this.userRepository.save(new User.UserBuilder().setName("Larry Librarian").setEmail("admin@example.com").setRole(Role.ADMIN).build());
        this.userRepository.save(new User.UserBuilder().setName("John Doe").setEmail("john@example.com").setRole(Role.USER).build());
        this.userRepository.save(new User.UserBuilder().setName("Jane Doe").setEmail("jane@example.com").setRole(Role.USER).build());
    }

    private void initBooks() {
        this.bookRepository.save(new Book("George R. R. Martin", "A Game of Thrones"));
        this.bookRepository.save(new Book("George R. R. Martin", "A Clash of Kings"));
        this.bookRepository.save(new Book("George R. R. Martin", "A Storm of Swords"));
        this.bookRepository.save(new Book("George R. R. Martin", "A Feast for Crows"));
        this.bookRepository.save(new Book("George R. R. Martin", "A Dance with Dragons"));

        this.bookRepository.save(new Book("J. R. R. Tolkien", "The Lord of the Rings - The Fellowship of the Ring"));
        this.bookRepository.save(new Book("J. R. R. Tolkien", "The Lord of the Rings - The Two Towers"));
        this.bookRepository.save(new Book("J. R. R. Tolkien", "The Lord of the Rings - The Return of the King"));

        this.bookRepository.save(new Book("J. K. Rowling", "Harry Potter - The Philosopher's Stone"));
        this.bookRepository.save(new Book("J. K. Rowling", "Harry Potter - The Chamber of Secrets"));
        this.bookRepository.save(new Book("J. K. Rowling", "Harry Potter - The Prisoner of Azkaban"));
        this.bookRepository.save(new Book("J. K. Rowling", "Harry Potter - The Goblet of Fire"));
        this.bookRepository.save(new Book("J. K. Rowling", "Harry Potter - The Order of the Phoenix"));
        this.bookRepository.save(new Book("J. K. Rowling", "Harry Potter - The Half-Blood Prince"));
        this.bookRepository.save(new Book("J. K. Rowling", "Harry Potter - The Deathly Hallows"));
    }
}
