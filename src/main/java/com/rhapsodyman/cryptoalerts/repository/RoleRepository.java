package com.rhapsodyman.cryptoalerts.repository;


import java.util.Optional;

import com.rhapsodyman.cryptoalerts.domain.ERole;
import com.rhapsodyman.cryptoalerts.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
