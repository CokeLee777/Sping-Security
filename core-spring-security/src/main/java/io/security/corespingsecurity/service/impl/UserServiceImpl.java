package io.security.corespingsecurity.service.impl;

import io.security.corespingsecurity.domain.Account;
import io.security.corespingsecurity.repository.UserRepository;
import io.security.corespingsecurity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public void createUser(Account account) {
        userRepository.save(account);
    }
}
