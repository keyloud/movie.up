package com.kosavpa.first.boot.example.data.repository;

import com.kosavpa.first.boot.example.data.entity.post.PostEntity;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<PostEntity, Long> {
}
