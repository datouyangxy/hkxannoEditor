package com.xy.hkxannoeditor.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.springframework.util.StreamUtils.BUFFER_SIZE;

public class StreamUtil {

    public static void outputStream(InputStream in) {
        String output;
        try (ByteArrayOutputStream outStream = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int len;
            while ((len = in.read(buffer)) != -1) {
                outStream.write(buffer, 0, len);
            }
            output = outStream.toString(StandardCharsets.ISO_8859_1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(output);
    }
}
