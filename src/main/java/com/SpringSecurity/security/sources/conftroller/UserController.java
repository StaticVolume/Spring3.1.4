package com.SpringSecurity.security.sources.conftroller;

import com.SpringSecurity.security.sources.dto.UserDTO.UserDTO;
import com.SpringSecurity.security.sources.dto.UserDTO.UserDTOWithID;
import com.SpringSecurity.security.sources.model.Role;
import com.SpringSecurity.security.sources.model.User;
import com.SpringSecurity.security.sources.service.RoleService;
import com.SpringSecurity.security.sources.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Использую data transfer objects, специальные классы прослойки для работы с формированием json,
 * так как в POST и PUT запросах не нужно со строны клиента указывать id у User и id у Role */

@Controller
@RequestMapping("/")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    private final ModelMapper modelMapper;


    @Autowired
    public UserController(UserService userService, RoleService roleService, ModelMapper modelMapper) {
        this.userService = userService;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }



    @GetMapping
    public String printStartPage(Principal principal, Model model) {
        User user = userService.getUserByName(principal.getName());
        for (Role role : user.getRoles()) {
            if (role.getName().contains("ADMIN")) {
               return  "redirect:admin";
            }
        }
        return "redirect:user";
    }

    @GetMapping("admin")
    public String adminPage(Principal principal, Model model) {
        User admin = userService.getUserByName(principal.getName());
        model.addAttribute("admin", admin);
        return "admin";
    }

    @GetMapping("user")
    public String userPage(Principal principal, Model model) {
        User user = userService.getUserByName(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("admin/api/user/{name}")
    @ResponseBody
    public UserDTOWithID getUserByName(@PathVariable("name") String name) {
        return modelMapper.map(userService.getUserByName(name),UserDTOWithID.class);
    }

    @GetMapping("admin/api/users")
    @ResponseBody
    public List<UserDTOWithID> getAllUsers() {
        List<UserDTOWithID> usersDTOWithId = new ArrayList<>();
        userService.getAllUsers().stream().forEach(user-> usersDTOWithId.add(modelMapper.map(user,UserDTOWithID.class)));
        return usersDTOWithId;
    }

    @GetMapping("admin/api/users/{id}")
    @ResponseBody
    public UserDTOWithID getUserById(@PathVariable("id") Long id) {
        return modelMapper.map(userService.getUser(id),UserDTOWithID.class);
    }


    /**По причине того, что компонент select даже с включенным multiple
     * или всязи с тем, что formData не может создать массив из 2-х выбранных значений роли,
     * а записывает в ключ только строку с последним выделенным знвчением,
     * было принято решение парсить строку на серверной стороне*/
    @PostMapping(value = "admin/api/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // TODO
        }
        User user = createUserFromUserTDO(userDTO);
        userService.addUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }


    @PutMapping (value = "admin/api/users", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserDTOWithID userDTOWithID, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            //TODO
        }
        User user = createUserFromUserTDO(userDTOWithID);
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("admin/api/users/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") Long id ) {
        userService.deleteUserById(id);
        return  ResponseEntity.ok(HttpStatus.OK);
    }



    /**Утилитная функция парсинга userDTO c строковым полем getRolesStr в обьект User с List<Role> */
    private User createUserFromUserTDO(UserDTO userDTO) {
        User user = new User();

        if (userDTO instanceof UserDTOWithID) {
            user.setId(((UserDTOWithID) userDTO).getId());
        }
        user.setName(userDTO.getName());
        user.setSurname(userDTO.getSurname());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        String str = userDTO.getRolesStr();
        List<Role> rolesList = new ArrayList<>();
        Arrays.stream(str.split(" ")).toList().stream().forEach(element -> rolesList.add(new Role(element)));
        user.setRoles(rolesList);
        return user;
    }

}



