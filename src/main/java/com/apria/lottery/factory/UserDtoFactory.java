package com.apria.lottery.factory;

import com.apria.lottery.dto.UserDto;
import com.apria.lottery.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserDtoFactory {

  public UserDto create(User user) {
    if (user != null) {
      return new UserDto.Builder()
          .id(user.getId())
          .firstName(user.getFirstName())
          .lastName(user.getLastName())
          .birthday(user.getBirthday().toString())
          .build();
    } else {
      return null;
    }
  }
}
