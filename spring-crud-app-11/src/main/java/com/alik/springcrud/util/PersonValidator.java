package com.alik.springcrud.util;


import com.alik.springcrud.dao.PersonDao;
import com.alik.springcrud.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import java.util.Objects;

@Component
public class PersonValidator implements Validator {

    private final PersonDao personDao;

    @Autowired
    public PersonValidator(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if(Objects.nonNull(personDao.show(person.getEmail()))&&
                personDao.show(person.getId()).getId()!=person.getId()){
            errors.rejectValue("email","","This email already exists");
        }

        if(Character.isLowerCase(person.getName().charAt(0))){
            errors.rejectValue("name","","Name should start from Upper Case");
        }

    }
}
