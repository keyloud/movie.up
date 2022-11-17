package com.kosavpa.first.boot.example.model.repository;

import com.kosavpa.first.boot.example.model.entity.post.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
}
