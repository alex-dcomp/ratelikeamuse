package RateLikeAMuse.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import RateLikeAMuse.entity.Funcao;

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
    public User registerUser(String username, String password, String email) {
        if (userRepository.existsByUsername(username)) {
            throw new RuntimeException("Username já existente");
        }
         if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email já existente");
        }
        User user = new User(null, username, email, passwordEncoder.encode(password), Funcao.USER);
        return userRepository.save(user); 
    }
    // Essa função recebe ussername e password, verefica se o username existe, confere se a senha coincide com a senha salva nos arquivos e retorna usuário
    public User loginUser(String username, String password) {
    	if(userRepository.existsByUsername(username)) {
    		User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    		if (passwordEncoder.matches(password, user.getPassword())) {
    			return user;
    		}
    	}
    	throw new RuntimeException("Usuário não encontrado");
    }
    
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }
}
