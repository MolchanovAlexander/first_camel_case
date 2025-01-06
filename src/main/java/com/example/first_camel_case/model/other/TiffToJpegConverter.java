package com.example.first_camel_case.model.other;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class TiffToJpegConverter {

    public static void main(String[] args) {
        try {
            // Input and Output file paths
            File tiffFile = new File("input.tiff");
            File jpegFile = new File("output.jpeg");

            // Create an ImageInputStream for the TIFF file
            ImageInputStream input = ImageIO.createImageInputStream(tiffFile);

            // Get all available TIFF readers
            Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("TIFF");
            if (!readers.hasNext()) {
                throw new IllegalStateException("No TIFF readers found!");
            }

            // Use the first available reader
            ImageReader reader = readers.next();
            reader.setInput(input);

            // Read the image
            BufferedImage image = reader.read(0);

            // Write the image as JPEG
            ImageIO.write(image, "JPEG", jpegFile);

            System.out.println("Conversion complete: " + jpegFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
