package com.apria.lottery.factory;

import static org.junit.Assert.*;

import com.apria.lottery.dto.UserDto;
import com.apria.lottery.entity.User;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserFactoryTest {

  UserFactory target;

  @Before
  public void setUp() {
    target = new UserFactory();
  }

  @Test
  public void create_shouldReturnUser() {
    UserDto userDto =
        new UserDto.Builder()
            .id(0L)
            .firstName("firstName")
            .lastName("lastName")
            .birthday(LocalDate.now().minusYears(20).toString())
            .build();
    User result = target.create(userDto);
    assertNotNull(result);
    assertEquals(userDto.getId(), result.getId());
    assertEquals(userDto.getFirstName(), result.getFirstName());
    assertEquals(userDto.getLastName(), result.getLastName());
    assertEquals(userDto.getLastName(), result.getLastName());
  }

  @Test
  public void create_shouldReturnNull(){
    assertNull(target.create(null));
  }
}
