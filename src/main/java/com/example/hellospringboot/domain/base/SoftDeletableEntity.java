package com.example.hellospringboot.domain.base;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * SoftDeletableEntity class
 *
 * @author gukt
 * @since 1.0
 */
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public abstract class SoftDeletableEntity<T> extends BaseEntity<T> {

    @Builder.Default
    @Column(nullable = false, columnDefinition = "bit default 0")
    private Boolean deleted = false;

    // Optionally, you can add fields like deletedAt, deletedBy, etc.
}
