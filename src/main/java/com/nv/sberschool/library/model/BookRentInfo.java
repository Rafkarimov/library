package com.nv.sberschool.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "book_rent_info")
@SequenceGenerator(name = "default_gen", sequenceName = "book_rent_info_seq", allocationSize = 1)
public class BookRentInfo extends GenericModel {
    @ManyToOne
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_RENT_BOOK_INFO_BOOK"))
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_RENT_BOOK_INFO_USER"))
    private User user;

    @Column(name = "rent_date", nullable = false)
    private LocalDate rentDate;

    @Column(name = "return_date", nullable = false)
    private LocalDate returnDate;

    @Column(name = "returned", nullable = false)
    private boolean returned;

    @Column(name = "rent_period", nullable = false)
    private Integer rentPeriod;

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

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public Integer getRentPeriod() {
        return rentPeriod;
    }

    public void setRentPeriod(Integer rentPeriod) {
        this.rentPeriod = rentPeriod;
    }

    @Override
    public String toString() {
        return "BookRentInfo{" +
                "id=" + id +
                ", book=" + book +
                ", user=" + user +
                ", rentDate=" + rentDate +
                ", returnDate=" + returnDate +
                ", returned=" + returned +
                ", rentPeriod=" + rentPeriod +
                '}';
    }
}


