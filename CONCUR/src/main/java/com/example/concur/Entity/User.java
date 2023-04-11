package com.example.concur.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "User")
public class User {

    @Id
    @Column(name = "email")
    private String email;

    @Embedded
    private Address address;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "user_id")
    private Integer id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "website")
    private String website;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "name", column = @Column(name = "company_name"))
    })
    private Company company;

    public User(){
        super();
    }

    public User(Integer userId, String name, String username, String email, String phone, String website){
        this.id = userId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.website = website;
    }

    public User(String testName, String testUsername, String s, int i, Address address, String testPhone, String testWebsite) {

    }

    public String toString(){
        return "User id: "+this.id +"; "+
                "User name: "+this.name+"; "+
                "User username: "+this.username+"; "+
                "User email: "+this.email+"; "+
                "User address: "+this.address.toString()+
                "User phone: "+this.phone+"; "+
                "User website: "+this.website+"; "+
                "User company: "+this.company.toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer userId) {
        this.id = userId;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}