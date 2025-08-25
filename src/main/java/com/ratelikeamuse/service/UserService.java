package RateLikeAMuse.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import RateLikeAMuse.entity.Funcao;
import jakarta.servlet.http.HttpSession;
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
    // Essa função recebe username, password e uma sessão, verefica se o username existe, confere se a senha coincide com a senha salva nos arquivos, armazena o estado "logado" e retorna usuário 
    public User loginUser(String username, String password, HttpSession session) {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    		if (passwordEncoder.matches(password, user.getPassword())) {
                session.setAttribute("user", user);
    			return user;
    		}
            else{
                throw new RuntimeException("Senha Incorreta");
            }
    }
    // Essa função recebe uma sessão, remove o usuário e invalida a sessão
   public void logoutUser(HttpSession session) {
       session.removeAttribute("user");
       session.invalidate();
    }
}
