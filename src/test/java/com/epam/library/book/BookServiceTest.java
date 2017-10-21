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
    private static final long USER_ID_1 = 1L;
    private static final long USER_ID_2 = 2L;
    private static final int BORROW_DAYS = 30;

    @Autowired
    private BookService service;

    @Test
    public void testShouldBorrowBook() {
        Borrow borrow = service.borrow(BOOK_ID_2, USER_ID_2);

        assertThat(borrow, notNullValue());
//        assertThat(borrow.getUser().getId(), is(USER_ID_2));
        assertThat(borrow.getBook().getId(), is(BOOK_ID_2));
        assertThat(borrow.getExpiration(), is(LocalDate.now().plusDays(BORROW_DAYS)));

    }

    @Test(expected = RuntimeException.class)
    public void testShouldThrowException() {
        Borrow borrow = service.borrow(BOOK_ID_1, USER_ID_2);
    }

}