package com.epam.library.borrow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/borrow")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<Borrow> getAll(Pageable pageable) {
        return borrowService.findAll(pageable);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Borrow get(@PathVariable("id") Long id) {
        return borrowService.getBorrowById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Borrow create(@RequestBody Borrow borrow) {
        return borrowService.create(borrow);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public Borrow update(@RequestBody Borrow borrow) {
        return borrowService.create(borrow);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public void delete(@RequestBody BorrowDeleteRequest request) {
        borrowService.delete(request);
    }
}
