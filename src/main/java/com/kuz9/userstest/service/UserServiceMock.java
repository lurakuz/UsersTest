package com.kuz9.userstest.service;

import com.kuz9.userstest.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

//need for UserTestApplicationTest
@Service
public class UserServiceMock implements UserService {
    @Override
    public UserDTO create(UserDTO userDTO) {
        return null;
    }

    @Override
    public UserDTO edit(Map<String, String> userField, long id) {
        return null;
    }

    @Override
    public UserDTO replace(UserDTO userDTO, long id) {
        return null;
    }

    @Override
    public void delete(long id) {

    }

    @Override
    public List<UserDTO> searchByBirthDateRange(LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }
}
