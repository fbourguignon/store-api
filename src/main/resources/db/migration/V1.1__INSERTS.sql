INSERT INTO public.tb_role (id, type) VALUES ('a7dc2bc3-7cb2-4d75-b3e8-bdd90d4225f7', 'ROLE_ADMIN');
INSERT INTO public.tb_role (id, type) VALUES ('cf1f8145-9fa2-4a57-9f8a-22b367ad110c', 'ROLE_USER');

INSERT INTO public.tb_user (id, last_name, mail, name, password) VALUES ('0dfd56c9-7758-4cc0-984f-5f1a906f728c', 'Wick', 'jhon@gmail.com', 'Jhon', '$2a$10$oYjYwYZ41S3qlcDoPn7oFuIbKDah1CKgPm.uE.5KRiDg4E9QNewUO');
INSERT INTO public.tb_user (id, last_name, mail, name, password) VALUES ('1e7992d7-ce01-4154-91f3-b0580d6ed40f', 'Parker', 'peter@gmail.com', 'Peter', '$2a$10$oYjYwYZ41S3qlcDoPn7oFuIbKDah1CKgPm.uE.5KRiDg4E9QNewUO');

INSERT INTO public.tb_user_role (tb_user_id, tb_role_id) VALUES ('0dfd56c9-7758-4cc0-984f-5f1a906f728c', 'a7dc2bc3-7cb2-4d75-b3e8-bdd90d4225f7');
INSERT INTO public.tb_user_role (tb_user_id, tb_role_id) VALUES ('1e7992d7-ce01-4154-91f3-b0580d6ed40f', 'cf1f8145-9fa2-4a57-9f8a-22b367ad110c');

INSERT INTO public.tb_product (id, description, name) VALUES ('aa3355b8-da98-478f-8a9a-aef10739e416','Product 1 description', 'Product 1');
INSERT INTO public.tb_product (id, description, name) VALUES ('9cb79ff1-93bb-4686-a2f5-ed1dc48790d1','Product 2 description', 'Product 2');
INSERT INTO public.tb_product (id, description, name) VALUES ('9545994a-5008-442f-bbc3-fb0c28a311e1','Product 3 description', 'Product 3');
INSERT INTO public.tb_product (id, description, name) VALUES ('734200b3-e5d7-4ad0-bae3-21b4df73af52','Product 4 description', 'Product 4');
INSERT INTO public.tb_product (id, description, name) VALUES ('5cbeb7cb-9e74-4f03-bb25-e5f9e4276ab7','Product 5 description', 'Product 5');
INSERT INTO public.tb_product (id, description, name) VALUES ('aa186749-95e1-4be1-af22-79edf0e25db3','Product 6 description', 'Product 6');
INSERT INTO public.tb_product (id, description, name) VALUES ('4500682d-2010-45b0-99af-fecd89af2452','Product 7 description', 'Product 7');
INSERT INTO public.tb_product (id, description, name) VALUES ('f3e46563-6ab9-474e-b172-102eb3451ae5','Product 8 description', 'Product 8');
INSERT INTO public.tb_product (id, description, name) VALUES ('77bca3da-a1cd-4787-82b5-3c64ce05213a','Product 9 description', 'Product 9');
INSERT INTO public.tb_product (id, description, name) VALUES ('00d1e1a2-8467-4a31-ae25-0a066a0a6710','Product 10 description', 'Product 10');
INSERT INTO public.tb_product (id, description, name) VALUES ('3e014bcc-6b19-4be3-887c-4e6eb19c9902','Product 11 description', 'Product 11');
INSERT INTO public.tb_product (id, description, name) VALUES ('5d551023-69c3-4274-9470-6377e3623663','Product 12 description', 'Product 12');
INSERT INTO public.tb_product (id, description, name) VALUES ('21879dec-344e-4812-b577-02b6b1f26b61','Product 13 description', 'Product 13');
INSERT INTO public.tb_product (id, description, name) VALUES ('31c93eb8-7493-450b-ae0c-87c8698fb3da','Product 14 description', 'Product 14');
INSERT INTO public.tb_product (id, description, name) VALUES ('6e587570-4bb4-41cb-9c2f-a1c0686130f1','Product 15 description', 'Product 15');