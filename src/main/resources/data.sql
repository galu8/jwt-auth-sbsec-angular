insert into c_user(name,username,password) values ('Samuel Paredes','admin','admin');
insert into c_user(name,username,password) values ('Jimmy Hendrix','user','user');

insert into role(name) values ('ADMIN');
insert into role(name) values ('USER');

insert into c_user_roles(user_id,role_id) values (1,1);
insert into c_user_roles(user_id,role_id) values (1,2);
insert into c_user_roles(user_id,role_id) values (2,2);