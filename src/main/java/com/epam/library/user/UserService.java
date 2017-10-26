package com.epam.library.user;

import com.epam.library.borrow.Borrow;
import com.epam.library.borrow.BorrowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger log =  LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private BorrowService borrowService;

    @Autowired
    public UserService(UserRepository userRepository, BorrowService borrowService) {
        this.userRepository = userRepository;
        this.borrowService = borrowService;
    }

    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User create(User user) {
        return userRepository.save(user);
    }

    public void delete(User user) {
        user = userRepository.findById(user.getId());
        if (user.getBorrows() != null && user.getBorrows().size() > 0) {
            user.getBorrows().stream().map(Borrow::getId).forEach(borrowService::delete);
        }
        userRepository.delete(user);
    }
}
