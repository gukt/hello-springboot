package com.example.hellospringboot.domain;

import com.example.hellospringboot.domain.base.AuditableSoftDeletableEntity;
import com.example.hellospringboot.utils.ListConverter;
import com.example.hellospringboot.utils.MapConverter;
import com.example.hellospringboot.utils.SetConverter;
import com.example.hellospringboot.utils.Snowflake;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    //
    //    @Convert(converter = JsonConverter.class)
    //    @Column(columnDefinition = "json")
    //    private List<Integer> list1;
    //
    //    @Convert(converter = JsonConverter.class)
    //    @Column(columnDefinition = "json")
    //    private Set<Integer> set1;

    @Convert(converter = MapConverter.class)
    private Map<String, Object> attributes;

    @Convert(converter = ListConverter.class)
    // 如果不指定 columnDefinition = "json"，则默认使用 varchar(255)
    // varchar(255) 无法存储较长的 JSON 字符串，势必还要定义 Column 的长度，此时还不如直接定义为 json 类型
    private List<String> items;

    @Convert(converter = ListConverter.class)
    private List<Integer> numbers;

    @Convert(converter = SetConverter.class)
    private Set<String> tags;

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
