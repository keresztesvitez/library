package com.epam.library.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    Page<Book> findBookByAuthorIgnoreCaseContainingOrTitleIgnoreCaseContaining(String author, String title, Pageable pageable);

    Book findById(Long id);

    Page<Book> findAll(Pageable pageable);
}
