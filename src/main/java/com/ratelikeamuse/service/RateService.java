package RateLikeAMuse.service;
import org.springframework.stereotype.Service;
import RateLikeAMuse.entity.Rate;
import java.util.List; 

import RateLikeAMuse.repository.RateRepository;

@Service
public class RateService {
	private final RateRepository rateRepository;
    
    public RateService(RateRepository rateRepository ) {
        this.rateRepository = rateRepository;
    }
	// Essa função recebe ID do usuário e filme e a avaliação e retorna o registro da avaliação
	public Rate registerRate(Long userId, Long movieId, int score) {
		Rate rate = new Rate(null, userId, movieId, score);
		return rateRepository.save(rate);
	}

  // Essa função recebe o ID do usuário e retorna uma Lista das avaliações do usuário
	public List<Rate> myRates (Long userId) {
		return rateRepository.findByUserId(userId);
    }

  // Essa função recebe o ID do filme, pega todas as avaliações do filme com o ID recebido e faz uma média
	public int movieRate(Long movieId) {
		List<Rate> movieRates = rateRepository.findByMovieId(movieId);
		int rates = 0;
		int i;
		for(i = 0; i < movieRates.size() ; i++) {
			rates += movieRates.get(i).getScore();
		}
		int averageRate = rates/movieRates.size();
		return averageRate;
	}
	public void deleteRate(Long id) {
		rateRepository.deleteById(id);
	}
}
