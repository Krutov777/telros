package com.example.telros.Controller;

import com.example.telros.Entity.UserContactInfo;
import com.example.telros.Service.UserContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/user/contact_info")
public class UserContactInfoController {

    private final UserContactInfoService userContactInfoService;

    @Autowired
    public UserContactInfoController(UserContactInfoService userContactInfoService) {
        this.userContactInfoService = userContactInfoService;
    }


    /*
     * Получение контактной информации пользователей
     * */
    @GetMapping
    public ResponseEntity<List<UserContactInfo>> getUsersContactInfo() {
        ResponseEntity<List<UserContactInfo>> response = userContactInfoService.getUsersContactInfo();
        return new ResponseEntity<>
                (
                        response.getBody(),
                        response.getStatusCode()
                );
    }

    /*
     * Получение контактной информации пользователя по id
     * */
    @GetMapping("/{id}")
    public ResponseEntity<UserContactInfo> getUserContactInfo(@PathVariable Long id) {
        ResponseEntity<UserContactInfo> response = userContactInfoService.getUserContactInfo(id);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }

    /*
     * Добавление контактной информации пользователя
     * */
    @PostMapping
    public ResponseEntity<HttpStatus> addUserContactInfo(@RequestBody UserContactInfo userContactInfo) {
        ResponseEntity<HttpStatus> response = userContactInfoService.addUserContactInfo(userContactInfo);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }

    /*
     * Обновление контактной информации пользователя
     * */
    @PutMapping("/{id}")
    public ResponseEntity<UserContactInfo> updateUserContactInfo(@PathVariable Long id, @RequestBody UserContactInfo userContactInfo) {
        ResponseEntity<UserContactInfo> response = userContactInfoService.updateUserContactInfo(id, userContactInfo);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }

    /*
     * Удаление контактной информации пользователя
     * Удаление фото пользователя с диска
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<UserContactInfo> deleteUserContactInfo(@PathVariable Long id) throws IOException {
        ResponseEntity<UserContactInfo> response = userContactInfoService.deleteUserContactInfo(id);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }
}
