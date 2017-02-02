insert into custom_user(user_id,name,username,password) values (1,'John Doe','admin','admin');
insert into custom_user(user_id,name,username,password) values (2,'Liza Elizabeth','user','user');

insert into custom_user_roles(custom_user_userId,roles) values (1,'ADMIN');
insert into custom_user_roles(custom_user_userId,roles) values (1,'USER');
insert into custom_user_roles(custom_user_userId,roles) values (2,'USER');