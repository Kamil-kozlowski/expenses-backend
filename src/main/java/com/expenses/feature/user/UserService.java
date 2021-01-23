package com.expenses.feature.user;

import com.expenses.core.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SecurityService securityService;
    private final PasswordEncoder passwordEncoder;

    public Optional<UserDTO> getUserByUsername(String username) {
        final Optional<UserDTO> userDTO = userRepository.findFirstByUsername(username)
                .map(user -> new UserDTO(user.getId(), user.getUsername()));
        userDTO.ifPresent(u -> isRequestingUer(u.getUsername()));
        return userDTO;
    }

    public void createUser(UserCreateDTO userCreateDTO) {
        this.userRepository.findFirstByUsername(userCreateDTO.getUsername()).ifPresent(user -> {
            throw new RuntimeException(String.format("Username taken: %s", user.getUsername()));
        });
        this.userRepository.save(new User(
                userCreateDTO.getUsername(),
                this.passwordEncoder.encode(userCreateDTO.getPassword())
        ));
    }

    private void isRequestingUer(String username) {
        if (!securityService.getCurrentUser().equals(username)) {
            throw new AccessDeniedException("Unauthorized");
        }
    }
}
