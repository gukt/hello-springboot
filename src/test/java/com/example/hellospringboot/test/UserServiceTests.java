package com.example.hellospringboot.test;

import com.example.hellospringboot.domain.User;
import com.example.hellospringboot.domain.UserRepository;
import com.example.hellospringboot.service.UserService;
import com.example.hellospringboot.utils.Snowflake;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    @Transactional
    public void testEnum() {
        User user = new User();
        user.setStatus(User.Status.NEW);
        user.setStatus2(User.Status.COMPLETED);
        user = userRepository.save(user);

        assertThat(user.getStatus()).isEqualTo(User.Status.NEW);
    }

    //    @Test
    //    @Transactional
    //    public void testSaveEntityWithImmutableMap() {
    //        User user = new User();
    //        // 此时 map1 的类型为 ImmutableCollections.Map，不能直接修改。
    //        user.setMap1(Map.of("key1", "value1", "key2", 2));
    //        User user2 = userRepository.save(user);
    //        assertThat(user2).isEqualTo(user);
    //        // 调用 save 后的返回值 user2 不会创建一个新对象，还是 user 对象（但是经过了赋值，比如 id， createdAt, updatedAt)
    //        // 所以，此时我们如果调用 user2.getMap1() 来进行修改会报错，因为 map1 是不可变的。
    //        // 断言：往 map1 中添加一个新的键值对时会报错
    //        assertThrows(UnsupportedOperationException.class, () -> user2.getMap1().put("key3", 3));
    //    }

    @Test
    public void testJsonFields() {
        userRepository.findById(1L).ifPresentOrElse(user -> {
            System.out.println("user = " + user);
            System.out.println("user.getAttributes() = " + user.getAttributes());
            System.out.println("user.getItems() = " + user.getItems());
            System.out.println("user.getTags() = " + user.getTags());

            for (Integer key : user.getNumbers()) {
                System.out.println("key = " + key);
            }
        }, () -> System.out.println("User not found"));
    }

    @Test
    public void testJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "[\"1\", \"2\", \"3\"]";
        List<Integer> numbers = objectMapper.readValue(json, new TypeReference<List<Integer>>() {});
        System.out.println(numbers);
    }

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
