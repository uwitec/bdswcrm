--缺少字段
ALTER TABLE `sfd` MODIFY COLUMN `khlx` VARCHAR(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT 0,
 ADD COLUMN `linkmanls` VARCHAR(100) AFTER `khlx`;
 
 2010-08-11修改
--往来单位添加删除标志
ALTER TABLE `clients` ADD COLUMN `flag` VARCHAR(2) DEFAULT 1 AFTER `cg_xe`;


2010-08-20修改
--仓库资料增加删除标志
ALTER TABLE `storehouse` ADD COLUMN `flag` VARCHAR(2) DEFAULT 1 AFTER `remark`;

2010-09-16更新
--修改库房调拨单状态,将已入库改为了结算状态
--部署后需要执行
 UPDATE kfdb SET state='已入库' where state='已出库'
 

2010-09-20更新
--修改积分规则表修改字段类型
ALTER TABLE `jfgz` MODIFY COLUMN `xfje` DOUBLE DEFAULT 0,
 MODIFY COLUMN `dyjf` DOUBLE DEFAULT 0;
 
--会员卡制作表修改,添加状态,去掉了是否充值,初始金额
ALTER TABLE `hykzz` DROP COLUMN `sfcz`;
ALTER TABLE `hykzz` ADD COLUMN `state` VARCHAR(45) AFTER `cz_date`;
ALTER TABLE `hykzz` DROP COLUMN `csje`;

--会员卡档案表修改
ALTER TABLE `hykda` MODIFY COLUMN `hykh` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 DROP COLUMN `sfty`,
 MODIFY COLUMN `hymc` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `lxrname` VARCHAR(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `lxdh` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `mobile` VARCHAR(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `address` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 MODIFY COLUMN `mail` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
 DROP COLUMN `ffjg`,
 DROP COLUMN `zkrq`,
 DROP COLUMN `zkjsr`,
 DROP COLUMN `hybh`,
 MODIFY COLUMN `fkbz` VARCHAR(1000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
 
 
2010-10-02更新
--修改系统用户的所在库房字段,增加为varchar(500),可以支持选择多个库房
ALTER TABLE `sys_user` MODIFY COLUMN `szkf` VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;
2010-10-16更新
--将数据字典中的报修原因修改为“其他原因”

update sjzd_xmxx set xm_name='其他原因',xm_ms='其他原因' where xm_name='其他' and zd_id='SJZD_BXYY';

2010-10-18更新
--返还客户单中增加客户类型、联系电话、手机、地址、E-Mail
ALTER TABLE `fhkhd` ADD COLUMN `khlx` VARCHAR(10) DEFAULT NULL AFTER `lxr`;
ALTER TABLE `fhkhd` ADD COLUMN `address` VARCHAR(500) DEFAULT NULL AFTER `khlx`;
ALTER TABLE `fhkhd` ADD COLUMN `lxdh` VARCHAR(20) DEFAULT NULL AFTER `address`;
ALTER TABLE `fhkhd` ADD COLUMN `mobile` VARCHAR(20) DEFAULT NULL AFTER `lxdh`;
ALTER TABLE `fhkhd` ADD COLUMN `mail` VARCHAR(100) DEFAULT NULL AFTER `mobile`;

update fhkhd set khlx='往来单位';

2011-04-18更新
----增加货品销售毛利分类汇总
INSERT INTO `funcs` VALUES ('FC9915','货品毛利分类汇总','货品毛利分类汇总','showHpmlflHzCon.html','112.gif',11,'1','1');

INSERT INTO `column_funcs` VALUES ('001003','FC9915');

2011-05-23更新
--商品交易信息中添加销售人员所在部门列，并更新数据
ALTER TABLE `product_sale_flow` ADD COLUMN `xsry_dept` VARCHAR(45) AFTER `sfcytc`;
update product_sale_flow a set xsry_dept=(select dept from sys_user b where b.user_id=a.xsry)

2011-7-11更新
INSERT INTO `funcs` VALUES ('FC9916','强制序列号设置','强制序列号设置','openQzxlhRight.html','111.gif',10,'1','10');

INSERT INTO `column_funcs` VALUES ('009003','FC9916');

INSERT INTO `role_func` VALUES ('RL00000001','FC9916');

---强制序列号设置
DROP TABLE IF EXISTS `qzxlh_right`;
CREATE TABLE `qzxlh_right` (
  `sp_flag` varchar(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `qzxlh_right` VALUES ('01');


--库存盘点增加强制序列号字段
ALTER TABLE `kcpd_desc` ADD COLUMN `qz_serial_num` varchar(4000) default NULL  AFTER `remark`;

2011-07-20修改员工状态
update sys_user set zzzt='在职' where is_sys_user='0' and zzzt is null


2012-01-12修改
update funcs set func_name='序列号跟踪' where func_id='FC0056'
insert into funcs(func_id,func_name,func_ms,url,img,xh,ywflag,funcflag) values('FC9917','序列号查询','序列号查询','querySerialNum.html','102.gif','10','1','3')
INSERT INTO `column_funcs` VALUES ('003001','FC9917');
update sys_user set zzzt='在职' where is_sys_user='0' and zzzt is null

2012-02-16更新
--商品交易信息中添加仓库列，并更新数据
ALTER TABLE `product_sale_flow` ADD COLUMN `store_id` VARCHAR(20) AFTER `xsry_dept`;
update product_sale_flow a inner join ckd b on a.id=b.xsd_id set a.store_id=b.store_id;
update product_sale_flow a inner join rkd b on  a.id=b.jhd_id set a.store_id=b.store_id;
----增加仓库销售汇总
INSERT INTO `funcs` VALUES ('FC9918','仓库销售汇总','仓库销售汇总','showStorexsHzCondition.html','199.gif',13,'1','3');
INSERT INTO `column_funcs` VALUES ('001002','FC9918');

2012-03-01更新
--添加序列号盘点功能
INSERT INTO `funcs` VALUES ('FC9919','序列号盘点','序列号盘点','pdSerialNumCon.html','121.gif',14,'1','3');
INSERT INTO `column_funcs` VALUES ('003001','FC9919');

--序列号盘点记录表
CREATE TABLE serial_num_pd (
  `id` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `cdate` VARCHAR(45) NOT NULL,
  `jsr` VARCHAR(45) NOT NULL,
  `store_id` VARCHAR(45) NOT NULL,
  `cz_date` DATETIME NOT NULL,
  `pd_result` VARCHAR(400) NOT NULL,
  PRIMARY KEY(`id`)
)
ENGINE = InnoDB;


2012-05-29更新
--添加采购税点设置表
CREATE TABLE cgsd(
  `cgsd` DOUBLE DEFAULT 0
)
ENGINE = InnoDB;

--添加采购税点设置功能
INSERT INTO `funcs` VALUES ('FC9920','设置采购税点','设置采购税点','editCgsd.html','121.gif',5,'1','10');
INSERT INTO `column_funcs` VALUES ('009002','FC9920');

ALTER TABLE jhd_product ADD COLUMN `sd` DOUBLE AFTER `sjcj_nums`,
 ADD COLUMN `hsje` DOUBLE AFTER `sd`,
 ADD COLUMN `bhsje` DOUBLE AFTER `hsje`,
 ADD COLUMN `sje` DOUBLE AFTER `bhsje`;

ALTER TABLE jhd ADD COLUMN `hjsje` DOUBLE AFTER `ysws`,
 ADD COLUMN `hjbhsje` DOUBLE AFTER `hjsje`;
 
 update jhd set hjsje=0,hjbhsje=total;
UPDATE jhd_product SET sd=0,hsje=price*nums,bhsje=price*nums,sje=0;

ALTER TABLE rkd_product ADD COLUMN `sd` DOUBLE AFTER `qz_serial_num`,
 ADD COLUMN `hsje` DOUBLE AFTER `sd`,
 ADD COLUMN `bhsje` DOUBLE AFTER `hsje`,
 ADD COLUMN `sje` DOUBLE AFTER `bhsje`;

 update rkd_product set sd=0.0,hsje=price*nums,bhsje=price*nums,sje=0.0;
 
 2012-06-06修改
 --添加客户销售毛利汇总功能
 INSERT INTO `funcs` VALUES ('FC9921','客户销售毛利汇总','客户销售毛利汇总','showXstjClientMlCondition.html','121.gif',12,'1','1');
 INSERT INTO `column_funcs` VALUES ('001003','FC9921');
 
 2012-06-07修改
 --序列号去掉首尾空格
 update serial_num_mng set serial_num=trim(serial_num) where serial_num like '% %';
 update serial_num_flow set serial_num=trim(serial_num) where serial_num like '% %';

2012-07-12修改
--增加售后库存的商品规格长度
ALTER TABLE `shkc` MODIFY COLUMN `product_xh` VARCHAR(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;


2012-08-04修改
--零售单中添加会员卡编号字段
ALTER TABLE `lsd` ADD COLUMN `hyk_id` VARCHAR(100) AFTER `pos_id`;

--添加会员卡积分流水表
CREATE TABLE `hyk_jf_flow` (
  `seq_id` BIGINT(20) UNSIGNED NOT NULL AUTO_INCREMENT,
  `hyk_id` VARCHAR(100) NOT NULL,
  `yw_id` VARCHAR(100) NOT NULL,
  `xfje` DOUBLE NOT NULL,
  `jf` DOUBLE NOT NULL,
  `cz_date` DATETIME NOT NULL,
  `czr` VARCHAR(45) NOT NULL,
  PRIMARY KEY(`seq_id`)
)
ENGINE = InnoDB
CHARACTER SET utf8 COLLATE utf8_general_ci;

2012-08-11修改
--退货单添加会员卡编号字段
ALTER TABLE `thd` ADD COLUMN `hyk_id` VARCHAR(100) AFTER `yw_type`;
--会员积分流水表添加经手人字段
ALTER TABLE `hyk_jf_flow` ADD COLUMN `jsr` VARCHAR(45) AFTER `czr`;
--添加会员积分查询功能
insert into column_mng VALUES ('011003','积分管理','011',30,'',1);
insert into column_funcs values('011003','FC9922');
insert into funcs values('FC9922','会员积分查询','会员积分查询','showJfcxCondition.html','121.gif','1','1','11');

2012-8-8修改
-- 修改accounts表中的double型字段为3位小数位
ALTER TABLE `accounts` MODIFY COLUMN `dqje` double(24,3)   default '0.000';
ALTER TABLE `accounts` MODIFY COLUMN `qcje` double(24,3)   default '0.000';


2012-8-10修改
---数据字典的基础表丢失一条记录
INSERT INTO `sjzd_jbxx` VALUES ('SJZD_ZCLX','支付类型（摊销付款）','支付类型（摊销付款）',4);


2012-08-25修改
--添加销售发票管理
CREATE TABLE `xsfpgl` (
  `id` VARCHAR(50) NOT NULL,
  `fplx` VARCHAR(45),
  `kpmc` VARCHAR(100),
  `fpje` DOUBLE,
  `kpdz` VARCHAR(100),
  `kpdh` VARCHAR(50),
  `khhzh` VARCHAR(200),
  `sh` VARCHAR(45),
  `fpxxzy` VARCHAR(500),
  `jy_jsr` VARCHAR(45),
  `jy_date` VARCHAR(45),
  `kp_jsr` VARCHAR(45),
  `kp_date` VARCHAR(45),
  `yw_type` VARCHAR(45),
  `yw_id` VARCHAR(45),
  `state` VARCHAR(45),
  `cz_date` DATETIME,
  `czr` VARCHAR(45),
  PRIMARY KEY(`id`)
)
ENGINE = InnoDB;

2013-04-21修改
--新的库存期初表，采用xml方式存储，提升数据库的查询效率
CREATE TABLE `product_kc_qc_xml` (
  `store_id` VARCHAR(20) NOT NULL,
  `cdate` VARCHAR(20) NOT NULL,
  `xmlString` LONGTEXT,
  `createDate` DATETIME NOT NULL,
  PRIMARY KEY(`store_id`, `cdate`)
)
ENGINE = InnoDB;

2013-06-26更新
----增加单位应收应付查询报表
INSERT INTO `funcs` VALUES ('FC9923','单位应收应付查询','单位应收应付查询','showDwysyfcxCon.html','112.gif',11,'1','4');

INSERT INTO `column_funcs` VALUES ('004002','FC9923');