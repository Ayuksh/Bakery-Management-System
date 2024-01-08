# Database code 

create database bakerydb ;
use bakerydb ;
***
create table user_info 
(
	user_id int auto_increment primary key ,
    user_name varchar(20) not null ,
    user_pass varchar(20) not null ,
    user_role varchar(10) not null 
);

***
create table order_info 
(
	order_id int primary key auto_increment , 
    user_id int ,
    order_amt double check (order_amt >= 0),
    foreign key (user_id) references user_info(user_id)
);
***

create table product_info
(
		product_id int primary key auto_increment ,
        product_name varchar(20) ,
        product_price double ,
        product_qty int check (product_qty>=0)
);
***
create table order_product
(
	order_id int ,
    product_id int ,
    product_qty int ,
    foreign key (order_id) references order_info(order_id),
    foreign key (product_id) references product_info(product_id)
);

***
insert into user_info(user_name , user_pass , user_role) values 
('abcdef' , '123456' , 'admin'),
('jklmnop' , '1234567' , 'customer');


***
insert into product_info(product_name , product_price , product_qty)
values 
('CHOCOLATE CAKE' , 250 , 4),
('BREAD' , 30 , 12 ),
('PASTRIES' , 60 , 10 ),
('COOKIES' , 30 , 30 );
***
select * from product_info ;
select * from user_info; 
select * from order_product ;
select * from order_info ;
***
SELECT 
    u.user_name, p.product_name, op.product_qty
FROM
    user_info u
        INNER JOIN
    order_info o
        INNER JOIN
    order_product op
        INNER JOIN
    product_info p 
ON 
		u.user_id = o.user_id
        AND o.order_id = op.order_id
        AND op.product_id = p.product_id;
***
delimiter //
create procedure insertUser(usrId int )
begin
	insert into order_info(user_id) values (usrId) ;
	select order_id from order_info where user_id = usrId limit 1 ;
end //
delimiter ;
***
delimiter //
create procedure placeOrder(ordId int , pName varchar(20) , ordQty int)
begin
	declare pId int ;
    select product_id into pId from product_info where product_name = pName ;
	insert into order_product values (ordId , pId , ordQty);
end //
delimiter ;
