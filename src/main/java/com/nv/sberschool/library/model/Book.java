package com.nv.sberschool.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "books")
@SequenceGenerator(name = "default_gen", sequenceName = "books_seq", allocationSize = 1)
public class Book extends GenericModel {
    @Column(name = "title", nullable = false)
    private String bookTitle;

    @Column(name = "publish_date", nullable = false)
    private LocalDate publishDate;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "storage_place", nullable = false)
    private String storagePlace;

    @Column(name = "online_copy_path")
    private String onlineCopyPath;

    @Column(name = "genre", nullable = false)
    @Enumerated
    private Genre genre;

    @ManyToMany
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"), foreignKey = @ForeignKey(name = "FK_BOOKS_AUTHORS"),
            inverseJoinColumns = @JoinColumn(name = "author_id"), inverseForeignKey = @ForeignKey(name = "FK_AUTHORS_BOOKS"))
    private Set<Author> authors;
    @OneToMany(mappedBy = "book")
    private Set<BookRentInfo> bookRentInfos;

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDate getPublisDate() {
        return publishDate;
    }

    public void setPublisDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getStoragePlace() {
        return storagePlace;
    }

    public void setStoragePlace(String storagePlace) {
        this.storagePlace = storagePlace;
    }

    public String getOnlineCopyPath() {
        return onlineCopyPath;
    }

    public void setOnlineCopyPath(String onlineCopyPath) {
        this.onlineCopyPath = onlineCopyPath;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public Set<BookRentInfo> getBookRentInfos() {
        return bookRentInfos;
    }

    public void setBookRentInfos(Set<BookRentInfo> bookRentInfos) {
        this.bookRentInfos = bookRentInfos;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", bookTitle='" + bookTitle + '\'' +
                ", publisDate=" + publishDate +
                ", pageCount=" + pageCount +
                ", amount=" + amount +
                ", storagePlace='" + storagePlace + '\'' +
                ", onlineCopyPath='" + onlineCopyPath + '\'' +
                ", genre=" + genre +
                ", authors=" + authors +
                ", bookRentInfos=" + bookRentInfos +
                '}';
    }
}


