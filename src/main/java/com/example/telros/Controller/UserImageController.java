package com.example.telros.Controller;

import com.example.telros.Entity.UserImage;
import com.example.telros.Service.UserImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(path = "api/user/image")
public class UserImageController {
    private final UserImageService userImageService;

    @Autowired
    public UserImageController(UserImageService userImageService) {
        this.userImageService = userImageService;
    }

    /*
     * Получение изображений пользователей
     * */
    @GetMapping
    public ResponseEntity<List<UserImage>> getUsersImage() {
        ResponseEntity<List<UserImage>> response = userImageService.getUsersImage();
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }

    /*
     * Получение изображения пользователя по id
     * */
    @GetMapping("/{id}")
    public ResponseEntity<UserImage> getUserImage(@PathVariable Long id) {
        ResponseEntity<UserImage> response = userImageService.getUserImage(id);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }

    /*
     * Добавление изображения пользователю
     * */
    @PostMapping("/{userContactInfoId}")
    public ResponseEntity<UserImage> addUserImage(@PathVariable Long userContactInfoId, @RequestParam("file") MultipartFile file) throws IOException {
        ResponseEntity<UserImage> response = userImageService.addUserImage(userContactInfoId, file);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }

    /*
     * Обновление изображения пользователя
     * */
    @PutMapping("/{id}")
    public ResponseEntity<UserImage> updateUserImage(@PathVariable Long id, @RequestParam MultipartFile file) throws IOException {
        ResponseEntity<UserImage> response = userImageService.updateUserImage(id, file);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }

    /*
     * Удаление изображения пользователя
     * */
    @DeleteMapping("/{id}")
    public ResponseEntity<UserImage> deleteUserImage(@PathVariable Long id) throws IOException {
        ResponseEntity<UserImage> response = userImageService.deleteUserImage(id);
        return new ResponseEntity<>(
                response.getBody(),
                response.getStatusCode()
        );
    }
}
