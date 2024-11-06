package com.assignment.account.management.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_date")
    private LocalDateTime updatedAt;


    @PrePersist
    public void prePersist() {
       this.createdAt = LocalDateTime.now();  // set the created date
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();  // update the updated date
    }
}
