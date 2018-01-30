package com.oan.management.repository;

import com.oan.management.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Oan on 30/01/2018.
 */
public interface ImageRepository extends JpaRepository<Image, Long>{
    List<Image> findAll();
    Image findById(Long id);
    Image findByTitle(String title);
}
