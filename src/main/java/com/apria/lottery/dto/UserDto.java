package com.apria.lottery.dto;

public class UserDto {

  private Long id;
  private String firstName;
  private String lastName;
  private String birthday;

  private UserDto(){}

  public static class Builder {
    private Long id;
    private String firstName;
    private String lastName;
    private String birthday;

    public Builder id(Long id) {
      this.id = id;
      return this;
    }

    public Builder firstName(String firstName) {
      this.firstName = firstName;
      return this;
    }

    public Builder lastName(String lastName) {
      this.lastName = lastName;
      return this;
    }

    public Builder birthday(String birthday) {
      this.birthday = birthday;
      return this;
    }

    public UserDto build() {
      UserDto user = new UserDto();
      user.id = this.id;
      user.firstName = this.firstName;
      user.lastName = this.lastName;
      user.birthday = this.birthday;
      return user;
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getBirthday() {
    return birthday;
  }

  public void setBirthday(String birthday) {
    this.birthday = birthday;
  }
}
