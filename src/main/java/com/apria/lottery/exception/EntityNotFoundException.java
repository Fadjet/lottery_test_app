package com.apria.lottery.exception;

public class EntityNotFoundException extends RuntimeException {

  public EntityNotFoundException() {
    super("Entity is not found.");
  }
}
