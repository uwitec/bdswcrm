--2010-06-02销售统计增加货品销售执行汇总、客户销售执行汇总
--将销售订单汇总改为客户销售执行汇总
update funcs set func_name='客户销售执行汇总',func_ms='客户销售执行汇总'  where func_id='FC0047';
--增加货品销售执行汇总
INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0106','货品销售执行汇总','货品销售订单执行汇总','showHpxsddHzCondition.html','123.gif',12,'1','1');
INSERT INTO `column_funcs` VALUES ('001002','FC0106');


--售后服务单中的维修状态变为：已保存、提交咨询、提交派工
update sfd set wx_state='提交咨询' where wx_state='待处理' and flow='咨询';
update sfd set wx_state='提交派工' where wx_state='待处理' and flow='派工';
update sfd set wx_state='已保存' where state='已保存' and flow='';
update sfd set state='已提交' where wx_state<>'已保存';

2010-06-03增加会员管理
--菜单中增加会员管理
INSERT INTO `column_mng`(`id`,`name`,`parent_id`,`xh`,`img`,`yw_flag`) 
VALUES('011','会员管理','0','27','buy.gif','1'),('011001','会员卡管理','011',28,NULL,'1'),('011002','会员积分管理','011',29,NULL,'1');


INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0108','会员卡分类','会员卡分类','listHykfl.html','112.gif',1,'1','10');

INSERT INTO `column_funcs` VALUES ('011001','FC0108');

INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0113','会员卡制作','会员卡制作','listHykzz.html','129.gif',2,'1','10');

INSERT INTO `column_funcs` VALUES ('011001','FC0113');

INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0114','发卡管理','发卡管理','listHykfk.html','155.gif',3,'1','10');

INSERT INTO `column_funcs` VALUES ('011001','FC0114');

INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0109','会员卡档案','会员卡档案','listHykda.html','228.gif',4,'1','10');

INSERT INTO `column_funcs` VALUES ('011001','FC0109');


INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0107','积分规则设置','积分规则设置','listJfgz.html','229.gif',1,'1','10');

INSERT INTO `column_funcs` VALUES ('011002','FC0107');


