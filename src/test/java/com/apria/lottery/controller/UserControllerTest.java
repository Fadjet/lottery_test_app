package com.apria.lottery.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.apria.lottery.dto.UserDto;
import com.apria.lottery.service.impl.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

  @Mock
  UserServiceImpl userService;

  UserController target;

  @Before
  public void setUp() {
    target = new UserController(userService);
  }

  @Test
  public void getAllUsers_returnsNull() {
    when(userService.getAllUsers()).thenReturn(null);
    ResponseEntity actual = target.getAllUsers();
    assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
  }

  @Test
  public void getAllUsers_noValues() {
    List<UserDto> users = new ArrayList<>();
    when(userService.getAllUsers()).thenReturn(users);
    ResponseEntity actual = target.getAllUsers();
    assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
  }

  @Test
  public void getAllUsers() {
    UserDto userDto = mock(UserDto.class);
    List<UserDto> users = new ArrayList<>();
    users.add(userDto);
    when(userService.getAllUsers()).thenReturn(users);

    ResponseEntity<List<UserDto>> actual = target.getAllUsers();
    assertNotNull(actual);
    assertEquals(HttpStatus.OK, actual.getStatusCode());
    assertEquals(1, actual.getBody().size());
    assertEquals(userDto, actual.getBody().get(0));
  }

  @Test
  public void getUserById_notFound() {
    when(userService.getUserById(0L)).thenReturn(null);
    ResponseEntity actual = target.getUserById(0L);
    assertEquals(HttpStatus.NOT_FOUND, actual.getStatusCode());
  }

  @Test
  public void getUserById() {
    UserDto userDto = mock(UserDto.class);
    when(userService.getUserById(0L)).thenReturn(userDto);
    ResponseEntity<UserDto> actual = target.getUserById(0L);
    assertNotNull(actual);
    assertEquals(HttpStatus.OK, actual.getStatusCode());
    assertNotNull(actual.getBody());
    assertEquals(userDto, actual.getBody());
  }

  @Test
  public void createUser_noResponse() {
    UserDto userDtoRequest = mock(UserDto.class);
    when(userService.createUser(userDtoRequest)).thenReturn(null);
    ResponseEntity actual = target.createUser(userDtoRequest);
    assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
  }

  @Test
  public void createUser() {
    UserDto userDtoRequest = mock(UserDto.class);
    UserDto userDtoResponse = mock(UserDto.class);
    when(userService.createUser(userDtoRequest)).thenReturn(userDtoResponse);
    ResponseEntity<UserDto> actual = target.createUser(userDtoRequest);
    assertNotNull(actual);
    assertEquals(HttpStatus.OK, actual.getStatusCode());
    assertNotNull(actual.getBody());
    assertEquals(userDtoResponse, actual.getBody());
  }

  @Test
  public void updateUser_noResponse() {
    UserDto userDtoRequest = mock(UserDto.class);
    when(userService.updateUser(0L, userDtoRequest)).thenReturn(null);
    ResponseEntity actual = target.updateUser(0L, userDtoRequest);
    assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
  }

  @Test
  public void updateUser() {
    UserDto userDtoRequest = mock(UserDto.class);
    UserDto userDtoResponse = mock(UserDto.class);
    when(userService.updateUser(0L, userDtoRequest)).thenReturn(userDtoResponse);
    ResponseEntity<UserDto> actual = target.updateUser(0L, userDtoRequest);
    assertNotNull(actual);
    assertEquals(HttpStatus.OK, actual.getStatusCode());
    assertNotNull(actual.getBody());
    assertEquals(userDtoResponse, actual.getBody());
  }

  @Test
  public void deleteUser() {
    target.deleteUser(0L);
    verify(userService, times(1)).deleteUser(0L);
  }

}
