package com.esantefutur.esantefutur.service.Impl;
import com.esantefutur.esantefutur.repositories.UserRepository;
import com.esantefutur.esantefutur.service.UserService;
import com.esantefutur.esantefutur.service.mappers.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

}
