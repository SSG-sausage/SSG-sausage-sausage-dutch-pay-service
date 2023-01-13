package com.ssg.sausagedutchpayapi.dutchpay.entity;

import com.ssg.sausagedutchpayapi.common.entity.BaseEntity;
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

    @OneToMany(mappedBy = "dutchPay", cascade = CascadeType.ALL)
    private List<DutchPayDtl> dutchPayDtlList;

    public static DutchPay newInstance(Long cartShareOrdId) {
        return DutchPay.builder()
                .cartShareOrdId(cartShareOrdId)
                .build();
    }

}
