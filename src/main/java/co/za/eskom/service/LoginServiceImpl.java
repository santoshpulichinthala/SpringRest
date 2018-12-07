package co.za.eskom.service;

import co.za.eskom.dao.LoginRepository;
import co.za.eskom.model.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service("loginService")
@Transactional
public class LoginServiceImpl implements LoginService{

    @Autowired
    private LoginRepository loginRepository;


    public Optional<Login> findById(Long id) {
        return loginRepository.findById(id);
    }

    public Login findByName(String name) {
        return loginRepository.findByName(name);
    }

    public Login authenticate(String name,String password){
        return loginRepository.authenticate(name,password);
    }

    public void saveUser(Login user) {
        loginRepository.save(user);
    }

    public void updateUser(Login user){
        saveUser(user);
    }

    public void deleteUserById(Long id){
        loginRepository.deleteById(id);
    }

    public void deleteAllUsers(){
        loginRepository.deleteAll();
    }

    public List<Login> findAllUsers(){
        return loginRepository.findAll();
    }

    public boolean isUserExist(Login user) {
        return findByName(user.getUsername()) != null;
    }
}
