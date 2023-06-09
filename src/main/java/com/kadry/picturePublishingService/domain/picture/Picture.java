package com.kadry.picturePublishingService.domain.picture;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "pictures")
@Getter
@Setter
@NoArgsConstructor
public class Picture {

    public Picture(UUID uuid, String description, Category category, State state, byte[] pictureData) {
        this.uuid = uuid;
        this.description = description;
        this.category = category;
        this.state = state;
        this.pictureData = pictureData;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private UUID uuid;

    @NotNull
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;

    @NotNull
    @Enumerated(EnumType.STRING)
    private State state;

    @Lob
    private byte[] pictureData;
}
