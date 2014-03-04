INSERT INTO public.role (role) VALUES ('admin'), ('user');

INSERT INTO public.auth_provider (id, name) VALUES (1, 'application');
INSERT INTO public.auth_provider (id, name) VALUES (2, 'facebook');


INSERT INTO public.users (uuid, username, first_name, last_name, middle_name, birth_date, gender, address, address_visible, profile_visible, cellphone, cellphone_visible, file_id) VALUES ('2c948594442a92ca01442a94222d0000', 'dikkini', 'Артур', 'Карапетов', null, null, null, null, false, true, null, false, null);
INSERT INTO public.user_details (user_uuid, username, password, email, enabled, social_id, auth_provider_id, role_id, accountnonexpired, credentialsnonexpired, accountnonlocked, registration_date) VALUES ('2c948594442a92ca01442a94222d0000', 'dikkini', '94e2768be13e7b4840eb7ebca27cff6ff5a8445dc472b66fc1bdc595226dc8d40a3741f2aff33d57', 'dikkini@gmail.com', true, null, 1, 2, true, true, true, '2014-02-13 13:29:14');

INSERT INTO public.users (uuid, username, first_name, last_name, middle_name, birth_date, gender, address, address_visible, profile_visible, cellphone, cellphone_visible, file_id) VALUES ('402881a0442c6d0501442c6e81820000', 'tv0roq', 'Алексей', '', null, null, null, null, false, true, null, false, null);
INSERT INTO public.user_details (user_uuid, username, password, email, enabled, social_id, auth_provider_id, role_id, accountnonexpired, credentialsnonexpired, accountnonlocked, registration_date) VALUES ('402881a0442c6d0501442c6e81820000', 'tv0roq', '6b2d868973c7c23055fb23a88d5472a7b36eefdfb263c7a2868ec4b52f584e98fe02f2e5915fa0d8', 'tbopor@yahoo.com', true, null, 1, 1, true, true, true, '2014-02-13 22:07:23');



INSERT INTO public.file_type (id, name, path) VALUES (1, 'USER_IMAGE', 'C:\\temp\\');
INSERT INTO public.file_type (id, name, path) VALUES (2, 'CATEGORY_IMAGE', 'C:\\temp\\');
INSERT INTO public.file_type (id, name, path) VALUES (3, 'GIFT_IMAGE', 'C:\\temp\\');

INSERT INTO public.file (id, name, size, type_id) VALUES (1, 'category_technology', '1', 2);
INSERT INTO public.file (id, name, size, type_id) VALUES (2, 'category_women', '1', 2);
INSERT INTO public.file (id, name, size, type_id) VALUES (3, 'category_men', '1', 2);
INSERT INTO public.file (id, name, size, type_id) VALUES (4, 'category_children', '1', 2);
INSERT INTO public.file (id, name, size, type_id) VALUES (5, 'category_big_price', '1', 2);
INSERT INTO public.file (id, name, size, type_id) VALUES (6, 'iphone4', '1', 3);
INSERT INTO public.file (id, name, size, type_id) VALUES (7, 'iphone4s', '1', 3);
INSERT INTO public.file (id, name, size, type_id) VALUES (8, 'iphone5c', '1', 3);
INSERT INTO public.file (id, name, size, type_id) VALUES (9, 'iphone5s', '1', 3);


INSERT INTO public.gift_category (id, code, name, description, file_id) VALUES (1, 'tech', 'Технологии', 'Технологические новинки и прочее барахло', 1);
INSERT INTO public.gift_category (id, code, name, description, file_id) VALUES (2, 'women', 'Для слабого пола', 'Женские штучк-дрючки', 2);
INSERT INTO public.gift_category (id, code, name, description, file_id) VALUES (3, 'man', 'Для сильного пола', 'Лучший мужской подарок', 3);
INSERT INTO public.gift_category (id, code, name, description, file_id) VALUES (4, 'children', 'Дети - цветы жизни', 'Цветы детям не надо дарить, но конструктор Lego - вполне!', 4);
INSERT INTO public.gift_category (id, code, name, description, file_id) VALUES (5, 'expensive', 'Дорого и бесценно', 'Подарок бесценнен, а дорогой подарок еще дороже', 5);


INSERT INTO public.gift (uuid, name, cost, description, add_date, category_id) VALUES ('2c948591412a325ac14g2a91333d0000', 'iPhone 4', 2000, 'Супер новмодный телефон - будь как все!', now(), 1);
INSERT INTO public.gift (uuid, name, cost, description, add_date, category_id) VALUES ('2c948591412a325ac15g2a91333d0000', 'iPhone 4s', 2000, 'Супер новмодный телефон - будь как все!', '2014-01-13 13:29:14', 1);
INSERT INTO public.gift (uuid, name, cost, description, add_date, category_id) VALUES ('2c948591412a325ac16g2a91333d0000', 'iPhone 5c', 2000, 'Супер новмодный телефон - будь как все!', '2013-02-13 13:29:14', 1);
INSERT INTO public.gift (uuid, name, cost, description, add_date, category_id) VALUES ('2c948591412a325ac17g2a91333d0000', 'iPhone 5s', 2000, 'Супер новмодный телефон - будь как все!', '2012-02-13 13:29:14', 1);


INSERT INTO public.gift_file (id, file_id, gift_uuid) VALUES (1, 6, '2c948591412a325ac14g2a91333d0000');
INSERT INTO public.gift_file (id, file_id, gift_uuid) VALUES (2, 7, '2c948591412a325ac15g2a91333d0000');
INSERT INTO public.gift_file (id, file_id, gift_uuid) VALUES (3, 8, '2c948591412a325ac16g2a91333d0000');
INSERT INTO public.gift_file (id, file_id, gift_uuid) VALUES (4, 9, '2c948591412a325ac17g2a91333d0000');
