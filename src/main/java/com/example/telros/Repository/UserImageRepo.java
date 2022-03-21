package com.example.telros.Repository;

import com.example.telros.Entity.UserImage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageRepo extends CrudRepository<UserImage, Long> {
}
