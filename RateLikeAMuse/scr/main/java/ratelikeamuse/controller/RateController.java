// Define o pacote onde o controlador está localizado.
package ratelikeamuse.controller;

// Importações necessárias para criar um controlador e mapear requisições POST
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ratelikeamuse.service.RateService;

/**
 * Controlador responsável por lidar com o envio de notas de filmes
 * 
 * Atua na camada de controle do padrão MVC, conectando a view com a lógica de negócios
 * relacionada ao sistema de avaliação de filmes.
 */
@Controller
public class RateController {

    // Dependência RateService, que contém a lógica de negócio para avaliações
    private final RateService rateService;

    // Construtor com injeção de dependência (boa prática para facilitar testes e manutenção)
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    /**
     * Mapeia requisições HTTP POST para o caminho "/rate".
     * Recebe os parâmetros do formulário de avaliação e chama o serviço para salvar ou atualizar a nota do filme.
     *
     * @param movieId ID do filme que está sendo avaliado
     * @param userId ID do usuário que está fazendo a avaliação
     * @param score Nota atribuída ao filme 
     * @return redireciona para a listagem de filmes após salvar a avaliação
     */
    @PostMapping("/rate")
    public String saveRating(@RequestParam Long movieId,
                             @RequestParam Long userId,
                             @RequestParam int score) {

        // Salva ou atualiza a nota do filme para o usuário específico
        rateService.saveOrUpdateRate(userId, movieId, score);

        // Redireciona o usuário para a página de listagem de filmes
        return "redirect:/movies";
    }
}
