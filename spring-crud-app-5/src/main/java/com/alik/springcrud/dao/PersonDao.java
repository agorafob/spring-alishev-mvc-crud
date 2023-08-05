package com.alik.springcrud.dao;

import com.alik.springcrud.models.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private static int PEOPLE_COUNT;
    private static final String URL = "jdbc:postgresql://localhost:5433/first_db";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "admin";

    private static Connection connection;

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

//    public Person show(int id){
//        return people.stream().filter(p-> p.getId()==id).findAny().orElse(null);
//    }
//
public void save(Person person){
    try {
        Statement statement = connection.createStatement();
        String SQL = "INSERT INTO person VALUES("+5+",'"+person.getName()+
                "'," + person.getAge()+ ",'"+person.getEmail()+"')";
        statement.executeUpdate(SQL);
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
//
//    public void update(int id, Person updatePerson){
//        Person personToBeUpdated = show(id);
//        personToBeUpdated.setName(updatePerson.getName());
//        personToBeUpdated.setAge(updatePerson.getAge());
//        personToBeUpdated.setEmail(updatePerson.getEmail());
//    }
//
//    public void delete(int id){
//        people.removeIf(p->p.getId()==id);
//    }
}
