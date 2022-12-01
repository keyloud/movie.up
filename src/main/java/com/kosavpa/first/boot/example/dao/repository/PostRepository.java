package com.kosavpa.first.boot.example.dao.repository;

import com.kosavpa.first.boot.example.dao.entity.post.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
}
