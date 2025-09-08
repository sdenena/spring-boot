package com.example.springdemo.dto;

import com.example.springdemo.models.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {
    private Long id = null;

    @NotBlank(message = "Name is required")
    private String name = null;

    private Boolean admin = false;

    public Role toRole() {
        return new Role(id, name, admin);
    }

    public Role updateRole(Role role) {
        role.setRoleName(name);
        role.setAdmin(admin);
        return role;
    }

    public Role fromRole(Role role) {
        return new Role(role.getId(), role.getRoleName(), role.getAdmin());
    }
}
