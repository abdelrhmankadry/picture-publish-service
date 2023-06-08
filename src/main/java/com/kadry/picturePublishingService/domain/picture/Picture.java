package com.kadry.picturePublishingService.domain.picture;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pictures")
@Getter
@Setter
@NoArgsConstructor
public class Picture {

    public Picture(String description, Category category, State state, byte[] pictureData) {
        Description = description;
        this.category = category;
        this.state = state;
        this.pictureData = pictureData;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String Description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State state;

    @Lob
    private byte[] pictureData;
}
