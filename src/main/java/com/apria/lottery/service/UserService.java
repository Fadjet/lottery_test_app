package com.apria.lottery.service;

import com.apria.lottery.dto.UserDto;
import java.util.List;

public interface UserService {

  List<UserDto> getAllUsers();

  UserDto getUserById(Long id);

  UserDto createUser(UserDto userDto);

  UserDto updateUser(Long id, UserDto userDto);

  void deleteUser(Long id);

}
