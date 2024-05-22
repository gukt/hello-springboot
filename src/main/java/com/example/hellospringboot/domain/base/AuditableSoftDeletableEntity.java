package com.example.hellospringboot.domain.base;

/**
 * AuditableSoftDeletableEntity class
 *
 * @author gukt
 * @since 1.0
 */

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.SQLRestriction;

import java.util.Objects;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder
@SQLRestriction("deleted = false")
public abstract class AuditableSoftDeletableEntity<T> extends AuditableEntity<T> {

    @Builder.Default
    @Getter(AccessLevel.NONE)
    @Column(nullable = false, columnDefinition = "bit default 0")
    protected Boolean deleted = false;

    public boolean isDeleted() {
        return Objects.equals(deleted, true);
    }

    // Optionally, you can add fields like deletedAt, deletedBy, etc.
}