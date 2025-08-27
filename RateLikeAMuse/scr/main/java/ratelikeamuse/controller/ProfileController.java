// Define o pacote onde o controlador está localizado
package ratelikeamuse.controller;

// Importações necessárias para manipulação de dados do usuário logado, listas, mapeamentos e controle web.
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ratelikeamuse.entity.Movie;
import ratelikeamuse.entity.Rate;
import ratelikeamuse.entity.User;
import ratelikeamuse.service.MovieService;
import ratelikeamuse.service.RateService;
import ratelikeamuse.service.UserService;

/**
 * Controlador responsável por exibir a página de perfil do usuário autenticado.
 * Exibe as informações do usuário e as avaliações (ratings) que ele realizou nos filmes.
 * 
 * Essa classe faz parte da camada de apresentação (Controller) da arquitetura MVC
 * e interage diretamente com os serviços de negócio (MovieInterface, UserService, RateService e MovieService).
 */
@Controller
public class ProfileController {

    // Dependências necessárias para recuperação dos dados do usuário, avaliações e filmes.
    private final UserService userService;
    private final RateService rateService;
    private final MovieService movieService;

    // Construtor das dependências
    public ProfileController(UserService userService, RateService rateService, MovieService movieService) {
        this.userService = userService;
        this.rateService = rateService;
        this.movieService = movieService;
    }

    /**
     * Mapeia a requisição HTTP GET para o caminho "/profile".
     * Este método exibe a página de perfil do usuário logado com suas informações 
     *
     * @param model objeto responsável por passar dados para a view
     * @param principal objeto que representa o usuário atualmente autenticado no Spring Security
     * @return a view "profile/view", ou redirecionamento para "/" se o usuário não estiver logado
     */
    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        // Se o usuário não estiver autenticado, redireciona para a página inicial
        if (principal == null) {
            return "redirect:/";
        }
        
        // Obtém o nome de usuário do usuário logado através da segurança
        String username = principal.getName();

        // Recupera os detalhes completos do usuário usando o UserService
        UserDetails userDetails = userService.loadUserByUsername(username);

        // Faz a conversão para a entidade User (assumindo que User implementa UserDetails)
        User user = (User) userDetails;

        // Recupera todas as avaliações feitas pelo usuário
        List<Rate> userRates = rateService.myRates(user.getId());

        // Cria um mapa (id -> filme) para facilitar a exibição das informações dos filmes avaliados
        Map<Long, Movie> movieMap = movieService.getAllMovies().stream()
                .collect(Collectors.toMap(Movie::getId, movie -> movie));

        // Adiciona os dados necessários à view
        model.addAttribute("user", user);
        model.addAttribute("rates", userRates);
        model.addAttribute("movieMap", movieMap);
        
        // Retorna a view de perfil do usuário
        return "profile/view";
    }

}
