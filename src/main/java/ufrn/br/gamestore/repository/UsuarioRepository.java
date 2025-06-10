package ufrn.br.gamestore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ufrn.br.gamestore.model.Usuario;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar um usuário pelo seu username
    Optional<Usuario> findByUsername(String username);
}