package com.Spring.serviceImpl;

import com.Spring.entity.Developer;
import com.Spring.helper.DeveloperIdGenerator;
import com.Spring.helper.ExcelHelper;
import com.Spring.helper.FileUpload;
import com.Spring.repository.DeveloperRepository;
import com.Spring.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    @Autowired
    private DeveloperRepository developerRepository;

    @Override
    public String saveDeveloper(Developer developer){
        String idGenerator=DeveloperIdGenerator.idGenerator(developer);
        developer.setDeveloper_id(idGenerator);
        developerRepository.save(developer);
        Developer saveDeveloper=developerRepository.save(developer);
        return "hi"+ developer.getFname()+"your generated user id is"+developer.getDeveloper_id();
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

    @Override
    public String deleteById(int id) {
        developerRepository.deleteById(id);

        return "deleted developer";
    }

    @Override
    public Developer updateDeveloper(int id, Developer newData) {
        Developer developer=developerRepository.findById(id).orElseThrow(()->new NullPointerException("enter id not available in data base"+id));
        System.err.println("old developer from db"+developer);
        System.err.println("Developer object with to be updated"+newData);
        developer.setFname(newData.getFname());
        developer.setLname(newData.getLname());
        developer.setAge(newData.getAge());
        developer.setCity(newData.getCity());
        developer.setSalary(newData.getSalary());

        Developer updatedDeveloper=developerRepository.save(developer);
        System.err.println("developer with updated value"+updatedDeveloper);
        return updatedDeveloper;
    }
    @Override
    public List<Developer> filterDataByCity(String city) {
        List<Developer>  listOfCity=developerRepository.findAll();
        List<Developer> developerListByCity = listOfCity.stream().filter(developer->developer.getCity().equalsIgnoreCase(city)).collect(Collectors.toList());

        return developerListByCity;
    }

    @Override
    public List<Developer> filterByGender(String gender) {
        List<Developer> filter=developerRepository.findAll();
        List<Developer> filterList=filter.stream().filter(developer -> developer.getGender().equalsIgnoreCase(gender)).collect(Collectors.toList());
        return filterList;
    }


    @Override
    public void upload(MultipartFile file) {
        List<Developer> developers = FileUpload.convertExcelToListOfProduct(file);

        if (developers == null || developers.isEmpty()) {
            return;
        }

        // Generate developer_id for each Developer (using your existing generator)
        for (Developer dev : developers) {
            try {
                String generatedId = DeveloperIdGenerator.idGenerator(dev);
                dev.setDeveloper_id(generatedId); // keep original field name
            } catch (Exception ex) {
                // fallback id if generator fails
                dev.setDeveloper_id("DEV" + System.currentTimeMillis());
            }
        }

        developerRepository.saveAll(developers);
    }

    @Override
    public ByteArrayInputStream exportToExcel() {
        List<Developer> list = developerRepository.findAll();
        return ExcelHelper.developersToExcel(list);
    }


}
