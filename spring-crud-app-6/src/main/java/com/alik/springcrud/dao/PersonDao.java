package com.alik.springcrud.dao;

import com.alik.springcrud.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private static final String URL = "jdbc:postgresql://localhost:5433/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";

    private static final Connection connection;

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            connection= DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Person> index(){
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM person";
            ResultSet resultSet = statement.executeQuery(SQL);
            while (resultSet.next()){
                Person person = new Person();
                person.setId(resultSet.getInt("id"));
                person.setName(resultSet.getString("name"));
                person.setAge(resultSet.getInt("age"));
                person.setEmail(resultSet.getString("email"));
                people.add(person);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public Person show(int id){
        Person person = new Person();
        try {
            PreparedStatement prep = connection.prepareStatement
                    ("SELECT * FROM person WHERE id=?");
            prep.setInt(1,id);
            ResultSet rs = prep.executeQuery();
            while (rs.next()){
                person.setId(rs.getInt("id"));
                person.setName(rs.getString("name"));
                person.setAge(rs.getInt("age"));
                person.setEmail(rs.getString("email"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return person;
    }

public void save(Person person) {
    try {
        PreparedStatement prep = connection.prepareStatement
                ("INSERT INTO person VALUES (20,?,?,?)");
        prep.setString(1, person.getName());
        prep.setInt(2,person.getAge());
        prep.setString(3, person.getEmail());
        prep.executeUpdate();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

    public void update(int id, Person updatePerson){
        try {
            PreparedStatement prep = connection.prepareStatement
                    ("UPDATE person SET name = ?, age = ?, email = ? WHERE id = ?");
            prep.setString(1, updatePerson.getName());
            prep.setInt(2, updatePerson.getAge());
            prep.setString(3, updatePerson.getEmail());
            prep.setInt(4, id);
            prep.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void delete(int id){
        try {
        PreparedStatement prep = connection.prepareStatement("DELETE FROM person WHERE id=?");
        prep.setInt(1,id);
        prep.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
