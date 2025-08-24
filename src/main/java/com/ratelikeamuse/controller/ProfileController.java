package ratelikeamuse.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ratelikeamuse.entity.User;
import ratelikeamuse.service.UserService;

@Controller
@RequestMapping("/profile") 
  // Todas as rotas desse controller começam com "/profile"
public class ProfileController {

	private final UserService userService;

	// Uso de dependência do UserService
	public ProfileController(UserService userService) {
		this.userService = userService;
	}

	// Exibe o perfil de um usuário com base no username fornecido
	@GetMapping
	public String showProfile(@RequestParam String username, Model model) {
		User user = userService.findByUsername(username); 
    // Busca o usuário pelo nome
		if (user != null) {
			model.addAttribute("user", user); // Adiciona o objeto à view
			model.addAttribute("username", user.getUsername()); // Também adiciona o username separadamente
			return "profile/view"; // Retorna a view de visualização do perfil
		} else {
			return "redirect:/?error=userNotFound"; // Redireciona com erro se não encontrar
		}
	}

	// Exibe o formulário de edição do perfil
	@GetMapping("/edit")
	public String showEditForm(@RequestParam String username, Model model) {
		User user = userService.findByUsername(username);
		if (user != null) {
			model.addAttribute("username", user.getUsername()); 
      // Preenche o campo com o username atual
			return "profile/form"; 
      // Retorna o formulário de edição
		} else {
			return "redirect:/profile?error=userNotFound"; 
      // Redireciona com erro
		}
	}

	// Processa o envio do formulário de edição de perfil
	@PostMapping("/edit")
	public String editProfile(
		@RequestParam String username,
		Model model) {
		try {
			User user = userService.findByUsername(username); 
      // Busca o usuário novamente
			if (user == null) {
				model.addAttribute("error", "Usuário não encontrado");
				return "profile/form";
			}

			// Chama o serviço para salvar as alterações (nenhuma edição real está sendo feita aqui)
			userService.saveUser(user);

			// Redireciona para a visualização do perfil com sucesso
			return "redirect:/profile?username=" + user.getUsername() + "&success";
		} catch (RuntimeException e) {
			model.addAttribute("error", e.getMessage());
			model.addAttribute("username", username);
			return "profile/form";
		}
	}
}
