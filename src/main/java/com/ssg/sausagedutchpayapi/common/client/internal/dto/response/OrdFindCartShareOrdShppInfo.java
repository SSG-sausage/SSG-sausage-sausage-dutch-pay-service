package com.ssg.sausagedutchpayapi.common.client.internal.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class OrdFindCartShareOrdShppInfo {

    private int shppCst;

    private List<Long> mbrIdList;

    private int shppQuot;

    public void setShppQuot(int shppQuot) {
        this.shppQuot = shppQuot;
    }

}
