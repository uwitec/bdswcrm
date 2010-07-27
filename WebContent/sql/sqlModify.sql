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

ALTER TABLE `sfd` ADD COLUMN `khlx` VARCHAR(10) NOT NULL DEFAULT NULL AFTER `bxyy_ms`;


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


2010-07-27调整菜单
delete from funcs;
INSERT INTO `funcs` VALUES ('FC0001','商品维护','产品及分类的维护','product.html','134.gif',1,'1','9');
INSERT INTO `funcs` VALUES ('FC0002','采购订单','采购订单','listJhd.html','103.gif',1,'1','2');
INSERT INTO `funcs` VALUES ('FC0004','入库单','入库单','listRkd.html','124.gif',2,'1','3');
INSERT INTO `funcs` VALUES ('FC0005','出库单','出库单','listCkd.html','129.gif',1,'1','3');
INSERT INTO `funcs` VALUES ('FC0006','库存初始','库存初始','listInit.html','133.gif',8,'1','9');
INSERT INTO `funcs` VALUES ('FC0007','库存盘点','库存盘点','listKcpd.html','132.gif',5,'1','3');
INSERT INTO `funcs` VALUES ('FC0008','仓库资料','仓库资料','listStore.html','127.gif',4,'1','9');
INSERT INTO `funcs` VALUES ('FC0009','销售订单','销售订单','listXsd.html','108.gif',2,'1','1');
INSERT INTO `funcs` VALUES ('FC0010','零售单','零售单','listLsd.html','138.gif',1,'1','1');
INSERT INTO `funcs` VALUES ('FC0011','往来单位','往来单位','listClient.html','140.gif',2,'1','9');
INSERT INTO `funcs` VALUES ('FC0012','付款申请单','付款申请单','listCgfk.html','141.gif',8,'1','2');
INSERT INTO `funcs` VALUES ('FC0013','销售收款','销售收款','listXssk.html','123.gif',8,'1','1');
INSERT INTO `funcs` VALUES ('FC0014','用户管理','用户管理','listUser.html','173.gif',2,'1','10');
INSERT INTO `funcs` VALUES ('FC0015','修改密码','修改密码','changePass.html','183.gif',1,'1','10');
INSERT INTO `funcs` VALUES ('FC0016','角色管理','角色管理','listRole.html','228.gif',3,'1','10');
INSERT INTO `funcs` VALUES ('FC0020','销售退货单','销售退货单','listThd.html','176.gif',4,'1','1');
INSERT INTO `funcs` VALUES ('FC0023','调拨申请','调拨申请','listDbsq.html','201.gif',5,'1','1');
INSERT INTO `funcs` VALUES ('FC0024','库房调拨','库房调拨','listKfdb.html','199.gif',3,'1','3');
INSERT INTO `funcs` VALUES ('FC0025','存货调价','调整库存产品成本价','listChtj.html','229.gif',4,'1','3');
INSERT INTO `funcs` VALUES ('FC0026','账户资料','账户资料','listAccount.html','230.gif',7,'1','9');
INSERT INTO `funcs` VALUES ('FC0027','其它收入','其它收入','listQtsr.html','208.gif',4,'1','4');
INSERT INTO `funcs` VALUES ('FC0028','一般费用','其它支出','listQtzc.html','201.gif',5,'1','4');
INSERT INTO `funcs` VALUES ('FC0029','内部转账','内部转账','listNbzz.html','205.gif',6,'1','4');
INSERT INTO `funcs` VALUES ('FC0031','往来调账','往来调账','listPz.html','180.gif',7,'1','4');
INSERT INTO `funcs` VALUES ('FC0032','采购退货单','采购退货单','listCgthd.html','173.gif',4,'1','2');
INSERT INTO `funcs` VALUES ('FC0033','零售预收款','零售预收款','listLsysk.html','154.gif',3,'1','1');
INSERT INTO `funcs` VALUES ('FC0034','账户余额','账户余额','listZhye.html','155.gif',3,'1','4');
INSERT INTO `funcs` VALUES ('FC0035','现金银行汇总表','现金银行','showCashBankCondition.html','157.gif',1,'1','4');
INSERT INTO `funcs` VALUES ('FC0036','销售明细表','销售明细表','showXsmxReportCondition.html','158.gif',1,'1','1');
INSERT INTO `funcs` VALUES ('FC0037','业务员销售汇总','业务员销售汇总','showXstjXsryCondition.html','159.gif',5,'1','1');
INSERT INTO `funcs` VALUES ('FC0038','客户销售汇总','客户销售汇总','showXstjClientCondition.html','160.gif',10,'1','1');
INSERT INTO `funcs` VALUES ('FC0039','出入库汇总','出入库汇总','showKcMxCondition.html','167.gif',3,'1','3');
INSERT INTO `funcs` VALUES ('FC0040','客户应收汇总表','应收汇总','showYsmxCondition.html','141.gif',1,'1','4');
INSERT INTO `funcs` VALUES ('FC0041','销售收款统计','销售收款汇总','showXsskHzCondition.html','146.gif',2,'1','4');
INSERT INTO `funcs` VALUES ('FC0042','往来初始','往来初始','listClientWlInit.html','147.gif',7,'1','9');
INSERT INTO `funcs` VALUES ('FC0043','供应商应付汇总表','应付汇总','showYfHzCondition.html','148.gif',2,'1','4');
INSERT INTO `funcs` VALUES ('FC0044','采购付款统计','采购付款汇总','showCgfkHzCondition.html','149.gif',3,'1','4');
INSERT INTO `funcs` VALUES ('FC0045','账号初始','账号初始','initAccount.html','150.gif',6,'1','9');
INSERT INTO `funcs` VALUES ('FC0046','库存成本变化','库存成本变化','showKcJeHzCondition.html','153.gif',4,'1','3');
INSERT INTO `funcs` VALUES ('FC0047','客户销售执行汇总','客户销售执行汇总','showXsdHzCondition.html','102.gif',11,'1','1');
INSERT INTO `funcs` VALUES ('FC0048','货品销售汇总','货品销售汇总','showHpxsHzCondition.html','108.gif',4,'1','1');
INSERT INTO `funcs` VALUES ('FC0049','客户往来对账单','客户往来对账单','showClientWlDzdCondition.html','109.gif',4,'1','4');
INSERT INTO `funcs` VALUES ('FC0050','业务员销售毛利汇总','业务员销售毛利汇总','showXsmltjXsryCondition.html','110.gif',6,'1','1');
INSERT INTO `funcs` VALUES ('FC0051','货品销售毛利汇总','货品销售毛利汇总','showHpxsMlHzCondition.html','111.gif',3,'1','1');
INSERT INTO `funcs` VALUES ('FC0052','货品采购汇总','货品采购汇总','showHpcgHzCondition.html','112.gif',16,'1','2');
INSERT INTO `funcs` VALUES ('FC0053','客户采购汇总','客户采购汇总','showClientcgHzCondition.html','113.gif',17,'1','2');
INSERT INTO `funcs` VALUES ('FC0054','在线用户','在线用户','showOnlineUser.html','121.gif',4,'1','10');
INSERT INTO `funcs` VALUES ('FC0055','员工管理','员工管理','showEmployeeFrame.html','123.gif',6,'1','9');
INSERT INTO `funcs` VALUES ('FC0056','序列号查询','序列号查询','getSerialFlow.html','127.gif',8,'1','3');
INSERT INTO `funcs` VALUES ('FC0057','内部公告','内部公告','listNbgg.html','121.gif',1,'1','8');
INSERT INTO `funcs` VALUES ('FC0058','系统初始导航','系统初始导航','showSzXgqyrqPage.html','147.gif',0,'1','9');
INSERT INTO `funcs` VALUES ('FC0059','系统字典维护','系统字典维护','listJbxx.html','180.gif',1,'1','10');
INSERT INTO `funcs` VALUES ('FC0060','序列号管理','序列号管理','listSerialNum.html','108.gif',9,'1','3');
INSERT INTO `funcs` VALUES ('FC0061','销售单超额审批权限','销售单超额申批角色设定','openXsdSpRightRoles.html','110.gif',8,'1','10');
INSERT INTO `funcs` VALUES ('FC0062','价格审批权限','价格审批权限','openJgSpRightRoles.html','112.gif',9,'1','10');
INSERT INTO `funcs` VALUES ('FC0063','预收冲应收','预收冲应收','listYushouToYingshou.html','160.gif',8,'1','5');
INSERT INTO `funcs` VALUES ('FC0064','预付冲应付','预付冲应付','listYufuToYingfu.html','110.gif',9,'1','5');
INSERT INTO `funcs` VALUES ('FC0065','业务员考核毛利汇总','业务员考核毛利汇总','showXsryKhmlhzCondition.html','180.gif',7,'1','1');
INSERT INTO `funcs` VALUES ('FC0066','商品序列号销售汇总','商品序列号销售汇总','showSerialXshzCondition.html','153.gif',13,'1','1');
INSERT INTO `funcs` VALUES ('FC0067','销售未收单据汇总','销售未收单据列表','showWsdjCondition.html','167.gif',3,'1','4');
INSERT INTO `funcs` VALUES ('FC0068','费用申请','费用申请','listFysq.html','127.gif',2,'1','8');
INSERT INTO `funcs` VALUES ('FC0070','费用分类汇总表','费用分类汇总表','showFytjCondition.html','180.gif',4,'1','4');
INSERT INTO `funcs` VALUES ('FC0071','超期审批权限','超期审批权限','openCqSpRightRoles.html','180.gif',9,'1','10');
INSERT INTO `funcs` VALUES ('FC0072','设置零售税点','设置零售税点','editLssd.html','102.gif',5,'1','10');
INSERT INTO `funcs` VALUES ('FC0073','付摊销付款','付摊销付款','listTxfk.html','180.gif',13,'1','4');
INSERT INTO `funcs` VALUES ('FC0075','核心代理价格汇总','核心代理库存价格汇总','showDlKcNumsCondition.html','112.gif',12,'1','1');
INSERT INTO `funcs` VALUES ('FC0076','库存金额汇总','库存金额汇总','showKcJeCondition.html','180.gif',2,'1','3');
INSERT INTO `funcs` VALUES ('FC0077','零售库存数量汇总','零售库存数量汇总','showLsKcNumsCondition.html','110.gif',3,'1','3');
INSERT INTO `funcs` VALUES ('FC0078','刷卡POS机设定','刷卡POS机设定','listPos.html','111.gif',4,'1','10');
INSERT INTO `funcs` VALUES ('FC0079','月度利润表','月度利润表','getMothlyGainList.html','153.gif',6,'1','5');
INSERT INTO `funcs` VALUES ('FC0080','发送短信','发送短信','listlinkman.html','110.gif',5,'1','8');
INSERT INTO `funcs` VALUES ('FC0081','货品销售分类汇总','货品销售分类汇总','showHpflHzCon.html','180.gif',2,'1','1');
INSERT INTO `funcs` VALUES ('FC0082','部门销售汇总','部门销售汇总','showDeptxsHzCon.html','110.gif',9,'1','1');
INSERT INTO `funcs` VALUES ('FC0083','联系人','联系人','listLxr.html','102.gif',3,'1','9');
INSERT INTO `funcs` VALUES ('FC0084','费用类别管理','费用类别管理','listFyType.html','110.gif',5,'1','9');
INSERT INTO `funcs` VALUES ('FC0085','系统LOGO设置','系统LOGO设置','editLogo.html','123.gif',2,'1','10');
INSERT INTO `funcs` VALUES ('FC0086','分仓库存数量汇总','分仓库存数量汇总','showFckcCondition.html','153.gif',2,'1','3');
INSERT INTO `funcs` VALUES ('FC0087','单据打印设置','单据打印设置','editReportSet.html','108.gif',3,'1','10');
INSERT INTO `funcs` VALUES ('FC0088','考核指标调整','考核指标调整','queryProduct.html','110.gif',10,'1','1');
INSERT INTO `funcs` VALUES ('FC0089','接件单','接件单','listJjd.html','112.gif',1,'1','7');
INSERT INTO `funcs` VALUES ('FC0090','报修单','报修单','listBxd.html','102.gif',2,'1','7');
INSERT INTO `funcs` VALUES ('FC0091','报修返还单','报修返还单','listBxfhd.html','153.gif',3,'1','7');
INSERT INTO `funcs` VALUES ('FC0092','返还客户单','返还客户单','listFhkhd.html','180.gif',4,'1','7');
INSERT INTO `funcs` VALUES ('FC0093','维修入库单','维修入库单','listWxrkd.html','111.gif',5,'1','7');
INSERT INTO `funcs` VALUES ('FC0094','维修库存查询','维修库存查询','listShkc.html','110.gif',6,'1','7');
INSERT INTO `funcs` VALUES ('FC0095','维修序列号查询','维修序列号查询','getShSerialFlow.html','123.gif',7,'1','7');
INSERT INTO `funcs` VALUES ('FC0096','售后服务单','售后服务单','listSfd.html','121.gif',8,'1','7');
INSERT INTO `funcs` VALUES ('FC0097','派工单','派工单','listPgd.html','167.gif',9,'1','7');
INSERT INTO `funcs` VALUES ('FC0098','维修处理单','维修处理单','listWxcld.html','180.gif',10,'1','7');
INSERT INTO `funcs` VALUES ('FC0099','库龄汇总','库龄汇总','showKlCondition.html','123.gif',7,'1','3');
INSERT INTO `funcs` VALUES ('FC0100','库存数量汇总','库存数量汇总','showKcNumsCondition.html','110.gif',8,'1','3');
INSERT INTO `funcs` VALUES ('FC0101','移库出库','移库出库','listYkck.html','180.gif',78,'1','7');
INSERT INTO `funcs` VALUES ('FC0102','移库入库','移库入库','listYkrk.html','124.gif',79,'1','7');
INSERT INTO `funcs` VALUES ('FC0103','报废单','报废单','listBfd.html','176.gif',80,'1','7');
INSERT INTO `funcs` VALUES ('FC0104','换件单','维修返还的产品序列号发生变化','listHjd.html','229.gif',81,'1','7');
INSERT INTO `funcs` VALUES ('FC0105','采购发票','采购发票的处理','listCgfp.html','123.gif',82,'1','5');
INSERT INTO `funcs` VALUES ('FC0106','货品销售执行汇总','货品销售订单执行汇总','showHpxsddHzCondition.html','123.gif',12,'1','1');
INSERT INTO `funcs` VALUES ('FC0107','积分规则设置','积分规则设置','listJfgz.html','229.gif',1,'1','11');
INSERT INTO `funcs` VALUES ('FC0108','会员卡分类','会员卡分类','listHykfl.html','112.gif',1,'1','11');
INSERT INTO `funcs` VALUES ('FC0109','会员卡档案','会员卡档案','listHykda.html','228.gif',4,'1','11');
INSERT INTO `funcs` VALUES ('FC0110','采购发票统计','采购发票的统计','showCgfpCondtion.html','127.gif',82,'1','5');
INSERT INTO `funcs` VALUES ('FC0113','会员卡制作','会员卡制作','listHykzz.html','129.gif',2,'1','11');
INSERT INTO `funcs` VALUES ('FC0114','发卡管理','发卡管理','listHykfk.html','155.gif',3,'1','11');
INSERT INTO `funcs` VALUES ('FC0120','咨询工单','咨询工单','listZxgd.html','123.gif',11,'1','7');
INSERT INTO `funcs` VALUES ('FC0121','其他收入统计','其他收入统计','showQtsrtjCondition.html','123.gif',7,'1','4');
INSERT INTO `funcs` VALUES ('FC0205','员工资料','员工信息查询','listYgbb.html','121.gif',82,'1','9');
INSERT INTO `funcs` VALUES ('FC0206','往来单位资料','往来单位资料','listClientbb.html','140.gif',7,'1','9');
INSERT INTO `funcs` VALUES ('FC0207','货品采购执行汇总','货品采购订单执行汇总','showHpcgddHzCondition.html','123.gif',18,'1','2');
INSERT INTO `funcs` VALUES ('FC0208','供应商采购执行汇总','供应商采购订单执行汇总','showClientcgddHzCondition.html','124.gif',19,'1','2');
INSERT INTO `funcs` VALUES ('FC9001','提成比例设置','提成比例设置','editTcbl.html','149.gif',6,'1','10');
INSERT INTO `funcs` VALUES ('FC9002','业务员提成汇总','业务员提成汇总','showYwytcCondtion.html','123.gif',8,'1','4');
INSERT INTO `funcs` VALUES ('FC9003','部门费用汇总表','部门费用汇总表','showDeptFytjCondition.html','167.gif',5,'1','4');
INSERT INTO `funcs` VALUES ('FC9004','货品预估毛利汇总','货品预估毛利汇总','showHpYgmlHzCondition.html','149.gif',3,'1','1');
INSERT INTO `funcs` VALUES ('FC9005','日程安排','日程安排','showCalendar.html','167.gif',4,'1','8');
INSERT INTO `funcs` VALUES ('FC9903','商品拆卸单','商品拆卸单','listCxd.html','121.gif',6,'1','3');
INSERT INTO `funcs` VALUES ('FC9904','商品信息批量调整','商品信息批量调整','batchUpdateProductCon.html','149.gif',10,'1','9');
INSERT INTO `funcs` VALUES ('FC9905','出纳付款单','出纳付款单','listCnfkd.html','153.gif',0,'1','4');
INSERT INTO `funcs` VALUES ('FC9906','费用申请审批设置','费用申请审批设置','openFysqSpRight.html','102.gif',0,'1','10');
INSERT INTO `funcs` VALUES ('FC9907','采购付款审批设置','采购付款审批设置','openCgfkSpRight.html','129.gif',4,'1','10');
INSERT INTO `funcs` VALUES ('FC9908','商品组装单','商品组装单','listZzd.html','112.gif',7,'1','3');
INSERT INTO `funcs` VALUES ('FC9910','备忘录','备忘录','listBwl.html','121.gif',0,'1','8');
INSERT INTO `funcs` VALUES ('FC9998','客户查询','客户查询',NULL,'110.gif',0,'1','9');
INSERT INTO `funcs` VALUES ('FC9999','库存查询','库存查询',NULL,'149.gif',0,'1','3');

