INSERT INTO users(id, username, password, token)
VALUES (0, 'user', 'user', 'secret_token'),
       (1, 'other', 'other', 'other_token'),
       (2, 'other2', 'other', 'other2_token');

INSERT INTO resources(title, content, user_id)
VALUES ('some title', 'lorem ipsum....', 0),
       (null, 'some text', 0),
       ('some resource', 'dsadadasdsss a sda d ad dsa ds', 1);
