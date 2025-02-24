package com.example.first_camel_case.model.other;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileHelper {

    public static void parseTiffToJpeg(byte[] fileBytes, String outputDirectory) {
        try (InputStream fileInputStream = new ByteArrayInputStream(fileBytes);
             ImageInputStream imageInputStream = ImageIO.createImageInputStream(fileInputStream)) {
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

    public static byte[] scalePhoto(byte[] img, int inWidth, int inHeight) {
        if (null != img && img.length >= 1) {
            ImageIcon original;
            try {
                original = new ImageIcon(ImageIO.read(new ByteArrayInputStream(img)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (original.getIconWidth() > 0 && original.getIconHeight() > 0) {
                if (original.getIconWidth() == inWidth && original.getIconHeight() == inHeight) {
                    return img;
                } else {
                    Dimension d = convertScale(original, inWidth, inHeight);
                    ImageIcon imgScaled = new ImageIcon(original.getImage().getScaledInstance(d.width, d.height, 16));
                    return imgToBytes(imgScaled, "jpeg");
                }
            } else {
                return new byte[0];
            }
        } else {
            return new byte[0];
        }
    }

    private static Dimension convertScale(ImageIcon inImg, int inWidth, int inHeight) {
        double w = (double) inImg.getIconWidth();
        double h = (double) inImg.getIconHeight();
        double kw = (double) inWidth / w;
        double kh = (double) inHeight / h;
        double k = kw < kh ? kw : kh;
        return new Dimension((int) (w * k), (int) (h * k));
    }

    private static byte[] imgToBytes(ImageIcon img, String imgFormatTypeConversion) {
        try {
            if (null == img) {
                return new byte[0];
            } else {
                BufferedImage bufferedImage = new BufferedImage(img.getIconWidth(), img.getIconHeight(), 1);
                bufferedImage.createGraphics().drawImage(img.getImage(), 0, 0, new Panel());
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, imgFormatTypeConversion, out);
                return out.toByteArray();
            }
        } catch (Exception e) {
            throw new RuntimeException("img to byte fail", e);
        }
    }

    public static void zipFiles(String[] imagePaths, String outputZipPath) {
        try (FileOutputStream fos = new FileOutputStream(outputZipPath);
             ZipOutputStream zos = new ZipOutputStream(fos)) {

            for (String imagePath : imagePaths) {
                File file = new File(imagePath);
                if (!file.exists()) {
                    System.out.println("File not found: " + imagePath);
                    continue;
                }
                try (FileInputStream fis = new FileInputStream(file)) {
                    ZipEntry zipEntry = new ZipEntry(file.getName());
                    zos.putNextEntry(zipEntry);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = fis.read(buffer)) > 0) {
                        zos.write(buffer, 0, length);
                    }
                    zos.closeEntry();
                }
            }
            System.out.println("ZIP archive created: " + outputZipPath);
        } catch (IOException e) {
            throw new RuntimeException("Error creating ZIP file", e);
        }
    }

    public static void unzipFiles(String zipFilePath, String outputDirectory) {
        File dir = new File(outputDirectory);
        if (!dir.exists()) {
            dir.mkdirs(); // Create output directory if it doesn't exist
        }

        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFilePath))) {
            ZipEntry zipEntry;
            while ((zipEntry = zis.getNextEntry()) != null) {
                File newFile = new File(outputDirectory, zipEntry.getName());
                try (FileOutputStream fos = new FileOutputStream(newFile)) {
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }
                }
                zis.closeEntry();
            }
            System.out.println("Unzipped successfully to: " + outputDirectory);
        } catch (IOException e) {
            throw new RuntimeException("Error unzipping file", e);
        }
    }
}
