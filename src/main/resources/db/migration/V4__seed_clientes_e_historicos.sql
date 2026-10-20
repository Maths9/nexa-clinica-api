-- Seed de clientes iniciais
INSERT INTO clientes (nome, telefone, email, tipo, observacoes, ultima_visita) VALUES
('Ana Souza',      '(11) 98765-4321', 'ana@email.com',    'ATIVO',     'Prefere horário matinal', '2026-09-05 09:00:00'),
('Beatriz Lima',   '(11) 91234-5678', 'bea@email.com',    'ATIVO',     'Pele sensível',           '2026-09-03 14:00:00'),
('Carla Ferreira', '(11) 94444-3333', 'carla@email.com',  'POTENCIAL', 'Veio pelo Instagram',      NULL),
('Diana Rocha',    '(11) 93333-2222', 'diana@email.com',  'ATIVO',     'Tratamento anti-idade',   '2026-08-28 11:00:00'),
('Elaine Matos',   '(11) 92222-1111', 'elaine@email.com', 'INATIVO',   'Parou no inverno',        '2026-07-10 16:00:00'),
('Flávia Costa',   '(11) 95555-6666', 'flavia@email.com', 'ATIVO',     'Pacote 10 sessões',       '2026-09-06 10:30:00'),
('Gabi Alves',     '(11) 96666-7777', 'gabi@email.com',   'POTENCIAL', 'Indicação da Ana Souza',   NULL),
('Helena Dias',    '(11) 97777-8888', 'helena@email.com', 'ATIVO',     'Gestante adaptado',       '2026-09-01 15:00:00');

-- Seed de historico de atendimentos para os clientes
INSERT INTO historicos_cliente (cliente_id, procedimento_id, data, valor, observacoes) VALUES
(1, 1, '2026-09-05', 180.00, 'Sessão 8 de 10'),
(1, 1, '2026-08-22', 180.00, 'Sessão 7 de 10'),
(1, 3, '2026-08-08', 220.00, 'Primeira sessão nova metodologia'),
(2, 2, '2026-09-03', 150.00, 'Pele reagiu muito bem'),
(2, 2, '2026-08-03', 150.00, 'Extração completa'),
(4, 4, '2026-08-28', 200.00, 'Retorno agendado'),
(6, 3, '2026-09-06', 220.00, 'Resultado excelente'),
(8, 1, '2026-09-01', 180.00, 'Protocolo gestante suave');
