package ratelikeamuse.service;

import ratelikeamuse.entity.Movie;
import ratelikeamuse.repository.MovieRepository;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final MovieRepository movieRepository implements MovieInterface;
    
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }
    
    @PostConstruct
    public void initializeMovies() { // Inicializa todos os filmes indicados aos BigFives prêmios( Categorias principais do Oscar 2025) 
    
        movieRepository.save(new Movie(null, "Anora", "Comédia dramática, romance"));
        movieRepository.save(new Movie(null, "The Brutalist", "Drama, histórico"));
        movieRepository.save(new Movie(null, "A Complete Unknown", "Drama, biografia, musical"));
        movieRepository.save(new Movie(null, "Conclave", "Suspense, mistério, drama"));
        movieRepository.save(new Movie(null, "Dune: Part Two", "Ficção científica, aventura, ação"));
        movieRepository.save(new Movie(null, "Emilia Pérez", "Musical, comédia criminal, suspense"));
        movieRepository.save(new Movie(null, "I'm Still Here", "Drama"));
        movieRepository.save(new Movie(null, "Nickel Boys", "Drama histórico"));
        movieRepository.save(new Movie(null, "The Substance", "Terror corporal, suspense"));
        movieRepository.save(new Movie(null, "Wicked", "Fantasia, comédia musical"));
        movieRepository.save(new Movie(null, "Sing Sing", "Drama, prisão"));
        movieRepository.save(new Movie(null, "The Apprentice", "Cinebiografia, drama"));
        movieRepository.save(new Movie(null, "A Real Pain", "Comédia dramática"));
        movieRepository.save(new Movie(null, "September 5", "Drama histórico, suspense"));
    }  
    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    @Override
    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }
    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
    }
    @Override
    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
}
