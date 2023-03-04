package com.nv.sberschool.library.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Set;

//@Getter
//@Setter
//@RequiredArgsConstructor - создает конструктор только для final полей
//@AllArgsConstructor - создает конструктор для всех полей
//@NoArgsConstructor -создает пустой конструктор
@Entity
@Table(name = "authors")
@SequenceGenerator(name = "default_gen", sequenceName = "authors_seq", allocationSize = 1)
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "id")
public class Author extends GenericModel {
    @Column(name = "fio", nullable = false)
    private String authorFio;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "description")
    private String description;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "author_id"), foreignKey = @ForeignKey(name = "FK_AUTHORS_BOOKS"),
            inverseJoinColumns = @JoinColumn(name = "book_id"), inverseForeignKey = @ForeignKey(name = "FK_BOOKS_AUTHORS"))
    @JsonBackReference
    private Set<Book> books;

    public String getAuthorFio() {
        return authorFio;
    }

    public void setAuthorFio(String authorFio) {
        this.authorFio = authorFio;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorFio='" + authorFio + '\'' +
                ", birthDate=" + birthDate +
                ", description='" + description + '\'' +
                ", books=" + books.stream().map(Book::getId).toList() +
                ", id=" + id +
                ", createdWhen=" + createdWhen +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
