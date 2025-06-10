-- Limpa a tabela antes de inserir para evitar duplicatas ao reiniciar
DELETE FROM produtos;

-- Insere alguns produtos ativos (is_deleted é NULL)
INSERT INTO produtos (nome, descricao, preco, categoria, estoque, image_url, is_deleted) VALUES
    ('PlayStation 5', 'O mais novo console da Sony com SSD ultra-rápido.', 4499.99, 'Console', 15, '/img/PS5.png', NULL),
    ('Notebook Gamer Nitro 5', 'Notebook com RTX 3050 e 16GB RAM.', 5500.00, 'Notebook', 8, '/img/nitro5.webp', NULL),
    ('Jogo The Last of Us Part II', 'Aventura pós-apocalíptica exclusiva para PlayStation.', 199.50, 'Jogo', 30, '/img/thelast.png', NULL),
    ('Headset Gamer HyperX', 'Headset com som surround 7.1.', 450.00, 'Acessório', 50, '/img/headseth.webp', NULL);

-- Insere um produto "deletado" (com data em is_deleted)
INSERT INTO produtos (nome, descricao, preco, categoria, estoque, image_url, is_deleted) VALUES
    ('PlayStation 4', 'Console da geração anterior.', 2500.00, 'Console', 0, '/img/product05.png', '2025-01-10 10:00:00');