--Insert role
INSERT INTO role (name) VALUES ('ROLE_USER');

--Insert users
INSERT INTO user (username, password, email, enabled, role_id) VALUES ('user1', 'password', 'user1@test.com', true, 1);
INSERT INTO user (username, password, email, enabled, role_id) VALUES ('user2', 'password', 'user2@test.com', true, 1);

