package com.kadry.picturePublishingService.fixtures.builders.domain;

import com.kadry.picturePublishingService.domain.picture.Category;
import com.kadry.picturePublishingService.domain.picture.Picture;
import com.kadry.picturePublishingService.domain.picture.State;
import com.kadry.picturePublishingService.fixtures.Utils;


import java.util.UUID;

public class PictureBuilder {

    public static final String DESCRIPTION = "some test description";
    public static final  Category CATEGORY = Category.MACHINE;
    public static final State STATE = State.ACCEPTED;
    public  final UUID PICTURE_UUID = UUID.randomUUID();


    private String description;
    private Category category;
    private State state;
    private byte[] pictureData;

    private  UUID uuid;
    public PictureBuilder() {
        this.description = DESCRIPTION;
        this.category = CATEGORY;
        this.state = STATE;
        this.uuid = PICTURE_UUID;
        pictureData = Utils.loadPicture();
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

    public PictureBuilder withUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public Picture build() {
        return new Picture(uuid, description, category, state, pictureData);
    }

}
