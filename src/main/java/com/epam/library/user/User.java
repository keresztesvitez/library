package com.epam.library.user;

import com.epam.library.book.Book;
import com.epam.library.borrow.Borrow;

import javax.persistence.*;
import java.util.Set;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String username;

    private String name;

    private String email;

    @OneToMany(mappedBy = "user")
    private Set<Borrow> borrows;

    @ManyToMany(mappedBy = "subscribers")
    private Set<Book> subscriptions;

    private Boolean isLibrarian;

    public User() {
    }

    public User(String username, String name, String email, Boolean isLibrarian) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.isLibrarian = isLibrarian;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Borrow> getBorrows() {
        return borrows;
    }

    public void setBorrows(Set<Borrow> borrows) {
        this.borrows = borrows;
    }

    public Boolean getLibrarian() {
        return isLibrarian;
    }

    public void setLibrarian(Boolean librarian) {
        isLibrarian = librarian;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Book> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(Set<Book> subscriptions) {
        this.subscriptions = subscriptions;
    }
}
