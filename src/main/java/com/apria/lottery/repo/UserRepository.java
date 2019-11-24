package com.apria.lottery.repo;

import com.apria.lottery.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

}
