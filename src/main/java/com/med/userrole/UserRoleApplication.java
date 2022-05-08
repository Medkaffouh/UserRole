package com.med.userrole;

import com.med.userrole.entities.Role;
import com.med.userrole.entities.User;
import com.med.userrole.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class UserRoleApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserRoleApplication.class, args);
    }
    @Bean
    CommandLineRunner start(UserService userService){
        return args -> {
            User u = new User();
            u.setUsername("user1");
            u.setPassword("123456");
            userService.addNewUser(u);

            User u2 = new User();
            u2.setUsername("admin");
            u2.setPassword("123456");
            userService.addNewUser(u2);

            Stream.of("STUDENT","USER","ADMIN").forEach(r->{
                Role r1 = new Role();
                r1.setRoleName(r);
                userService.addNewRole(r1);
            });

            userService.addRoleToUser("user1","STUDENT");
            userService.addRoleToUser("user1","USER");
            userService.addRoleToUser("admin","USER");
            userService.addRoleToUser("admin","ADMIN");

            try {
                User user = userService.authenticate("user1","123456");
                System.out.println(user.getUserId());
                System.out.println(user.getUsername());
                System.out.println("Roles =>");
                user.getRoles().forEach(role -> {
                    System.out.println("Role : "+role.toString());
                });
            }
            catch (Exception exception){
                exception.printStackTrace();
            }
        };
    }

}
