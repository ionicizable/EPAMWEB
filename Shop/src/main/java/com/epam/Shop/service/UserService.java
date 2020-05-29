package com.epam.Shop.service;


import com.epam.Shop.entities.Users;
import com.epam.Shop.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Users> getAll() {
        return userRepository.findAll();
    }

    public void createUser(Users users){
        List<Users> usersList = getAll();
        int result = 0;
        for (Users user : usersList) {
            if (user.getId() > result) {
                result = user.getId();
            }
        }
        users.setId(result+1);
        userRepository.saveAndFlush(users);
    }

    public void deleteUser(Users user){
        userRepository.delete(user);
    }

    public void deleteById(Integer id){
        userRepository.deleteById(id);
    }
}
