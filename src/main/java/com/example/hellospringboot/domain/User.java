package com.example.hellospringboot.domain;

import com.example.hellospringboot.domain.base.AuditableSoftDeletableEntity;
import com.example.hellospringboot.utils.Snowflake;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.SQLDelete;

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

    @Override
    protected void onCreate() {
        log.info("onCreate() called");
        System.out.println("User: " + this);

        if (id == null) {
            log.info("id is null, will set it using snowflake");
            id = Snowflake.newId();
        }
    }
}
