package com.example.hellospringboot.domain;

import com.example.hellospringboot.domain.base.AuditableSoftDeletableEntity;
import com.example.hellospringboot.utils.Snowflake;
import com.example.hellospringboot.utils.converter.JsonConverter;
import com.example.hellospringboot.utils.converter.ListConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;

import java.util.List;

/**
 * User class
 *
 * @author gukt
 * @since 1.0
 */


@Entity
@Table(name = "t_users")
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
@ToString
@SQLDelete(sql = "UPDATE t_users SET deleted = true WHERE id = ?")
//@SQLRestriction("deleted = false")
@Slf4j
public class User extends AuditableSoftDeletableEntity<Long> {
    private String name;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated
    private Status status2;

    //    @Convert(converter = JsonConverter.class)
    //    @Column(columnDefinition = "json")
    //    private Map<String, Object> map1;

    @Convert(converter = ListConverter.Int.class)
    @Column(columnDefinition = "json")
    private List<Integer> intList;

    @Convert(converter = ListConverter.Long.class)
    @Column(columnDefinition = "json")
    private List<Long> longList;

    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "json")
    private List<String> stringList;

    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "json")
    private List<?> objectList;

    // 使用 JsonConverter 也是支持的，只是有一些限制，比如它不支持默认值。
    // 而如果使用 ListConverter，则默认值是空数组 []，这样就不会出现空指针异常。
    // 如果你想要更精确的解析，可以使用 ListConverter.Int 或 ListConverter.Long。
    @Convert(converter = JsonConverter.class)
    @Column(columnDefinition = "json")
    private List<Object> objectList2;

    //    @Convert(converter = JsonConverter.class)
    //    @Column(columnDefinition = "json")
    //    private Set<Integer> set1;

    //    @Convert(converter = MapConverter.class)
    //    private Map<String, Object> attributes;
    //
    //    @Convert(converter = ListConverter.class)
    //    // 如果不指定 columnDefinition = "json"，则默认使用 varchar(255)
    //    // varchar(255) 无法存储较长的 JSON 字符串，势必还要定义 Column 的长度，此时还不如直接定义为 json 类型
    //    private List<String> items;
    //
    //    @Convert(converter = ListConverter.class)
    //    private List<Integer> numbers;
    //
    //    @Convert(converter = SetConverter.class)
    //    private Set<String> tags;

    @Override
    protected void onCreate() {
        log.info("onCreate() called");
        System.out.println("User: " + this);

        if (id == null) {
            log.info("id is null, will set it using snowflake");
            id = Snowflake.newId();
        }
    }

    public enum Status {
        NEW, IN_PROGRESS, COMPLETED
    }
}
