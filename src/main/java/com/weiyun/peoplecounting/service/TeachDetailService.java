package com.weiyun.peoplecounting.service;

import com.weiyun.peoplecounting.error.BusinessException;
import com.weiyun.peoplecounting.pojo.TeachDetail;
import com.weiyun.peoplecounting.response.CommonReturnType;

public interface TeachDetailService {

    CommonReturnType addTeachAnfo(TeachDetail teachDetail, String userId) throws BusinessException;

}
