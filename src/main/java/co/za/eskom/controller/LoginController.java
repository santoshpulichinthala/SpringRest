package co.za.eskom.controller;

import co.za.eskom.model.Login;
import co.za.eskom.service.LoginService;
import co.za.eskom.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("ALL")
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class LoginController {
    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    LoginService userService; //Service which will do all data retrieval/manipulation work

    // -------------------Retrieve All Users---------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<Login>> listAllUsers() {
        List<Login> users = userService.findAllUsers();
        if (users.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            // You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Login>>(users, HttpStatus.OK);
    }

    // -------------------Retrieve Single User------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable("id") long id) {
        logger.info("Fetching User with id {}", id);
        Optional<Login> user = userService.findById(id);
        Login login;
        if (user.isPresent()){
            login = user.get();
            login.setUsername(login.getUsername());
            login.setPassword(login.getPassword());

            userService.updateUser(login);
        }else{
            logger.error("User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("User with id " + id
                    + " not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Login>(login, HttpStatus.OK);
    }

    // -------------------Create a User-------------------------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody Login user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);

        if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exist", user.getUsername());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name " +
                    user.getUsername() + " already exist."),HttpStatus.CONFLICT);
        }
        userService.saveUser(user);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }

    // -------------------Authenticate-------------------------------------------
    @RequestMapping(value = "/user/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> authenticate(@RequestBody Login user) {
        logger.info("Authenticating User : {}", user);
        Login login = userService.authenticate(user.getUsername(),user.getPassword());
        if (login != null && login.getUsername() == null) {
            logger.error("Unable to authenticate. A User with name {} ", user.getUsername());
            return new ResponseEntity(new CustomErrorType("Unable to authenticate. A User with name " +
                    user.getUsername() + " "),HttpStatus.CONFLICT);
        }

        return new ResponseEntity<Login>(login, HttpStatus.OK);
    }

    // ------------------- Update a User ------------------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody Login user) {
        logger.info("Updating User with id {}", id);

        Optional<Login> currentUser = userService.findById(id);
        Login login;

        if (currentUser.isPresent()){
            login = currentUser.get();
            login.setUsername(user.getUsername());
            login.setPassword(user.getPassword());


            userService.updateUser(login);
        }else{
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to upate. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }


        return new ResponseEntity<Login>(login, HttpStatus.OK);
    }

    // ------------------- Delete a User-----------------------------------------

    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
        logger.info("Fetching & Deleting User with id {}", id);

        Optional<Login> user = userService.findById(id);
        Login login;
        if (user.isPresent()){
            login = user.get();
            login.setUsername(login.getUsername());
            login.setPassword(login.getPassword());

            userService.updateUser(login);
        }else{
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<Login>(HttpStatus.NO_CONTENT);
    }

    // ------------------- Delete All Users-----------------------------

    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<Login> deleteAllUsers() {
        logger.info("Deleting All Users");

        userService.deleteAllUsers();
        return new ResponseEntity<Login>(HttpStatus.NO_CONTENT);
    }
}
