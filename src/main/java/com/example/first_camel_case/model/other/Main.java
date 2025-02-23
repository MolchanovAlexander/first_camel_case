package com.example.first_camel_case.model.other;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            byte[] tiffBytes = Files.readAllBytes(Paths.get("file_example_TIFF_1MB.tiff"));
            FileHelper.parseTiffToJpeg(tiffBytes, "/");
            System.out.println("Conversion complete. Check the output_directory for JPEG files.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
