package com.epam.library.borrow;

import com.epam.library.book.Book;
import com.epam.library.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Borrow {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private Book book;

    private User user;

    private LocalDateTime expiration;

    private Boolean isExtended;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDateTime expiration) {
        this.expiration = expiration;
    }

    public Boolean getExtended() {
        return isExtended;
    }

    public void setExtended(Boolean extended) {
        isExtended = extended;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Borrow borrow = (Borrow) o;

        if (id != borrow.id) return false;
        if (!book.equals(borrow.book)) return false;
        if (!user.equals(borrow.user)) return false;
        if (!expiration.equals(borrow.expiration)) return false;
        return isExtended.equals(borrow.isExtended);

    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + book.hashCode();
        result = 31 * result + user.hashCode();
        result = 31 * result + expiration.hashCode();
        result = 31 * result + isExtended.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                ", expiration=" + expiration +
                ", isExtended=" + isExtended +
                '}';
    }
}
