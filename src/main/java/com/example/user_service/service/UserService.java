package com.example.user_service.service;

import com.example.user_service.consumers.event.UserRegisteredEvent;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.dto.UserRequestDTO;
import com.example.user_service.entity.User;
import com.example.user_service.mapper.UserMapper;
import com.example.user_service.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements IUserService{

    private final IUserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOs(users);
    }

    @Override
    public UserDTO getUserByID(String id) {
        User user = userRepository.findById(id).orElseThrow(()-> new RuntimeException("user not found"));
        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO getOwnProfile(String username) {
        String userId = (String) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.toDTO(user);
    }

    @Override
    public UserDTO updateUser(String id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (userRequestDTO.getUsername() != null && !userRequestDTO.getUsername().isBlank()) {
            user.setUsername(userRequestDTO.getUsername());
        }

        if (userRequestDTO.getAvatarUrl() != null && !userRequestDTO.getAvatarUrl().isBlank()) {
            user.setAvatarUrl(userRequestDTO.getAvatarUrl());
        }

        return userMapper.toDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

    @Override
    public void createFromEvent(UserRegisteredEvent event) {

        if (userRepository.existsById(event.getUserId())) {
            return;
        }

        User user = new User();
        user.setId(event.getUserId());
        user.setEmail(event.getEmail());
        user.setUsername(event.getUsername());
        user.setRole(event.getRole());

        userRepository.save(user);
    }
}
