package com.epam.library.internal;

import com.epam.library.borrow.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduler {

    @Autowired
    private BorrowService borrowService;

    @Scheduled(fixedRate = 60000)
    public void notifyExceededBorrows() {
        borrowService.notifyExceededBorrows();
    }

}
