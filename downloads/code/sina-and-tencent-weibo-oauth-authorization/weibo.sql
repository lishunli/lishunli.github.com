-- Table structure for table `application`
DROP TABLE IF EXISTS `application`;
CREATE TABLE `application` (
  `seqid` int(11) NOT NULL AUTO_INCREMENT,
  `appid` varchar(6) NOT NULL,
  `oauthconsumerkey` varchar(20) NOT NULL,
  `oauthconsumersecret` varchar(48) NOT NULL,
  `provider` varchar(20) NOT NULL DEFAULT 'SINA' COMMENT 'SINA=新浪微博，TENCENT=腾讯微博',
  PRIMARY KEY (`seqid`),
  KEY `appid` (`appid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table structure for table `follower`
DROP TABLE IF EXISTS `follower`;
CREATE TABLE `follower` (
  `seqid` bigint(20) NOT NULL AUTO_INCREMENT,
  `userid` varchar(48) NOT NULL,
  `appid` varchar(6) NOT NULL,
  `username` varchar(40) NOT NULL DEFAULT '',
  `token` varchar(48) NOT NULL DEFAULT '' COMMENT '新浪微博授权后的token',
  `tokensecret` varchar(48) NOT NULL DEFAULT '' COMMENT '新浪微博授权后的tokenSecret',
  `verifier` varchar(12) NOT NULL DEFAULT '' COMMENT '腾讯微博授权后的验证码',
  `lastid` bigint(20) DEFAULT NULL COMMENT '最近的weibo id',
  `lasttimestamp` bigint(20) DEFAULT NULL COMMENT '最近weibo的时间戳',
  PRIMARY KEY (`seqid`),
  UNIQUE KEY `unique_index` (`userid`,`appid`),
  KEY `appid_ref` (`appid`),
  CONSTRAINT `appid_ref` FOREIGN KEY (`appid`) REFERENCES `application` (`appid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table structure for table `relation`
DROP TABLE IF EXISTS `relation`;
CREATE TABLE `relation` (
  `seqid` bigint(20) NOT NULL AUTO_INCREMENT,
  `leftfollowerid` bigint(20) DEFAULT NULL,
  `rightfollowerid` bigint(20) DEFAULT NULL,
  `istwoway` tinyint(4) DEFAULT '0',
  PRIMARY KEY (`seqid`),
  KEY `left_follower` (`leftfollowerid`),
  KEY `right_follower` (`rightfollowerid`),
  CONSTRAINT `right_follower` FOREIGN KEY (`rightfollowerid`) REFERENCES `follower` (`seqid`),
  CONSTRAINT `left_follower` FOREIGN KEY (`leftfollowerid`) REFERENCES `follower` (`seqid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Table structure for table `syncrecord`
DROP TABLE IF EXISTS `syncrecord`;
CREATE TABLE `syncrecord` (
  `seqid` bigint(20) NOT NULL AUTO_INCREMENT,
  `followerid` bigint(20) NOT NULL,
  `weiboid` bigint(20) DEFAULT NULL,
  `inputday` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`seqid`),
  KEY `index1` (`followerid`,`weiboid`),
  CONSTRAINT `follower_ref` FOREIGN KEY (`followerid`) REFERENCES `follower` (`seqid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

