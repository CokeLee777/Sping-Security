package io.security.corespingsecurity.repository;

import io.security.corespingsecurity.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);
}
