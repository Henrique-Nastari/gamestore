package ufrn.br.gamestore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ufrn.br.gamestore.model.Produto;
import ufrn.br.gamestore.repository.ProdutoRepository;
import ufrn.br.gamestore.service.CarrinhoService;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

@Controller
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @Autowired
    private ProdutoRepository produtoRepository;

    @GetMapping("/adicionarCarrinho")
    public String adicionarCarrinho(@RequestParam("id") Long idProduto) {
        Optional<Produto> produtoOpt = produtoRepository.findById(idProduto);
        produtoOpt.ifPresent(carrinhoService::adicionarItem);
        return "redirect:/index";
    }

    /**
     * Rota GET para exibir a página do carrinho de compras.
     * Se o carrinho estiver vazio, redireciona para o index com uma mensagem.
     */
    @GetMapping("/verCarrinho")
    public String verCarrinho(Model model, RedirectAttributes redirectAttributes) {

        if (carrinhoService.getItens().isEmpty()) {
            // Adicionamos a mensagem E o tipo dela
            redirectAttributes.addFlashAttribute("mensagem", "Não existem itens no carrinho.");
            redirectAttributes.addFlashAttribute("tipoMensagem", "warning"); // "warning" para um alerta amarelo
            return "redirect:/index";
        }

        model.addAttribute("itensCarrinho", carrinhoService.getItens());
        model.addAttribute("valorTotal", carrinhoService.getValorTotal());

        return "carrinho";
    }

    /**
     * Rota placeholder para a finalização da compra.
     */
    @GetMapping("/finalizarCompra")
    public String finalizarCompra(HttpSession session, RedirectAttributes redirectAttributes) {
        // Invalida a sessão HTTP atual. Isso destrói o CarrinhoService da sessão.
        session.invalidate();

        // Adiciona uma mensagem de sucesso para ser exibida na página inicial
        redirectAttributes.addFlashAttribute("mensagem", "Compra finalizada com sucesso! Seu carrinho foi esvaziado.");
        redirectAttributes.addFlashAttribute("tipoMensagem", "success");

        // Redireciona para a página inicial
        return "redirect:/index";
    }
}
