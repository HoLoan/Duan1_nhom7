package com.example.asm1.models;

import java.io.Serializable;

public class AppUser implements Serializable {
    private Integer id, role;
    private String email, password;

    public AppUser()
    {

    }
    public AppUser(Integer id, Integer role, String email, String password)
    {
        this.id=id;
        this.role=role;
        this.email=email;
        this.password=password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws Exception {
        String pattern = "";
        if(!email.matches(pattern))
        {
            throw new Exception("Email không đúng định dạng!!!");
        }
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
