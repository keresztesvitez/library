package com.epam.library.book;

import com.epam.library.borrow.Borrow;
import com.epam.library.borrow.BorrowRequest;
import com.epam.library.user.User;
import com.epam.library.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.Mockito.mock;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookServiceTest { //TODO Crerate test database

    private static final long BOOK_ID_1 = 1L;
    private static final long BOOK_ID_2 = 2L;
    private static final long BOOK_ID_3 = 3L;
    private static final long BOOK_ID_4 = 4L;
    private static final long BOOK_ID_5 = 5L;
    private static final long USER_ID_1 = 1L;
    private static final long USER_ID_2 = 2L;
    private static final long USER_ID_3 = 3L;
    private static final int BORROW_DAYS = 30;

    @Autowired
    private BookService service;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testShouldBorrowBook() {
        Borrow borrow = service.borrow(BOOK_ID_3, USER_ID_2);

        assertThat(borrow, notNullValue());
        assertThat(borrow.getUser().getId(), is(USER_ID_2));
        assertThat(borrow.getBook().getId(), is(BOOK_ID_3));
        assertThat(borrow.getExpiration(), is(LocalDate.now().plusDays(BORROW_DAYS)));
    }

    @Test(expected = RuntimeException.class)
    public void bookIsCurrentlyBorrowed() {
        service.borrow(BOOK_ID_1, USER_ID_2);
    }

    @Test
    public void testShouldExtendBorrow() {
        service.borrow(BOOK_ID_4, USER_ID_2);
        Borrow extendedBorrow = service.extend(BOOK_ID_4, USER_ID_2);

        assertThat(extendedBorrow, notNullValue());
        assertThat(extendedBorrow.getUser().getId(), is(USER_ID_2));
        assertThat(extendedBorrow.getBook().getId(), is(BOOK_ID_4));
        assertThat(extendedBorrow.getExpiration(), is(LocalDate.now().plusDays(BORROW_DAYS + BORROW_DAYS)));
        assertThat(extendedBorrow.getExtended(), is(true));
    }

    @Test(expected = RuntimeException.class)
    public void tryToExtendANotBorrowedBook() {
        service.extend(BOOK_ID_5, USER_ID_1);
    }

    @Test(expected = RuntimeException.class)
    public void tryToExtendSomebodyElsesBook() {
        service.extend(BOOK_ID_4, USER_ID_1);
    }

    @Test(expected = RuntimeException.class)
    public void tryToExtendSecondTime() {
        service.extend(BOOK_ID_4, USER_ID_2);
    }

    @Test
    public void testShouldGetBorrows() {
        Pageable pageable = mock(Pageable.class);
        Page<Borrow> borrows = service.getBorrowsByUserId(USER_ID_2, pageable);
        assertThat(borrows, notNullValue());
        assertThat(borrows.getContent().size(), is(1));
    }

    @Test
    public void testShouldReturnBorrowedBook() {
        service.borrow(BOOK_ID_5, USER_ID_2);

        BorrowRequest borrowRequest = new BorrowRequest();
        borrowRequest.setUserId(USER_ID_2);
        borrowRequest.setBookId(BOOK_ID_5);

        service.returnBook(borrowRequest);

        Book book = service.getBookById(BOOK_ID_5);
        assertThat(book.getBorrow(), is(nullValue()));
    }

    @Test
    public void testShouldSubscribeToBook() {
        BorrowRequest borrowRequest = new BorrowRequest();
        borrowRequest.setUserId(USER_ID_3);
        borrowRequest.setBookId(BOOK_ID_2);

        service.subscribe(borrowRequest);

        Book book = service.getBookById(BOOK_ID_2);
        Set<User> subscribers = book.getSubscribers();
        User user = userRepository.findById(USER_ID_3);

        boolean contains = subscribers.contains(user);
        assertThat(contains, is(true));
    }

    @Test
    public void testShouldSearchBooks() {
        Pageable pageable = mock(Pageable.class);
        Page<Book> books = service.search("ring", pageable);
        assertThat(books, notNullValue());
        assertThat(books.getContent().size(), is(3));
    }

    @Test
    public void testShouldFindAll() {
        Pageable pageable = mock(Pageable.class);
        Page<Book> books = service.findAll(pageable);
        assertThat(books, notNullValue());
        assertThat(books.getContent().size(), greaterThan(0));
    }

    @Test
    public void testShouldGetBook() {
        Book book = service.getBookById(BOOK_ID_1);
        assertThat(book, notNullValue());
        assertThat(book.getTitle(), is("A Game of Thrones"));
    }

    @Test
    public void testShouldDeleteBook() {
        Book book = service.create(new Book("author", "title"));
        Long bookId = book.getId();

        assertThat(bookId, notNullValue());

        service.delete(book);

        Book deletedBook = service.getBookById(bookId);

        assertThat(deletedBook, nullValue());
    }


}