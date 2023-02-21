package com.nv.sberschool.library.model;

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
public class Author extends GenericModel {
    @Column(name = "fio", nullable = false)
    private String authorFio;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "description")
    private String description;
    @ManyToMany(mappedBy = "authors")
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
                "id=" + id +
                ", authorFio='" + authorFio + '\'' +
                ", birthDate=" + birthDate +
                ", description='" + description + '\'' +
                ", books=" + books +
                '}';
    }
}
