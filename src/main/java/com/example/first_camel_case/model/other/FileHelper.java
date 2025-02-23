package com.example.first_camel_case.model.other;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileHelper {
    public static void parseTiffToJpeg(byte[] fileBytes, String outputDirectory) {
        try (InputStream fileInputStream = new ByteArrayInputStream(fileBytes);
             ImageInputStream imageInputStream = ImageIO.createImageInputStream(fileInputStream)){
            ImageReader tiffReader = ImageIO.getImageReadersByFormatName("TIFF").next();
            tiffReader.setInput(imageInputStream);
            int numPages = tiffReader.getNumImages(true);

            for (int i = 0; i < numPages; i++) {
                BufferedImage image = tiffReader.read(i);
                BufferedImage jpegImage = new BufferedImage(
                        image.getWidth(),
                        image.getHeight(),
                        BufferedImage.TYPE_INT_RGB
                );
                Graphics2D g = jpegImage.createGraphics();
                g.drawImage(image, 0, 0, Color.WHITE, null);
                g.dispose();

                // Write to JPEG
                File outputFile = new File(outputDirectory, i + ".jpeg");
                try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
                    ImageWriter jpegWriter = ImageIO.getImageWritersByFormatName("jpeg").next();
                    jpegWriter.setOutput(ios);
                    jpegWriter.write(null, new IIOImage(jpegImage, null, null), null);
                    jpegWriter.dispose();
                }

            }

        } catch (IOException e) {
            throw new RuntimeException("you are newbie", e);
        }
    }
}
