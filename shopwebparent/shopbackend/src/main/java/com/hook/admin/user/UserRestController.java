package com.hook.admin.user;

import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    private UserService service;

    public UserRestController(UserService service) {
        this.service = service;
    }

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id,
                                      @Param("email") String email) {
        return service.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
