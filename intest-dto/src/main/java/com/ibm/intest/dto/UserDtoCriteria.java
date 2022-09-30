package com.ibm.intest.dto;



import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@Data
public class UserDtoCriteria {

    @Nullable
    private Long id;

    @Nullable
    private String firstName;

    @NotNull(message = "il campo cognome non deve essere vuoto")
    private String lastName;

    @Nullable
    private Integer age;

}