delete from column_mng;
INSERT INTO `column_mng` VALUES ('001','销售管理','0',2,'merchandise_sales.gif','1');
INSERT INTO `column_mng` VALUES ('001001','销售业务','001',10,NULL,'1');
INSERT INTO `column_mng` VALUES ('001002','销售统计','001',11,NULL,'1');
INSERT INTO `column_mng` VALUES ('001003','决策助理','001',12,NULL,'1');
INSERT INTO `column_mng` VALUES ('002','采购管理','0',3,'stock_m.gif','1');
INSERT INTO `column_mng` VALUES ('002001','采购业务','002',12,NULL,'1');
INSERT INTO `column_mng` VALUES ('002002','采购统计','002',13,NULL,'1');
INSERT INTO `column_mng` VALUES ('003','库存管理','0',4,'stock.gif','1');
INSERT INTO `column_mng` VALUES ('003001','库存管理','003',14,NULL,'1');
INSERT INTO `column_mng` VALUES ('003002','库存统计','003',15,NULL,'1');
INSERT INTO `column_mng` VALUES ('004','出纳管理','0',5,'funds.gif','1');
INSERT INTO `column_mng` VALUES ('004001','资金往来','004',16,NULL,'1');
INSERT INTO `column_mng` VALUES ('004002','往来统计','004',18,NULL,'1');
INSERT INTO `column_mng` VALUES ('004003','财务统计','004',17,NULL,'1');
INSERT INTO `column_mng` VALUES ('005','账务管理','0',6,'WORDPAD.gif','1');
INSERT INTO `column_mng` VALUES ('005001','账务处理','005',16,NULL,'1');
INSERT INTO `column_mng` VALUES ('005002','发票管理','005',25,NULL,'1');
INSERT INTO `column_mng` VALUES ('005003','账务统计','005',26,NULL,'1');
INSERT INTO `column_mng` VALUES ('007','售后管理','0',8,'buy.gif','1');
INSERT INTO `column_mng` VALUES ('007001','售后接待','007',1,NULL,'1');
INSERT INTO `column_mng` VALUES ('007002','售后处理','007',2,NULL,'1');
INSERT INTO `column_mng` VALUES ('007003','售后统计','007',3,NULL,'1');
INSERT INTO `column_mng` VALUES ('008','基础资料','0',14,'AddDocument.gif','0');
INSERT INTO `column_mng` VALUES ('008001','基础资料','008',19,NULL,'0');
INSERT INTO `column_mng` VALUES ('008002','系统初始','008',20,NULL,'0');
INSERT INTO `column_mng` VALUES ('008003','基础报表','008',27,NULL,'0');
INSERT INTO `column_mng` VALUES ('009','系统管理','0',15,'set.gif','0');
INSERT INTO `column_mng` VALUES ('009001','权限管理','009',21,NULL,'0');
INSERT INTO `column_mng` VALUES ('009002','系统参数设置','009',23,NULL,'0');
INSERT INTO `column_mng` VALUES ('009003','审批设置','009',24,NULL,'0');
INSERT INTO `column_mng` VALUES ('010','OA办公','0',9,'itinerary.gif','1');
INSERT INTO `column_mng` VALUES ('010001','OA办公','010',22,NULL,'1');
INSERT INTO `column_mng` VALUES ('011','会员管理','0',10,'buy.gif','1');
INSERT INTO `column_mng` VALUES ('011001','会员卡管理','011',28,NULL,'1');
INSERT INTO `column_mng` VALUES ('011002','会员积分管理','011',29,NULL,'1');

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

