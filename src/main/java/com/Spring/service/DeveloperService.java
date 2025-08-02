package com.Spring.service;

import com.Spring.entity.Developer;

import java.util.List;

public interface DeveloperService {
    String saveDeveloper(Developer developer);

    List<Developer> getAllDeveloper();

    Developer getDeveloperById(int id);
}
