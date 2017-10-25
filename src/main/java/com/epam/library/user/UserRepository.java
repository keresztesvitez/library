package com.epam.library.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findById(Long id);

//    Page<User> findAll(Pageable pageable);

}
