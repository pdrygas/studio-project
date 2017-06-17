INSERT INTO users(id, username, password, token)
VALUES (0, 'user', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 'secret_token'),
       (1, 'other', 'other', 'other_token'),
       (2, 'other2', 'other', 'other2_token');

INSERT INTO categories(title, user_id)
VALUES ('cat1', 0),
       ('cat2', 0),
       ('other1_cat', 1);

INSERT INTO resources(title, content, user_id)
VALUES ('some title', 'lorem ipsum....', 0),
       (null, 'some text', 0),
       ('some resource', 'dsadadasdsss a sda d ad dsa ds', 1);
