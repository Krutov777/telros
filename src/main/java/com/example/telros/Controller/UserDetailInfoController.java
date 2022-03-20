package com.example.telros.Controller;

import com.example.telros.Entity.UserDetailInfo;
import com.example.telros.Service.UserDetailInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/user/detail_info")
public class UserDetailInfoController {
    private final UserDetailInfoService userDetailInfoService;

    @Autowired
    public UserDetailInfoController(UserDetailInfoService userDetailInfoService) {
        this.userDetailInfoService = userDetailInfoService;
    }


    /*
     * Получение детальной информации пользователей
     * */
    @GetMapping
    public ResponseEntity<List<UserDetailInfo>> getUsersDetailInfo() {
        ResponseEntity<List<UserDetailInfo>> response = userDetailInfoService.getUsersDetailInfo();
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }

    /*
     * Получение детальной информации пользователя по id
     * */
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailInfo> getUserDetailInfo(@PathVariable Long id) {
        ResponseEntity<UserDetailInfo> response = userDetailInfoService.getUserDetailInfo(id);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }

    /*
     * Добавление детальной информации пользователя
     * */
    @PostMapping("/{userContactInfoId}")
    public ResponseEntity<?> addUserDetailInfoAndContactInfo
            (
                    @RequestBody UserDetailInfo userDetailInfo,
                    @PathVariable Long userContactInfoId
            ) {
        ResponseEntity<?> response = userDetailInfoService.addUserDetailInfoAndContactInfo(userDetailInfo, userContactInfoId);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }

    /*
     * Обновление детальной информации пользователя
     * */
    @PutMapping("/{id}")
    public ResponseEntity<UserDetailInfo> updateUserDetailInfo(@PathVariable Long id, @RequestBody UserDetailInfo userDetailInfo) {
        ResponseEntity<UserDetailInfo> response = userDetailInfoService.updateUserDetailInfo(id, userDetailInfo);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }

    /*
     * Удаление детальной информации пользователя
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<UserDetailInfo> deleteUserContactInfo(@PathVariable Long id) {
        ResponseEntity<UserDetailInfo> response = userDetailInfoService.deleteUserDetailInfo(id);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }
}
