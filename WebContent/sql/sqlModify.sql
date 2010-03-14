2010-03-14 售后部分修改内容

--往仓库资料表中插入维修库的信息（好件库、坏件库）
INSERT INTO `storehouse`(id,name) VALUES ('WX00000001','好件库'),('WX00000002','坏件库');

-- 售后库存表
DROP TABLE IF EXISTS `shkc`;
CREATE TABLE   `shkc` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `product_id` varchar(50) default NULL,
  `product_xh` varchar(50) default NULL,
  `product_name` varchar(200) default NULL,
  `qz_serial_num` varchar(500) default NULL,
  `state` varchar(20) default NULL,
  `day_num` int(11) unsigned default '1',  
  `store_id` varchar(20) default NULL,  
  `nums` int(11) unsigned default '1',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- 接件单
DROP TABLE IF EXISTS  `jjd`;
CREATE TABLE   `jjd` (
  `id` varchar(50) NOT NULL,
  `sfd_id` varchar(50) default NULL,
  `client_name` varchar(30) default NULL,
  `lxdh` varchar(20) default NULL,
  `mobile` varchar(20) default NULL,
  `linkman` varchar(50) default NULL,
  `jjr` varchar(20) default NULL,
  `cjr` varchar(20) default NULL,
  `jj_date` varchar(20) default NULL,
  `cj_date` datetime default NULL,
  `state` varchar(20) default NULL,
  `ms` varchar(1000) default NULL,
  `mail` varchar(100) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--接件单产品信息
DROP TABLE IF EXISTS `jjd_product`;
CREATE TABLE  `jjd_product` (
  `id` int(10) NOT NULL auto_increment,
  `jjd_id` varchar(50) default NULL,
  `product_id` varchar(20) default NULL,
  `product_name` varchar(100) default NULL,
  `product_xh` varchar(100) default NULL,
  `qz_serial_num` varchar(500) default NULL,
  `remark` varchar(1000) default NULL,
  `nums` int(11) default '0',
  `store_id` varchar(20) default NULL,
  `storestate` varchar(20) default NULL,
  `cpfj` varchar(1000) default NULL,
  `fxts` int(11) default '0',  
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--报修单
DROP TABLE IF EXISTS  `bxd`;
CREATE TABLE   `bxd` (
  `id` varchar(50) NOT NULL,
  `bxdate` varchar(20) default NULL,
  `cjdate` datetime default NULL,
  `jsr` varchar(50) default NULL,
  `cjr` varchar(50) default NULL,
  `state` varchar(50) default NULL,  
  `bxcs` varchar(50) default NULL,
  `remark` varchar(1000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--报修单产品详细信息
DROP TABLE IF EXISTS  `bxd_product`;
CREATE TABLE   `bxd_product` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `bxd_id` varchar(50) default NULL,
  `product_id` varchar(50) default NULL,
  `product_name` varchar(200) default NULL, 
  `product_xh` varchar(200) default NULL,
  `qz_serial_num` varchar(500) default NULL,
  `product_remark` varchar(1000) default NULL,
  `store_id` varchar(20) default NULL,
  `storestate` varchar(20) default NULL,
  `cpfj` varchar(1000) default NULL,
  `sxts` int(11) default '0', 
  `nums` int(11) default '0',      
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--报修返还单
DROP TABLE IF EXISTS  `bxfhd`;
CREATE TABLE   `bxfhd` (
  `id` varchar(30) NOT NULL,
  `fh_date` varchar(20) default NULL,
  `cj_date` datetime default NULL,
  `jsr` varchar(50) default NULL,
  `cjr` varchar(50) default NULL,    
  `state` varchar(50) default NULL,
  `remark` varchar(1000) default NULL,
  `bxcs` varchar(50) default NULL, 
  `fkzh` varchar(20) default NULL,
  `ssje` double default '0',
  `hjje` double default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--报修返还单产品明细
DROP TABLE IF EXISTS  `bxfhd_product`;
CREATE TABLE   `bxfhd_product` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `bxfhd_id` varchar(50) default NULL,
  `product_id` varchar(50) default NULL,
  `product_name` varchar(50) default NULL,
  `product_xh` varchar(200) default NULL,
  `qz_serial_num` varchar(500) default NULL,
  `remark` varchar(1000) default NULL,
  `store_id` varchar(20) default NULL,
  `storestate` varchar(20) default NULL,
  `cpfj` varchar(1000) default NULL,
  `price` double default '0', 
  `totalmoney` double default '0',
  `nums` int(11) default '0',   
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--返还客户单
DROP TABLE IF EXISTS `fhkhd`;
CREATE TABLE `fhkhd` (
  `id` varchar(30) NOT NULL,
  `fh_date` varchar(20) default NULL,
  `cj_date` datetime default NULL,
  `jsr` varchar(50) default NULL,
  `cjr` varchar(50) default NULL,
  `state` varchar(50) default NULL,
  `client_id` varchar(50) default NULL,
  `skje` double default '0',
  `skzh` varchar(30) default NULL,
  `remark` varchar(1000) default NULL, 
  `lxr` varchar(50) default NULL, 
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--返还客户单产品明细
DROP TABLE IF EXISTS `fhkhd_product`;
CREATE TABLE  `fhkhd_product` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `fhkhd_id` varchar(50) default NULL,
  `product_id` varchar(20) default NULL,
  `product_name` varchar(200) default NULL,
  `product_xh` varchar(200) default NULL,
  `qz_serial_num` varchar(500) default NULL,
  `remark` varchar(1000) default NULL,
  `price` double default NULL,
  `totalmoney` double default NULL,
  `store_id` varchar(20) default NULL,
  `storestate` varchar(20) default NULL,
  `nums` int(11) default '0',
  `cpfj` varchar(1000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--换件单，当产品序列号发生变化时，使用
DROP TABLE IF EXISTS  `hjd`;
CREATE TABLE   `hjd` (
  `id` varchar(30) NOT NULL,
  `hj_date` varchar(20) default NULL,
  `cj_date` datetime default NULL,
  `jsr` varchar(50) default NULL,
  `cjr` varchar(50) default NULL,
  `state` varchar(50) default NULL,  
  `remark` varchar(1000) default NULL,  
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--换件单产品明细
DROP TABLE IF EXISTS  `hjd_product`;
CREATE TABLE   `hjd_product` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `hjd_id` varchar(30) default NULL,
  `product_id` varchar(20) default NULL,
  `product_name` varchar(200) default NULL,
  `product_xh` varchar(200) default NULL,
  `oqz_serial_num` varchar(500) default NULL,
  `nqz_serial_num` varchar(500) default NULL,
  `product_remark` varchar(1000) default NULL,  
    PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--报废单，当产品不能再修时，使用
DROP TABLE IF EXISTS  `bfd`;
CREATE TABLE `bfd` (
  `id` varchar(30) NOT NULL,
  `bf_date` varchar(20) default NULL,
  `cj_date` datetime default NULL,
  `jsr` varchar(50) default NULL,
  `cjr` varchar(50) default NULL,
  `state` varchar(50) default NULL,
  `remark` varchar(1000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--报废单产品明细
DROP TABLE IF EXISTS  `bfd_product`;
CREATE TABLE `bfd_product` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `bfd_id` varchar(30) default NULL,
  `product_id` varchar(20) default NULL,
  `product_name` varchar(200) default NULL,
  `product_xh` varchar(200) default NULL,
  `qz_serial_num` varchar(500) default NULL,
  `product_remark` varchar(1000) default NULL,
  `store_id` varchar(20) default NULL,
  `storestate` varchar(20) default NULL,
  `nums` int(11) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--移库出库单,从产品库移出，移入坏件库
DROP TABLE IF EXISTS  `ykck`;
CREATE TABLE   `ykck` (
  `id` varchar(20) NOT NULL,
  `ck_date` varchar(20) default NULL,
  `jsr` varchar(20) default NULL,
  `ck_store_id` varchar(20) default NULL,
  `czr` varchar(20) default NULL,
  `cz_date` datetime default NULL,
  `state` varchar(20) default NULL,
  `remark` varchar(1000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--移库出库单产品明细
DROP TABLE IF EXISTS  `ykck_product`;
CREATE TABLE   `ykck_product` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `ykck_id` varchar(20) default NULL,
  `product_id` varchar(20) default NULL,
  `product_name` varchar(200) default NULL,
  `product_xh` varchar(200) default NULL,
  `product_remark` varchar(500) default NULL,
  `qz_serial_num` varchar(4000) default NULL,
  `nums` int(10) default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
   
--移库入库单,从好件库移出，移入产品库
DROP TABLE IF EXISTS  `ykrk`;
CREATE TABLE   `ykrk` (
  `id` varchar(20) NOT NULL,
  `rk_date` varchar(20) default NULL,
  `jsr` varchar(20) default NULL,
  `rk_store_id` varchar(20) default NULL,
  `czr` varchar(20) default NULL,
  `cz_date` datetime default NULL,
  `state` varchar(20) default NULL,
  `remark` varchar(1000) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--移库入库单产品明细
DROP TABLE IF EXISTS  `ykrk_product`;
CREATE TABLE   `ykrk_product` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `ykrk_id` varchar(30) default NULL,
  `product_id` varchar(20) default NULL,
  `product_name` varchar(200) default NULL,
  `product_xh` varchar(200) default NULL,
  `qz_serial_num` varchar(500) default NULL,  
  `product_remark` varchar(1000) default NULL,      
  `nums` int(11) default '0', 
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE `cms_all_seq` ADD COLUMN `bfdid` INTEGER UNSIGNED DEFAULT 1 AFTER `zzdid`;
ALTER TABLE `cms_all_seq` ADD COLUMN `hjdid` INTEGER UNSIGNED DEFAULT 1 AFTER `bfdid`;

ALTER TABLE `wxcld_product` CHANGE COLUMN `product_serial_num` `qz_serial_num` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;

--维修入库单
DROP TABLE IF EXISTS `wxrkd`;
CREATE TABLE  `wxrkd` (
  `id` varchar(30) NOT NULL,
  `wxrk_date` varchar(20) default NULL,
  `cj_date` datetime default NULL,
  `jsr` varchar(50) default NULL,
  `cjr` varchar(50) default NULL,
  `state` varchar(50) default NULL,  
  `remark` varchar(1000) default NULL,  
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--维修入库单产品明细
DROP TABLE IF EXISTS `wxrkd_product`;
CREATE TABLE  `wxrkd_product` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `wxrkd_id` varchar(30) default NULL,
  `product_id` varchar(20) default NULL,
  `product_name` varchar(200) default NULL,
  `product_xh` varchar(200) default NULL,
  `qz_serial_num` varchar(500) default NULL,  
  `remark` varchar(1000) default NULL,  
  `store_id` varchar(20) default NULL,
  `storestate` varchar(20) default NULL,  
  `nums` int(11) default '0', 
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8; 

--备忘录
DROP TABLE IF EXISTS `bwl`;
CREATE TABLE  `bwl` (
  `id` varchar(50) NOT NULL default '',
  `title` varchar(200) default NULL,
  `content` text,
  `czr` varchar(20) default NULL,
  `cz_date` varchar(20) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--导入表 funcs column_funcs


2010-03-14 信息发布修改内容
--添加parent_id字段，支持查看状态下信息的回复
ALTER TABLE `xxfb_nbgg` ADD COLUMN `parent_id` VARCHAR(50) NOT NULL DEFAULT 0 AFTER `type`;
