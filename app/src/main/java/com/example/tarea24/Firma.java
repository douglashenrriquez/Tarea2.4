package com.example.tarea24;

import android.graphics.Bitmap;

public class Firma {
    private Bitmap image;
    private String description;

    public Firma(Bitmap image, String description) {
        this.image = image;
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }
}
