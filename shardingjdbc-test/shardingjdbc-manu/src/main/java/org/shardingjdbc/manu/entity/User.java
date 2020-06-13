package org.shardingjdbc.manu.entity;


import lombok.Data;
import java.util.Date;
 
@Data
public class User {
    private Long userId;
    private String idNumber;
    private String name;
    private Integer age;
    private Integer gender;
    private Date birthDate;
}

