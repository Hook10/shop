package com.hook.admin.user;

import com.hook.common.Role;
import com.hook.common.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
class UserRepositoryTest {

    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    void testCreateUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1);
        User userHook = new User("hook@gmail.com", "hook2020", "Hook", "Ten");
        userHook.addRole(roleAdmin);

        User savedUser = repo.save(userHook);
        assertThat(savedUser.getId()).isPositive();
    }

    @Test
    void testCreateUserWithTwoRoles() {
        User userRavi = new User("ravi@gmail.com", "ravi2020", "Ravi", "Kumar");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);

        userRavi.addRole(roleEditor);
        userRavi.addRole(roleAssistant);

        User savedUser = repo.save(userRavi);
        assertThat(savedUser.getId()).isPositive();
    }

    @Test
    void testListAllUsers() {
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(System.out::println);
    }

    @Test
    void testGetUserById() {
        Optional<User> optionalUser = repo.findById(1);
        assertThat(optionalUser.isPresent());
        assertThat(optionalUser.get()).isNotNull();
    }

    @Test
    void testUpdateUserDetails() {
        User hook = repo.findById(1).get();
        hook.setEnabled(true);
        hook.setEmail("hookTen@gmail.com");

        User updatedUser = repo.save(hook);

        assertThat(updatedUser.getEmail()).isEqualTo(hook.getEmail());
        assertThat(updatedUser.isEnabled()).isEqualTo(hook.isEnabled());
    }

    @Test
    void testUpdateUserRole() {
        User userRavi = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesPerson = new Role(3);
        userRavi.getRoles().remove(roleEditor);
        userRavi.addRole(roleSalesPerson);

        User savedUser = repo.save(userRavi);
        assertThat(savedUser.getRoles().stream()
                .filter(u -> u.equals(roleSalesPerson))
                .findAny().get()).isEqualTo(roleSalesPerson);
    }

    @Test
    void deleteUser() {
        Integer userId = 2;
        repo.deleteById(userId);

        assertThrows(Exception.class, () -> repo.findById(userId)
                .orElseThrow(() -> new Exception("User not found")));
    }
}
