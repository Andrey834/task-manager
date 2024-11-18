package ru.em.taskmanager.service;

import ru.em.taskmanager.dto.*;
import ru.em.taskmanager.dto.auth.RefreshRequest;
import ru.em.taskmanager.dto.auth.SigninRequest;
import ru.em.taskmanager.dto.auth.SignupRequest;
import ru.em.taskmanager.dto.auth.Tokens;

public interface AuthService {

    PersonDto signup(SignupRequest request);

    Tokens signin(SigninRequest signinRequest);

    Tokens refresh(RefreshRequest refreshRequest);

    void logout(String email);
}
