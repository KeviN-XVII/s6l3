package kevinquarta.s6l3.controllers;



import kevinquarta.s6l3.entities.User;
import kevinquarta.s6l3.exceptions.ValidationException;
import kevinquarta.s6l3.payloads.UserDTO;
import kevinquarta.s6l3.payloads.UserPayload;
import kevinquarta.s6l3.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


//1 GET /users ritorna lista di autori
    @GetMapping
    public Page<User> findAll(@RequestParam(defaultValue = "0")int page,
                              @RequestParam(defaultValue = "10")int size,
                              @RequestParam(defaultValue = "name")String orderBy,
                              @RequestParam(defaultValue = "asc")String sortCriteria) {
        return usersService.findAll(page, size, orderBy, sortCriteria);
    }


//2 GET /users/123 ritorna una singolo autore
    @GetMapping("/{userId}")
    public User getUserById(@PathVariable long userId) {
        return usersService.findById(userId);
    }

//3 POST /users crea un nuovo autore
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody @Validated UserDTO payload, BindingResult validationResult) {

        if (validationResult.hasErrors()) {

            List<String> errorsList = validationResult.getFieldErrors()
                    .stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();
            throw new ValidationException(errorsList);
        }else{
        return this.usersService.saveUser(payload);
        }
    }

//4 PUT /users/123 modifica lo specifico autore
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable long userId, @RequestBody UserDTO payload) {
        return this.usersService.findByIdAndUpdate(userId, payload);
    }

//5 DELETE /users/123 elimina uno specifico autore
    @DeleteMapping("/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable long userId) {
        this.usersService.findByIdAndDelete(userId);
    }

}
