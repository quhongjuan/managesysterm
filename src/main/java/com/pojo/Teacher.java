package com.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private int id;
    private String number;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String sex;
    private String school;
    private int status;

    public Teacher(String number, String password, String name, String email, String phone,int status) {
        this.number = number;
        this.password = password;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.status=status;
    }
}
