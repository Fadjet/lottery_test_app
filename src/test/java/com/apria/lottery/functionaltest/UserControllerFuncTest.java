package com.apria.lottery.functionaltest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.apria.lottery.LotteryApplication;
import com.apria.lottery.dto.UserDto;
import com.apria.lottery.repo.UserRepository;
import java.time.LocalDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest(
    classes = LotteryApplication.class,
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerFuncTest {

  @Autowired private TestRestTemplate template;
  @Autowired private UserRepository userRepository;
  @Autowired private WebApplicationContext context;

  private MockMvc mvc;

  @Before
  public void setup() {
    mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  public void getAllUsers_shouldNotReturnUsers() {
    ResponseEntity<UserDto[]> result =
        template.withBasicAuth("lottery_user", "passw0rd").getForEntity("/users", UserDto[].class);
    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  @Test
  public void getAllUsers_shouldNotAllow() {
    ResponseEntity<UserDto> result = template.getForEntity("/users", UserDto.class);
    assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
  }

  @Test
  public void postUsers_shouldCreateUser() {
    UserDto userDto =
        new UserDto.Builder().firstName("test").lastName("test").birthday("2017-01-01").build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDto);
    ResponseEntity<UserDto> result =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, UserDto.class);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(userDto.getFirstName(), result.getBody().getFirstName());
    assertEquals(userDto.getLastName(), result.getBody().getLastName());
    assertEquals(userDto.getBirthday(), result.getBody().getBirthday());
  }

  @Test
  public void postUsers_shouldNotAllow() throws Exception {
    mvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(401));
  }

  @Test
  public void putUsers_shouldUpdateUser() {
    UserDto userDtoForPost =
        new UserDto.Builder().firstName("test").lastName("test").birthday("2017-01-01").build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDtoForPost);
    ResponseEntity<UserDto> postResult =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, UserDto.class);
    assertNotNull(postResult.getBody());
    UserDto userDtoForPut =
        new UserDto.Builder()
            .id(postResult.getBody().getId())
            .firstName("test1")
            .lastName("test1")
            .birthday("2007-01-01")
            .build();
    request = new HttpEntity<>(userDtoForPut);
    template
        .withBasicAuth("lottery_user", "passw0rd")
        .put(String.format("/users/%s", userDtoForPut.getId()), request, UserDto.class);

    ResponseEntity<UserDto> finalResult =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .getForEntity(String.format("/users/%s", postResult.getBody().getId()), UserDto.class);

    assertEquals(HttpStatus.OK, finalResult.getStatusCode());
    assertNotNull(finalResult.getBody());
    assertEquals(postResult.getBody().getId(), finalResult.getBody().getId());
    assertEquals(userDtoForPut.getFirstName(), finalResult.getBody().getFirstName());
    assertEquals(userDtoForPut.getLastName(), finalResult.getBody().getLastName());
    assertEquals(userDtoForPut.getBirthday(), finalResult.getBody().getBirthday());
  }

  @Test
  public void putUsers_shouldNotAllow() throws Exception {
    mvc.perform(put("/users/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(401));
  }

  @Test
  public void getAllUsers_shouldReturnThreeUsers() {
    createUsers(3);
    ResponseEntity<UserDto[]> result =
        template.withBasicAuth("lottery_user", "passw0rd").getForEntity("/users", UserDto[].class);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertNotNull(result.getBody());
    assertEquals(3, result.getBody().length);
  }

  private void createUsers(int usersCount) {
    for (int i = 0; i < usersCount; i++) {
      UserDto userDto =
          new UserDto.Builder().firstName("test").lastName("test").birthday("2017-01-01").build();
      HttpEntity<UserDto> request = new HttpEntity<>(userDto);
      template
          .withBasicAuth("lottery_user", "passw0rd")
          .postForEntity("/users", request, UserDto.class);
    }
  }

  @Test
  public void deleteUsers_shouldDeleteUser() {
    createUsers(1);
    template.withBasicAuth("lottery_user", "passw0rd").delete("/users/1");
    ResponseEntity<UserDto[]> result =
        template.withBasicAuth("lottery_user", "passw0rd").getForEntity("/users", UserDto[].class);
    assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
  }

  @Test
  public void deleteUsers_shouldNotAllow() throws Exception {
    mvc.perform(delete("/users/1").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().is(401));
  }

  @Test
  public void postUsers_shouldNotCreateUser_invalidBirthdayFormat() {
    UserDto userDto =
        new UserDto.Builder().firstName("test").lastName("test").birthday("20177-01-01").build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDto);
    ResponseEntity<String> result =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals("Birthday format is incorrect.", result.getBody());
  }

  @Test
  public void postUsers_shouldNotCreateUser_emptyBirthday() {
    UserDto userDto = new UserDto.Builder().firstName("test").lastName("test").birthday("").build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDto);
    ResponseEntity<String> result =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals("Birthday cannot be empty.", result.getBody());
  }

  @Test
  public void postUsers_shouldNotCreateUser_nullBirthday() {
    UserDto userDto = new UserDto.Builder().firstName("test").lastName("test").build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDto);
    ResponseEntity<String> result =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals("Birthday cannot be empty.", result.getBody());
  }

  @Test
  public void postUsers_shouldNotCreateUser_birthdayDateIsInFuture() {
    UserDto userDto =
        new UserDto.Builder()
            .firstName("test")
            .lastName("test")
            .birthday(LocalDate.now().plusYears(1).toString())
            .build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDto);
    ResponseEntity<String> result =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals("Birthday date is incorrect.", result.getBody());
  }

  @Test
  public void postUsers_shouldNotCreateUser_birthdayDateIsCurrentDate() {
    UserDto userDto =
        new UserDto.Builder()
            .firstName("test")
            .lastName("test")
            .birthday(LocalDate.now().toString())
            .build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDto);
    ResponseEntity<String> result =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals("Birthday date is incorrect.", result.getBody());
  }

  @Test
  public void postUsers_shouldNotCreateUser_emptyFirstName() {
    UserDto userDto =
        new UserDto.Builder()
            .firstName(" ")
            .lastName("test")
            .birthday(LocalDate.now().minusYears(20).toString())
            .build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDto);
    ResponseEntity<String> result =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals("First name cannot be empty.", result.getBody());
  }

  @Test
  public void postUsers_shouldNotCreateUser_nullFirstName() {
    UserDto userDto =
        new UserDto.Builder()
            .lastName("test")
            .birthday(LocalDate.now().minusYears(20).toString())
            .build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDto);
    ResponseEntity<String> result =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals("First name cannot be empty.", result.getBody());
  }

  @Test
  public void postUsers_shouldNotCreateUser_emptyLastName() {
    UserDto userDto =
        new UserDto.Builder()
            .firstName("test")
            .lastName(" ")
            .birthday(LocalDate.now().minusYears(20).toString())
            .build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDto);
    ResponseEntity<String> result =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals("Last name cannot be empty.", result.getBody());
  }

  @Test
  public void postUsers_shouldNotCreateUser_nullLastName() {
    UserDto userDto =
        new UserDto.Builder()
            .firstName("test")
            .birthday(LocalDate.now().minusYears(20).toString())
            .build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDto);
    ResponseEntity<String> result =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, String.class);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals("Last name cannot be empty.", result.getBody());
  }

  @Test
  public void getUserById_shouldReturnUser() {
    UserDto userDto =
        new UserDto.Builder().firstName("test").lastName("test").birthday("2017-01-01").build();
    HttpEntity<UserDto> request = new HttpEntity<>(userDto);
    ResponseEntity<UserDto> postResult =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .postForEntity("/users", request, UserDto.class);
    assertNotNull(postResult.getBody());
    ResponseEntity<UserDto> getResult =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .getForEntity(String.format("/users/%s", postResult.getBody().getId()), UserDto.class);

    assertEquals(HttpStatus.OK, getResult.getStatusCode());
    assertNotNull(getResult.getBody());
    assertNotNull(getResult.getBody().getId());
    assertTrue(getResult.getBody().getId().equals(postResult.getBody().getId()));
    assertEquals(userDto.getFirstName(), getResult.getBody().getFirstName());
    assertEquals(userDto.getLastName(), getResult.getBody().getLastName());
    assertEquals(userDto.getBirthday(), getResult.getBody().getBirthday());
  }

  @Test
  public void getUserById_shouldNotReturnUser_EntityNotFoundException() {
    ResponseEntity<String> result =
        template
            .withBasicAuth("lottery_user", "passw0rd")
            .getForEntity("/users/1", String.class);

    assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    assertEquals("Entity is not found.", result.getBody());
  }

  @After
  public void clear() {
    userRepository.deleteAll();
  }
}
