package com.kosavpa.first.boot.example.model.repository;

import com.kosavpa.first.boot.example.model.entity.users.RoleEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
}
