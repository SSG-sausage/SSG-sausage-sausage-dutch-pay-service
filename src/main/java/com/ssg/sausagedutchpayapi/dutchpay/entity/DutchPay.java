package com.ssg.sausagedutchpayapi.dutchpay.entity;

import com.ssg.sausagedutchpayapi.common.entity.BaseEntity;
import com.ssg.sausagedutchpayapi.dutchpay.dto.request.DutchPayDtlUpdateRequest;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "DUTCH_PAY")
@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class DutchPay extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DUTCH_PAY_ID")
    private Long dutchPayId;

    @Column(name = "CART_SHARE_ORD_ID")
    private Long cartShareOrdId;

    @Column(name = "MASTR_MBR_ID")
    private Long mastrMbrId;

    @Column(name = "DUTCH_PAY_ST_YN")
    private boolean dutchPayStYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "DUTCH_PAY_OPT_CD")
    private DutchPayOptCd dutchPayOptCd;

    @Column(name = "PAYMT_AMT")
    private int paymtAmt;

    @Column(name = "DUTCH_PAY_RMD")
    private int dutchPayRmd;

    @Column(name = "DUTCH_PAY_AMT")
    private int dutchPayAmt;

    @OneToMany(mappedBy = "dutchPay", cascade = CascadeType.ALL)
    private List<DutchPayDtl> dutchPayDtlList;

    public static DutchPay newInstance(Long cartShareOrdId, Long mastrMbrId, int paymtAmt) {
        return DutchPay.builder()
                .cartShareOrdId(cartShareOrdId)
                .mastrMbrId(mastrMbrId)
                .dutchPayOptCd(DutchPayOptCd.SECTION)
                .dutchPayStYn(false)
                .dutchPayRmd(0)
                .dutchPayAmt(0)
                .paymtAmt(paymtAmt)
                .build();
    }

    public void update(DutchPayDtlUpdateRequest request) {
        this.dutchPayOptCd = request.getDutchPayOptCd();
        this.dutchPayRmd = request.getDutchPayRmd();
        this.dutchPayAmt = request.getDutchPayAmt();
    }

    public void start(){
        this.dutchPayStYn = true;
    }

}
