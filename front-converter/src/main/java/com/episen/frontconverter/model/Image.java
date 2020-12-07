package com.episen.frontconverter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

//import javax.persistence.Entity;


@Data
//@Entity(name="Image")
@AllArgsConstructor
@NoArgsConstructor
public class Image implements Serializable {
    private String id;
}
