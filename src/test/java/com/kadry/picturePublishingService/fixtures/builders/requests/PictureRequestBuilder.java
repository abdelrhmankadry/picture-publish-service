package com.kadry.picturePublishingService.fixtures.builders.requests;

import com.kadry.picturePublishingService.api.models.requests.PictureRequest;
import com.kadry.picturePublishingService.domain.picture.Category;
import com.kadry.picturePublishingService.fixtures.Utils;

import static com.kadry.picturePublishingService.fixtures.Utils.asJsonString;

public class PictureRequestBuilder {
    public static final String DESCRIPTION = "some test description";
    public static final Category CATEGORY = Category.MACHINE;

    private String description;
    private Category category;
    private String pictureData;

    public PictureRequestBuilder() {
        this.description = DESCRIPTION;
        this.category = CATEGORY;
        pictureData = Utils.loadPicture();
    }

    public static PictureRequestBuilder aPictureRequest(){
        return new PictureRequestBuilder();
    }
    public PictureRequestBuilder withDescription(String description) {
        this.description = description;
        return this;
    }

    public PictureRequestBuilder withCategory(Category category) {
        this.category = category;
        return this;
    }

    public PictureRequestBuilder withPictureData(String pictureData) {
        this.pictureData = pictureData;
        return this;
    }

    public PictureRequest build(){
        return new PictureRequest(this.description, this.category, this.pictureData);
    }

    public String json(){
        return asJsonString(new PictureRequest(this.description, this.category, this.pictureData));
    }
}
