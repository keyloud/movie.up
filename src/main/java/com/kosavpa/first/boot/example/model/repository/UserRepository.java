package com.kosavpa.first.boot.example.model.repository;

import com.kosavpa.first.boot.example.model.entity.users.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByUsername(String userName);
}
