package com.tomasky.departure.common.utils;

import com.tomasky.departure.BaseModel;
import com.tomasky.departure.cons.Constants;

import java.util.Date;

/**
 * Created by sam on 2019/6/14.9:28 PM
 */
public class BaseModelUtils<T extends BaseModel> {

    public void buildBaseEntity(T superEntity) {
        superEntity.setCreatedTime(new Date());
        // 新创建的数据版本号为1
        superEntity.setVersionNum(Constants.DATA_DEFAULT_VERSION);
        // 默认数据未逻辑删除
        superEntity.setDeleted(Constants.DATA_NOT_DELETE);
        // 默认数据有效
        superEntity.setIsValid(Constants.DATA_VALID);
    }

    public void buildCreateEntity(T superEntity, Integer userId) {
        // 设置创建者ID
        superEntity.setCreatedId(userId);
        buildBaseEntity(superEntity);
    }

    public void buildModifiyEntity(T superEntity, Integer userId) {
        // 设置创建者ID
        if (userId != null) {
            superEntity.setLastModifyId(userId);
        }
        superEntity.setLastModifyTime(new Date());
        Integer versionNum = superEntity.getVersionNum() == null ? Constants.DATA_DEFAULT_VERSION : superEntity.getVersionNum();
        superEntity.setVersionNum(versionNum + 1);
    }
}
