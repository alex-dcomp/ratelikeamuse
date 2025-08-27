// Define o pacote onde esta classe está localizada, facilitando a organização e o gerenciamento da aplicação.
package ratelikeamuse.controller;

// Importações necessárias para o funcionamento do controlador Spring MVC.
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ratelikeamuse.service.UserService;

/**
 * Classe responsável por lidar com as requisições relacionadas à autenticação
 * de usuários por processo de registro (cadastro).
 * Esta classe é um componente do padrão MVC (Model-View-Controller),
 * atuando como o Controller que coordena as interações entre a View e a Service.
 */
@Controller
public class AuthController {

	// Dependência do serviço responsável pela lógica de negócios relacionada ao usuário.
	private final UserService userService;

	// Construtor que permite a injeção do UserService por mecanismo de Inversão de Controle (IoC) do Spring.
	public AuthController(UserService userService) {
    	this.userService = userService;
	}

	/**
 	* Mapeia requisições HTTP GET para o caminho "/register".
 	* Retorna a view correspondente à página de registro de usuários.
 	*
 	* @return nome da view (template) de registro
 	*/
	@GetMapping("/register")
	public String showRegistrationForm() {
    	return "register"; // Nome do template HTML (ex.: register.html)
	}

	/**
 	* Mapeia requisições HTTP POST para o caminho "/register".
 	* Recebe os dados de registro do usuário, delega a lógica de criação ao UserService
 	* e trata possíveis exceções relacionadas ao processo de registro.
 	*
 	* @param username nome de usuário fornecido no formulário
 	* @param password senha fornecida no formulário
 	* @param model objeto usado para repassar dados à view
 	* @param redirectAttributes usado para enviar mensagens flash após redirecionamento
 	* @return redireciona para a página principal em caso de sucesso ou retorna à página de registro em caso de erro
 	*/
	@PostMapping("/register")
	public String registerUser(
        	@RequestParam String username,
        	@RequestParam String password,
        	Model model,
        	RedirectAttributes redirectAttributes) {
    	try {
        	// Chama o serviço para registrar o novo usuário
        	userService.registerUser(username, password);

        	// Adiciona uma mensagem flash de sucesso que será exibida após o redirecionamento
        	redirectAttributes.addFlashAttribute("success", "Usuário registrado com sucesso! Por favor, faça o login.");

// Redireciona para a página inicial ("/") após registro bem-sucedido
        	return "redirect:/";
    	} catch (RuntimeException e) {
// Em caso de erro (ex: usuário já existente), adiciona a mensagem de erro à view
        	model.addAttribute("error", e.getMessage());

// Reenvia o nome de usuário para que o campo seja preenchido automaticamente no formulário
        	model.addAttribute("username", username);

// Retorna para a página de registro com os dados de erro
        	return "register";
    	}
	}

}

