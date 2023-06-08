package com.kadry.picturePublishingService.fixtures.builders.domain;

import com.kadry.picturePublishingService.domain.picture.Category;
import com.kadry.picturePublishingService.domain.picture.Picture;
import com.kadry.picturePublishingService.domain.picture.State;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

public class PictureBuilder {

    public static final String DESCRIPTION = "some test description";
    public static final  Category CATEGORY = Category.MACHINE;
    public static final State STATE = State.ACCEPTED;

    private String description;
    private Category category;
    private State state;
    private byte[] pictureData;

    public PictureBuilder() {
        this.description = DESCRIPTION;
        this.category = CATEGORY;
        this.state = STATE;
        loadPicture();
    }

    public static PictureBuilder  aPicture(){
        return new PictureBuilder();
    }
    public PictureBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public PictureBuilder withState(State state) {
        this.state = state;
        return this;
    }

    public PictureBuilder withPictureData(byte[] pictureData) {
        this.pictureData = pictureData;
        return this;
    }

    public Picture build() {
        return new Picture(description, category, state, pictureData);
    }

    private void loadPicture(){
        try {
            // Read the image file
            BufferedImage image = ImageIO.read(new File("test.png"));

            // Write the image to a byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);
             pictureData = baos.toByteArray();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
