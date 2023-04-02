package com.nv.sberschool.library.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor // - создает конструктор только для final полей
@AllArgsConstructor // - создает конструктор для всех полей
// @NoArgsConstructor -создает пустой конструктор
@Entity
@Table(name = "authors")
@SequenceGenerator(name = "default_gen", sequenceName = "authors_seq", allocationSize = 1)
//@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "jsonId")
public class Author extends GenericModel {
    @Column(name = "fio", nullable = false)
    private String authorFio;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "description")
    private String description;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "books_authors",
            joinColumns = @JoinColumn(name = "author_id"), foreignKey = @ForeignKey(name = "FK_AUTHORS_BOOKS"),
            inverseJoinColumns = @JoinColumn(name = "book_id"), inverseForeignKey = @ForeignKey(name = "FK_BOOKS_AUTHORS"))
//    @JsonBackReference
    private Set<Book> books;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Author author = (Author) o;

        if (!Objects.equals(authorFio, author.authorFio)) {
            return false;
        }
        if (!Objects.equals(birthDate, author.birthDate)) {
            return false;
        }
        if (!Objects.equals(description, author.description)) {
            return false;
        }
        return Objects.equals(books, author.books);
    }

    @Override
    public int hashCode() {
        int result = authorFio != null ? authorFio.hashCode() : 0;
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (books != null ? books.hashCode() : 0);
        return result;
    }
}

