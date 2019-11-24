package com.apria.lottery.controller;

import com.apria.lottery.dto.UserDto;
import com.apria.lottery.service.UserService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping
  public ResponseEntity<List<UserDto>> getAllUsers() {
    List<UserDto> userDtos = userService.getAllUsers();
    if (userDtos == null || userDtos.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
  }

  @GetMapping("/{id}")
  public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
    UserDto userDto = userService.getUserById(id);
    if (userDto == null) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(userDto, HttpStatus.OK);
    }
  }

  @PostMapping
  public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto) {
    UserDto userDtoResponse = userService.createUser(userDto);
    if (userDtoResponse == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }
  }

  @PutMapping("/{id}")
  public ResponseEntity<UserDto> updateUser(
      @PathVariable("id") Long id, @RequestBody UserDto userDto) {
    UserDto userDtoResponse = userService.updateUser(id, userDto);
    if (userDtoResponse == null) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    } else {
      return new ResponseEntity<>(userDtoResponse, HttpStatus.OK);
    }
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteUser(@PathVariable("id") Long id) {
    userService.deleteUser(id);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
