package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.yandex.practicum.filmorate.annotaion.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.TreeSet;

@Data
@AllArgsConstructor(staticName = "create")
public class Film implements Comparable<Film> {
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
    private final Mpa mpa;

    private final TreeSet<Genre> genres = new TreeSet<>();

    @Override
    public int compareTo(Film o) {
        return 0;
    }
}
