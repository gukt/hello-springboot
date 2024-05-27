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

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
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
    public void testJsonConverters() {
        userRepository.findById(1L).ifPresentOrElse(user -> {
            System.out.println("user = " + user);

            printFieldGenericType("intList");
            // printFieldGenericType("stringList");
            // printFieldGenericType("objectList");

            user.getIntList().forEach(item -> System.out.println(item + ":" + item.getClass().getName()));
            user.getLongList().forEach(item -> System.out.println(item + ":" + item.getClass().getName()));
            user.getStringList().forEach(item -> System.out.println(item + ":" + item.getClass().getName()));
            user.getObjectList().forEach(item -> System.out.println(item + ":" + item.getClass().getName()));
            user.getObjectList2().forEach(item -> System.out.println(item + ":" + item.getClass().getName()));
        }, () -> System.out.println("👉 User not found"));
    }

    private void printFieldGenericType(String fieldName) {
        Field field = Arrays.stream(User.class.getDeclaredFields())
                .filter(f -> f.getName().equals(fieldName))
                .findFirst()
                .orElseThrow();

        Type genericType = field.getGenericType();

        if (genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (Type type : actualTypeArguments) {
                System.out.println("Generic type: " + type);
            }
        }
    }

    @Test
    public void test1() {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "[\"111\", \"2\", \"3\"]";
        try {
            // 当需要转换为数值类型时，如果元素的值超出了指定的泛型类型值，则会抛出异常，比如：
            // com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `java.lang.Integer` from String "1111111111111": Overflow: numeric value (1111111111111) out of range of `java.lang.Integer` (-2147483648 -2147483647)
            // 如果元素的值均不超过指定的泛型类型值，则可以正常转换，比如：["1", "2", "3"] -> [1, 2, 3] 可以顺利地转换为以下类型：
            // List<Byte> OK -> List<Byte>
            // List<Short> OK -> List<Short>
            // List<Integer> OK -> List<Integer>
            // List<Long> OK -> List<Long>
            // List<Double> OK -> List<Double>
            // List<Float> OK -> List<Float>
            // List<BigInteger> OK -> List<BigInteger>
            // List<BigDecimal> OK -> List<BigDecimal>
            //
            // 👉🏻 List<Number>
            // List<Number> OK -> List<Number>,
            // 此时如果数值比较小就会转换为 Integer，如果比较大就会转换为 Long，所以 List<Number> 中并不是每个元素的类型都是一样的。而是根据数值的大小来决定的。
            // 例如：[1111111111111, 2, 3]
            // 1111111111111:java.lang.Long
            // 2:java.lang.Integer
            // 3:java.lang.Integer
            //
            // 当需要转换 Character 时，元素就必须是单个字符，否则会抛出异常：
            // com.fasterxml.jackson.databind.exc.InvalidFormatException: Cannot deserialize value of type `java.lang.Character` from String "111": Expected either Integer value code or 1-character String
            // List<Character> OK -> List<Character>
            //
            // 👉🏻 List<String>, List<?>, List<Object>:
            // 因为原始数据为 String，所以以下几种类型都被解析成 List<String>
            // List<String> OK -> List<String>
            // List<?> OK -> List<String>
            // List<Object> OK -> List<String>
            List<Character> numbers = objectMapper.readValue(json, new TypeReference<>() {});
            System.out.println(numbers);
            numbers.forEach(item -> System.out.println(item + ":" + item.getClass().getName()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "[\"1\", \"2\", \"3\"]";
        List<Integer> numbers = objectMapper.readValue(json, new TypeReference<List<Integer>>() {
        });
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
