// Define o pacote ao qual a classe pertence, promovendo organização no projeto.
package ratelikeamuse.controller;

// Importa a anotação @Controller para que esta classe seja reconhecida como um controlador pelo Spring MVC.
import org.springframework.stereotype.Controller;
// Importa a anotação @GetMapping para mapear requisições HTTP do tipo GET.
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador responsável por lidar com requisições para a página inicial da aplicação.
 * 
 * Esta classe faz parte da camada de apresentação (Controller) do padrão arquitetural MVC (Model-View-Controller).
 * Seu principal papel é mapear a rota principal ("/") da aplicação e retornar a view correspondente.
 */
@Controller
public class IndexController {

    /**
     * Mapeia requisições HTTP GET para a URL raiz ("/").
     * Ao acessar a raiz do site, o Spring irá retornar a página inicial, normalmente um formulário de login ou criação de conta.
     *
     * @return o nome da view (template) correspondente à página inicial (ex: index.html)
     */
    @GetMapping("/")
    public String showIndexPage() {
        return "index"; // Nome do template da página inicial
    }

}
