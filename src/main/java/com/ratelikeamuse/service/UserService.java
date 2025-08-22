package RateLikeAMuse.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import RateLikeAMuse.entity.User;
import RateLikeAMuse.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    //Esta função recebe um usuário e senha, após isso ela verifica se o usuário já existe, caso não exista ela criptografa a senha e cria o usuário.
    public User registerUser(String username, String password) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username já existente");
        }
        User user = new User(username, passwordEncoder.encode(password));
        return userRepository.save(user); 
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
