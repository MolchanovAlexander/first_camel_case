package com.example.first_camel_case.model.other;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.example.first_camel_case.tracker.Graphics2DTrackerAspect;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FileHelperTest {

    @Test
    public void parseTiffToJpeg() throws IOException {
        byte[] tiffBytes = Files.readAllBytes(Paths.get("/home/salat/IdeaProjects/first_camel_case/multipage_tiff_example.tif"));
        // Convert to JPEG
        FileHelper.parseTiffToJpeg(tiffBytes, "/home/salat/IdeaProjects/first_camel_case");
        // Verify all Graphics2D instances are properly disposed
        System.out.println(Graphics2DTrackerAspect.getActiveGraphicsCount());
    }

    @Test
    public void scaleTest() throws IOException {
        // InputStream is = FileHelper.class.getResourceAsStream("/0.jpeg");
        FileInputStream is = new FileInputStream("src/test/resources/com.example.first_camel_case/0.jpeg");
        byte[] img = new byte[is.available()];
        is.read(img);

        byte[] scaled = FileHelper.scalePhoto(img, 100, 100);
        String res = "scaled_0.jpeg";
        FileOutputStream fos = new FileOutputStream(res);
        fos.write(scaled);
        fos.close();
        File result = new File(res);
        assertNotNull(result);
    }

    @Test
    public void zipFileTest() throws IOException {
        String[] images = {
                "i1.png",
                "i2.png"
        };
        String outputZip = "output.zip";
        FileHelper.zipFiles(images, outputZip);

        File result = new File(outputZip);
        assertTrue("ZIP file should be created", result.exists());
        assertTrue("ZIP file should not be empty", result.length() > 0);

        //Files.deleteIfExists(Paths.get(outputZip));
    }

    @Test
    public void testUnzipFiles() throws IOException {
        String zipFile = "output.zip";
        String outputDir = "/home/salat/IdeaProjects/first_camel_case/zip_files";

        //  Unzipping the file
        FileHelper.unzipFiles(zipFile, outputDir);

        // Check if files exist in the extracted directory
        File extractedFile1 = new File(outputDir, "i1.png");
        File extractedFile2 = new File(outputDir, "i2.png");

        assertTrue("Extracted file 1 should exist", extractedFile1.exists());
        assertTrue("Extracted file 2 should exist", extractedFile2.exists());

        // Clean up test files
//        Files.deleteIfExists(Paths.get(image1));
//        Files.deleteIfExists(Paths.get(image2));
//        Files.deleteIfExists(Paths.get(zipFile));
//        Files.deleteIfExists(extractedFile1.toPath());
//        Files.deleteIfExists(extractedFile2.toPath());
//        Files.deleteIfExists(Paths.get(outputDir)); // Delete empty directory
    }
}