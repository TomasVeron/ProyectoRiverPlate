INSERT INTO categorias
    (id_categoria, edad_desde, edad_hasta, nombre, valor_cuota)
SELECT 1, 6,18,'Grupo Familiar',500
WHERE
    NOT EXISTS (
        SELECT id_categoria FROM categorias WHERE id_categoria = 1
    );

INSERT INTO categorias
    (id_categoria, edad_desde, edad_hasta, nombre, valor_cuota)
SELECT 2, 18,100,'Individual',300
WHERE
    NOT EXISTS (
        SELECT id_categoria FROM categorias WHERE id_categoria = 2
    );

INSERT INTO usuarios
    (id_usuario, apellido, email, nombre, password,rol)
SELECT 99, 'admin','admin@admin','admin','$2a$10$EDpfGI64PyGS2swEPonP/uHntHpEWWAxzHTOfa3LuPAkP8LM64PQi',true
WHERE
    NOT EXISTS (
        SELECT id_usuario FROM usuarios WHERE id_usuario =99
    );