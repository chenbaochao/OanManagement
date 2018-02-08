package com.oan.management.service;

import com.oan.management.model.Image;
import com.oan.management.model.User;
import com.oan.management.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Oan on 30/01/2018.
 */

@Service
public class ImageServiceImpl implements ImageService {
    private final String ROOT = "C:/oanManagement/src/main/resources/static/img/";

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    UserService userService;

    @Override
    public List<Image> findAll() {
        return imageRepository.findAll();
    }

    @Override
    public Image findById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image findByTitle(String title) {
        return imageRepository.findFirstByTitle(title);
    }

    @Override
    public Image findFirstByTitle(String title) {
        return imageRepository.findFirstByTitle(title);
    }

    @Override
    public Image uploadImage(MultipartFile file, String path, User user) {
        String folder = ROOT + path;
        Image image = new Image();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                File dir = new File(folder);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                String createdFileName = user.getId().toString() + file.getContentType().replace("image/", ".");
                File serverFile = new File(dir.getAbsolutePath() + "/" + createdFileName);
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();

                String completeUrl = "/avatar/"+createdFileName;
                if (findByTitle(user.getId()+".png") == null) {
                    image = new Image(createdFileName, completeUrl, user);
                } else {
                    image = findByTitle(user.getId()+".png");
                }
            } catch (IOException e) {
                e.printStackTrace();
                return new Image();
            }
        }
        imageRepository.save(image);
        return image;
    }
}
