// Define o pacote onde a classe está localizada.
package ratelikeamuse.controller;

// Importações necessárias para operações com listas, mapas, optional e funcionalidades do Spring.
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ratelikeamuse.entity.Movie;
import ratelikeamuse.service.MovieService;
import ratelikeamuse.service.RateService;

/**
 * Controlador responsável por gerenciar operações relacionadas aos filmes,
 *
 * Atua como a camada de "Controller" no padrão arquitetural MVC,
 * coordenando a interação entre a interface (views)  e a services.
 */
@Controller
@RequestMapping("/movies") // comum para todas as rotas definidas neste controlador onde filtra as ações seguintes
public class MovieController {

    private final MovieService movieService;
    private final RateService rateService;

    // Dependências por construtor (prática para testabilidade e imutabilidade)
    public MovieController(MovieService movieService, RateService rateService) {
        this.movieService = movieService;
        this.rateService = rateService;
    }

    /**
     * Lista todos os filmes cadastrados no sistema, juntamente com suas médias de avaliação.
     * 
     * @param model objeto usado para repassar dados à view
     * @return view "movies/list" com os filmes e suas médias
     */
    @GetMapping
    public String listMovies(Model model) {
        // Recupera todos os filmes do serviço
        List<Movie> movies = movieService.getAllMovies();
        
        // Cria um mapa onde a chave é o ID do filme e o valor é sua média de avaliação
        Map<Long, Double> averageRates = new HashMap<>();
        for (Movie movie : movies) {
            double averageRate = rateService.movieRate(movie.getId());
            averageRates.put(movie.getId(), averageRate);
        }

        // Adiciona os filmes e suas avaliações médias ao modelo para serem exibidos na view
        model.addAttribute("movies", movies);
        model.addAttribute("averageRates", averageRates);

        // Retorna a view de listagem
        return "movies/list";
    }

    /**
     * Exibe o formulário de criação de um novo filme.
     *
     * @param model objeto usado para enviar dados à view
     * @return view "movies/form" com um objeto Movie vazio
     */
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("movie", new Movie()); // Movie vazio para preencher o formulário
        return "movies/form";
    }

    /**
     * Salva um novo filme ou atualiza um existente com base nos dados do formulário.
     *
     * @param movie objeto Movie populado automaticamente pelo Spring a partir dos dados do formulário
     * @return redireciona para a listagem de filmes após o salvamento
     */
    @PostMapping("/save")
    public String saveMovie(@ModelAttribute Movie movie) {
        movieService.saveMovie(movie); // Lógica de criação ou atualização é tratada no service
        return "redirect:/movies";
    }

    /**
     * Exibe o formulário de edição para um filme específico, baseado em seu ID.
     *
     * @param id ID do filme a ser editado
     * @param model objeto usado para enviar dados à view
     * @param redirectAttributes utilizado para enviar mensagens após redirecionamentos
     * @return view "movies/form" com dados do filme, ou redirecionamento com erro se não encontrado
     */
    @GetMapping("/edit")
    public String showEditForm(@RequestParam Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Movie> movieOpt = movieService.getMovieById(id);

        if (movieOpt.isPresent()) {
            model.addAttribute("movie", movieOpt.get());
            return "movies/form"; // Reutiliza o mesmo formulário de criação
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Filme não encontrado!");
            return "redirect:/movies";
        }
    }

    /**
     * Remove um filme do sistema, com base no seu ID.
     *
     * @param id ID do filme a ser excluído
     * @param redirectAttributes permite o envio de mensagens flash para feedback ao usuário
     * @return redirecionamento para a listagem de filmes com mensagem de sucesso ou erro
     */
    @PostMapping("/delete")
    public String deleteMovie(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        try {
            movieService.deleteMovie(id);
            redirectAttributes.addFlashAttribute("successMessage", "Filme excluído com sucesso!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Erro ao excluir filme!");
        }
        return "redirect:/movies";
    }
}
