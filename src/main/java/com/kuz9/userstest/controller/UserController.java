package com.kuz9.userstest.controller;

import com.kuz9.userstest.dto.UserDTO;
import com.kuz9.userstest.exception.UserTestException;
import com.kuz9.userstest.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Provides API for User related REST operations
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    @Value("#{${usertest.app.requiredYears}}")
    private int minimumYears;

    private final UserService userService;

    /**
     * Create user from request body
     *
     * @param userDTO user data to create
     * @return userDTO object with id field
     */
    @PostMapping
    public UserDTO create(@RequestBody UserDTO userDTO) {
        log.info("Creation of user was started. User = {}", userDTO);
        if (!checkYears(userDTO.getBirthDate()))
            throw new UserTestException("You are underage");
        return userService.create(userDTO);
    }

    /**
     * Replace user
     *
     * @param userDTO user data to replace
     * @param id      user id
     * @return replaced user
     */
    @PutMapping(value = {"/{id}"})
    public UserDTO replace(@RequestBody UserDTO userDTO, @PathVariable long id) {
        return userService.replace(userDTO, id);
    }

    /**
     * Edit user
     *
     * @param userField user field to edit
     * @param id        user id
     * @return edited user
     */
    @PatchMapping(value = "/{id}")
    public UserDTO edit(@RequestParam Map<String, String> userField, @PathVariable long id) {
        return userService.edit(userField, id);
    }

    /**
     * Delete user by id
     *
     * @param id id of user that should be deleted
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        userService.delete(id);
    }

    /**
     * Search users by birth dates range
     *
     * @param dateFrom date 'from' to search
     * @param dateTo   date 'to' to search
     * @return list of UserDTO
     */
    @GetMapping
    public List<UserDTO> searchByBirthDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo) {
        if (dateFrom.isAfter(dateTo))
            throw new UserTestException("Date 'from' is after date 'to'");
        return userService.searchByBirthDateRange(dateFrom, dateTo);
    }

    /**
     * Check if provided birth date is greater than minimumYears value
     *
     * @param dateToCheck provided date
     * @return true if greater and false if not
     */
    private boolean checkYears(LocalDate dateToCheck) {
        LocalDate today = LocalDate.now();

        Period period = Period.between(dateToCheck, today);
        int diff = period.getYears();

        return diff >= minimumYears;
    }
}
