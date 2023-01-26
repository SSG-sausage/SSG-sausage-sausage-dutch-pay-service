package com.ssg.sausagecartsharecalculationapi.dutchpay.entity;

import com.ssg.sausagecartsharecalculationapi.common.entity.BaseEntity;
import com.ssg.sausagecartsharecalculationapi.dutchpay.dto.request.CartShareCalUpdateRequest;
import java.util.List;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Table(name = "CART_SHARE_CAL")
@Getter
@Entity
@DynamicUpdate
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CartShareCal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_SHARE_CAL_ID")
    private Long cartShareCalId;

    @Column(name = "CART_SHARE_ORD_ID")
    private Long cartShareOrdId;

    @Column(name = "MASTR_MBR_ID")
    private Long mastrMbrId;

    @Column(name = "CAL_ST_YN")
    private boolean calStYn;

    @Enumerated(EnumType.STRING)
    @Column(name = "CAL_OPT_CD")
    private CalOptCd calOptCd;

    @Column(name = "CAL_RMD")
    private int calRmd;

    @Column(name = "CAL_AMT")
    private int calAmt;


    @Column(name = "TTL_PAYMT_AMT")
    private int ttlPaymtAmt;

    @OneToMany(mappedBy = "cartShareCal", cascade = CascadeType.ALL)
    private List<CartShareCalDtl> cartShareCalDtlList;

    public static CartShareCal newInstance(Long cartShareOrdId, Long mastrMbrId, int ttlPaymtAmt) {
        return CartShareCal.builder()
                .cartShareOrdId(cartShareOrdId)
                .mastrMbrId(mastrMbrId)
                .calOptCd(CalOptCd.INPUT)
                .calStYn(false)
                .calRmd(0)
                .calAmt(0)
                .ttlPaymtAmt(ttlPaymtAmt)
                .build();
    }

    public void update(CartShareCalUpdateRequest request) {
        this.calOptCd = request.getCalOptCd();
        this.calRmd = request.getCalRmd();
        this.calAmt = request.getCalAmt();
    }

    public void start(){
        this.calStYn = true;
    }

}
