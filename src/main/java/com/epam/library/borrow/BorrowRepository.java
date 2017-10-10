package com.epam.library.borrow;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "borrows", path = "borrows")
public interface BorrowRepository extends PagingAndSortingRepository<Borrow, Long> {
}
