package co.za.eskom.service;

import co.za.eskom.model.Login;

import java.util.List;
import java.util.Optional;

public interface LoginService {

    Optional<Login> findById(Long id);

    Login authenticate(String name,String password);

    Login findByName(String name);

    void saveUser(Login user);

    void updateUser(Login user);

    void deleteUserById(Long id);

    void deleteAllUsers();

    List<Login> findAllUsers();

    boolean isUserExist(Login user);
}
