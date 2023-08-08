package com.hook.admin.user;

import com.hook.common.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
class RoleRepositoryTest {

    @Autowired
    private RoleRepository repo;

    @Test
    void testCreateFirstRole() {
        Role roleAdmin = new Role("Admin", "Manages everything");
        Role savedRole = repo.save(roleAdmin);
        assertThat(savedRole.getId()).isPositive();
    }

    @Test
    void testCreateRestRoles() {
        Role roleSalesPerson = new Role("SalesPerson", "Manages product price, customers," +
                "shipping, orders and sales report");
        Role roleEditor = new Role("Editor", "Manages categories, brands, products, articles and menus");
        Role roleShipper = new Role("Shipper", "View products, view orders and update order status");
        Role roleAssistant = new Role("Assistant", "Manages question and reviews");
        repo.saveAll(List.of(roleSalesPerson, roleEditor, roleShipper, roleAssistant));
    }


}
