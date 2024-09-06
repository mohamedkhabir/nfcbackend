package com.camelsoft.scano.client.Repository.Auth;



import com.camelsoft.scano.client.models.Auth.PasswordResetToken;
import com.camelsoft.scano.client.models.Auth.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken,Long> {
    PasswordResetToken findByUser(users user);
    List<PasswordResetToken> findAllByUser(users user);
    PasswordResetToken findByToken(String token);
}
