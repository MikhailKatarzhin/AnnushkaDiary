INSERT INTO
    "Role" (id, name)
    VALUES
       (0, 'АДМИНИСТРАТОР'), (2, 'ПОЛЬЗОВАТЕЛЬ')
ON CONFLICT (id)
    DO UPDATE SET id = Excluded.id;

INSERT INTO
    "User" (id, email, name, hash_password)
VALUES
    (0, 'admin@mail.ru', 'admin', '$2a$10$YD/o4ka8TPfc2OYha2A3Bu2xUTJMyFHVYWR5Sl96YyrzNFD89W7Fe')
ON CONFLICT (id)
    DO NOTHING;

INSERT INTO
    "User_Role" (user_id, role_id)
VALUES
    (0, 0);