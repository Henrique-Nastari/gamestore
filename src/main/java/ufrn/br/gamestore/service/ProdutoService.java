package ufrn.br.gamestore.service;

import ufrn.br.gamestore.model.Produto;
import ufrn.br.gamestore.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    /**
     * Realiza a deleção lógica de um produto, definindo a data de deleção.
     * @param id O ID do produto a ser deletado.
     */
    public void deleteLogico(Long id) {
        // Busca o produto no banco de dados
        repository.findById(id).ifPresent(produto -> {
            // Define a data de deleção como o momento atual
            produto.setIsDeleted(LocalDateTime.now());
            // Salva a alteração no banco
            repository.save(produto);
        });
    }

    /**
     * Restaura um produto logicamente deletado, tornando seu campo de deleção nulo.
     * @param id O ID do produto a ser restaurado.
     */
    public void restaurar(Long id) {
        repository.findById(id).ifPresent(produto -> {
            // Define a data de deleção como nula
            produto.setIsDeleted(null);
            repository.save(produto);
        });
    }
}
