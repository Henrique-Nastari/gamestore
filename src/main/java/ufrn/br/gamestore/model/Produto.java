package ufrn.br.gamestore.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity // Anotação que informa ao JPA que esta classe é uma entidade do banco de dados
@Table(name = "produtos") // Define o nome da tabela no banco de dados
@Data // Anotação do Lombok para gerar automaticamente getters, setters, toString, etc.
@NoArgsConstructor // Lombok: cria um construtor sem argumentos
@AllArgsConstructor // Lombok: cria um construtor com todos os argumentos
public class Produto {

    @Id // Define que este atributo é a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define que o valor do ID será gerado pelo banco de dados (autoincremento)
    private Long id;

    @NotBlank(message = "O nome do produto não pode ser vazio ou nulo.")
    @Size(min = 3, max = 255, message = "O nome do produto deve ter entre 3 e 255 caracteres.")
    @Column(nullable = false, length = 255) // Restrição a nível de banco de dados
    private String nome;

    @NotBlank(message = "A descrição não pode ser vazia ou nula.")
    @Size(max = 1000, message = "A descrição não pode exceder 1000 caracteres.")
    @Column(nullable = false, length = 1000)
    private String descricao;

    @NotNull(message = "O preço não pode ser nulo.")
    @Positive(message = "O preço deve ser um valor positivo.")
    @Column(nullable = false, precision = 10, scale = 2) // Precisão de 10 dígitos, com 2 casas decimais
    private BigDecimal preco;

    @NotBlank(message = "A categoria não pode ser vazia.")
    @Column(nullable = false)
    private String categoria; // Ex: "Console", "Notebook Gamer", "Jogo PS5"

    @NotNull(message = "A quantidade em estoque não pode ser nula.")
    @PositiveOrZero(message = "O estoque não pode ser um número negativo.")
    @Column(nullable = false)
    private Integer estoque;

    @NotBlank(message = "A URL da imagem é obrigatória.")
    @Column(nullable = false, name = "image_url")
    private String imageUrl;

    @Column(name = "is_deleted")
    private LocalDateTime isDeleted; // Padrão 'false'. Usado para soft delete (remoção lógica)
}
