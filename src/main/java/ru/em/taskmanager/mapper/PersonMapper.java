package ru.em.taskmanager.mapper;

import ru.em.taskmanager.dto.PersonDto;
import ru.em.taskmanager.dto.auth.SignupRequest;
import ru.em.taskmanager.model.Person;

public class PersonMapper {

    public static Person toPerson(SignupRequest signupRequest) {
        final String email = signupRequest.email().toLowerCase();
        final String password = signupRequest.password();

        return Person.builder()
                .email(email)
                .password(password)
                .enabled(true)
                .build();
    }

    public static PersonDto toPersonDto(Person person) {
        return PersonDto.builder()
                .id(person.getId())
                .email(person.getEmail())
                .enabled(person.isEnabled())
                .build();
    }
}
