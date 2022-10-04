package com.ibm.intest.dto;



import lombok.Data;
import org.springframework.lang.Nullable;
import javax.validation.constraints.NotBlank;

@Data
public class UserDtoCriteria {

    @Nullable
    private Long id;

    @Nullable
    private String firstName;

    @NotBlank(message = "il campo cognome non deve essere vuoto")
    private String lastName;

    @Nullable
    private Integer age;

}
