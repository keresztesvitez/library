package com.epam.library.book;

import com.epam.library.borrow.Borrow;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

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
    private static final int BORROW_DAYS = 30;

    @Autowired
    private BookService service;

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
        Borrow borrow = service.borrow(BOOK_ID_1, USER_ID_2);
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
        Borrow borrow = service.extend(BOOK_ID_5, USER_ID_1);
    }

    @Test(expected = RuntimeException.class)
    public void tryToExtendSomebodyElsesBook() {
        Borrow borrow = service.extend(BOOK_ID_4, USER_ID_1);
    }

    @Test(expected = RuntimeException.class)
    public void tryToExtendSecondTime() {
        Borrow borrow = service.extend(BOOK_ID_4, USER_ID_2);
    }

}