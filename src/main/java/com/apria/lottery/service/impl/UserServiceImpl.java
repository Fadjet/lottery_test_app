package com.apria.lottery.service.impl;

import static com.apria.lottery.utils.UserDtoValidator.validateUserDto;

import com.apria.lottery.dto.UserDto;
import com.apria.lottery.entity.User;
import com.apria.lottery.exception.EntityNotFoundException;
import com.apria.lottery.exception.EntityValidationException;
import com.apria.lottery.factory.UserFactory;
import com.apria.lottery.factory.UserDtoFactory;
import com.apria.lottery.repo.UserRepository;
import com.apria.lottery.service.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;
  private UserDtoFactory userDtoFactory;
  private UserFactory userFactory;

  public UserServiceImpl(
      UserRepository userRepository, UserDtoFactory userDtoFactory, UserFactory userFactory) {
    this.userRepository = userRepository;
    this.userDtoFactory = userDtoFactory;
    this.userFactory = userFactory;
  }

  @Override
  public List<UserDto> getAllUsers() {
    Iterable<User> users = userRepository.findAll();
    return StreamSupport.stream(users.spliterator(), false)
        .map(userDtoFactory::create)
        .collect(Collectors.toList());
  }

  @Override
  public UserDto getUserById(Long id) {
    Optional<User> user = userRepository.findById(id);
    if (user.isPresent()) {
      return userDtoFactory.create(user.get());
    } else {
      throw new EntityNotFoundException();
    }
  }

  @Override
  public UserDto createUser(UserDto userDto) {
    if (userDto != null) {
      validateUserDto(userDto);
      User user = userFactory.create(userDto);
      return userDtoFactory.create(userRepository.save(user));
    } else {
      throw new EntityValidationException("User request is invalid.");
    }
  }

  @Override
  public UserDto updateUser(Long id, UserDto userDto) {
    if (id != null && userDto != null) {
      Optional<User> existingUser = userRepository.findById(id);
      if (!existingUser.isPresent()) {
        throw new EntityNotFoundException();
      }
      userDto.setId(id);
      validateUserDto(userDto);
        User user = userFactory.create(userDto);
        return userDtoFactory.create(userRepository.save(user));
      } else {
      throw new EntityValidationException("User request is invalid.");
    }
  }

  @Override
  public void deleteUser(Long id) {
    Optional<User> user = userRepository.findById(id);
    if (!user.isPresent()) {
      throw new EntityNotFoundException();
    } else {
      userRepository.delete(user.get());
    }
  }
}
