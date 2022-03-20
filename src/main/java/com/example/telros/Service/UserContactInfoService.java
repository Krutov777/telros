package com.example.telros.Service;

import com.example.telros.Entity.UserContactInfo;
import com.example.telros.Repository.UserContactInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserContactInfoService {
    private final UserContactInfoRepo userContactInfoRepo;

    @Autowired
    public UserContactInfoService(UserContactInfoRepo userContactInfoRepo) {
        this.userContactInfoRepo = userContactInfoRepo;
    }

   /*
    * Добавление контактной информации пользователя
    * проверка на наличие пустых полей в объекте
    * */
    public ResponseEntity<HttpStatus> addUserContactInfo(UserContactInfo userContactInfo) {
        if (userContactInfo.getSurname().equals("") || userContactInfo.getName().equals("")|| userContactInfo.getPatronymic().equals(""))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        else {
            userContactInfoRepo.save(userContactInfo);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }

    /*
     * Получение контактной информации пользователей
     * */
    public ResponseEntity<List<UserContactInfo>> getUsersContactInfo() {
        List<UserContactInfo> list = (List<UserContactInfo>) userContactInfoRepo.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /*
     * Получение контактной информации пользователя
     * */
    public ResponseEntity<UserContactInfo> getUserContactInfo(Long id) {
        Optional<UserContactInfo> row = userContactInfoRepo.findById(id);
        if(row.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(row.get(), HttpStatus.OK);
    }

    /*
     * Обновление контактной информации пользователя
     * проверка на наличие контактной информации с переданным id
     * проверка на наличие пустых полей в переданном объекте
     * */
    public ResponseEntity<UserContactInfo> updateUserContactInfo(Long id, UserContactInfo userContactInfo) {
        Optional<UserContactInfo> oldUserContactInfo = userContactInfoRepo.findById(id);
        if (oldUserContactInfo.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else {
            if (userContactInfo.getSurname().equals("") || userContactInfo.getName().equals("") || userContactInfo.getPatronymic().equals(""))
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            else {
                userContactInfo.setId(oldUserContactInfo.get().getId());
                userContactInfoRepo.save(userContactInfo);
                return new ResponseEntity<>(userContactInfo, HttpStatus.OK);
            }
        }
    }

    /*
     * Удаление контактной информации пользователя
     * проверка на наличие контактной информации с переданным id
     * */
    public ResponseEntity<UserContactInfo> deleteUserContactInfo(Long id) {
        Optional<UserContactInfo> row = userContactInfoRepo.findById(id);
        if (row.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            userContactInfoRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
