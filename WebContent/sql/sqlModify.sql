﻿--缺少字段
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