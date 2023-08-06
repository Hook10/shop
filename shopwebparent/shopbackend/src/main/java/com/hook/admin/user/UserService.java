package com.hook.admin.user;

import com.hook.common.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository repository;


    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> listAll() {
        return (List<User>) repository.findAll();
    }

}
