2010-02-04修改
ALTER TABLE `xxfb_nbgg` MODIFY COLUMN `id` VARCHAR(50) DEFAULT NULL;

2010-02-18添加
CREATE TABLE `zzd` (
  `id` varchar(20) NOT NULL,
  `cdate` varchar(45) default NULL,
  `store_id` varchar(45) default NULL,
  `product_id` varchar(45) default NULL,
  `product_name` varchar(200) default NULL,
  `product_xh` varchar(200) default NULL,
  `product_dw` varchar(50) default NULL,
  `nums` int(11) default '0',
  `price` double default '0',
  `hjje` double default '0',
  `jsr` varchar(45) default NULL,
  `remark` varchar(45) default NULL,
  `czr` varchar(45) default NULL,
  `cz_date` datetime default NULL,
  `state` varchar(45) default NULL,
  `serial_nums` varchar(400) default NULL,
  `qz_flag` varchar(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `zzd_product` (
  `id` int(10) unsigned NOT NULL auto_increment,
  `zzd_id` varchar(45) NOT NULL,
  `product_id` varchar(45) default NULL,
  `product_name` varchar(200) default NULL,
  `product_xh` varchar(200) default NULL,
  `product_dw` varchar(45) default NULL,
  `price` double default '0',
  `nums` int(11) default '0',
  `hj` double default '0',
  `remark` varchar(200) default NULL,
  `qz_serial_num` varchar(500) default NULL,
  `qz_flag` varchar(10) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


ALTER TABLE `cms_all_seq` ADD COLUMN `zzdid` INTEGER UNSIGNED DEFAULT 1 AFTER `cnfkdid`;

ALTER TABLE `xssk_desc` ADD COLUMN `ysk` DOUBLE DEFAULT 0 AFTER `fsje`;

2010-02-27更新
--修改客户往来情况视图
CREATE OR REPLACE VIEW view_client_wl_info AS
(select id as dj_id,gysbh as client_id,total as fsje,DATE_FORMAT(cz_date,'%Y-%m-%d') as cdate,'应付发生' as je_type,'采购' as yw_type,'viewXsd.html?id=' as url,cz_date,fzr as jsr from jhd where state='已入库')
union all
(select id as dj_id, provider_name as client_id,(0-tkzje) as fsje,DATE_FORMAT(cz_date,'%Y-%m-%d') as cdate,'应付发生' as je_type,'采购退货' as yw_type,'viewCgthd.html?id=' as url,cz_date,jsr from cgthd where state='已出库')
union all
(select id as dj_id,client_name as client_id,pzje as fsje,DATE_FORMAT(cz_date,'%Y-%m-%d') as cdate,'应付发生' as je_type,'往来调账' as yw_type,'viewPz.html?id=' as url,cz_date,jsr from pz where state='已提交' and type='应付')
union all
(select id as dj_id,client_name as client_id,sjcjje as fsje,DATE_FORMAT(cz_date,'%Y-%m-%d') as cdate,'应收发生' as je_type,'销售' as yw_type,'viewXsd.html?id=' as url,cz_date,fzr as jsr from xsd where state='已出库')
union all
(select thd_id as dj_id,client_name as client_id,(0-thdje) as fsje,DATE_FORMAT(cz_date,'%Y-%m-%d') as cdate,'应收发生' as je_type,'销售退货' as yw_type,'viewThd.html?thd_id=' as url,cz_date,th_fzr as jsr from thd where state='已入库')
union all
(select id as dj_id,client_name as client_id,pzje as fsje,DATE_FORMAT(cz_date,'%Y-%m-%d') as cdate,'应收发生' as je_type,'往来调账' as yw_type,'viewPz.html?id=' as url,cz_date,jsr from pz where state='已提交' and type='应收')
union all
(select id as dj_id,gysbh as client_id,fkje as fsje,DATE_FORMAT(cz_date,'%Y-%m-%d') as cdate,'已付发生' as je_type,'采购付款' as yw_type,'viewCgfk.html?id=' as url,cz_date,jsr from cgfk where state='已提交' or state='已支付')
union all
(select a.id as dj_id,a.client_name as client_id,a.skje as fsje,DATE_FORMAT(a.cz_date,'%Y-%m-%d') as cdate,'已收发生' as je_type,'销售收款' as yw_type,'viewXssk.html?id=' as url,a.cz_date,jsr from xssk a inner join clients b on b.id=a.client_name where a.state='已提交')


2010-03-05更新
--往来调账添加结算金额
ALTER TABLE `pz` ADD COLUMN `jsje` DOUBLE DEFAULT 0 AFTER `pzxm`;

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

--接件单商品信息
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

--报修单商品详细信息
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

--报修返还单商品明细
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

--返还客户单商品明细
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

--换件单，当商品序列号发生变化时，使用
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

--换件单商品明细
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

--报废单，当商品不能再修时，使用
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

--报废单商品明细
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

--移库出库单,从商品库移出，移入坏件库
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

--移库出库单商品明细
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
   
--移库入库单,从好件库移出，移入商品库
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

--移库入库单商品明细
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

--维修入库单商品明细
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


2010-03-19 添加预估成本价

--商品基本信息添加预估成本价
ALTER TABLE `product` ADD COLUMN `ygcbj` DOUBLE DEFAULT 0 AFTER `sp_txm`;
--将预估成本默认设置为考核成本价
update product set ygcbj=khcbj

--零售单关联商品添加预估成本价
ALTER TABLE `lsd_product` ADD COLUMN `ygcbj` DOUBLE DEFAULT 0 AFTER `lsxj`;
update lsd_product set ygcbj=kh_cbj

--销售订单关联商品添加预估成本价
ALTER TABLE `xsd_product` ADD COLUMN `ygcbj` DOUBLE DEFAULT 0 AFTER `lsxj`;
update xsd_product set ygcbj=kh_cbj

--退货单关联商品添加预估成本价
ALTER TABLE `thd_product` ADD COLUMN `ygcbj` DOUBLE DEFAULT 0 AFTER `lsxj`;
update thd_product set ygcbj=kh_cbj

--修改货品销售统计视图
CREATE OR REPLACE VIEW `view_hpxshz_tj` AS 
(select b.id,'销售单' as yw_type,a.product_id AS product_id,c.product_name AS product_name,c.product_xh AS product_xh,c.product_kind AS product_kind,c.prop AS prop,b.client_name AS client_name,e.client_type AS client_type,b.fzr AS xsry,d.real_name AS real_name,d.dept AS dept,date_format(b.cz_date,'%Y-%m-%d') AS cz_date,a.sjcj_nums AS nums,a.price AS price,a.sjcj_xj AS hjje,
a.cbj as dwcb,(a.cbj * a.sjcj_nums) AS cb,
a.kh_cbj as dwkhcb,(a.kh_cbj*a.sjcj_nums) AS khcb,a.ygcbj as dwygcb,(a.ygcbj*a.sjcj_nums) as ygcb,
a.sd,a.bhsje,a.gf,(a.ds*a.nums) as ds,a.basic_ratio,a.out_ratio,(a.nums * a.lsxj) as lsxj
from ((((xsd_product a left join xsd b on((b.id = a.xsd_id))) left join product c on((c.product_id = a.product_id))) left join sys_user d on((d.user_id = b.fzr))) left join clients e on((e.id = b.client_name)))
where (b.state = '已出库')) 
union all 
(select b.thd_id as id,'退货单' as yw_type, a.product_id AS product_id,c.product_name AS product_name,c.product_xh AS product_xh,c.product_kind AS product_kind,c.prop AS prop,b.client_name AS client_name,e.client_type AS client_type,b.th_fzr AS xsry,d.real_name AS real_name,d.dept AS dept,date_format(b.cz_date,'%Y-%m-%d') AS cz_date,(0 - a.nums) AS nums,a.th_price AS price,(0 - a.xj) AS hjje,
a.cbj as dwcb,(0 - (a.cbj * a.nums)) AS cb,
a.kh_cbj as dwkhcb,(0 - (a.kh_cbj*a.nums)) AS khcb,a.ygcbj as dwygcb,(0 - (a.ygcbj*a.nums)) AS ygcb,
a.sd,(0-a.bhsje) as bhsje,a.gf,((0-a.ds)*a.nums) as ds,a.basic_ratio,a.out_ratio,(0-(a.nums * a.lsxj)) as lsxj
from ((((thd_product a left join thd b on((b.thd_id = a.thd_id))) left join product c on((c.product_id = a.product_id))) left join sys_user d on((d.user_id = b.th_fzr))) left join clients e on((e.id = b.client_name)))
where (b.state = '已入库')) 
union all 
(select b.id,'零售单' as yw_type, a.product_id AS product_id,c.product_name AS product_name,c.product_xh AS product_xh,c.product_kind AS product_kind,c.prop AS prop,b.client_name AS client_name,_utf8'' AS client_type,b.xsry AS xsry,d.real_name AS real_name,d.dept AS dept,date_format(b.cz_date,'%Y-%m-%d') AS cz_date,a.nums AS nums,a.price AS price,a.xj AS hjje,
a.cbj as dwcb,(a.cbj * a.nums)AS cb,
a.kh_cbj as dwkhcb,(a.kh_cbj*a.nums) AS khcb,a.ygcbj as dwygcb,(a.ygcbj*a.nums) as ygcb,
a.sd,a.bhsje,a.gf,(a.ds*a.nums) as ds,a.basic_ratio,a.out_ratio,(a.lsxj*a.nums) as lsxj
from (((lsd_product a left join lsd b on((b.id = a.lsd_id))) left join product c on((c.product_id = a.product_id))) left join sys_user d on((d.user_id = b.xsry)))
where (b.state = '已提交'))


2010-03-20 修改

--客户往来期初表添加索引
ALTER TABLE `client_qc` ADD INDEX `Index_client_qc_query`(`client_name`, `cdate`);

--添加日程计划安排表
CREATE TABLE `calendar_plan` (
  `id` varchar(50) NOT NULL,
  `cdate` varchar(20) default NULL,
  `start_time` varchar(20) default NULL,
  `end_time` varchar(20) default NULL,
  `address` varchar(200) default NULL,
  `content` varchar(1000) default NULL,
  `is_remind` char(1) default NULL,
  `grade` char(1) default NULL,
  `czr` varchar(20) default NULL,
  `cz_date` datetime default NULL,
  `remind_time` int(10) unsigned default '0',
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

2010-03-24 修改
--用户管理增加字段
ALTER TABLE `sys_user` ADD COLUMN `id_card` VARCHAR(40) AFTER `is_del`;
ALTER TABLE `sys_user` ADD COLUMN `nation` VARCHAR(10) AFTER `id_card`;
ALTER TABLE `sys_user` ADD COLUMN `lxr` VARCHAR(20) AFTER `nation`;
ALTER TABLE `sys_user` ADD COLUMN `relation` VARCHAR(20) AFTER `lxr`;
ALTER TABLE `sys_user` ADD COLUMN `jbgz` double default  0 AFTER `relation`;
ALTER TABLE `sys_user` ADD COLUMN `rzrq` VARCHAR(20) AFTER `jbgz`;
ALTER TABLE `sys_user` ADD COLUMN `gl` int(10) AFTER `rzrq`;
ALTER TABLE `sys_user` ADD COLUMN `byxx` VARCHAR(50) AFTER `gl`;
ALTER TABLE `sys_user` ADD COLUMN `major` VARCHAR(40) AFTER `byxx`;
ALTER TABLE `sys_user` ADD COLUMN `xl` VARCHAR(10) AFTER `major`;
ALTER TABLE `sys_user` ADD COLUMN `gzjl` VARCHAR(500)    AFTER `xl`;
ALTER TABLE `sys_user` ADD COLUMN `ldkh` VARCHAR(500)  AFTER `gzjl`;
ALTER TABLE `sys_user` ADD COLUMN `remark`  VARCHAR(100)  AFTER `ldkh`;
ALTER TABLE `sys_user` ADD COLUMN `zzmm`  VARCHAR(20)  AFTER `remark`;


2010-03-27 修改
--修改采购付款表的状态
update cgfk set state='已支付' where state='已提交'
--导入column_funcs表

ALTER TABLE `sys_user` MODIFY COLUMN `gs_phone` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `mobile` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `jt_phone` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `fax` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `qq` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `p_code` VARCHAR(40) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;

2010-03-29修改
--增加婚否、家庭成员、政治面貌
ALTER TABLE `sys_user` ADD COLUMN `sfjh`  VARCHAR(20)  AFTER `remark`;
ALTER TABLE `sys_user` ADD COLUMN `jtcy`  VARCHAR(200)  AFTER `sfjh`;

--进货单增加地址、联系人、联系电话、未税/已税
ALTER TABLE `jhd` ADD COLUMN `kh_address`  VARCHAR(200)  AFTER `yjdhsj`;
ALTER TABLE `jhd` ADD COLUMN `kh_lxr`  VARCHAR(45)  AFTER `kh_address`;
ALTER TABLE `jhd` ADD COLUMN `kh_lxdh`  VARCHAR(45)  AFTER `kh_lxr`;
ALTER TABLE `jhd` ADD COLUMN `ysws`  VARCHAR(6)  AFTER `kh_lxdh`;

2010-04-01修改
--更新采购订单发票类型字段值
update jhd set ysws='含税' where ysws='已税'

2010-04-02修改
update jhd set ysws='含税' where ysws='已税'


2010-04-03修改
--添加商品销售流水表，提高后续商品销售的统计的效率
CREATE TABLE `product_sale_flow` (
  `seq_id` bigint(20) NOT NULL auto_increment,
  `id` varchar(20) default NULL,
  `yw_type` varchar(3) default NULL,
  `product_id` varchar(20) default NULL,
  `client_name` varchar(100) default NULL,
  `xsry` varchar(20) default NULL,
  `cz_date` varchar(20) default NULL,
  `nums` bigint(20) default '0',
  `price` double default '0',
  `hjje` double default '0',
  `dwcb` double default '0',
  `cb` double default '0',
  `dwkhcb` double default '0',
  `khcb` double default '0',
  `dwygcb` double default '0',
  `ygcb` double default '0',
  `sd` double default '0',
  `bhsje` double default '0',
  `gf` double default '0',
  `ds` double default '0',
  `basic_ratio` double default '0',
  `out_ratio` double default '0',
  `lsxj` double default '0',
  `jy_time` datetime default NULL,
  PRIMARY KEY  (`seq_id`),
  KEY `index_product_sale_flow` (`id`,`product_id`,`client_name`,`xsry`,`cz_date`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into product_sale_flow ( select 0 as seq_id,a.id,a.yw_type,product_id,client_name,xsry,cz_date,nums,price,hjje,dwcb,cb,dwkhcb,khcb,dwygcb,ygcb,
sd,bhsje,gf,ds,basic_ratio,out_ratio,lsxj,jy_time from  
((select b.id,'销售单' as yw_type,a.product_id AS product_id,c.product_name AS product_name,c.product_xh AS product_xh,c.product_kind AS product_kind,c.prop AS prop,b.client_name AS client_name,e.client_type AS client_type,b.fzr AS xsry,d.real_name AS real_name,d.dept AS dept,date_format(b.cz_date,'%Y-%m-%d') AS cz_date,a.sjcj_nums AS nums,a.price AS price,a.sjcj_xj AS hjje,
a.cbj as dwcb,(a.cbj * a.sjcj_nums) AS cb,
a.kh_cbj as dwkhcb,(a.kh_cbj*a.sjcj_nums) AS khcb,a.ygcbj as dwygcb,(a.ygcbj*a.sjcj_nums) as ygcb,
a.sd,a.bhsje,a.gf,(a.ds*a.nums) as ds,a.basic_ratio,a.out_ratio,(a.nums * a.lsxj) as lsxj,cz_date as jy_time
from ((((xsd_product a left join xsd b on((b.id = a.xsd_id))) left join product c on((c.product_id = a.product_id))) left join sys_user d on((d.user_id = b.fzr))) left join clients e on((e.id = b.client_name)))
where (b.state = '已出库')) 
union all 
(select b.thd_id as id,'退货单' as yw_type, a.product_id AS product_id,c.product_name AS product_name,c.product_xh AS product_xh,c.product_kind AS product_kind,c.prop AS prop,b.client_name AS client_name,e.client_type AS client_type,b.th_fzr AS xsry,d.real_name AS real_name,d.dept AS dept,date_format(b.cz_date,'%Y-%m-%d') AS cz_date,(0 - a.nums) AS nums,a.th_price AS price,(0 - a.xj) AS hjje,
a.cbj as dwcb,(0 - (a.cbj * a.nums)) AS cb,
a.kh_cbj as dwkhcb,(0 - (a.kh_cbj*a.nums)) AS khcb,a.ygcbj as dwygcb,(0 - (a.ygcbj*a.nums)) AS ygcb,
a.sd,(0-a.bhsje) as bhsje,a.gf,((0-a.ds)*a.nums) as ds,a.basic_ratio,a.out_ratio,(0-(a.nums * a.lsxj)) as lsxj,cz_date as jy_time
from ((((thd_product a left join thd b on((b.thd_id = a.thd_id))) left join product c on((c.product_id = a.product_id))) left join sys_user d on((d.user_id = b.th_fzr))) left join clients e on((e.id = b.client_name)))
where (b.state = '已入库')) 
union all 
(select b.id,'零售单' as yw_type, a.product_id AS product_id,c.product_name AS product_name,c.product_xh AS product_xh,c.product_kind AS product_kind,c.prop AS prop,b.client_name AS client_name,_utf8'' AS client_type,b.xsry AS xsry,d.real_name AS real_name,d.dept AS dept,date_format(b.cz_date,'%Y-%m-%d') AS cz_date,a.nums AS nums,a.price AS price,a.xj AS hjje,
a.cbj as dwcb,(a.cbj * a.nums)AS cb,
a.kh_cbj as dwkhcb,(a.kh_cbj*a.nums) AS khcb,a.ygcbj as dwygcb,(a.ygcbj*a.nums) as ygcb,
a.sd,a.bhsje,a.gf,(a.ds*a.nums) as ds,a.basic_ratio,a.out_ratio,(a.lsxj*a.nums) as lsxj,cz_date as jy_time
from (((lsd_product a left join lsd b on((b.id = a.lsd_id))) left join product c on((c.product_id = a.product_id))) left join sys_user d on((d.user_id = b.xsry)))
where (b.state = '已提交'))) a)

--商品表添加索引
ALTER TABLE `product` ADD INDEX `Index_product_kind`(`product_id`, `product_kind`);

2010-04-06修改
--修改零售单扩充部分字段值大小
ALTER TABLE `lsd` MODIFY COLUMN `client_name` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `lxr` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `lxdh` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `mobile` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `msn` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `kp_dh` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;


2010-04-07修改
--往来单位的联系人中增加qq、msn、称呼、年龄段
ALTER TABLE `clients_linkman` ADD COLUMN `ch`  VARCHAR(50)  AFTER `clients_id`;
ALTER TABLE `clients_linkman` ADD COLUMN `qq`  VARCHAR(20)  AFTER `ch`;
ALTER TABLE `clients_linkman` ADD COLUMN `msn`  VARCHAR(50)  AFTER `qq`;
ALTER TABLE `clients_linkman` ADD COLUMN `nld`  VARCHAR(20)  AFTER `msn`;

ALTER TABLE `clients_linkman` MODIFY COLUMN `msn` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;


--增加年龄段的数据字典
INSERT INTO `sjzd_jbxx` VALUES ('SJZD_LXRNLD','联系人年龄段','联系人年龄段',12);
INSERT INTO `sjzd_xmxx`(zd_id,xm_name,xm_ms,xh) VALUES ('SJZD_LXRNLD','20以下','20以下',6);
INSERT INTO `sjzd_xmxx`(zd_id,xm_name,xm_ms,xh) VALUES ('SJZD_LXRNLD','21-30','21-30',5);
INSERT INTO `sjzd_xmxx`(zd_id,xm_name,xm_ms,xh) VALUES ('SJZD_LXRNLD','31-40','31-40',4);
INSERT INTO `sjzd_xmxx`(zd_id,xm_name,xm_ms,xh) VALUES ('SJZD_LXRNLD','41-50','41-50',3);
INSERT INTO `sjzd_xmxx`(zd_id,xm_name,xm_ms,xh) VALUES ('SJZD_LXRNLD','51-60','51-60',2);
INSERT INTO `sjzd_xmxx`(zd_id,xm_name,xm_ms,xh) VALUES ('SJZD_LXRNLD','61-70','61-70',1);
INSERT INTO `sjzd_xmxx`(zd_id,xm_name,xm_ms,xh) VALUES ('SJZD_LXRNLD','70以上','70以上',0);

2010-04-05修改
--菜单中增加发票管理
INSERT INTO `column_mng`(`id`,`name`,`parent_id`,`xh`,`img`,`yw_flag`) 
VALUES('010','发票管理','0','10','WORDPAD.gif','1'),('010001','发票管理','010',25,NULL,'1'),('010002','发票统计','010',26,NULL,'1');

INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0105','采购发票','采购发票的处理','listCgfp.html','123.gif',82,'1','9');

INSERT INTO `column_funcs` VALUES ('010001','FC0105');

2010-04-13增加发票表
DROP TABLE IF EXISTS `cgfpd`;
CREATE TABLE `cgfpd` (
  `id` varchar(20) NOT NULL,
  `cg_date` varchar(45) default NULL,
  `jhd_id` varchar(20) default NULL,
  `gysbh` varchar(20) default NULL,
  `total` double default NULL,
  `czr` varchar(45) default NULL,
  `cz_date` datetime default NULL,
  `state` varchar(45) default NULL,
  `ms` varchar(2000) default NULL,
   PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--id自动生成CGFP******
ALTER TABLE `cms_all_seq` ADD COLUMN `zxgdid` INTEGER UNSIGNED DEFAULT 1 AFTER `hjdid`;
ALTER TABLE `cms_all_seq` ADD COLUMN `cgfpdid` INTEGER UNSIGNED DEFAULT 1 AFTER `zxgdid`;

--将进货单中含税的单据导入到采购发票表中
INSERT INTO `cgfpd`(id,cg_date,jhd_id,gysbh,total,czr,cz_date,state) select id,cg_date,id,gysbh,total,czr,cz_date,'未入库' from jhd where ysws='含税' and state='已入库';

update  `cgfpd`   set   id=REPLACE(id,'JH', 'CGFP');   

2010-04-14修改
--菜单中增加基础报表
INSERT INTO `column_mng`(`id`,`name`,`parent_id`,`xh`,`img`,`yw_flag`) 
VALUES('007003','基础报表','007',27,NULL,'0');

INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0205','员工资料','员工信息查询','listYgbb.html','121.gif',82,'1','7');

INSERT INTO `column_funcs` VALUES ('007003','FC0205');

--员工管理中增加是否在职的字段信息

ALTER TABLE `sys_user` ADD COLUMN `zzzt`  VARCHAR(10)  AFTER `zzmm`;

2010-04-15增加采购发票统计

INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0110','采购发票统计','采购发票的统计','showCgfpCondtion.html','127.gif',82,'1','9');

INSERT INTO `column_funcs` VALUES ('010002','FC0110');

2010-04-16修改
--增加咨询工单表
DROP TABLE IF EXISTS `zxgd`;
CREATE TABLE `zxgd` (
  `id` varchar(50) NOT NULL,  
  `sfd_id` varchar(50) default NULL,
  `hfr` varchar(20) default NULL,
  `hf_date` varchar(20) default NULL,
  `content` varchar(1000) default NULL,
  `khyj` varchar(1000) default NULL,
  `czr` varchar(20) default NULL,
  `cz_date` datetime default NULL,
  `state` varchar(50) default NULL,   
  PRIMARY KEY  (`id`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

2010-04-20修改
--售后服务表中添加设置流程
ALTER TABLE `sfd` ADD COLUMN `flow` VARCHAR(20) AFTER `ms`;

2010-04-21修改
--售后接待菜单中增加咨询工单
INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0120','咨询工单','咨询工单','listZxgd.html','123.gif',11,'1','5');

INSERT INTO `column_funcs` VALUES ('005001','FC0120');

--增加售后服务单中联系电话的长度
ALTER TABLE `sfd` MODIFY COLUMN `mobile` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;


ALTER TABLE `thd` MODIFY COLUMN `fpxx` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;

2010-04-23修改
--财务统计中增加其他收入统计菜单
INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0121','其他收入统计','其他收入统计','showQtsrtjCondition.html','123.gif',7,'1','4');

INSERT INTO `column_funcs` VALUES ('004003','FC0121');

2010-04-26修改
--基础报表中增加往来单位资料
INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0206','往来单位资料','往来单位资料','listClientbb.html','140.gif',7,'1','7');

INSERT INTO `column_funcs` VALUES ('007003','FC0206');

2010-05-02修改
--修改采购统计名称
INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0207','货品采购执行汇总','货品采购订单执行汇总','showHpcgddHzCondition.html','123.gif',18,'1','2');
INSERT INTO `column_funcs` VALUES ('002002','FC0207');

INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0208','供应商采购执行汇总','供应商采购订单执行汇总','showClientcgddHzCondition.html','124.gif',19,'1','2');
INSERT INTO `column_funcs` VALUES ('002002','FC0208');

--修改出纳付款单添加费用相应字段
ALTER TABLE `cnfkd` ADD COLUMN `has_fy` VARCHAR(20) AFTER `client_all_name`,
 ADD COLUMN `fy_type` VARCHAR(45) AFTER `has_fy`,
 ADD COLUMN `fy_account` VARCHAR(20) AFTER `fy_type`,
 ADD COLUMN `fy_je` DOUBLE DEFAULT 0 AFTER `fy_account`;
 
 ----------------------------------------------------------------
 --截止2010-05-02所有帐套更新完毕
 ----------------------------------------------------------------
 
 2010-05-03添加
 --销售收款添加收款方式和pos机编号
 ALTER TABLE `xssk` ADD COLUMN `skfs` VARCHAR(100) AFTER `delete_key`,
 ADD COLUMN `pos_id` VARCHAR(45) AFTER `skfs`;
 
 2010-05-05增加
 --备忘录增加共享人的存储
ALTER TABLE `bwl` ADD COLUMN `gxr` VARCHAR(400) AFTER `cz_date`;
 
DROP TABLE IF EXISTS `bwl_share`;
CREATE TABLE `bwl_share` (
  `id` int(10)  NOT NULL auto_increment,  
  `bwl_id` varchar(50) default NULL,
  `share` varchar(20) default NULL,     
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


2010-05-20修改
--sfd增加报修原因的存储
ALTER TABLE `sfd` ADD COLUMN `qz_serial_num` VARCHAR(4000) AFTER `flow`;
ALTER TABLE `sfd` ADD COLUMN `bxyy` VARCHAR(100) AFTER `qz_serial_num`;
ALTER TABLE `sfd` ADD COLUMN `bxyy_ms` VARCHAR(1000) AFTER `bxyy`;

--增加报修原因的字典信息
INSERT INTO `sjzd_jbxx` VALUES ('SJZD_BXYY','报修原因','报修原因',13);
INSERT INTO `sjzd_xmxx` VALUES (196,'SJZD_BXYY','其他原因','其他原因',0);


2010-05-21修改
--移库出库中增加入库仓库的选择，可以是好件库，也可以是坏件库
ALTER TABLE `ykck` ADD COLUMN `rk_store_id` VARCHAR(20) AFTER `remark`;

2010-05-30商品表修改，
--添加是否参与提成
ALTER TABLE `product` ADD COLUMN `sfcytc` VARCHAR(10) DEFAULT 1 AFTER `ygcbj`;

--商品交易表中添加是否参与提成字段
ALTER TABLE `product_sale_flow` ADD COLUMN `sfcytc` VARCHAR(10) DEFAULT 1 AFTER `jy_time`;

--零售单明细添加是否参与提成字段
ALTER TABLE `lsd_product` ADD COLUMN `sfcytc` VARCHAR(10) DEFAULT 1 AFTER `ygcbj`;

--销售单明细添加是否参与提成字段
ALTER TABLE `xsd_product` ADD COLUMN `sfcytc` VARCHAR(10) DEFAULT 1 AFTER `ygcbj`;

--退货单明细添加是否参与提成字段
ALTER TABLE `thd_product` ADD COLUMN `sfcytc` VARCHAR(10) DEFAULT 1 AFTER `ygcbj`;


2010-06-02销售统计增加货品销售执行汇总、客户销售执行汇总
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


INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0111','兑奖货品设置','兑奖货品设置','listDjhpsz.html','176.gif',2,'1','10');

INSERT INTO `column_funcs` VALUES ('011002','FC0111');

INSERT INTO `funcs`(`func_id`,`func_name`,`func_ms`,`url`,`img`,`xh`,`ywflag`,`funcflag`)  values
('FC0112','积分查询兑奖','积分查询兑奖','listJfcxdj.html','180.gif',3,'1','10');

INSERT INTO `column_funcs` VALUES ('011002','FC0112');

--增加积分规则设置表
DROP TABLE IF EXISTS `jfgz`;
CREATE TABLE `jfgz` (
  `id` varchar(50) NOT NULL,  
  `jfff` varchar(200) default NULL,
  `xfje` int(5) default 0,
  `dyjf` int(2) default NULL, 
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
