package com.lengyue.databindingbase.common;

import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final int REQUEST_CODE_PAY_STEP = 1000;
    public static final int REQUEST_CODE_REPAYMENT = 1001;

    public static final String INTENT_DATA_IS_SUCCESS = "isSuccess";
    public static final String INTENT_DATA_PAY_ORDER = "payOrder";

    public static final String HTTP_CODE_TOKEN_OVERDUE = "402";

    public static final String AES_KEY = "fipluskenya";


    public static List<String> getGenders(){
        List<String> list = new ArrayList<>();
        list.add("男");
        list.add("女");
        return list;
    }
}
