package ru.yandex.practicum.filmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.films.FilmDbStorage;
import ru.yandex.practicum.filmorate.storage.users.UserDbStorage;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class FilmorateApplicationTests {

    private final FilmDbStorage filmDbStorage;

    private final UserDbStorage userDbStorage;

    @Test()
    @Order(1)
    void testCreateAndGetUser() {
        User user = User.create(1L,
                "email",
                "user",
                "qwe",
                LocalDate.of(2000, 1, 1));
        userDbStorage.create(user);
        User fetchedUSer = userDbStorage.getUser(1L);
        assertThat(fetchedUSer).isEqualTo(user);
    }

    @Test
    @Order(2)
    void testUpdateUser() {
        User user = User.create(1L,
                "email",
                "user",
                "qwe",
                LocalDate.of(2000, 1, 1));
        User updatedUser = User.create(1L,
                "update",
                "user",
                "qwe",
                LocalDate.of(2000, 1, 1));
        userDbStorage.create(user);
        userDbStorage.update(updatedUser);
        User fetchedUser = userDbStorage.getUser(1L);
        assertThat(updatedUser).isEqualTo(fetchedUser);
    }

    @Test
    @Order(3)
    void testGetUserById() {
        User user1 = User.create(1L,
                "email",
                "user",
                "qwe",
                LocalDate.of(2000, 1, 1));
        User user2 = User.create(2L,
                "update",
                "user",
                "qwe",
                LocalDate.of(2000, 1, 1));

        assertThat(List.of(user2, user1)).isEqualTo(userDbStorage.getUsersByIds(List.of(1L, 2L)));
    }

    @Test
    @Order(4)
    void testDeleteUser() {
        userDbStorage.delete(1L);
        assertThat(1).isEqualTo(userDbStorage.getAll().size());
    }

    @Test
    @Order(5)
    void testAddAndGetFriend() {
        userDbStorage.addFriend(1L, 2L);
        assertThat(userDbStorage.getUser(2L)).isEqualTo(userDbStorage.getFriends(1L).get(0));
    }

    @Test
    @Order(6)
    void testGetMutualFriends() {
        User user3 = User.create(1L,
                "update",
                "user",
                "qwe",
                LocalDate.of(2000, 1, 1));

        userDbStorage.create(user3);
        userDbStorage.addFriend(3L, 2L);
        assertThat(userDbStorage.getMutalFriends(1L, 3L).get(0)).isEqualTo(userDbStorage.getUser(2L));
    }

    @Test
    @Order(7)
    void testCreateAndGetFilm() {
        Film film = Film.create(1L,
                "Film 1",
                "Film 1",
                LocalDate.of(2000, 1, 1),
                100,
                new Mpa(1, "G")
                );
        Film film2 = Film.create(2L,
                "Film 2",
                "Film 2",
                LocalDate.of(2000, 1, 1),
                100,
                new Mpa(1, "G")
        );
        filmDbStorage.create(film);
        filmDbStorage.create(film2);
        assertThat(film).isEqualTo(filmDbStorage.getFilm(1L));
    }

    @Test
    @Order(8)
    void testUpdateFilm() {
        Film film = Film.create(1L,
                "Film 1 update",
                "Film 1",
                LocalDate.of(2000, 1, 1),
                100,
                new Mpa(1, "G")
        );
        filmDbStorage.update(film);
        assertThat(film).isEqualTo(filmDbStorage.getFilm(1L));
    }

    @Test
    @Order(9)
    void testGetAll() {
        assertThat(2).isEqualTo(filmDbStorage.getAll().size());
    }

    @Test
    @Order(10)
    void testTop() {
        filmDbStorage.likeFilm(2L, 1L);
        assertThat(List.of(filmDbStorage.getFilm(1L), filmDbStorage.getFilm(2L)))
                .isEqualTo(filmDbStorage.getTop(2));
    }

    @Test
    @Order(11)
    void testDeleteFilm() {
        filmDbStorage.delete(1L);
        assertThat(1).isEqualTo(filmDbStorage.getAll().size());
    }

    @Test
    @Order(12)
    void testGetMPA() {
        assertThat(new Mpa(1, "G")).isEqualTo(filmDbStorage.getMpa(1L));
    }

    @Test
    @Order(13)
    void testGetAllMPA() {
        List<Mpa> mpas = List.of(new Mpa(1, "G"),
                new Mpa(2, "PG"),
                new Mpa(3, "PG-13"),
                new Mpa(4, "R"),
                new Mpa(5, "NC-17"));

        assertThat(mpas).isEqualTo(filmDbStorage.getAllMpa());
    }

    @Test
    @Order(14)
    void testGetGenre() {
        assertThat(new Genre(1, "Комедия")).isEqualTo(filmDbStorage.getGenre(1L));
    }

    @Test
    @Order(15)
    void testGetAllGenres() {
        List<Genre> genres = List.of(new Genre(1 , "Комедия"),
                new Genre(2, "Драма"),
                new Genre(3, "Мультфильм"),
                new Genre(4, "Триллер"),
                new Genre(5, "Документальный"),
                new Genre(6, "Боевик"));

        assertThat(genres).isEqualTo(filmDbStorage.getAllGenres());
    }
}
