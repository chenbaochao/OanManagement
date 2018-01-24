INSERT INTO role (id, name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (2, 'ROLE_MANAGER');
INSERT INTO role (id, name) VALUES (3, 'ROLE_USER');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);