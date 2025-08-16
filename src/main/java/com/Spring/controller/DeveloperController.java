package com.Spring.controller;

import com.Spring.entity.Developer;

import com.Spring.helper.FileUpload;
import com.Spring.service.DeveloperService;
import com.Spring.serviceImpl.DeveloperServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/developer")
public class DeveloperController {
    @Autowired
    private DeveloperService developerService;

    @PostMapping("/add")
    public ResponseEntity<String> addDeveloper(@RequestBody Developer developer){
        System.err.println(developer);
        developerService.saveDeveloper(developer);
        return new ResponseEntity<>("hi"+developer.getFname()+"your genrated id is"+developer.getDeveloper_id() , HttpStatus.CREATED);
    }
@GetMapping("/getAllData")
    public ResponseEntity<List<Developer>> getAllData(){
        List<Developer> developerList=developerService.getAllDeveloper();
        return new ResponseEntity<>(developerList, HttpStatus.OK);
    }


    @GetMapping("/getById/{id}")
    public ResponseEntity<Developer> getDeveloperById(@PathVariable("id") int id){
        Developer devloper= developerService.getDeveloperById(id);
        return  new ResponseEntity<>(devloper,HttpStatus.OK);
    }


    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") int id){
        String msg=developerService.deleteById(id);
        return  new ResponseEntity<>(msg, HttpStatus.OK);
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<Developer> updateDeveloperByid(@PathVariable("id") int id,@RequestBody Developer developer) {
        Developer updatedeveloper=developerService.updateDeveloper(id,developer);
        return new ResponseEntity<>(updatedeveloper, HttpStatus.OK);
    }
    @GetMapping("/filterList")
    public ResponseEntity<List<Developer>> filterByCity(@RequestParam (required = false) String city,
                                                        @RequestParam (required = false) String gender){


        List<Developer> list=new ArrayList<>();
        if(city !=null){
            list=developerService.filterDataByCity(city);
        }
        else{
            list=developerService.filterByGender(gender);
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }



    @PostMapping( value="/upload" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) {
        if (!FileUpload.checkExcelFormat(file)) {
            //true


            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please upload excel file ");



        }
        developerService.upload(file);
        return ResponseEntity.ok(Map.of("message", "File is uploaded and data is saved to db"));

    }


    @GetMapping("/download")
    public ResponseEntity<InputStreamResource> downloadDevelopersExcel() {
        ByteArrayInputStream in = developerService.exportToExcel();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=developers.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }

}
