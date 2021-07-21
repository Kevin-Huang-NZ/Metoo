create database ts charset utf8mb4 collate utf8mb4_general_ci;
create user tsuser identified by '123456';
grant all privileges on ts.* to 'tsuser'@'%';
flush privileges;