package com.kuz9.userstest.entity;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {

    private Long id;

    @NotBlank
    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    @NotBlank
    @Past
    private LocalDate birthDate;

    private String address;

    private String phoneNumber;

}
