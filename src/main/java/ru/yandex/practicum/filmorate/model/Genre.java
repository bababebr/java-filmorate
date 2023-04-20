package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level= AccessLevel.PRIVATE)
public class Genre implements Comparable<Genre> {
    int id;
    String name;
    @Override
    public int compareTo(Genre o) {
        if (id == o.getId()) {
            return 0;
        }
        return id > o.getId() ? 1 : -1;
    }
}
