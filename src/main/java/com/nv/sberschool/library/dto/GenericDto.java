package com.nv.sberschool.library.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class GenericDto {
    protected Long id;
    protected LocalDateTime createdWhen = LocalDateTime.now();
    protected String createdBy = "DEFAULT_USER";
}

