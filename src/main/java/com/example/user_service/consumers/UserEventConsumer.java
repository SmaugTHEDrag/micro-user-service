package com.example.user_service.consumers;

import com.example.user_service.consumers.event.UserRegisteredEvent;
import com.example.user_service.repository.IUserRepository;
import com.example.user_service.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserEventConsumer {

    private final IUserService userService;

    @RabbitListener(queues = "user.registered.queue")
    public void handleUserRegistered(UserRegisteredEvent event) {

        log.info("Received event: {}", event.getEmail());

        userService.createFromEvent(event);
    }
}
