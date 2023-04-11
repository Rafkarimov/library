package com.nv.sberschool.library.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class GenericDto {

    protected Long id;
    //  protected LocalDateTime createdWhen = LocalDateTime.now();
    protected String createdBy = "DEFAULT_USER";
    protected boolean isDeleted = false;
    protected String deletedBy;
    //  protected LocalDateTime deletedWhen;
//  protected LocalDateTime updatedWhen;
    protected String updatedBy;
}
