package com.apria.lottery.utils;


import static com.apria.lottery.utils.UserDtoValidator.validateUserDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import com.apria.lottery.dto.UserDto;
import com.apria.lottery.exception.EntityValidationException;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDtoValidatorTest {

  @Test
  public void birthdayFormatIsIncorrect_shouldThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday("20177-01-01")
            .firstName("test")
            .lastName("test")
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("Birthday format is incorrect.", e.getMessage());
    }
  }

  @Test
  public void birthdayIsEmpty_shouldThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday(" ")
            .firstName("test")
            .lastName("test")
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("Birthday cannot be empty.", e.getMessage());
    }
  }

  @Test
  public void birthdayIsNull_shouldThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday(null)
            .firstName("test")
            .lastName("test")
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("Birthday cannot be empty.", e.getMessage());
    }
  }

  @Test
  public void birthdayIsInFuture_shouldThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday(LocalDate.now().plusYears(1).toString())
            .firstName("test")
            .lastName("test")
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("Birthday date is incorrect.", e.getMessage());
    }
  }

  @Test
  public void birthdayIsCurrentDate_shouldThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday(LocalDate.now().toString())
            .firstName("test")
            .lastName("test")
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("Birthday date is incorrect.", e.getMessage());
    }
  }

  @Test
  public void birthdayIsOk_shouldNotThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday(LocalDate.now().minusYears(10).toString())
            .firstName("test")
            .lastName("test")
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
    } catch (EntityValidationException e) {
      fail("Exception has been thrown.");
    }
  }

  @Test
  public void firstNameIsEmpty_shouldThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday(LocalDate.now().minusYears(10).toString())
            .firstName(" ")
            .lastName("test")
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("First name cannot be empty.", e.getMessage());
    }
  }

  @Test
  public void firstNameIsNull_shouldThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday(LocalDate.now().minusYears(10).toString())
            .firstName(null)
            .lastName("test")
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("First name cannot be empty.", e.getMessage());
    }
  }

  @Test
  public void firstNameIsOk_shouldNotThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday(LocalDate.now().minusYears(10).toString())
            .firstName("test")
            .lastName("test")
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
    } catch (EntityValidationException e) {
      fail("Exception has been thrown.");
    }
  }

  @Test
  public void lastNameIsEmpty_shouldThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday(LocalDate.now().minusYears(10).toString())
            .firstName("test")
            .lastName(" ")
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("Last name cannot be empty.", e.getMessage());
    }
  }

  @Test
  public void lastNameIsNull_shouldThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday(LocalDate.now().minusYears(10).toString())
            .firstName("test")
            .lastName(null)
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("Last name cannot be empty.", e.getMessage());
    }
  }

  @Test
  public void lastNameIsOk_shouldNotThrowException(){
    UserDto userDto =
        new UserDto.Builder()
            .birthday(LocalDate.now().minusYears(10).toString())
            .firstName("test")
            .lastName("test")
            .id(0L)
            .build();

    try {
      validateUserDto(userDto);
    } catch (EntityValidationException e) {
      fail("Exception has been thrown.");
    }
  }

}