package com.tomasky.departure.service.impl;

import com.tomasky.departure.common.utils.CommonUtils;
import com.tomasky.departure.enums.DepartureReasonEnum;
import com.tomasky.departure.enums.OfficialDepartureReasonEnum;
import com.tomasky.departure.enums.PersonalDepartureReasonEnum;
import com.tomasky.departure.enums.SendTypeEnum;
import com.tomasky.departure.service.CommonService;
import com.tomasky.departure.vo.SelectVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sam on 2019-08-09.10:46
 */
@Service
public class CommonServiceImpl implements CommonService {
    @Override
    public Map<String, Object> findSelectList(String type) {
        List<SelectVo> selectVoList = new ArrayList<>();
        try {
            switch (type) {
                case "officialDepartureReason":
                    for (OfficialDepartureReasonEnum enumOption : OfficialDepartureReasonEnum.values()) {
                        selectVoList.add(new SelectVo(enumOption.getValue(), enumOption.getName()));
                    }
                    break;
                case "sendType":
                    for (SendTypeEnum enumOption : SendTypeEnum.values()) {
                        selectVoList.add(new SelectVo(enumOption.getValue(), enumOption.getName()));
                    }
                    break;
                case "personalDepartureReason":
                    for (PersonalDepartureReasonEnum enumOption : PersonalDepartureReasonEnum.values()) {
                        selectVoList.add(new SelectVo(enumOption.getValue(), enumOption.getName()));
                    }
                    break;
                case "departureReasonEnum":
                    for (DepartureReasonEnum enumOption : DepartureReasonEnum.values()) {
                        selectVoList.add(new SelectVo(enumOption.getValue(), enumOption.getName()));
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return CommonUtils.setErrorInfo(e);
        }
        return CommonUtils.setSuccessInfo(selectVoList);
    }

}
