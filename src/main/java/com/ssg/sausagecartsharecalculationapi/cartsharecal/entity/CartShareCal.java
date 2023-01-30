package com.ssg.sausagecartsharecalculationapi.cartsharecal.entity;

import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request.CartShareCalSaveRequest;
import com.ssg.sausagecartsharecalculationapi.common.entity.BaseEntity;
import com.ssg.sausagecartsharecalculationapi.cartsharecal.dto.request.CartShareCalUpdateRequest;
import java.time.LocalDateTime;
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
    @Column(name = "CART_SHARE_ID")
    private Long cartShareId;

    @Column(name = "MASTR_MBR_ID")
    private Long mastrMbrId;

    @Column(name = "CAL_ST_YN")
    private boolean calStYn;

    @Column(name = "CART_SHARE_NM")
    private String cartShareNm;

    @Column(name = "CART_SHARE_CAL_ST_DTS")
    private LocalDateTime cartShareCalStDts;

    @Column(name = "CART_SHARE_ORD_NO")
    private String cartShareOrdNo;

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

    public static CartShareCal newInstance(CartShareCalSaveRequest request) {
        return CartShareCal.builder()
                .cartShareOrdId(request.getCartShareOrdId())
                .cartShareId(request.getCartShareId())
                .mastrMbrId(request.getMastrMbrId())
                .calOptCd(CalOptCd.INPUT)
                .calStYn(false)
                .calRmd(0)
                .calAmt(0)
                .cartShareNm(request.getCartShareNm())
                .cartShareOrdNo(request.getCartShareNm())
                .ttlPaymtAmt(request.getTtlPaymtAmt())
                .build();
    }

    public void update(CartShareCalUpdateRequest request) {
        this.calOptCd = request.getCalOptCd();
        this.calRmd = request.getCalRmd();
        this.calAmt = request.getCalAmt();
    }

    public void start(){
        this.calStYn = true;
        this.cartShareCalStDts = LocalDateTime.now();
    }

}
