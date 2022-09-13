package com.kuz9.userstest.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kuz9.userstest.dto.UserDTO;
import com.kuz9.userstest.exception.UserTestException;
import com.kuz9.userstest.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void shouldReturnCreatedUser() throws Exception {
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress("Lysenka 14/14");
        userDTO.setEmail("lurakuz08@gmail.com");
        LocalDate birthDate = LocalDate.of(1999, 11, 8);
        userDTO.setBirthDate(birthDate);
        userDTO.setLastName("Kuzyk");
        userDTO.setPhoneNumber("0957079790");
        userDTO.setFirstName("Yurii");

        UserDTO userDTOToReturn = new UserDTO();
        userDTOToReturn.setAddress("Lysenka 14/14");
        userDTOToReturn.setEmail("lurakuz08@gmail.com");
        userDTOToReturn.setBirthDate(birthDate);
        userDTOToReturn.setLastName("Kuzyk");
        userDTOToReturn.setPhoneNumber("0957079790");
        userDTOToReturn.setFirstName("Yurii");
        userDTOToReturn.setId(1111111111L);

        //when
        when(userService.create(userDTO)).thenReturn(userDTOToReturn);

        mockMvc.perform(
                        post("/api/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDTO))
                ).andExpect(status().isOk())
                .andExpect(content().string(containsString("1111111111")));
    }

    @Test
    void shouldFailWithUnderageException() throws Exception {
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress("Lysenka 14/14");
        userDTO.setEmail("lurakuz08@gmail.com");
        LocalDate birthDate = LocalDate.of(2010, 11, 8);
        userDTO.setBirthDate(birthDate);
        userDTO.setLastName("Kuzyk");
        userDTO.setPhoneNumber("0957079790");
        userDTO.setFirstName("Yurii");

        UserDTO userDTOToReturn = new UserDTO();
        userDTOToReturn.setAddress("Lysenka 14/14");
        userDTOToReturn.setEmail("lurakuz08@gmail.com");
        userDTOToReturn.setBirthDate(birthDate);
        userDTOToReturn.setLastName("Kuzyk");
        userDTOToReturn.setPhoneNumber("0957079790");
        userDTOToReturn.setFirstName("Yurii");
        userDTOToReturn.setId(1L);

        when(userService.create(userDTO)).thenReturn(userDTOToReturn);

        mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO))).andExpect(status().isBadRequest());
    }

    @Test
    void shouldFailOnEmailValidation() throws Exception {
        //given
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress("Lysenka 14/14");
        userDTO.setEmail("lurakuz08gmail.com");
        LocalDate birthDate = LocalDate.of(1999, 11, 8);
        userDTO.setBirthDate(birthDate);
        userDTO.setLastName("Kuzyk");
        userDTO.setPhoneNumber("0957079790");
        userDTO.setFirstName("Yurii");
        userDTO.setId(1L);


        when(userService.create(userDTO)).thenThrow(UserTestException.class);

        mockMvc.perform(
                post("/api/user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO))).andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnReplacedUser() throws Exception {
        long id = 1;

        UserDTO userDTO = new UserDTO();
        userDTO.setAddress("Lysenka 14/1");
        userDTO.setEmail("lurakuz0@gmail.com");
        LocalDate birthDate = LocalDate.of(1999, 11, 9);
        userDTO.setBirthDate(birthDate);
        userDTO.setLastName("Kuzy");
        userDTO.setPhoneNumber("0957079791");
        userDTO.setFirstName("Yuri");


        when(userService.replace(userDTO, id)).thenReturn(userDTO);

        mockMvc.perform(
                put("/api/user/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO))).andExpect(status().isOk());
    }

    @Test
    void shouldReturnEditedUser() throws Exception {
        Map<String, String> userField = new HashMap<>();
        userField.put("firstName", "Yurii");

        LocalDate birthDate = LocalDate.of(1999, 11, 8);
        UserDTO userDTOToReturn = new UserDTO();
        userDTOToReturn.setAddress("Lysenka 14/14");
        userDTOToReturn.setEmail("lurakuz08@gmail.com");
        userDTOToReturn.setBirthDate(birthDate);
        userDTOToReturn.setLastName("Kuzyk");
        userDTOToReturn.setPhoneNumber("0957079790");
        userDTOToReturn.setFirstName("Yurii");
        userDTOToReturn.setId(1L);

        when(userService.edit(userField, 1)).thenReturn(userDTOToReturn);

        mockMvc.perform(
                        patch("/api/user/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userField)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/api/user/1")).andExpect(status().isOk());
    }

    @Test
    void shouldReturnListOfUsers() throws Exception {
        List<UserDTO> userDTOS = new ArrayList<>();

        LocalDate dateFrom = LocalDate.of(1999, 11, 8);
        LocalDate dateTo = LocalDate.of(1999, 11, 9);

        UserDTO userDTOToReturn = new UserDTO();
        userDTOToReturn.setAddress("Lysenka 14/14");
        userDTOToReturn.setEmail("lurakuz08@gmail.com");
        userDTOToReturn.setBirthDate(dateFrom);
        userDTOToReturn.setLastName("Kuzyk");
        userDTOToReturn.setPhoneNumber("0957079790");
        userDTOToReturn.setFirstName("Yurii");
        userDTOToReturn.setId(1L);

        userDTOS.add(userDTOToReturn);

        when(userService.searchByBirthDateRange(dateFrom, dateTo)).thenReturn(userDTOS);

        mockMvc.perform(get("/api/user")
                .param("dateFrom", String.valueOf(dateFrom))
                .param("dateTo", String.valueOf(dateTo)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldFailOnDatesValidation() throws Exception {
        LocalDate dateFrom = LocalDate.of(1999, 11, 8);
        LocalDate dateTo = LocalDate.of(1998, 11, 8);

        when(userService.searchByBirthDateRange(dateFrom, dateTo)).thenThrow(UserTestException.class);

        mockMvc.perform(get("/api/user")
                .param("dateFrom", String.valueOf(dateFrom))
                .param("dateTo", String.valueOf(dateTo)))
                .andExpect(status().isBadRequest());
    }
}
