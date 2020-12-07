package com.episen.frontconverter.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@Entity(name="Image")
@AllArgsConstructor
@NoArgsConstructor
public class ImageProperties {
    private Image image;
    private boolean canalAlpha;
    private String maxColors;
    private boolean entrelacement;

}
