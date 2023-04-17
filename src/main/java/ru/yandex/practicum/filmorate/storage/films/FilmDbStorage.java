package ru.yandex.practicum.filmorate.storage.films;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmServiceException;
import ru.yandex.practicum.filmorate.exception.NoSuchFilmException;
import ru.yandex.practicum.filmorate.exception.NoSuchGenreException;
import ru.yandex.practicum.filmorate.exception.NoSuchMPAException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component("dbFilmStorage")
@Slf4j
public class FilmDbStorage implements  IFilmStorage{

    private long id = 0;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FilmDbStorage(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Film create(Film film) {
        film.setId(++id);
        jdbcTemplate.update("INSERT INTO FILM (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA) " +
                "VALUES (?, ?, ?, ?, ?)", film.getName(), film.getDescription(), film.getReleaseDate()
                , film.getDuration(), film.getMpa().getId());
        return film;
    }

    @Override
    public boolean delete(Long id) {
        return jdbcTemplate.update("DELETE FROM FILM WHERE ID = ?", id) > 0;
    }

    @Override
    public Film update(Film film) {
        int result = jdbcTemplate.update("UPDATE FILM SET NAME = ?, DESCRIPTION = ?, RELEASE_DATE = ?" +
                        ", DURATION = ?, MPA = ?  WHERE ID = ?",
                film.getName(), film.getDescription(), film.getReleaseDate(), film.getDuration(), film.getMpa().getId()
                , film.getId());
        jdbcTemplate.update("DELETE FROM FILMS_GENRES WHERE FILM_ID = ?", film.getId());
        for(Genre genre : film.getGenres()){
            jdbcTemplate.update("INSERT INTO Films_Genres (FILM_ID, GENRE_ID) VALUES (?, ?)",
                    film.getId(), genre.getId());
        }
        if(result != 0){
            return film;
        } else {
            throw new NoSuchFilmException(String.format("Неудалось обновить Фильм. " +
                    "Такого Фильма с ID %d не существует", film.getId()));
        }
    }

     @Override
    public List<Film> getAll() {
        String sql = "SELECT * FROM FILM LEFT OUTER JOIN RATING R on FILM.MPA = R.ID";
        List<Film> result = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
        for(Film film : result){
            String s = String.format("SELECT G2.ID, G2.NAME FROM FILMS_GENRES LEFT JOIN GENRE G2 on G2.ID = " +
                    "FILMS_GENRES.GENRE_ID WHERE FILMS_GENRES.FILM_ID = %d", film.getId());
            Collection<Genre> genresIds= jdbcTemplate.query(s, (rs, rowNum) ->
                    new Genre(rs.getInt("id"), rs.getString("name")));
            film.getGenres().addAll(genresIds);
        }
        return result;
    }
    @Override
    public Film getFilm(Long id) {
        String sql = String.format("SELECT * FROM FILM LEFT OUTER JOIN RATING R on FILM.MPA = R.ID WHERE FILM.ID = %s ", id);
        List<Film> result = jdbcTemplate.query(sql, (rs, rowNum) -> makeFilm(rs));
        for(Film film : result){
            String s = String.format("SELECT G2.ID, G2.NAME FROM FILMS_GENRES LEFT JOIN GENRE G2 on G2.ID = " +
                    "FILMS_GENRES.GENRE_ID WHERE FILMS_GENRES.FILM_ID =  %s", film.getId());
            Collection<Genre> genresIds= jdbcTemplate.query(s, (rs, rowNum) ->
                    new Genre(rs.getInt("id"), rs.getString("name")));
            film.getGenres().addAll(genresIds);
        }
        if (!result.isEmpty()) {
            return result.get(0);
        } else {
            log.info("Фильм с ID {} не найден", id);
            throw new NoSuchFilmException("Фильм с ID " + id + " не найден");
        }
    }

    @Override
    public void likeFilm(Long id, Long userId) {
        if(id < 1 || userId < 1) {
            throw new FilmServiceException("Неверный ID");
        }
        jdbcTemplate.update("INSERT INTO FILM_LIKES (FILM_ID, USER_ID) VALUES ( ?, ? )", id, userId);
    }

    @Override
    public void removeLike(Long id, Long userId) {
        if(id < 1 || userId < 1) {
            throw new FilmServiceException("Неверный ID");
        }
        jdbcTemplate.update("DELETE FROM FILM_LIKES WHERE FILM_ID = ? AND USER_ID = ?", id, userId);
    }

    @Override
    public List<Film> getTop(Integer count) {
        String sql = String.format("SELECT FILM.ID, COUNT(FILM_ID) AS count FROM FILM LEFT OUTER JOIN FILM_LIKES FL on FILM.ID = FL.FILM_ID  " +
                "GROUP BY FILM.ID ORDER BY count DESC LIMIT %s", count);
        Collection<Integer> ids = jdbcTemplate.query(sql, (rs, rowNum) -> makeInt(rs));
        String inParams = String.join(",", ids.stream().map(id -> "?").collect(Collectors.toList()));
        return jdbcTemplate.query(String.format("SELECT * FROM FILM LEFT OUTER JOIN RATING R on FILM.MPA = R.ID WHERE FILM.ID IN (%s)", inParams), ids.toArray()
                , ((rs, rowNum) -> makeFilm(rs)));
    }

    @Override
    public List<Mpa> getAllMpa() {
        return jdbcTemplate.query("SELECT * FROM RATING",
                (rs, rowNum) -> new Mpa(rs.getInt("id"), rs.getString("name")));
    }

    @Override
    public Mpa getMpa(Long id) {
        List<Mpa> result = jdbcTemplate.query (String.format("SELECT * FROM RATING WHERE ID = %s", id),
                (rs, rowNum) -> new Mpa(rs.getInt("id"), rs.getString("name")));
        if(result.isEmpty()){
            throw new NoSuchMPAException();
        }
        return result.get(0);
    }

    @Override
    public Genre getGenre(Long genreId) {
        List<Genre> result = jdbcTemplate.query(String.format("SELECT * FROM Genre WHERE ID = %s", genreId),
                (rs, rowNum) -> new Genre(rs.getInt("id"), rs.getString("name")));
        if(result.isEmpty()){
            throw new NoSuchGenreException();
        }
        return result.get(0);
    }

    @Override
    public List<Genre> getAllGenres() {
        return jdbcTemplate.query("SELECT * FROM GENRE",
                (rs, rowNum) -> new Genre(rs.getInt("id"), rs.getString("name")));
    }

    private Film makeFilm(ResultSet rs) throws SQLException {
        return Film.create(rs.getInt("id"),
                rs.getString("name"),
                rs.getString("description"),
                rs.getDate("Release_date").toLocalDate(),
                rs.getInt("duration"),
                new Mpa(rs.getInt("MPA"),  rs.getString("Rating.Name")));
    }

    private int makeInt(ResultSet rs) throws SQLException {
        return rs.getInt("id");
    }
}
