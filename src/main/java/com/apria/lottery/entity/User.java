package com.apria.lottery.entity;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "TEST_USER")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "FIRST_NAME", length = 50)
  @NotNull
  @Size(min = 1, max = 50)
  private String firstName;

  @Column(name = "LAST_NAME", length = 50)
  @NotNull
  @Size(min = 2, max = 50)
  private String lastName;

  @Column(name = "BIRTHDAY")
  @NotNull
  private LocalDate birthday;

  private User() {}

  public static class Builder {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate birthday;

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

    public Builder birthday(LocalDate birthday) {
      this.birthday = birthday;
      return this;
    }

    public User build() {
      User user = new User();
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

  public LocalDate getBirthday() {
    return birthday;
  }

  public void setBirthday(LocalDate birthday) {
    this.birthday = birthday;
  }
}
