package finalProject.user.service;


import finalProject.user.dto.request.UserCreateRequestDto;
import finalProject.user.dto.response.UserResponseDto;
import finalProject.user.entity.User;
import finalProject.user.mapper.UserMapper;
import finalProject.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    final private UserMapper userMapper;

    public UserResponseDto createUser(UserCreateRequestDto dto) {
        User user = userMapper.toUser(dto);

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Transactional
    public UserResponseDto getUserById(UUID userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return userMapper.toDto(user);
    }

    @Transactional
    public UserResponseDto getUserByEmail(String email){
        User user = (User) userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        return userMapper.toDto(user);
    }
}
