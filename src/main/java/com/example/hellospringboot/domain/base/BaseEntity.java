package com.example.hellospringboot.domain.base;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

/**
 * BaseEntity class
 *
 * @author gukt
 * @since 1.0
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@ToString
@Data
@SuperBuilder
@Slf4j
public abstract class BaseEntity<T> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected T id;

    @PrePersist
    protected void onCreate() {
        log.info("onCreate() called");
    }

    @PreUpdate
    protected void onUpdate() {
        log.info("onUpdate() called");
    }
}
