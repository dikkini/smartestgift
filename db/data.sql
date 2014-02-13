INSERT INTO public.role (role) VALUES ('admin'), ('user');

INSERT INTO public.file_type (id, name, path) VALUES (1, 'USER_IMAGE', 'C:\\temp\\');


INSERT INTO public.users (uuid, firstname, lastname, middlename, birthdate, gender, address, addressvisible, profilevisible, cellphone, cellphonevisible, fileid) VALUES ('2c948594442a92ca01442a94222d0000', 'Артур', 'Карапетов', null, null, null, null, false, true, null, false, null);
INSERT INTO public.user_details (useruuid, username, password, enabled, roleid, accountnonexpired, credentialsnonexpired, accountnonlocked, registrationdate) VALUES ('2c948594442a92ca01442a94222d0000', 'dikkini@gmail.com', '94e2768be13e7b4840eb7ebca27cff6ff5a8445dc472b66fc1bdc595226dc8d40a3741f2aff33d57', true, 2, true, true, true, '2014-02-13 13:29:14');