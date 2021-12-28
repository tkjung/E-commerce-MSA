package com.example.userservice.jpa;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {  // Long이 기본키인 Entity에 대해 CRUD 관련 기능 제공
    UserEntity findByUserId(String userId);
    UserEntity findByEmail(String username);
}
