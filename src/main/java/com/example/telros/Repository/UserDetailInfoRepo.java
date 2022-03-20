package com.example.telros.Repository;

import com.example.telros.Entity.UserDetailInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDetailInfoRepo extends CrudRepository<UserDetailInfo, Long>{
    Optional<UserDetailInfo> findByPhoneNumber(String phoneNumber);
    Optional<UserDetailInfo> findByEmail(String email);
}