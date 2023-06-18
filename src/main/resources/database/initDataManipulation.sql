INSERT INTO
    "Role" (id, name)
    VALUES
       (0, 'АДМИНИСТРАТОР'), (2, 'ПОЛЬЗОВАТЕЛЬ')
ON CONFLICT (id)
    DO UPDATE SET id = Excluded.id;

INSERT INTO
    "User" (id, email, name, hash_password)
VALUES
    (0, 'admin@mail.ru', 'admin', '$2a$10$Gx5MidAbeq4fO0V2IKdbVOqoTPWlaqM9DevsGzys/BeGL5SHP36FO')
ON CONFLICT (id)
    DO NOTHING;

INSERT INTO
    "User_Role" (user_id, role_id)
VALUES
    (0, 0);

INSERT INTO
    "Diary" (id, user_id)
VALUES
    (0, 0);