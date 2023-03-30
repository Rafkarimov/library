package com.nv.sberschool.library.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

//@ToString(callSuper = true)
@Entity
@Table(name = "books")
@Getter
@Setter
@SequenceGenerator(name = "default_gen", sequenceName = "books_seq", allocationSize = 1)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "jsonId")
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
//    genre smallint not null

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "books_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            foreignKey = @ForeignKey(name = "FK_BOOKS_AUTHORS"),
            inverseJoinColumns = @JoinColumn(name = "author_id"),
            inverseForeignKey = @ForeignKey(name = "FK_AUTHORS_BOOKS"))
//    @JsonBackReference
    private Set<Author> authors;

    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private Set<BookRentInfo> bookRentInfos;

    @Override
    public String toString() {
        return "Book{" +
                "bookTitle='" + bookTitle + '\'' +
                ", publishDate=" + publishDate +
                ", pageCount=" + pageCount +
                ", amount=" + amount +
                ", storagePlace='" + storagePlace + '\'' +
                ", onlineCopyPath='" + onlineCopyPath + '\'' +
                ", genre=" + genre +
//                ", authors=" + authors != null ? authors.stream().map(Author::getId).toList().toString() : null +
                ", bookRentInfos=" + bookRentInfos +
                ", id=" + id +
                ", createdWhen=" + createdWhen +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}

