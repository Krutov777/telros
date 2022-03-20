package com.example.telros.Service;

import com.example.telros.Entity.UserContactInfo;
import com.example.telros.Entity.UserDetailInfo;
import com.example.telros.Repository.UserContactInfoRepo;
import com.example.telros.Repository.UserDetailInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserDetailInfoService {
    private final UserDetailInfoRepo userDetailInfoRepo;

    private final UserContactInfoRepo userContactInfoRepo;

    @Autowired
    public UserDetailInfoService(UserDetailInfoRepo userDetailInfoRepo, UserContactInfoRepo userContactInfoRepo) {
        this.userDetailInfoRepo = userDetailInfoRepo;
        this.userContactInfoRepo = userContactInfoRepo;
    }

    /*
     * Добавление детальной информации пользователя
     * проверка: на наличие пустых полей в объекте,
     *           на уникальность телефона и почты,
     *           привязан ли уже к userContactInfo userDetailInfo
     * */
    public ResponseEntity<?> addUserDetailInfoAndContactInfo(UserDetailInfo userDetailInfo, Long userContactInfoId) {
        UserContactInfo userContactInfo = userContactInfoRepo.findById(userContactInfoId).orElse(null);
        if (
                userDetailInfo.getDateOfBirth() == null
                        || userDetailInfo.getEmail().equals("")
                        || userDetailInfo.getPhoneNumber().equals("")
                        || Objects.requireNonNull(userContactInfo).getName().equals("")
        )
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        else {
            Optional<UserDetailInfo> row = userDetailInfoRepo.findByPhoneNumber(userDetailInfo.getPhoneNumber());
            Optional<UserDetailInfo> row1 = userDetailInfoRepo.findByEmail(userDetailInfo.getEmail());
            if ((row.isEmpty() || row1.isEmpty()) && Objects.requireNonNull(userContactInfo).getUserDetailInfo() == null) {
                userDetailInfo.setUserContactInfo(userContactInfo);
                userDetailInfoRepo.save(userDetailInfo);
                return new ResponseEntity<>(HttpStatus.CREATED);
            } else
                return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
        }
    }

    /*
     * Получение детальной информации пользователей
     * проверка: на пустоту списка детальной информации
     * */
    public ResponseEntity<List<UserDetailInfo>> getUsersDetailInfo() {
        List<UserDetailInfo> list = (List<UserDetailInfo>) userDetailInfoRepo.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /*
     * Получение детальной информации пользователя
     * проверка: на пустоту списка детальной информации
     * */
    public ResponseEntity<UserDetailInfo> getUserDetailInfo(Long id) {
        Optional<UserDetailInfo> row = userDetailInfoRepo.findById(id);
        if (row.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(row.get(), HttpStatus.OK);
    }

    /*
     * Обновление детальной информации пользователя
     * проверка: на наличие пустых полей в объекте,
     *           на уникальность телефона и почты,
     * */
    public ResponseEntity<UserDetailInfo> updateUserDetailInfo(Long id, UserDetailInfo userDetailInfo) {
        Optional<UserDetailInfo> oldUserDetailInfo = userDetailInfoRepo.findById(id);
        if (oldUserDetailInfo.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            if (userDetailInfo.getDateOfBirth() == null || userDetailInfo.getEmail().equals("") || userDetailInfo.getPhoneNumber().equals(""))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else {
                Optional<UserDetailInfo> row = userDetailInfoRepo.findByPhoneNumber(userDetailInfo.getPhoneNumber());
                Optional<UserDetailInfo> row1 = userDetailInfoRepo.findByEmail(userDetailInfo.getEmail());
                if (row.isEmpty() || row1.isEmpty()) {
                    UserContactInfo userContactInfo = oldUserDetailInfo.get().getUserContactInfo();
                    userDetailInfo.setUserContactInfo(userContactInfo);
                    userDetailInfo.setId(oldUserDetailInfo.get().getId());
                    userContactInfo.setUserDetailInfo(userDetailInfo);
                    userDetailInfoRepo.save(userDetailInfo);
                    return new ResponseEntity<>(userDetailInfo, HttpStatus.OK);
                } else
                    return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
            }
        }
    }

    /*
     * Удаление детальной информации пользователя
     * проверка на наличие детальной информации с переданным id
     * */
    public ResponseEntity<UserDetailInfo> deleteUserDetailInfo(Long id) {
        Optional<UserDetailInfo> row = userDetailInfoRepo.findById(id);
        if (row.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            userDetailInfoRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