--增加积分规则设置表
DROP TABLE IF EXISTS `jfgz`;
CREATE TABLE `jfgz` (
  `id` varchar(50) NOT NULL,  
  `jfff` varchar(200) default NULL,
  `xfje` int(5) default 0,
  `dyjf` int(2) default 0, 
  `czr` varchar(20) default NULL,
  `cz_date` datetime default NULL,   
  PRIMARY KEY  (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--增加会员卡分类表
DROP TABLE IF EXISTS `hykfl`;
CREATE TABLE `hykfl` (
  `id` varchar(50) NOT NULL,  
  `name` varchar(50) default NULL,
  `yhfs` varchar(50) default NULL,
  `zkl` double default 0, 
  `jffs` varchar(50) default NULL,
  `czyhl` double default 0, 
  `czr` varchar(20) default NULL,
  `cz_date` datetime default NULL,   
  PRIMARY KEY  (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--增加会员卡制作表
DROP TABLE IF EXISTS `hykzz`;
CREATE TABLE `hykzz` (
  `id` varchar(50) NOT NULL,  
  `card_type` varchar(10) default NULL,
  `dept` varchar(50) default NULL,
  `csjf` int(10) default 0, 
  `csmm` varchar(20) default NULL,
  `csje` double default 0, 
  `yxrq` varchar(30) default NULL,
  `sxrq` varchar(30) default NULL,
  `sfcz` varchar(10) default NULL,
  `hykh` varchar(30) default NULL,
  `ssfl` varchar(50) default NULL,
  `czr` varchar(20) default NULL,
  `cz_date` datetime default NULL,   
  PRIMARY KEY  (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--增加会员卡档案表
DROP TABLE IF EXISTS `hykda`;
CREATE TABLE `hykda` (
  `id` varchar(50) NOT NULL,  
  `hykh` varchar(30) default NULL,
  `state` varchar(10) default NULL,
  `sfty` varchar(5) default NULL,
  `hymc` varchar(50) default NULL,
  `lxrname` varchar(30) default NULL,  
  `lxdh`  varchar(30) default NULL,
  `mobile` varchar(30) default NULL,  
  `address`  varchar(30) default NULL,
  `sex`  varchar(10) default NULL,
  `mail` varchar(30) default NULL, 
  `gzdw` varchar(200) default NULL, 
  `birth` varchar(20) default NULL, 
  `sfzh` varchar(50) default NULL, 
  `ffjg` varchar(50) default NULL, 
  `fkrq` varchar(20) default NULL, 
  `fkjsr` varchar(20) default NULL, 
  `zkrq` varchar(20) default NULL, 
  `zkjsr` varchar(20) default NULL, 
  `hybh` varchar(20) default NULL,
  `fkbz` varchar(200) default NULL,
  `czr` varchar(20) default NULL,
  `cz_date` datetime default NULL,     
  PRIMARY KEY  (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


--增加会员管理模块的编号
ALTER TABLE `cms_all_seq` ADD COLUMN `jfgzid` INTEGER UNSIGNED DEFAULT 1 AFTER `cgfpdid`;
ALTER TABLE `cms_all_seq` ADD COLUMN `hykflid` INTEGER UNSIGNED DEFAULT 1 AFTER `jfgzid`;
ALTER TABLE `cms_all_seq` ADD COLUMN `hykzzid` INTEGER UNSIGNED DEFAULT 1 AFTER `hykflid`;
ALTER TABLE `cms_all_seq` ADD COLUMN `hykdaid` INTEGER UNSIGNED DEFAULT 1 AFTER `hykzzid`;


2010-06-17增加
--售后服务单添加khlx字段，保存客户类型的信息

ALTER TABLE `sfd` ADD COLUMN `khlx` VARCHAR(10) DEFAULT NULL AFTER `bxyy_ms`;


--修改积分规则表结构
ALTER TABLE `jfgz` MODIFY COLUMN `xfje` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
ALTER TABLE `jfgz` MODIFY COLUMN `dyjf` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;


--增加兑奖货品设置表
DROP TABLE IF EXISTS `djhpsz`;
CREATE TABLE `djhpsz` (
`product_id` varchar(45) NOT NULL,
`product_name` varchar(200) NOT NULL,
`product_xh` varchar(200) default NULL,
`dwjf` varchar(10) default NULL,
`czr` varchar(10) default NULL,
`cz_date` datetime default NULL,
PRIMARY KEY  (`product_id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--修改积分规则表结构
ALTER TABLE `jfgz` MODIFY COLUMN `xfje` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
ALTER TABLE `jfgz` MODIFY COLUMN `dyjf` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;

ALTER TABLE `cms_all_seq` ADD COLUMN `hykdjid` INTEGER UNSIGNED DEFAULT 1 AFTER `hykdaid`;

2010-07-06修改
--维修处理单增加解决方法的保存
ALTER TABLE `wxcld` ADD COLUMN `w_jjff` VARCHAR(1000) default NULL AFTER `w_address`;
--维修处理单（产品）增加新序列号
ALTER TABLE `wxcld_product` ADD COLUMN `n_product_serial_num` VARCHAR(200) default NULL AFTER `product_clfs`;


2010-07-24修改
--退货单添加退货类型
ALTER TABLE `thd` ADD COLUMN `yw_type` VARCHAR(5) DEFAULT 1 AFTER `fpxx`;



delete from column_funcs;
INSERT INTO `column_funcs` VALUES ('001001','FC0009');
INSERT INTO `column_funcs` VALUES ('001001','FC0010');
INSERT INTO `column_funcs` VALUES ('001001','FC0013');
INSERT INTO `column_funcs` VALUES ('001001','FC0020');
INSERT INTO `column_funcs` VALUES ('001001','FC0023');
INSERT INTO `column_funcs` VALUES ('001001','FC0033');
INSERT INTO `column_funcs` VALUES ('001001','FC0088');
INSERT INTO `column_funcs` VALUES ('001002','FC0036');
INSERT INTO `column_funcs` VALUES ('001002','FC0037');
INSERT INTO `column_funcs` VALUES ('001002','FC0038');
INSERT INTO `column_funcs` VALUES ('001002','FC0047');
INSERT INTO `column_funcs` VALUES ('001002','FC0048');
INSERT INTO `column_funcs` VALUES ('001002','FC0106');
INSERT INTO `column_funcs` VALUES ('001003','FC0050');
INSERT INTO `column_funcs` VALUES ('001003','FC0051');
INSERT INTO `column_funcs` VALUES ('001003','FC0065');
INSERT INTO `column_funcs` VALUES ('001003','FC0066');
INSERT INTO `column_funcs` VALUES ('001003','FC0075');
INSERT INTO `column_funcs` VALUES ('001003','FC0081');
INSERT INTO `column_funcs` VALUES ('001003','FC0082');
INSERT INTO `column_funcs` VALUES ('001003','FC9002');
INSERT INTO `column_funcs` VALUES ('001003','FC9004');
INSERT INTO `column_funcs` VALUES ('002001','FC0002');
INSERT INTO `column_funcs` VALUES ('002001','FC0012');
INSERT INTO `column_funcs` VALUES ('002001','FC0032');
INSERT INTO `column_funcs` VALUES ('002002','FC0052');
INSERT INTO `column_funcs` VALUES ('002002','FC0053');
INSERT INTO `column_funcs` VALUES ('002002','FC0207');
INSERT INTO `column_funcs` VALUES ('002002','FC0208');
INSERT INTO `column_funcs` VALUES ('003001','FC0004');
INSERT INTO `column_funcs` VALUES ('003001','FC0005');
INSERT INTO `column_funcs` VALUES ('003001','FC0007');
INSERT INTO `column_funcs` VALUES ('003001','FC0024');
INSERT INTO `column_funcs` VALUES ('003001','FC0025');
INSERT INTO `column_funcs` VALUES ('003001','FC0056');
INSERT INTO `column_funcs` VALUES ('003001','FC0060');
INSERT INTO `column_funcs` VALUES ('003001','FC9903');
INSERT INTO `column_funcs` VALUES ('003001','FC9908');
INSERT INTO `column_funcs` VALUES ('003002','FC0039');
INSERT INTO `column_funcs` VALUES ('003002','FC0046');
INSERT INTO `column_funcs` VALUES ('003002','FC0076');
INSERT INTO `column_funcs` VALUES ('003002','FC0077');
INSERT INTO `column_funcs` VALUES ('003002','FC0086');
INSERT INTO `column_funcs` VALUES ('003002','FC0099');
INSERT INTO `column_funcs` VALUES ('003002','FC0100');
INSERT INTO `column_funcs` VALUES ('004001','FC0027');
INSERT INTO `column_funcs` VALUES ('004001','FC0028');
INSERT INTO `column_funcs` VALUES ('004001','FC0029');
INSERT INTO `column_funcs` VALUES ('004001','FC0031');
INSERT INTO `column_funcs` VALUES ('004001','FC0073');
INSERT INTO `column_funcs` VALUES ('004001','FC9905');
INSERT INTO `column_funcs` VALUES ('004002','FC0040');
INSERT INTO `column_funcs` VALUES ('004002','FC0043');
INSERT INTO `column_funcs` VALUES ('004002','FC0049');
INSERT INTO `column_funcs` VALUES ('004002','FC0067');
INSERT INTO `column_funcs` VALUES ('004003','FC0035');
INSERT INTO `column_funcs` VALUES ('004003','FC0041');
INSERT INTO `column_funcs` VALUES ('004003','FC0044');
INSERT INTO `column_funcs` VALUES ('004003','FC0070');
INSERT INTO `column_funcs` VALUES ('004003','FC0121');
INSERT INTO `column_funcs` VALUES ('004003','FC9003');
INSERT INTO `column_funcs` VALUES ('005001','FC0063');
INSERT INTO `column_funcs` VALUES ('005001','FC0064');
INSERT INTO `column_funcs` VALUES ('005002','FC0105');
INSERT INTO `column_funcs` VALUES ('005003','FC0079');
INSERT INTO `column_funcs` VALUES ('005003','FC0110');
INSERT INTO `column_funcs` VALUES ('007001','FC0096');
INSERT INTO `column_funcs` VALUES ('007001','FC0097');
INSERT INTO `column_funcs` VALUES ('007001','FC0098');
INSERT INTO `column_funcs` VALUES ('007001','FC0120');
INSERT INTO `column_funcs` VALUES ('007002','FC0089');
INSERT INTO `column_funcs` VALUES ('007002','FC0090');
INSERT INTO `column_funcs` VALUES ('007002','FC0091');
INSERT INTO `column_funcs` VALUES ('007002','FC0092');
INSERT INTO `column_funcs` VALUES ('007002','FC0093');
INSERT INTO `column_funcs` VALUES ('007002','FC0101');
INSERT INTO `column_funcs` VALUES ('007002','FC0102');
INSERT INTO `column_funcs` VALUES ('007002','FC0103');
INSERT INTO `column_funcs` VALUES ('007002','FC0104');
INSERT INTO `column_funcs` VALUES ('007003','FC0094');
INSERT INTO `column_funcs` VALUES ('007003','FC0095');
INSERT INTO `column_funcs` VALUES ('008001','FC0001');
INSERT INTO `column_funcs` VALUES ('008001','FC0008');
INSERT INTO `column_funcs` VALUES ('008001','FC0011');
INSERT INTO `column_funcs` VALUES ('008001','FC0026');
INSERT INTO `column_funcs` VALUES ('008001','FC0055');
INSERT INTO `column_funcs` VALUES ('008001','FC0083');
INSERT INTO `column_funcs` VALUES ('008001','FC0084');
INSERT INTO `column_funcs` VALUES ('008001','FC9904');
INSERT INTO `column_funcs` VALUES ('008002','FC0006');
INSERT INTO `column_funcs` VALUES ('008002','FC0042');
INSERT INTO `column_funcs` VALUES ('008002','FC0045');
INSERT INTO `column_funcs` VALUES ('008002','FC0058');
INSERT INTO `column_funcs` VALUES ('008003','FC0205');
INSERT INTO `column_funcs` VALUES ('008003','FC0206');
INSERT INTO `column_funcs` VALUES ('009001','FC0014');
INSERT INTO `column_funcs` VALUES ('009001','FC0015');
INSERT INTO `column_funcs` VALUES ('009001','FC0016');
INSERT INTO `column_funcs` VALUES ('009001','FC0054');
INSERT INTO `column_funcs` VALUES ('009002','FC0059');
INSERT INTO `column_funcs` VALUES ('009002','FC0072');
INSERT INTO `column_funcs` VALUES ('009002','FC0078');
INSERT INTO `column_funcs` VALUES ('009002','FC0085');
INSERT INTO `column_funcs` VALUES ('009002','FC0087');
INSERT INTO `column_funcs` VALUES ('009002','FC9001');
INSERT INTO `column_funcs` VALUES ('009003','FC0061');
INSERT INTO `column_funcs` VALUES ('009003','FC0062');
INSERT INTO `column_funcs` VALUES ('009003','FC0071');
INSERT INTO `column_funcs` VALUES ('009003','FC9906');
INSERT INTO `column_funcs` VALUES ('009003','FC9907');
INSERT INTO `column_funcs` VALUES ('010001','FC0057');
INSERT INTO `column_funcs` VALUES ('010001','FC0068');
INSERT INTO `column_funcs` VALUES ('010001','FC0080');
INSERT INTO `column_funcs` VALUES ('010001','FC9005');
INSERT INTO `column_funcs` VALUES ('010001','FC9910');
INSERT INTO `column_funcs` VALUES ('011001','FC0108');
INSERT INTO `column_funcs` VALUES ('011001','FC0109');
INSERT INTO `column_funcs` VALUES ('011001','FC0113');
INSERT INTO `column_funcs` VALUES ('011001','FC0114');
INSERT INTO `column_funcs` VALUES ('011002','FC0107');


2010-07-31修改
--账号添加删除标志
ALTER TABLE `accounts` ADD COLUMN `flag` VARCHAR(2) DEFAULT 1 AFTER `remark`;

2010-08-04增加
--接件单添加khlx字段，保存客户类型的信息
ALTER TABLE `jjd` ADD COLUMN `khlx` VARCHAR(10) DEFAULT NULL AFTER `mail`;
ALTER TABLE `jjd` ADD COLUMN `address` VARCHAR(500) DEFAULT NULL AFTER `khlx`;

2010-08-05
--销售订单的发票信息长度加大
ALTER TABLE `xsd` MODIFY  `fpxx` VARCHAR(200) DEFAULT NULL;

