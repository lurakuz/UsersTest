package com.kuz9.userstest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Data
public class UserDTO {

    private Long id;

    @NotBlank
    @Pattern(regexp = "^(.+)@(.+)$")
    private String email;

    @NotBlank
    @Pattern(regexp = "[A-Za-zА-Яа-яІіЇї]+(\\s[A-Za-zА-яа-яІіЇї]+)*")
    private String firstName;

    @NotBlank
    @Pattern(regexp = "[A-Za-zА-Яа-яІіЇї]+(\\s[A-Za-zА-яа-яІіЇї]+)*")
    private String lastName;

    @NotBlank
    @Past
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthDate;

    @NotBlank
    private String address;

    @Pattern(regexp = "\\+[0-9]{12}")
    private String phoneNumber;

}
