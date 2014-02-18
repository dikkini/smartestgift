INSERT INTO public.role (role) VALUES ('admin'), ('user');

INSERT INTO public.file_type (id, name, path) VALUES (1, 'USER_IMAGE', 'C:\\temp\\');

INSERT INTO public.auth_provider (id, name) VALUES (1, 'application');
INSERT INTO public.auth_provider (id, name) VALUES (2, 'facebook');


INSERT INTO public.users (uuid, username, firstname, lastname, middlename, birthdate, gender, address, addressvisible, profilevisible, cellphone, cellphonevisible, fileid) VALUES ('2c948594442a92ca01442a94222d0000', 'dikkini', 'Артур', 'Карапетов', null, null, null, null, false, true, null, false, null);
INSERT INTO public.user_details (useruuid, username, password, email, enabled, socialId, authProviderId, roleid, accountnonexpired, credentialsnonexpired, accountnonlocked, registrationdate) VALUES ('2c948594442a92ca01442a94222d0000', 'dikkini', '94e2768be13e7b4840eb7ebca27cff6ff5a8445dc472b66fc1bdc595226dc8d40a3741f2aff33d57', 'dikkini@gmail.com', true, null, 1, 2, true, true, true, '2014-02-13 13:29:14');

INSERT INTO public.users (uuid, username, firstname, lastname, middlename, birthdate, gender, address, addressvisible, profilevisible, cellphone, cellphonevisible, fileid) VALUES ('402881a0442c6d0501442c6e81820000', 'tv0roq', 'Алексей', '', null, null, null, null, false, true, null, false, null);
INSERT INTO public.user_details (useruuid, username, password, email, enabled, socialId, authProviderId, roleid, accountnonexpired, credentialsnonexpired, accountnonlocked, registrationdate) VALUES ('402881a0442c6d0501442c6e81820000', 'tv0roq', '6b2d868973c7c23055fb23a88d5472a7b36eefdfb263c7a2868ec4b52f584e98fe02f2e5915fa0d8', 'tbopor@yahoo.com', true, null, 1, 1, true, true, true, '2014-02-13 22:07:23');