package vn.online.shop.onlineshop.common.config;

import vn.online.shop.onlineshop.common.util.StringUtils;
import vn.online.shop.onlineshop.exception.RestExceptionHandler;

public class BusinessCommon {
    public static void validLengthData(String str, String column, int max) {
        if (!StringUtils.isNullOrEmpty(str) && str.length() > max) {
            throw new RestExceptionHandler(column + " chỉ tối đa " + max + " kí tự.");
        }
    }
}
