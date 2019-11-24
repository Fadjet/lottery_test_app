package com.apria.lottery.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.apria.lottery.dto.UserDto;
import com.apria.lottery.entity.User;
import com.apria.lottery.exception.EntityNotFoundException;
import com.apria.lottery.exception.EntityValidationException;
import com.apria.lottery.factory.UserDtoFactory;
import com.apria.lottery.factory.UserFactory;
import com.apria.lottery.repo.UserRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  @Mock
  UserRepository userRepository;
  @Mock
  UserFactory userFactory;
  @Mock
  UserDtoFactory userDtoFactory;

  private UserServiceImpl target;

  @Before
  public void setUp() {
    target = new UserServiceImpl(userRepository, userDtoFactory, userFactory);
  }

  @Test
  public void getAllUsers_noUsers() {
    Iterable iterable = new ArrayList<>();
    when(userRepository.findAll()).thenReturn(iterable);
    List<UserDto> result = target.getAllUsers();
    assertEquals(0, result.size());
  }

  @Test
  public void getAllUsers() {
    User user = mock(User.class);
    UserDto userDto = mock(UserDto.class);
    List<User> users = new ArrayList<>();
    users.add(user);
    when(userRepository.findAll()).thenReturn(users);
    when(userDtoFactory.create(user)).thenReturn(userDto);

    List<UserDto> result = target.getAllUsers();
    assertNotNull(result);
    assertFalse(result.isEmpty());
    assertEquals(1, result.size());
    assertEquals(userDto, result.get(0));
  }

  @Test
  public void getUserById_userNotFound() {
    when(userRepository.findById(0L)).thenReturn(Optional.empty());
    try {
      UserDto result = target.getUserById(0L);
      fail("Exception has not been thrown.");
    } catch (EntityNotFoundException e) {
      assertEquals("Entity is not found.", e.getMessage());
    }
  }

  @Test
  public void getUserById() {
    User user = mock(User.class);
    UserDto userDto = mock(UserDto.class);
    when(userRepository.findById(0L)).thenReturn(Optional.of(user));
    when(userDtoFactory.create(user)).thenReturn(userDto);
    UserDto result = target.getUserById(0L);
    assertEquals(userDto, result);
  }

  @Test
  public void createUser_nullRequest() {
    try {
      UserDto result = target.createUser(null);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("User request is invalid.", e.getMessage());
    }
  }

  @Test
  public void createUser() {
    User user = mock(User.class);
    UserDto userDtoRequest =
        new UserDto.Builder()
            .birthday("2017-01-01")
            .firstName("test")
            .lastName("test")
            .id(0L)
            .build();
    UserDto userDtoResponse = mock(UserDto.class);
    when(userFactory.create(userDtoRequest)).thenReturn(user);
    when(userRepository.save(user)).thenReturn(user);
    when(userDtoFactory.create(user)).thenReturn(userDtoResponse);
    UserDto result = target.createUser(userDtoRequest);
    assertNotNull(result);
    assertEquals(userDtoResponse, result);
  }

  @Test
  public void updateUser_nullId() {
    UserDto userDtoRequest = mock(UserDto.class);
    try {
      UserDto result = target.updateUser(null, userDtoRequest);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("User request is invalid.", e.getMessage());
    }
  }

  @Test
  public void updateUser_nullRequest() {
    try {
      UserDto result = target.updateUser(0L, null);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("User request is invalid.", e.getMessage());
    }
  }

  @Test
  public void updateUser_nullId_nullRequest() {
    try {
      UserDto result = target.updateUser(null, null);
      fail("Exception has not been thrown.");
    } catch (EntityValidationException e) {
      assertEquals("User request is invalid.", e.getMessage());
    }
  }

  @Test
  public void updateUser_userNotFound() {
    UserDto userDtoRequest = mock(UserDto.class);
    when(userRepository.findById(0L)).thenReturn(Optional.empty());
    try {
      UserDto result = target.updateUser(0L, userDtoRequest);
      fail("Exception has not been thrown.");
    } catch (EntityNotFoundException e) {
      assertEquals("Entity is not found.", e.getMessage());
    }
  }

  @Test
  public void updateUser() {
    User user = mock(User.class);
    UserDto userDtoRequest =
        new UserDto.Builder()
            .birthday("2017-01-01")
            .firstName("test")
            .lastName("test")
            .id(0L)
            .build();
    UserDto userDtoResponse = mock(UserDto.class);
    when(userRepository.findById(0L)).thenReturn(Optional.of(user));
    when(userFactory.create(userDtoRequest)).thenReturn(user);
    when(userRepository.save(user)).thenReturn(user);
    when(userDtoFactory.create(user)).thenReturn(userDtoResponse);
    UserDto result = target.updateUser(0L, userDtoRequest);
    assertNotNull(result);
    assertEquals(userDtoResponse, result);
    verify(userRepository, times(1)).save(user);
  }

  @Test
  public void deleteUser_userNotFound() {
    when(userRepository.findById(0L)).thenReturn(Optional.empty());
    try {
      target.deleteUser(0L);
      fail("Exception has not been thrown");
    } catch (EntityNotFoundException e) {
      assertEquals("Entity is not found.", e.getMessage());
    }
  }

  @Test
  public void deleteUser() {
    User user = mock(User.class);
    when(userRepository.findById(0L)).thenReturn(Optional.of(user));
    target.deleteUser(0L);
    verify(userRepository).delete(user);
  }
}
