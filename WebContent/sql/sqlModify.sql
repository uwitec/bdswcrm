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
ALTER TABLE `crm_sw`.`sys_user` MODIFY COLUMN `szkf` VARCHAR(500) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL;


 
 

