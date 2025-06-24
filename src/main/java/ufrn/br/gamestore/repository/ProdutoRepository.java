package ufrn.br.gamestore.repository;

import ufrn.br.gamestore.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    /**
     * Encontra todos os produtos que não foram deletados logicamente.
     * O Spring Data JPA cria a query: "SELECT p FROM Produto p WHERE p.isDeleted IS NULL"
     * @return Uma lista de produtos ativos.
     */
    List<Produto> findByIsDeletedIsNull();

}
