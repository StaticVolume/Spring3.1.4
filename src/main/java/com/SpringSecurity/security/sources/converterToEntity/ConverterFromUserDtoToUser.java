package com.SpringSecurity.security.sources.converterToEntity;

import com.SpringSecurity.security.sources.dto.UserDTO.UserDTO;
import com.SpringSecurity.security.sources.dto.UserDTO.UserDTOWithID;
import com.SpringSecurity.security.sources.model.Role;
import com.SpringSecurity.security.sources.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
/**Утилитная функция парсинга userDTO c строковым полем getRolesStr в обьект User с List<Role> */
public class ConverterFromUserDtoToUser implements ConverterFromTo<UserDTO, User> {
    @Override
    public User Convert(UserDTO userDTO) {
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
