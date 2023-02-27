package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import ru.yandex.practicum.filmorate.annotaion.DurationIsPositive;
import ru.yandex.practicum.filmorate.annotaion.ReleaseDate;
import ru.yandex.practicum.filmorate.serializer.DurationSerializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.Duration;
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
    @JsonSerialize(using = DurationSerializer.class)
    @DurationIsPositive
    private final Duration duration;
}
