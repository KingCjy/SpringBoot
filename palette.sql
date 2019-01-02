create table `user` (
	`id` int(11) not null auto_increment,
	`email` varchar(320) not null,
	`password` varchar(100) not null,

	`phone` varchar(11) null,
	`age` int(4) null,
	`birth` varchar(8) null comment '생일 yyyymmdd',
	`gender` enum('M', 'F') null comment 'M: 남자, F: 여자',
	`ci` varchar(500) null comment '본인인증 ci값',

	primary key (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


