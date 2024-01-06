# Database code 

create database bakerydb ;
use bakerydb ;

create table user_info 
(
	user_id int auto_increment primary key ,
    user_name varchar(20) not null ,
    user_pass varchar(20) not null ,
    user_role varchar(10) not null 
);


create table order_info 
(
	order_id int primary key auto_increment , 
    user_id int ,
    order_amt double check (order_amt >= 0),
    foreign key (user_id) references user_info(user_id)
);


create table product_info
(
		product_id int primary key auto_increment ,
        product_name varchar(20) ,
        product_price double ,
        product_qty int check (product_qty>=0)
);

create table order_product
(
	order_id int ,
    product_id int ,
    product_qty int ,
    foreign key (order_id) references order_info(order_id),
    foreign key (product_id) references product_info(product_id)
);


insert into user_info(user_name , user_pass , user_role) values 
('abcdef' , '123456' , 'admin'),
('jklmnop' , '1234567' , 'customer');
