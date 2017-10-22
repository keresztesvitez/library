package com.epam.library.borrow;

import com.epam.library.internal.TaskScheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BorrowService {

    private static final Logger log =  LoggerFactory.getLogger(TaskScheduler.class);

    private BorrowRepository borrowRepository;

    @Autowired
    public BorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
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
        log.info("Send email to: {}", email);
    }

    private List<Borrow> getExceededBorrows() {
        List<Borrow> exceededBorrows = borrowRepository.findByExpirationLessThan(LocalDate.now());

        List<Borrow> notNotifiedBorrows = exceededBorrows.stream()
                                          .filter(borrow -> Boolean.FALSE.equals(borrow.getNotified()))
                                          .collect(Collectors.toList());

        log.info("notNotifiedBorrows size: {}", notNotifiedBorrows.size());

        return notNotifiedBorrows;
    }
}
