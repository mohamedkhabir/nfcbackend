package com.camelsoft.scano.client.Repository.Auth;

import com.camelsoft.scano.client.models.Auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRole(String role);
    boolean existsByRole(String role);

}
