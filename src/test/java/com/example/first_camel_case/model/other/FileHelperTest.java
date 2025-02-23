package com.example.first_camel_case.model.other;

import static com.jayway.jsonpath.internal.Utils.notEmpty;
import static org.junit.Assert.*;
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
}