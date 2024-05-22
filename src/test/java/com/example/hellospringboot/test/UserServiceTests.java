package com.example.hellospringboot.test;

import com.example.hellospringboot.domain.User;
import com.example.hellospringboot.domain.UserRepository;
import com.example.hellospringboot.service.UserService;
import com.example.hellospringboot.utils.Snowflake;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UserServiceTests class
 *
 * @author gukt
 * @since 1.0
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class UserServiceTests {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() throws InterruptedException {
        User user = new User();
        user.setName("gukt");
        user = userRepository.save(user);
        // 断言 user 的 createdAt, updatedAt 不为空
        assertThat(user.getCreatedAt()).isNotNull();
        assertThat(user.getUpdatedAt()).isNotNull();

        // 2024-05-22T09:43:49.632550
        // 2024-05-22T09:43:49.632550
        System.out.println(user.getCreatedAt());
        System.out.println(user.getUpdatedAt());

        Thread.sleep(1000);

        user.setName("gukt(updated)");
        user = userRepository.save(user);
        // 2024-05-22T09:43:49.632550 (unchanged)
        // 2024-05-22T09:43:50.783164 (changed)
        System.out.println(user.getCreatedAt());
        System.out.println(user.getUpdatedAt());
    }

    @Test
    public void testSoftDelete() {
        User user = User.builder().name("Foo").build();
        user = userRepository.save(user);
        System.out.println(user);

        userRepository.delete(user);
        userRepository.findById(user.getId()).ifPresentOrElse(
                u -> System.out.println("u = " + u),
                () -> System.out.println("User not found")
        );
    }

    @Test
    public void testUserBuilder() {
        User user = User.builder()
                .id(Snowflake.newId())
                .name("gukt")
                .build();
        System.out.println("user = " + user);
        assertThat(user.getName()).isEqualTo("gukt");
    }
}
