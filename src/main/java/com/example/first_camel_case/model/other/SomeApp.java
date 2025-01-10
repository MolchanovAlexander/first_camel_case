package com.example.first_camel_case.model.other;

import javax.imageio.*;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;
import java.util.Locale;

public class SomeApp {

//    public static List<AppFile> parseTiffWithCompression(byte[] arr) throws IOException {
//        // Initialize result list
//        List<AppFile> result = new ArrayList<>();
//
//        // Create InputStream and ImageInputStream
//        try (InputStream tis = new ByteArrayInputStream(arr);
//             ImageInputStream input = ImageIO.createImageInputStream(tis)) {
//
//            // Obtain a TIFF reader
//            ImageReader tiffReader = ImageIO.getImageReadersByFormatName("TIFF").next();
//            if (tiffReader == null) {
//                throw new IOException("No TIFF reader available.");
//            }
//
//            tiffReader.setInput(input);
//            int numPages = tiffReader.getNumImages(true);
//
//            if (numPages == 0) {
//                return null; // No pages to process
//            }
//
//            for (int i = 0; i < numPages; i++) {
//                // Read each page as BufferedImage
//                BufferedImage image = tiffReader.read(i);
//                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//                // Obtain a JPEG writer
//                ImageWriter jpegWriter = ImageIO.getImageWritersByFormatName("jpeg").next();
//                ImageOutputStream ios = ImageIO.createImageOutputStream(baos);
//                jpegWriter.setOutput(ios);
//
//                // Set compression parameters
//                JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(Locale.getDefault());
//                if (jpegParams.canWriteCompressed()) {
//                    jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
//                    jpegParams.setCompressionQuality(0.05f); // Compression quality 5%
//                }
//
//                // Write compressed image
//                jpegWriter.write(null, new IIOImage(image, null, null), jpegParams);
//
//                // Close streams
//                ios.close();
//                jpegWriter.dispose();
//
//                // Create AppFile and add to results
//                String fileName = i + ".jpeg";
//                result.add(new AppFile(AppFile.FSource.FILE, i, fileName, "jpeg", baos.toByteArray()));
//                baos.close();
//            }

//        } catch (IOException e) {
//            throw new IOException("Error processing TIFF file.", e);
//        }
//
//        return result;
//    }
}
