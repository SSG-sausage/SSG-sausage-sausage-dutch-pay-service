package com.ssg.sausagedutchpayapi.dutchpay.entity;

import com.ssg.sausagedutchpayapi.common.entity.BaseEntity;
import com.ssg.sausagedutchpayapi.dutchpay.dto.request.DutchPayDtlUpdateInfo;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "DUTCH_PAY_DTL")
@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DutchPayDtl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DUTCH_PAY_DTL_ID")
    private Long dutchPayDtlId;

    @Column(name = "MBR_ID")
    private Long mbrId;

    @ManyToOne
    @JoinColumn(name = "DUTCH_PAY_ID")
    private DutchPay dutchPay;

    @Column(name = "DUTCH_PAY_DTL_AMT")
    private int dutchPayDtlAmt;

    @Column(name = "DUTCH_PAY_CMPL_YN")
    private boolean dutchPayCmplYn;

    @Column(name = "SHPP_AMT")
    private int shppAmt;

    @Column(name = "COMM_AMT")
    private int commAmt;

    @Column(name = "PR_AMT")
    private int prAmt;

    public static DutchPayDtl newInstance(DutchPay dutchPay, Long mbrId) {
        return DutchPayDtl.builder().dutchPay(dutchPay).mbrId(mbrId).dutchPayDtlAmt(0).dutchPayCmplYn(false).build();
    }

    public void updateOptSection(DutchPayDtlUpdateInfo request) {
        this.dutchPayDtlAmt = request.getDutchPayDtlAmt();
        this.shppAmt = request.getShppAmt();
        this.commAmt = request.getCommAmt();
        this.prAmt = request.getPrAmt();
    }

    public void updateOptSplit(int dutchPayDtlAmt) {
        this.dutchPayDtlAmt = dutchPayDtlAmt;
    }

    public void updateOptInput(DutchPayDtlUpdateInfo request) {
        this.dutchPayDtlAmt = request.getDutchPayDtlAmt();
    }

    public void updateDutchPayCmplYn() {
        this.dutchPayCmplYn = !this.isDutchPayCmplYn();
    }

}
