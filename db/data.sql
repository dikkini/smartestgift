INSERT INTO public.role(id, name) VALUES (1, 'admin');
INSERT INTO public.role(id, name) VALUES (2, 'user');

INSERT INTO public.currency(sign) VALUES ('₽');
INSERT INTO public.currency(sign) VALUES ('$');
INSERT INTO public.currency(sign) VALUES ('€');


INSERT INTO public.file(id, name, extension, mimetype, size) VALUES (0, 'unknown', 'png', 'test', 10);

INSERT INTO public.user(username, password, email, firstname, lastname, birthdate, gender, roleid, cellphone, enabled, accountnonexpired, accountnonlocked, credentialsnonexpired, registrationdate)
VALUES ('dikkini', 'dc429d7bebcf1352572acb5e8e1c5f75fce4807a92f86bd8e650dceacaf72413fabd26950740e728', 'dikkini@gmail.com', 'Artur', 'Karapetov', to_date('18.03.1990', 'DD.MM.YYYY'), true, 1, '89153304834', true, true, true, true, now());