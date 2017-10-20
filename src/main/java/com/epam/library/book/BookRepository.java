package com.epam.library.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

@RepositoryRestResource(collectionResourceRel = "books", path = "books")
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {

    @RestResource(path = "byTitle", rel = "byTitle")
    Page<Book> findBookByTitleIgnoreCaseContaining(@Param("title") String title, Pageable pageable);

    @RestResource(path = "byAuthor", rel = "byAuthor")
    Page<Book> findBookByAuthorIgnoreCaseContaining(@Param("author") String author, Pageable pageable);
}
