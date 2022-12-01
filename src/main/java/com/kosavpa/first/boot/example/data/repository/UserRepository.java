package com.kosavpa.first.boot.example.data.repository;

import com.kosavpa.first.boot.example.data.entity.users.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String userName);
}
