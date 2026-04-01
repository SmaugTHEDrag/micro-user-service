package com.example.user_service.service;

import com.example.user_service.consumers.event.UserRegisteredEvent;
import com.example.user_service.dto.UserDTO;
import com.example.user_service.dto.UserRequestDTO;

import java.util.List;

public interface IUserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserByID(String id);
    UserDTO getOwnProfile(String username);
    UserDTO updateUser(String id, UserRequestDTO userRequestDTO);
    void deleteUser(String id);
    void createFromEvent(UserRegisteredEvent registeredEvent);
}
