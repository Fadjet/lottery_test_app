package com.apria.lottery.factory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.apria.lottery.dto.UserDto;
import com.apria.lottery.entity.User;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDtoFactoryTest {

  UserDtoFactory target;

  @Before
  public void setUp() {
    target = new UserDtoFactory();
  }

  @Test
  public void create_shouldReturnUserDto() {
    User user = new User.Builder().id(0L).firstName("firstName").lastName("lastName").birthday(
        LocalDate.now().minusYears(20)).build();
    UserDto result = target.create(user);
    assertNotNull(result);
    assertEquals(user.getId(), result.getId());
    assertEquals(user.getFirstName(), result.getFirstName());
    assertEquals(user.getLastName(), result.getLastName());
    assertEquals(user.getLastName(), result.getLastName());
  }

  @Test
  public void create_shouldReturnNull(){
    assertNull(target.create(null));
  }
}