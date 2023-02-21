package com.nv.sberschool.library.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class GenericModel {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "default_gen")
    protected Long id;

    @Column(name = "created_when")
    protected LocalDateTime createdWhen;

    @Column(name = "created_by")
    protected String createdBy;
}

