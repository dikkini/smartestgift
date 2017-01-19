INSERT INTO public.role(id, name) VALUES (1, 'admin');
INSERT INTO public.role(id, name) VALUES (2, 'user');


INSERT INTO public.file(id, name, extension, mimetype, size) VALUES (0, 'unknown', 'png', 'test', 10);

INSERT INTO public.users(username, email, firstname, lastname, birthdate, gender, roleid, cellphone, enabled, accountnonexpired, accountnonlocked, credentialsnonexpired, registrationdate)
VALUES ('dikkini', 'dikkini@gmail.com', 'Artur', 'Karapetov', to_date('18.03.1990', 'DD.MM.YYYY'), true, 1, '89153304834', true, true, true, true, now());

INSERT INTO public.user_role(useruuid, roleid) VALUES ('4aba0f66-f517-4fad-be04-9384961118e9', 1)