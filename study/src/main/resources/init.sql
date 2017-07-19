CREATE TABLE `lable` (
  `lid` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) DEFAULT NULL,
  `id` varchar(32) DEFAULT NULL,
  `pid` varchar(32) DEFAULT NULL,
  `usingFlag` varchar(256) DEFAULT NULL,
  `scene` varchar(128) DEFAULT NULL,
  `value` varchar(256) DEFAULT NULL,
  `type` varchar(8) DEFAULT NULL,
  `displayName` varchar(256) DEFAULT NULL,
  PRIMARY KEY (`lid`)
) ENGINE=InnoDB AUTO_INCREMENT=362 DEFAULT CHARSET=utf8;



CREATE TABLE `sys_user` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;


