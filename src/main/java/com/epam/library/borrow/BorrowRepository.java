package com.epam.library.borrow;

import com.epam.library.book.Book;
import com.epam.library.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(collectionResourceRel = "borrows", path = "borrows")
public interface BorrowRepository extends PagingAndSortingRepository<Borrow, Long> {
    
    Page<Borrow> findByUser(User user, Pageable pageable);

    Borrow findByBook(Book book);

    List<Borrow> findByExpirationLessThan(LocalDate date);

    Borrow findById(Long id);
}
