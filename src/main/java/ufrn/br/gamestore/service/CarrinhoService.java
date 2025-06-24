package ufrn.br.gamestore.service;

import ufrn.br.gamestore.model.Produto;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@SessionScope //Cada usuário terá sua própria instância deste serviço em sua sessão.
public class CarrinhoService {

    private final List<Produto> itens = new ArrayList<>();

    public void adicionarItem(Produto produto) {
        itens.add(produto);
    }

    public List<Produto> getItens() {
        return itens;
    }

    public int getQuantidadeItens() {
        return itens.size();
    }

    public void limpar() {
        itens.clear();
    }

    public BigDecimal getValorTotal() {
        return itens.stream()
                .map(Produto::getPreco) // Extrai o preço de cada produto
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Soma todos os preços
    }
}
