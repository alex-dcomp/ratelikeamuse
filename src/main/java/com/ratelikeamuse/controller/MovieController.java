package ratelikeamuse.controller;

import ratelikeamuse.entity.Movie;
import ratelikeamuse.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/movies") 
  // Define o prefixo comum para todas as rotas deste controller
public class MovieController {

	private final MovieService movieService;

	// Dependência por construtor 
	public MovieController(MovieService movieService) {
    	this.movieService = movieService;
	}

	// Lista todos os filmes
	@GetMapping
	public String listMovies(Model model) {
    	model.addAttribute("movies", movieService.getAllMovies()); // Adiciona a lista de filmes ao modelo
    	return "movies/list"; // Retorna a view de listagem de filmes 
	}

	// Exibe o formulário para criação de um novo filme
	@GetMapping("/new")
	public String showCreateForm() {
    	return "movies/form"; // View reutilizada tanto para criar quanto para editar
	}

	// Processa o envio do formulário de criação de filme
	@PostMapping("/save")
	public String saveMovie(
    	@RequestParam String nome,
    	@RequestParam String diretor,
    	Model model) {
    	try {
			// Cria um novo objeto Movie e define os atributos recebidos do formulário
			Movie movie = new Movie();
			movie.setNome(nome);
			movie.setDiretor(diretor);

        	// Salva o filme usando o serviço
        	movieService.saveMovie(movie);

        	// Redireciona para a listagem com mensagem de sucesso
        	return "redirect:/movies?success";
    	} catch (RuntimeException e) {
        	// Em caso de erro, retorna para o formulário com os dados preenchidos e a mensagem de erro
        	model.addAttribute("error", e.getMessage());
        	model.addAttribute("nome", nome);
        	model.addAttribute("diretor", diretor);
        	return "movies/form";
    	}
	}

	// Exibe o formulário de edição para um filme existente
	@GetMapping("/edit")
	public String showEditForm(@RequestParam Long id, Model model) {
    	Optional<Movie> movieOpt = movieService.getMovieById(id); 

    	if (movieOpt.isPresent()) {
        	Movie movie = movieOpt.get();
        	model.addAttribute("movie", movie); 
        // Adiciona o filme ao modelo
        	model.addAttribute("nome", movie.getNome()); 
        	model.addAttribute("diretor", movie.getDiretor());
        	return "movies/form"; 
        // Usa a mesma view do formulário de criação
    	} else {
        	// Se o filme não for encontrado, redireciona com uma mensagem de erro
        	return "redirect:/movies?error=notfound";
    	}
	}

	// Processa o envio do formulário de edição de filme
	@PostMapping("/edit")
	public String editMovie(
    	@RequestParam Long id,
    	@RequestParam String nome,
    	@RequestParam String diretor,
    	Model model) {
    	try {
        	// Cria um novo objeto Movie com os dados atualizados (incluindo o ID)
        	Movie movie = new Movie();
        	movie.setId(id);
        	movie.setNome(nome);
        	movie.setDiretor(diretor);

        	// Salva novamente (sobrescreve o existente)
        	movieService.saveMovie(movie);

        	return "redirect:/movies?success";
    	} catch (RuntimeException e) {
        	// Em caso de erro, retorna ao formulário com os dados e erro
        	model.addAttribute("error", e.getMessage());
        	model.addAttribute("id", id);
        	model.addAttribute("nome", nome);
        	model.addAttribute("diretor", diretor);
        	return "movies/form";
    	}
	}

	// Exclui um filme a partir do ID
	@GetMapping("/delete")
	public String deleteMovie(@RequestParam Long id) {
    	movieService.deleteMovie(id); // Chama o serviço para
      return "redirect:/movies?deleted";
	}
}

