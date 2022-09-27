package com.ibm.intest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserGetDto {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;

}
