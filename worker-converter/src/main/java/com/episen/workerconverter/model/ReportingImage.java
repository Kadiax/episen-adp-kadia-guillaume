package com.episen.workerconverter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
@NoArgsConstructor
public class ReportingImage extends Image  {
    private boolean canalAlpha;
    private int width;
    private int height;
    private int pixelSize;
    private double convertedTime;
    private String pathFileConverted;
    private String idImageProperties;
}
