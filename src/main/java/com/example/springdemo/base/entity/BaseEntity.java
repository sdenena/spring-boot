package com.example.springdemo.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@MappedSuperclass
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @Column(columnDefinition = "boolean default true")
    private Boolean status = true;
    @Column(name = "version")
    @Version
    private int version;
    @CreatedDate
    @Column(name = "created_date", updatable = false)
    private Date createdDate;
    @LastModifiedDate
    @Column(name = "updated_date")
    private Date updatedDate;
}
