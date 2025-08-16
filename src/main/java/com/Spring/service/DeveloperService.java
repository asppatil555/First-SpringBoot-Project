package com.Spring.service;

import com.Spring.entity.Developer;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface DeveloperService {
    String saveDeveloper(Developer developer);

    List<Developer> getAllDeveloper();

    Developer getDeveloperById(int id);

    String deleteById(int id);

    Developer updateDeveloper(int id, Developer newData);



    List<Developer> filterDataByCity(String city);


    List<Developer> filterByGender(String gender);

    void upload(MultipartFile file);

    ByteArrayInputStream exportToExcel();
}
