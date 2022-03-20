package com.example.telros.Repository;

import com.example.telros.Entity.UserContactInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserContactInfoRepo extends CrudRepository<UserContactInfo, Long> {
}
