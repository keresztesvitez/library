package com.epam.library.borrow;

import com.epam.library.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "borrows", path = "borrows")
public interface BorrowRepository extends PagingAndSortingRepository<Borrow, Long> {

    Page<Borrow> findByUser(User user, Pageable pageable);
}
