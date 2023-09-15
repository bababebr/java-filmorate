package ru.yandex.practicum.filmorate.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import ru.yandex.practicum.filmorate.annotaion.ReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.TreeSet;

@Data
@AllArgsConstructor(staticName = "create")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class Film implements Comparable<Film> {
    @NotNull
    @EqualsAndHashCode.Exclude
    @NonFinal
    long id;
    @NotNull
    @NotBlank
    String name;
    @NotNull
    @Size(max = 200, message = "Description is too long")
    String description;
    @NotNull
    @ReleaseDate
    LocalDate releaseDate;
    @Positive
    int duration;
    Mpa mpa;
    TreeSet<Genre> genres = new TreeSet<>();

    @Override
    public int compareTo(Film o) {
        if (id == o.getId()) {
            return 0;
        }
        return id > o.getId() ? 1 : -1;
    }
}

