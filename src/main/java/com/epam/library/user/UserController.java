package com.epam.library.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;
    
//    As a User I want to be able to suspend my account
//    As a User I want to be able to modify my account data
//    As a User I want to be able to see my account data
//    As a User I want to be able to see what books do I have currently

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Page<User> getAll(Pageable pageable) {
        return userService.findAll(pageable);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public User get(@PathVariable("id") Long id) {
        return userService.getUserById(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public User create(@RequestBody User user) {
        return userService.create(user);
    }

//    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public User update(@RequestBody User user) {
        return userService.create(user);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    public String delete(@RequestBody User user) {
        userService.delete(user);
        return "User is deleted.";
    }
}
