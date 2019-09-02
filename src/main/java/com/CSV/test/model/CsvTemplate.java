package com.CSV.test.model;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import lombok.*;

import com.CSV.test.valid.Phone;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import javax.validation.constraints.Size;
//import java.util.UUID;

@JsonAutoDetect
@Entity
@Table(name= "users")
public class CsvTemplate {

    @JsonIgnore
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id",unique=true, nullable = false)
    private Integer id;
    @Id
    private String name;
    private String country;




//    @Size(min = 10, max = 10)
//    @Digits(integer = 10, fraction = 0)
//    @Pattern(regexp = "[\\s]*[0-9]*[1-9]+")
//    @PersAnnotation
    @Phone
    private String phone;

    public CsvTemplate() {
    }

    public CsvTemplate(Integer id, String name, String country, String phone) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.phone = phone;
    }

    public CsvTemplate(String name, String country, String phone) {
        this.name = name;
        this.country = country;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
