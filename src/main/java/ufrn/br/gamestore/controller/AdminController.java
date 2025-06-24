package ufrn.br.gamestore.controller;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ufrn.br.gamestore.model.Produto;
import ufrn.br.gamestore.repository.ProdutoRepository;
import ufrn.br.gamestore.service.ProdutoService;

import java.util.List;
import java.util.Random;

@Controller
public class AdminController {

    private static final Logger log = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/admin")
    public String adminPage(Model model) {
        List<Produto> todosOsProdutos = produtoRepository.findAll();
        model.addAttribute("produtos", todosOsProdutos);
        return "admin";
    }

    @GetMapping("/deletar")
    public String deletarProduto(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        // A lógica de negócio continua no service, como já estava
        produtoService.deleteLogico(id);
        // 1. Adicionamos a mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Produto removido com sucesso!");
        // 2. Mudamos o redirecionamento para "/index"
        return "redirect:/index";
    }

    @GetMapping("/restaurar")
    public String restaurarProduto(@RequestParam Long id, RedirectAttributes redirectAttributes) {
        // A lógica de negócio continua no service
        produtoService.restaurar(id);
        // 1. Adicionamos a mensagem de sucesso
        redirectAttributes.addFlashAttribute("mensagem", "Produto restaurado com sucesso!");
        // 2. Mudamos o redirecionamento para "/index"
        return "redirect:/index";
    }

    @GetMapping("/cadastro")
    public String paginaCadastro(Model model) {
        Produto produto = new Produto();
        produto.setImageUrl("placeholder_para_validacao");
        model.addAttribute("produto", produto);
        return "cadastro";
    }

    @GetMapping("/editar/{id}")
    public String paginaEditar(@PathVariable Long id, Model model) {
        produtoRepository.findById(id).ifPresent(produto -> {
            model.addAttribute("produto", produto);
        });
        return "editar";
    }

    // ==========================================================
    //      MÉTODO /salvar UNIFICADO 
    // ==========================================================
    @PostMapping("/salvar")
    public String salvarProduto(@Valid @ModelAttribute("produto") Produto produto,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes) {

        log.info(">>> Iniciando o método salvarProduto...");

        // 1. Verifica se há erros de validação
        if (bindingResult.hasErrors()) {
            log.warn("!!! Erros de validação encontrados.");
            for (FieldError error : bindingResult.getFieldErrors()) {
                log.warn("--> Erro no campo '{}': {}", error.getField(), error.getDefaultMessage());
            }

            // 2. Se houver erros, verifica se é uma edição ou um cadastro para retornar à página certa
            if (produto.getId() != null) {
                log.warn("Retornando para o formulário de EDIÇÃO.");
                return "editar"; // Volta para o formulário de edição
            } else {
                log.warn("Retornando para o formulário de CADASTRO.");
                return "cadastro"; // Volta para o formulário de cadastro
            }
        }

        log.info(">>> Validação passou. Não há erros.");

        // 3. Diferencia entre CRIAÇÃO (ID nulo) e ATUALIZAÇÃO (ID existe)
        if (produto.getId() == null) {
            // É um novo produto, então atribuímos uma imagem aleatória
            log.info("É um novo produto. Atribuindo imagem aleatória...");
            List<String> imagensDisponiveis = List.of(
                    "/img/product01.png", "/img/product02.png", "/img/product03.png",
                    "/img/product04.png", "/img/product05.png", "/img/product06.png"
            );
            Random random = new Random();
            produto.setImageUrl(imagensDisponiveis.get(random.nextInt(imagensDisponiveis.size())));
            // Adiciona a mensagem de sucesso para cadastro
            redirectAttributes.addFlashAttribute("mensagem", "Produto cadastrado com sucesso!");
        } else {
            // É uma atualização, então não mexemos na imagem
            log.info("É uma atualização para o produto ID: {}", produto.getId());
            // Adiciona a mensagem de sucesso para atualização
            redirectAttributes.addFlashAttribute("mensagem", "Produto atualizado com sucesso!");
        }

        // 4. Salva no banco (funciona para criar e para atualizar)
        produtoRepository.save(produto);
        log.info("Produto salvo no banco com sucesso!");

        // 5. Redireciona para a página de administração
        return "redirect:/admin";
    }
}
