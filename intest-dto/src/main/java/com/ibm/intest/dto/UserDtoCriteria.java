package com.ibm.intest.dto;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
//JsonInclude not null
public class UserDtoCriteria {

    @Nullable
    private Long id;

    @Nullable
    private String firstName;

    @NotNull
    private String lastName;

    @Nullable
    private Integer age;

}
