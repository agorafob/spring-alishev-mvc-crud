package com.alik.springcrud.dao;

import com.alik.springcrud.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id=?",
                    new BeanPropertyRowMapper<>(Person.class), id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Person show(String email) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM person WHERE email=?",
                    new BeanPropertyRowMapper<>(Person.class), email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name,age,email) VALUES (?,?,?)",
                person.getName(), person.getAge(), person.getEmail());
    }

    public void update(int id, Person updatePerson) {
        jdbcTemplate.update("UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?",
                updatePerson.getName(), updatePerson.getAge(), updatePerson.getEmail(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public void testMultipleUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();

        for (Person person : people) {
            jdbcTemplate.update("INSERT INTO person(name,age,email) VALUES (?,?,?)",
                    person.getName(), person.getAge(), person.getEmail());
        }
        long after = System.currentTimeMillis();

        System.out.println("Time: " + (after - before));
    }

    public void testBatchUpdate() {
        List<Person> people = create1000People();
        long before = System.currentTimeMillis();
        jdbcTemplate.batchUpdate("INSERT INTO person(name,age,email) VALUES (?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setString(1, people.get(i).getName());
                ps.setInt(2, people.get(i).getAge());
                ps.setString(3, people.get(i).getEmail());
            }

            @Override
            public int getBatchSize() {
                return people.size();
            }
        });
        long after = System.currentTimeMillis();
        System.out.println("Time: " + (after - before));
    }

    private List<Person> create1000People() {
        List<Person> people = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            people.add(new Person("Alik" + i, i + 10, "alik" + i + "@test.com"));
        }
        return people;
    }


}
