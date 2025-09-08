package com.example.springdemo.services.implementations;

import com.example.springdemo.dto.RoleDto;
import com.example.springdemo.exceptions.CustomException;
import com.example.springdemo.models.Role;
import com.example.springdemo.repositories.RoleRepository;
import com.example.springdemo.services.RoleService;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Role createRole(Role req) {
        return roleRepository.save(req);
    }

    @Override
    public Role updateRole(Long id, RoleDto req) {
        logger.info("updateRole - id: {}, req: {}", id, req.toString());
        Role roleObj = getRoleDetail(id);
        Role updatedRole = req.updateRole(roleObj);
        return roleRepository.save(updatedRole);
    }

    @Override
    public Role getRoleDetail(Long id) {
        logger.info("getRoleDetail - id: {}", id);
        return roleRepository.findByIdAndStatusTrue(id).orElseThrow(() -> new CustomException(404, "Role not found"));
    }

    @Override
    public Page<Role> getRoleList(String query, int page, int size) {
        logger.info("getRoleList - query: {}, page: {}, size: {}", query, page, size);
        return roleRepository.findAll((root, cq, cb) -> {
            ArrayList<Predicate> predicates = new ArrayList<>();

            if (query != null) {
                var searchRoleName = cb.like(cb.upper(root.get("roleName")), "%" + query.toUpperCase() + "%");
                predicates.add(cb.or(searchRoleName));
            }

            predicates.add(cb.isTrue(root.get("status")));
            Objects.requireNonNull(cq).orderBy(cb.desc(root.get("id")));
            return cb.and(predicates.toArray(new Predicate[0]));
        }, PageRequest.of(page, size));
    }

    @Override
    public void deleteRole(Long id) {
        Role roleObj = getRoleDetail(id);
        roleObj.setStatus(false);

        roleRepository.save(roleObj);
    }
}
