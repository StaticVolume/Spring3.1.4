package com.SpringSecurity.security.sources.conftroller;

import com.SpringSecurity.security.sources.dto.UserDTO.UserDTO;
import com.SpringSecurity.security.sources.dto.UserDTO.UserDTOWithID;
import com.SpringSecurity.security.sources.model.User;
import com.SpringSecurity.security.sources.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

/** Использую data transfer objects, специальные классы прослойки для работы с формированием json,
 * так как в POST и PUT запросах не нужно со строны клиента указывать id у User и id у Role */

@RestController
@RequestMapping("/")
public class UserController {

    private final UserService userService;

    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public RedirectView redirectView() {
        return new RedirectView("/login");
    }

    @GetMapping("admin")
    public ModelAndView adminPage(Principal principal) throws JsonProcessingException {
        User admin = userService.getUserByName(principal.getName());
        return new ModelAndView("admin","admin",
                                objectMapper.writeValueAsString(modelMapper.map(admin,UserDTOWithID.class)));
    }

    @GetMapping("user")
    public ModelAndView userPage(Principal principal) throws JsonProcessingException {
        User user = userService.getUserByName(principal.getName());
        return new ModelAndView("user","user",
                                objectMapper.writeValueAsString(modelMapper.map(user,UserDTOWithID.class)));
    }

    @GetMapping("admin/api/user/{name}")
    public UserDTOWithID getUserByName(@PathVariable("name") String name) {
        return modelMapper.map(userService.getUserByName(name),UserDTOWithID.class);
    }

    @GetMapping("admin/api/users")
    public List<UserDTOWithID> getAllUsers() {
        List<UserDTOWithID> usersDTOWithId = new ArrayList<>();
        userService.getAllUsers().stream().forEach(user-> usersDTOWithId.add(modelMapper.map(user,UserDTOWithID.class)));
        return usersDTOWithId;
    }

    @GetMapping("admin/api/users/{id}")
    public UserDTOWithID getUserById(@PathVariable("id") Long id) {
        return modelMapper.map(userService.getUser(id),UserDTOWithID.class);
    }


    /**По причине того, что компонент select даже с включенным multiple
     * или всязи с тем, что formData не может создать массив из 2-х выбранных значений роли,
     * а записывает в ключ только строку с последним выделенным знвчением,
     * было принято решение парсить строку на серверной стороне*/
    @PostMapping(value = "admin/api/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // TODO
        }
        userService.addUser(userDTO.Convert(userDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PutMapping (value = "admin/api/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserDTOWithID userDTOWithID, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //TODO
        }

        userService.updateUser(userDTOWithID.Convert(userDTOWithID));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("admin/api/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id ) {
        userService.deleteUserById(id);
        return  ResponseEntity.ok(HttpStatus.OK);
    }

}



