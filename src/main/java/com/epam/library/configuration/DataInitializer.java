package com.epam.library.configuration;

import com.epam.library.book.Book;
import com.epam.library.book.BookRepository;
import com.epam.library.user.User;
import com.epam.library.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer {

    private BookRepository bookRepository;
    private UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(DataInitializer.class);

    @Autowired
    public DataInitializer(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;

        initRepos();
    }

    private void initRepos() {
        initBooks();
        initUsers();
    }

    private void initUsers() {
        this.userRepository.save(new User("admin", "Larry Librarian", true));
        this.userRepository.save(new User("john", "John Doe", false));
        this.userRepository.save(new User("jane", "Jane Doe", false));
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
