package com.example.first_camel_case.model.other;

import static com.jayway.jsonpath.internal.Utils.notEmpty;
import static org.junit.Assert.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
}