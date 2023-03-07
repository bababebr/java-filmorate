package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.annotaion.ReleaseDate;
import ru.yandex.practicum.filmorate.serializer.DurationSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {
    @NotNull
    @EqualsAndHashCode.Exclude
    private long id;
    @NotNull
    @NotBlank
    private final String name;
    @NotNull
    @Size(max = 200, message = "Description is too long")
    private final String description;
    @NotNull
    @ReleaseDate
    private final LocalDate releaseDate;
    @Positive
    private final int duration;
}
