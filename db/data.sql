
INSERT INTO role (role) VALUES ('admin'), ('user');
INSERT INTO "user" (uuid, login, passwordmd5, firstname, lastname, middlename, birthdate, registrationdate, roleId) VALUES ('f81d4fae-7dec-11d0-a765-00a0c91e6bf6', 'admin', '21232f297a57a5a743894a0e4a801fc3', 'artur', 'karapetov', 'grigorievich', '19990-11-11 00:00:00', '19990-11-11 00:00:00', 1);
INSERT INTO "user" (uuid, login, passwordmd5, firstname, lastname, middlename, birthdate, registrationdate, roleId) VALUES ('f81d4fae-7dec-11d0-a765-00a0c91e6bf5', 'user', 'ee11cbb19052e40b07aac0ca060c23ee', 'artur', 'karapetov', 'grigorievich', '19990-11-11 00:00:00', '19990-11-11 00:00:00', 2);