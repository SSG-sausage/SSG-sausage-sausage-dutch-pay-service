DROP TABLE IF EXISTS `DUTCH_PAY`;
DROP TABLE IF EXISTS `DUTCH_PAY_DTL`;
DROP TABLE IF EXISTS `DUTCH_PAY_NOTI`;

CREATE TABLE `DUTCH_PAY`
(
    `DUTCH_PAY_ID`      bigint   NOT NULL AUTO_INCREMENT,
    `CART_SHARE_ORD_ID` bigint   NOT NULL,
    `DUTCH_PAY_RMD`     int      NOT NULL,
    `REG_DTS`           datetime NOT NULL,
    `REGPE_ID`          bigint NULL,
    `MOD_DTS`           datetime NOT NULL,
    `MODPE_ID`          bigint NULL,
    PRIMARY KEY (`DUTCH_PAY_ID`)
);

CREATE TABLE `DUTCH_PAY_DTL`
(
    `DUTCH_PAY_DTL_ID`  bigint   NOT NULL AUTO_INCREMENT,
    `MBR_ID`            bigint   NOT NULL,
    `DUTCH_PAY_ID`      bigint   NOT NULL,
    `DUTCH_PAY_DTL_AMT` int      NOT NULL,
    `DUTCH_PAY_CMPL_YN` boolean  NOT NULL,
    `REG_DTS`           datetime NOT NULL,
    `REGPE_ID`          bigint NULL,
    `MOD_DTS`           datetime NOT NULL,
    `MODPE_ID`          bigint NULL,
    PRIMARY KEY (`DUTCH_PAY_DTL_ID`)
);


CREATE TABLE `DUTCH_PAY_NOTI`
(
    `DUTCH_PAY_NOTI_ID`   bigint   NOT NULL AUTO_INCREMENT,
    `MBR_ID`              bigint   NOT NULL,
    `CART_SHARE_ID`       bigint   NOT NULL,
    `DUTCH_PAY_NOTI_CNTT` text     NOT NULL,
    `REG_DTS`             datetime NOT NULL,
    `REGPE_ID`            bigint NULL,
    `MOD_DTS`             datetime NOT NULL,
    `MODPE_ID`            bigint NULL,
    PRIMARY KEY (`DUTCH_PAY_NOTI_ID`)
);