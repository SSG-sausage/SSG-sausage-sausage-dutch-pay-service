DROP TABLE IF EXISTS `CART_SHARE_CAL`;
DROP TABLE IF EXISTS `CART_SHARE_CAL_DTL`;

CREATE TABLE `CART_SHARE_CAL`
(
    `CART_SHARE_CAL_ID` bigint      NOT NULL AUTO_INCREMENT,
    `CART_SHARE_ORD_ID` bigint      NOT NULL,
    `CART_SHARE_ID`     bigint      NOT NULL,
    `MASTR_MBR_ID`      bigint      NOT NULL,
    `CART_SHARE_NM`     varchar(30) NOT NULL,
    `CART_SHARE_ORD_NO` varchar(30) NOT NULL,
    `CART_SHARE_CAL_ST_DTS` datetime NULL,
    `CAL_ST_YN`         boolean     NOT NULL,
    `CAL_OPT_CD`        varchar(30) NOT NULL,
    `CAL_RMD`           int         NOT NULL,
    `CAL_AMT`           int         NOT NULL,
    `TTL_PAYMT_AMT`     int         NOT NULL,
    `REG_DTS`           datetime    NOT NULL,
    `REGPE_ID`          bigint NULL,
    `MOD_DTS`           datetime    NOT NULL,
    `MODPE_ID`          bigint NULL,
    PRIMARY KEY (`CART_SHARE_CAL_ID`)
);

CREATE TABLE `CART_SHARE_CAL_DTL`
(
    `CART_SHARE_CAL_DTL_ID` bigint   NOT NULL AUTO_INCREMENT,
    `MBR_ID`                bigint   NOT NULL,
    `CART_SHARE_CAL_ID`     bigint   NOT NULL,
    `CAL_DTL_AMT`           int      NOT NULL,
    `CAL_CMPL_YN`           boolean  NOT NULL,
    `SHPP_CST`              int      NOT NULL,
    `COMM_AMT`              int      NOT NULL,
    `PER_AMT`               int      NOT NULL,
    `REG_DTS`               datetime NOT NULL,
    `REGPE_ID`              bigint NULL,
    `MOD_DTS`               datetime NOT NULL,
    `MODPE_ID`              bigint NULL,
    PRIMARY KEY (`CART_SHARE_CAL_DTL_ID`)
);
