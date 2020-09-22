
create table if not exists charge (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `year` varchar(10) NOT NULL DEFAULT '' COMMENT '年份',
  `month` varchar(10) NOT NULL DEFAULT '' COMMENT '月份',
  `room` int(10) DEFAULT NULL COMMENT '房间号',
  `type` varchar(10) NOT NULL DEFAULT '' COMMENT '水费:电费',
  `charge` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '收费',
  `real_charge` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '付费',
  `profit` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '盈亏',
  `status` varchar(10) NOT NULL DEFAULT '' COMMENT '状态',
  `pre_count` int(10) NOT NULL DEFAULT '0' COMMENT '上次抄表总值',
  `current_count` int(10) NOT NULL DEFAULT '0' COMMENT '本次抄表总值',
  `use_count` int(10) NOT NULL DEFAULT '0' COMMENT '本次使用量',
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '上级ID',
  PRIMARY KEY (`id`)
);
