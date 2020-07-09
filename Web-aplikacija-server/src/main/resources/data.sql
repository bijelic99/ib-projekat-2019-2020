
INSERT INTO authorities(ID, NAME) VALUES (1, 'Regular');
INSERT INTO authorities(ID, NAME) VALUES (2, 'Admin');
--INSERT INTO authorities(NAME) VALUES ( 'Regular');
--INSERT INTO authorities(NAME) VALUES ( 'Admin');

INSERT INTO users(id, email, password, certificate, active, authority_id) VALUES (1, 'admin@mail.com', 'password', 'dadasd', 1, 2);
--INSERT INTO users(email, password, certificate, active, authority_id) VALUES ('user@mail.com', 'password', 'dadasd', 1, 2);
--INSERT INTO users(email, password, certificate, active, authority_id) VALUES ('user2@mail.com', 'password', 'dadasd', 0, 2);
