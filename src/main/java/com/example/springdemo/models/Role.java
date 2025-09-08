package com.example.springdemo.models;

import com.example.springdemo.base.entity.BaseEntity;
import com.example.springdemo.dto.RoleDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mas_role")
@Getter
@Setter
@NoArgsConstructor
public class Role extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String roleName;
    private Boolean admin = false;

    public Role(Long id) {
        this.id = id;
    }

    public Role(Long id, @NotBlank(message = "Name is required") String name, Boolean admin) {
        this.id = id;
        this.roleName = name;
        this.admin = admin;
    }

    public RoleDto toRoleDto() {
        return new RoleDto(id, roleName, admin);
    }
}
