package com.episen.frontconverter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class ImageProperties extends Image {
    private boolean canalAlpha;
    private int width;
    private int height;
    private int pixelSize;
    private String pathFileOriginal;

}
