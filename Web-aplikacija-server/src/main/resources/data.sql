
INSERT INTO authorities(ID, NAME) VALUES (1, 'Regular');
INSERT INTO authorities(ID, NAME) VALUES (2, 'Admin');

INSERT INTO users(email, password, certificate, active, authority*) VALUES ('admin@mail.com', 'password', 'dadasd', 1, 1);
INSERT INTO users(email, password, certificate, active, authority) VALUES ('user@mail.com', 'password', 'dadasd', 1, 2);
INSERT INTO users(email, password, certificate, active, authority) VALUES ('user2@mail.com', 'password', 'dadasd', 0, 2);
*