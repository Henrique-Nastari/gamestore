package ufrn.br.gamestore.controller;

import ufrn.br.gamestore.model.Produto;
import ufrn.br.gamestore.repository.ProdutoRepository;
import ufrn.br.gamestore.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProdutoController {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CarrinhoService carrinhoService;

    @GetMapping({"/", "/index"}) // Respondendo tanto para a raiz quanto para /index
    public String verPaginaInicial(Model model) {
        // Busca no banco de dados apenas os produtos não deletados
        List<Produto> produtos = produtoRepository.findByIsDeletedIsNull();

        // Adiciona a lista de produtos ao modelo, para o Thymeleaf usar
        model.addAttribute("produtos", produtos);

        // Adiciona a quantidade de itens no carrinho ao modelo
        model.addAttribute("qtdCarrinho", carrinhoService.getQuantidadeItens());

        return "index"; // Renderiza a página index.html
    }
}
