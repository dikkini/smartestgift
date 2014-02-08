INSERT INTO public.role (role) VALUES ('admin'), ('user');
INSERT INTO public.users (uuid, firstname, lastname, middlename, birthdate)
VALUES ('f81d4fae-7dec-11d0-a765-00a0c91e6bf6', 'Artur', 'Karapetov', '', '19990-11-11 00:00:00');
INSERT INTO public.user_details (useruuid, username, password, enabled, roleid, accountnonexpired, credentialsnonexpired, accountnonlocked, registrationdate)
VALUES ('f81d4fae-7dec-11d0-a765-00a0c91e6bf6', 'artur@sg.com', 'c9b5ecdf0b853aab02103542187ea208', TRUE, 2, TRUE, TRUE, TRUE,
        '1990-11-11 00:00:00');
INSERT INTO public.users (uuid, firstname, lastname, middlename, birthdate)
VALUES ('f81d4fae-7dec-11d0-a765-00a0c91e6bf5', 'Alexei', 'Emelyanov', '', '19990-11-11 00:00:00');
INSERT INTO public.user_details (useruuid, username, password, enabled, roleid, accountnonexpired, credentialsnonexpired, accountnonlocked, registrationdate)
VALUES ('f81d4fae-7dec-11d0-a765-00a0c91e6bf5', 'alexei@sg.com', '261dddbd9d81f6d8e5ea8ac96ab2cc9d', TRUE, 2, TRUE, TRUE, TRUE,
        '1990-11-11 00:00:00');
INSERT INTO public.users (uuid, firstname, lastname, middlename, birthdate)
VALUES ('f81d4fae-7dec-11d0-a765-00a0c91e6bf4', 'Admin', '', '', '19990-11-11 00:00:00');
INSERT INTO public.user_details (useruuid, username, password, enabled, roleid, accountnonexpired, credentialsnonexpired, accountnonlocked, registrationdate)
VALUES ('f81d4fae-7dec-11d0-a765-00a0c91e6bf4', 'admin@sg.com', '21232f297a57a5a743894a0e4a801fc3', TRUE, 1, TRUE, TRUE, TRUE,
        '1990-11-11 00:00:00');

INSERT INTO public.file_type (id, name, path) VALUES (1, 'USER_IMAGE', 'C:\\temp\\');
