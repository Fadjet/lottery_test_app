package com.apria.lottery.utils;

import static org.apache.commons.lang3.StringUtils.trim;

import com.apria.lottery.dto.UserDto;
import com.apria.lottery.exception.EntityValidationException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UserDtoValidator {

  private UserDtoValidator() {
  }

  public static void validateUserDto(UserDto userDto) {
    if (userDto.getBirthday() == null || trim(userDto.getBirthday()).isEmpty()) {
      throw new EntityValidationException("Birthday cannot be empty.");
    }

    try {
      LocalDate.parse(userDto.getBirthday());
    } catch (DateTimeParseException ex) {
      throw new EntityValidationException("Birthday format is incorrect.");
    }

    LocalDate birthday = LocalDate.parse(userDto.getBirthday());
    if (birthday.equals(LocalDate.now()) || birthday.isAfter(LocalDate.now())) {
      throw new EntityValidationException("Birthday date is incorrect.");
    }

    if (userDto.getFirstName() == null || trim(userDto.getFirstName()).isEmpty()) {
      throw new EntityValidationException("First name cannot be empty.");
    }

    if (userDto.getLastName() == null || trim(userDto.getLastName()).isEmpty()) {
      throw new EntityValidationException("Last name cannot be empty.");
    }
  }
}
