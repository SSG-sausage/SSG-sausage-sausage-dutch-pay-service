package com.ssg.sausagedutchpayapi.dutchpay.entity;

import com.ssg.sausagedutchpayapi.common.entity.BaseEntity;
import com.ssg.sausagedutchpayapi.dutchpay.dto.request.CartShareCalDtlUpdateInfo;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "CART_SHARE_CAL_DTL")
@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CartShareCalDtl extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_SHARE_CAL_DTL_ID")
    private Long cartShareCalDtlId;

    @Column(name = "MBR_ID")
    private Long mbrId;

    @ManyToOne
    @JoinColumn(name = "CART_SHARE_CAL_ID")
    private CartShareCal cartShareCal;

    @Column(name = "CAL_DTL_AMT")
    private int calDtlAmt;

    @Column(name = "CAL_CMPL_YN")
    private boolean calCmplYn;

    @Column(name = "SHPP_CST")
    private int shppCst;

    @Column(name = "COMM_AMT")
    private int commAmt;

    @Column(name = "PER_AMT")
    private int perAmt;

    public static CartShareCalDtl newInstance(CartShareCal cartShareCal, Long mbrId) {
        return CartShareCalDtl.builder()
                .cartShareCal(cartShareCal)
                .mbrId(mbrId)
                .calDtlAmt(0)
                .calCmplYn(false)
                .shppCst(0)
                .commAmt(0)
                .perAmt(0)
                .build();
    }

    public void updateCalDtlAmt(int calDtlAmt) {
        this.calDtlAmt = calDtlAmt;
        this.shppCst = 0;
        this.commAmt = 0;
        this.perAmt = 0;
    }

    public void updateCalDtlAmtOnOptSection(CartShareCalDtlUpdateInfo request) {
        this.calDtlAmt = request.getCalDtlAmt();
        this.shppCst = request.getShppAmt();
        this.commAmt = request.getCommAmt();
        this.perAmt = request.getPrAmt();
    }

    public void updateCalDtlCmplYn() {
        this.calCmplYn = !this.isCalCmplYn();
    }

}
