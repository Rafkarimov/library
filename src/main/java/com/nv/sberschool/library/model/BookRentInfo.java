package com.nv.sberschool.library.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "amount", nullable = false)
    private Integer amount;

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

