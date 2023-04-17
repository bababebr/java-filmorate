package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Genre implements Comparable<Genre> {
    private int id;
    private String name;

    public Genre() {

    }

    @Override
    public int compareTo(Genre o) {
        if (id == o.getId()) {
            return 0;
        }
        return id > o.getId() ? 1 : -1;
    }
}
