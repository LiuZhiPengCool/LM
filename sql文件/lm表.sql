create database LM;

use LM;

create table Book (
	id int primary key not null auto_increment, -- 书号
    `book_name` varchar(255) not null, -- 书名
    `book_type` int not null, -- 0 科幻 1 现实 2 悬疑 ...
    author varchar(255) not null, -- 作者
    price int not null, -- 价格
    store int not null, -- 有多少本
    borrwo_number bigint not null, -- 借阅次数
    introduce varchar(255) not null -- 简介
)