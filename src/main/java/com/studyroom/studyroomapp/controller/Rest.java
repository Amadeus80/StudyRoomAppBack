package com.studyroom.studyroomapp.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prueba")
public class Rest {
    
    @GetMapping("/lista")
    @Secured("ROLE_ADMIN")
    public List<Long> listar(){
        return Arrays.asList(1L,2L,3L,4L);
    }
}
