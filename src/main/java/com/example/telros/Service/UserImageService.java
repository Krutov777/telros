package com.example.telros.Service;

import com.example.telros.Entity.UserContactInfo;
import com.example.telros.Entity.UserImage;
import com.example.telros.Repository.UserContactInfoRepo;
import com.example.telros.Repository.UserImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserImageService {

    private final UserImageRepo userImageRepo;

    private final UserContactInfoRepo userContactInfoRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @Autowired
    public UserImageService(UserImageRepo userImageRepo, UserContactInfoRepo userContactInfoRepo) {
        this.userImageRepo = userImageRepo;
        this.userContactInfoRepo = userContactInfoRepo;
    }

    /*
     * Сохрание файла на диск
     * */
    private String saveFileOnDisk(MultipartFile file) throws IOException {
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + "." + file.getOriginalFilename();

        file.transferTo(new File(uploadPath + "/" + resultFilename));
        return resultFilename;
    }

    /*
     * Добавление изображения к пользователю + на диск
     * проверка: на наличие контактной информации о пользователе,
     * */
    public ResponseEntity<UserImage> addUserImage(Long userContactInfoId, MultipartFile file) throws IOException {
        UserImage userImage = new UserImage();
        UserContactInfo userContactInfo = userContactInfoRepo.findById(userContactInfoId).orElse(null);
        if (Objects.requireNonNull(userContactInfo).getName().equals("")){
            return new ResponseEntity<>(userImage, HttpStatus.NOT_FOUND);
        }

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            String resultFilename = saveFileOnDisk(file);

            userImage.setFilename(resultFilename);
            Objects.requireNonNull(userContactInfo).setUserImage(userImage);
            userImage.setUserContactInfo(userContactInfo);
            userImageRepo.save(userImage);
            return new ResponseEntity<>(userImage, HttpStatus.CREATED);
        }
        else
            return new ResponseEntity<>(userImage, HttpStatus.BAD_REQUEST);
    }

    /*
     * Получение изображений пользователей
     * проверка: на пустоту списка изображений
     * */
    public ResponseEntity<List<UserImage>> getUsersImage() {
        List<UserImage> list = (List<UserImage>) userImageRepo.findAll();
        if (list.isEmpty())
            return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
        else
            return new ResponseEntity<>(list, HttpStatus.OK);
    }

    /*
     * Получение изображения пользователя
     * проверка: на пустоту объекта с изображением
     * */
    public ResponseEntity<UserImage> getUserImage(Long id) {
        Optional<UserImage> row = userImageRepo.findById(id);
        if (row.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(row.get(), HttpStatus.OK);
    }

    /*
     * Обновление изображения пользователя
     * проверка: на наличие пустого имени файла в объекте
     * удаление старого фото с диска и бд
     * добавление нового фото на диск и в бд
     * */
    public ResponseEntity<UserImage> updateUserImage(Long id, MultipartFile file) throws IOException {
        UserImage userImage = new UserImage();
        Optional<UserImage> oldUserImage = userImageRepo.findById(id);

        if (oldUserImage.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        if (file != null && !Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            userImage.setFilename(saveFileOnDisk(file));
            Files.deleteIfExists(Paths.get(uploadPath + "/" + oldUserImage.get().getFilename()));

            UserContactInfo userContactInfo = oldUserImage.get().getUserContactInfo();
            userImage.setUserContactInfo(userContactInfo);
            userImage.setId(oldUserImage.get().getId());
            userContactInfo.setUserImage(userImage);
            userImageRepo.save(userImage);
            return new ResponseEntity<>(userImage, HttpStatus.OK);
        }
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /*
     * Удаление изображения пользователя
     * проверка на наличие изображения с переданным id
     * Удаление фото пользователя с диска, если призанно к пользователю
     * */
    public ResponseEntity<UserImage> deleteUserImage(Long id) throws IOException {
        Optional<UserImage> row = userImageRepo.findById(id);
        if (row.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            Files.deleteIfExists(Paths.get(uploadPath+"/"+row.get().getFilename()));
            userImageRepo.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
