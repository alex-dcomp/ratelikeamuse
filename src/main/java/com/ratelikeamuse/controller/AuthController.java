package ratelikeamuse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ratelikeamuse.service.UserService;

@Controller
public class AuthController {

    private final UserService userService;

    // Dependência por construtor
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    // Mapeia requisições GET para "/login" e retorna a view de login
    @GetMapping("/login")
    public String login() {
        return "login";  
    }

    // Mapeia requisições GET para "/register" e retorna a view de registro
    @GetMapping("/register")
    public String showRegistrationForm() {
        return "register";  
    }

    // Mapeia requisições POST para "/register" e trata o envio do formulário de registro
    @PostMapping("/register")
    public String registerUser(
            @RequestParam String username,    
            @RequestParam String password,     
            Model model) {                     
        try {
            // Chama o serviço para registrar o novo usuário
            userService.registerUser(username, password);

            // Redireciona para a página de login após registro bem-sucedido
            return "redirect:/login?success";
        } catch (RuntimeException e) {
            // Em caso de erro, adiciona a mensagem no modelo para exibir na view
            model.addAttribute("error", e.getMessage());

            // Mantém o valor do campo username preenchido no formulário
            model.addAttribute("username", username);
            return "register";
        }
    }
}
