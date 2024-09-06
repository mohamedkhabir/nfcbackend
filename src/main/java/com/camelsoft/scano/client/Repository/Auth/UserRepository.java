package com.camelsoft.scano.client.Repository.Auth;



import com.camelsoft.scano.client.Interfaces.CusomUserResponse;
import com.camelsoft.scano.client.models.Auth.Role;
import com.camelsoft.scano.client.models.Auth.users;
import com.camelsoft.scano.client.models.UserTools.nfc_card;
import com.camelsoft.scano.client.models.UserTools.socialmedia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<users,Long> {
    users findByEmail(String email);

    List<users> findAllByCompanies_Id(Long id);

    boolean existsByEmail(String email);
    boolean existsById(Long id);
    users findByUsername(String username);
    List<users> findBySocialmedias(socialmedia media);
    Boolean existsByCard_Uid(String nfcCard);
    users findTopByOrderByIdDesc();
    users findTopByRoleOrderByIdDesc(Role role);
    List<users> findByRole(Role role);
    CusomUserResponse findByCard_Uid(String uid);
    Page<users> findAllByRole(Pageable page, Role role);
    List<users> findAllByRole(Role role);
    Long countAllByRole(Role role);


}
