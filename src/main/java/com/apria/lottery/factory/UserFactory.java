package com.apria.lottery.factory;

import com.apria.lottery.dto.UserDto;
import com.apria.lottery.entity.User;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
  public User create(UserDto userDto) {
    if (userDto != null) {
      return new User.Builder()
          .id(userDto.getId())
          .firstName(userDto.getFirstName())
          .lastName(userDto.getLastName())
          .birthday(LocalDate.parse(userDto.getBirthday()))
          .build();
    } else {
      return null;
    }
  }
}
