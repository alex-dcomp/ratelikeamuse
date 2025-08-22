package RateLikeAMuse.repository; // verificar e depois provavelmente corrigir esse package 

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.RateLikeAMuse.RateLikeAMuse.entity.Rate; // corrigir o package aqui também
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class RateRepository {

    private final File rateJsonFile;
    private final ObjectMapper objectMapper;
    private final AtomicLong idCounter;

    public RateRepository() throws IOException {
        this.objectMapper = new ObjectMapper();
        // Registra o módulo para que o ObjectMapper saiba como lidar com LocalDateTime
        this.objectMapper.registerModule(new JavaTimeModule());

        this.rateJsonFile = ResourceUtils.getFile("classpath:rate.json");

        List<Rate> rates = findAllInternal();
        long maxId = rates.stream()
                           .mapToLong(Rate::getId)
                           .max()
                           .orElse(0L);
        this.idCounter = new AtomicLong(maxId);
    }

    private List<Rate> findAllInternal() {
        try {
            return objectMapper.readValue(rateJsonFile, new TypeReference<List<Rate>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private synchronized void saveAllInternal(List<Rate> rates) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(rateJsonFile, rates);
    }

// esse metodo vai buscar as avaliações por filme
    public List<Rate> findByMovieId(Long movieId) {
        return findAllInternal().stream()
                .filter(rate -> rate.getMovieId().equals(movieId))
                .collect(Collectors.toList());
    }

// provavelmente o que mais vai ser usado, pq aqui eu busco a avaliação por usuário
    public List<Rate> findByUserId(Long userId) {
        return findAllInternal().stream()
                .filter(rate -> rate.getUserId().equals(userId))
                .collect(Collectors.toList());
    }
    
// salva/atualiza a avaliação
    public Rate save(Rate rate) {
        try {
            List<Rate> rates = new ArrayList<>(findAllInternal());
            if (rate.getId() == null) {
                rate.setId(idCounter.incrementAndGet());
            }
            rates.removeIf(r -> r.getId().equals(rate.getId()));
            rates.add(rate);
            saveAllInternal(rates);
            return rate;
        } catch (IOException e) {
            throw new RuntimeException("Falha ao salvar avaliação", e);
        }
    }

// apaga a aavaliação
    public void deleteById(Long id) {
        try {
            List<Rate> rates = new ArrayList<>(findAllInternal());
            boolean removed = rates.removeIf(r -> r.getId().equals(id));
            if (removed) {
                saveAllInternal(rates);
            }
        } catch (IOException e) {
            throw new RuntimeException("Falha ao deletar avaliação", e);
        }
    }
}
