package com.studyroom.studyroomapp.controller.errors;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ErrorMessage {
    
    private Integer status;
    private Date date;
    private String message;
    private String description;

    public ErrorMessage(Integer status, String message, String description) {
        this.status = status;
        this.date = new Date();
        this.message = message;
        this.description = description;
    }
}
