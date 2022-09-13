package com.kuz9.userstest.service;

import com.kuz9.userstest.dto.UserDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface UserService {

    UserDTO create(UserDTO userDTO);

    UserDTO edit(Map<String, String> userField, long id);

    UserDTO replace(UserDTO userDTO, long id);

    void delete(long id);

    List<UserDTO> searchByBirthDateRange(LocalDate dateFrom, LocalDate dateTo);
}
