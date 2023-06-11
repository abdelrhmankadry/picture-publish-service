package com.kadry.picturePublishingService.fixtures;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Base64;

public class Utils {
    public static String asJsonString(final Object obj){
        try{
            return new ObjectMapper().writeValueAsString(obj);
        }catch (JsonProcessingException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public static String loadPicture(){
        try {
            // Read the image file
            BufferedImage image = ImageIO.read(new File("test.png"));

            // Write the image to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
           return Base64.getEncoder().encodeToString(baos.toByteArray());


        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

