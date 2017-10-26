package com.epam.library.borrow;

import com.epam.library.book.Book;
import com.epam.library.book.BookRepository;
import com.epam.library.internal.TaskScheduler;
import com.epam.library.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BorrowService {

    private static final Logger logger =  LoggerFactory.getLogger(TaskScheduler.class);

    private BorrowRepository borrowRepository;
    private BookRepository bookRepository;

    @Autowired
    public BorrowService(BorrowRepository borrowRepository, BookRepository bookRepository) {
        this.borrowRepository = borrowRepository;
        this.bookRepository = bookRepository;
    }

    public void notifyExceededBorrows() {
        List<Borrow> exceededBorrows = getExceededBorrows();
        exceededBorrows.forEach(this::notify);
    }

    private void notify(Borrow borrow) {
        sendEmail(borrow.getUser().getEmail());
        setNotifiedState(borrow);
    }

    private void setNotifiedState(Borrow borrow) {
        borrow.setNotified(true);
        borrowRepository.save(borrow);
    }

    private void sendEmail(String email) {
        logger.info("Send email to: {}", email);
    }

    private List<Borrow> getExceededBorrows() {
        List<Borrow> exceededBorrows = borrowRepository.findByExpirationLessThan(LocalDate.now());

        List<Borrow> notNotifiedBorrows = exceededBorrows.stream()
                                          .filter(borrow -> Boolean.FALSE.equals(borrow.getNotified()))
                                          .collect(Collectors.toList());

        logger.info("notNotifiedBorrows size: {}", notNotifiedBorrows.size());

        return notNotifiedBorrows;
    }

    public Page<Borrow> findAll(Pageable pageable) {
        return borrowRepository.findAll(pageable);
    }

    public Borrow getBorrowById(Long id) {
        return borrowRepository.findById(id);
    }

    public Borrow create(Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    public void returnBook(BorrowRequest request) {
        delete(request);
    }

    public void delete(BorrowDeleteRequest request) {
        Borrow borrow = borrowRepository.findById(request.getId());
        BorrowRequest borrowRequest = new BorrowRequest();

        borrowRequest.setBookId(borrow.getBook().getId());
        borrowRequest.setUserId(borrow.getUser().getId());

        delete(borrowRequest);
    }

    public void delete(Long borrowId) {
        BorrowDeleteRequest request = new BorrowDeleteRequest();
        request.setId(borrowId);
        delete(request);
    }

    private void delete(BorrowRequest request) {
        Book book = bookRepository.findById(request.getBookId());
        Borrow borrow = borrowRepository.findByBook(book);

        Set<User> subscribers = borrow.getBook().getSubscribers();
        sendEmailToSubscribers(subscribers);

        borrowRepository.delete(borrow);
    }

    private void sendEmailToSubscribers(Set<User> subscribers) {
        subscribers.stream().map(User::getEmail).forEach(this::sendEmail);
    }

}
