package com.alik.springcrud.dao;

import com.alik.springcrud.models.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDao {
    private List<Person> people;
    private static int PEOPLE_COUNT;

    {
        people=new ArrayList<>();
        people.add(new Person(++PEOPLE_COUNT, "Tom", 24, "tom@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Bob", 52, "bob@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Mike", 18, "mike@gmail.com"));
        people.add(new Person(++PEOPLE_COUNT, "Katy", 34, "katy@gmail.com"));
    }

    public List<Person> index(){
        return people;
    }

    public Person show(int id){
        return people.stream().filter(p-> p.getId()==id).findAny().orElse(null);
    }

    public void save(Person person){
        person.setId(++PEOPLE_COUNT);
        people.add(person);
    }

    public void update(int id, Person updatePerson){
        Person personToBeUpdated = show(id);
        personToBeUpdated.setName(updatePerson.getName());
        personToBeUpdated.setAge(updatePerson.getAge());
        personToBeUpdated.setEmail(updatePerson.getEmail());
    }

    public void delete(int id){
        people.removeIf(p->p.getId()==id);
    }
}
