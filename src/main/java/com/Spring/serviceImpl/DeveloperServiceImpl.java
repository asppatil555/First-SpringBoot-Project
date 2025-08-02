package com.Spring.serviceImpl;

import com.Spring.entity.Developer;
import com.Spring.repository.DeveloperRepository;
import com.Spring.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Override
    public String saveDeveloper(Developer developer){
        Developer saveDeveloper=developerRepository.save(developer);
        return "developer saved";
    }

    @Override
    public List<Developer> getAllDeveloper() {
        List<Developer> developerList=developerRepository.findAll();
        return developerList;
    }

    @Override
    public Developer getDeveloperById(int id) {
        Developer developer=developerRepository.findById(id).orElseThrow(()->new NullPointerException("Developer with id not found"+id));
        return developer;
    }

}
