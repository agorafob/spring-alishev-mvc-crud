package com.alik.springcrud.dao;

import com.alik.springcrud.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PersonDao {
    JdbcTemplate jdbcTemplate;
    private static int PERSON_COUNTER=10;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());
    }

    public Person show(int id) {
//        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",new Object[]{id},
//                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id=?",
                new BeanPropertyRowMapper<>(Person.class),id);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person VALUES (?,?,?,?)",
                ++PERSON_COUNTER,person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplate.update("UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?",
                updatePerson.getName(), updatePerson.getAge(), updatePerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }
}
