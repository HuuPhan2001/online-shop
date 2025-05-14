package vn.online.shop.onlineshop.common.config;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import vn.online.shop.onlineshop.common.util.StringUtils;
import vn.online.shop.onlineshop.entity.BaseModel;
import vn.online.shop.onlineshop.entity.User;
import vn.online.shop.onlineshop.exception.RestExceptionHandler;

import java.util.List;
import java.util.function.Function;

public class BusinessCommon {
    public static void validLengthData(String str, String column, int max) {
        if (!StringUtils.isNullOrEmpty(str) && str.length() > max) {
            throw new RestExceptionHandler(column + " chỉ tối đa " + max + " kí tự.");
        }
    }

    public static <T extends BaseModel> T validateUpdateStatus(StatusEnum status, T entity) {
        if (entity == null) {
            throw new RestExceptionHandler(Constant.WRONG_INPUT_DATA);
        }
        switch (status) {
            case ACTIVE:
                if (StatusEnum.ACTIVE == entity.getStatus()) {
                    throw new RestExceptionHandler(Constant.INVALID_DATA);
                }
                entity.setStatus(status);
                break;
            case INACTIVE:
                if (StatusEnum.INACTIVE == entity.getStatus()) {
                    throw new RestExceptionHandler(Constant.INVALID_DATA);
                }
                entity.setStatus(StatusEnum.INACTIVE);
                break;
            case DELETED:
                entity.setStatus(StatusEnum.DELETED);
                break;
            default:
                throw new RestExceptionHandler(Constant.INVALID_DATA);
        }
        return entity;
    }

    public static String validInputData(String str) {
        if (StringUtils.isNullOrEmpty(str)) {
            return null;
        }
        return str.toLowerCase();
    }

    @NonNull
    public static User getUser() {
        User u = SecurityContext.getCurrentUser();
        if (u == null) {
            throw new RestExceptionHandler(Constant.INVALID_ACCOUNT);
        }
        return u;
    }

    public static <T, U> Page<U> mapPage(Page<T> sourcePage, Function<T, U> mapper) {
        List<U> mappedContent = sourcePage.getContent()
                .stream()
                .map(mapper)
                .toList();
        return new PageImpl<>(mappedContent, sourcePage.getPageable(), sourcePage.getTotalElements());
    }

}
