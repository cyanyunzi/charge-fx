package app.utils;

import app.exception.AppException;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

/**
 * @author ：林雾
 * @date ：2020/09/21
 * @description :
 */
public class ExUtils {
  public static void requireNull(Object obj, String msg) {
    if (obj != null) {
      throw new AppException(msg);
    }
  }

  public static void requireNotNull(Object obj, String msg) {
    if (obj == null) {
      throw new AppException(msg);
    }
  }

  public static void requireEquals(Object o1, Object o2, String msg) {
    if (!o1.equals(o2)) {
      throw new AppException(msg);
    }
  }

  public static void requireNotContains(Collection collection, Object o1, String msg) {
    if (collection.contains(o1)) {
      throw new AppException(msg);
    }
  }

  public static void notEmpty(Collection collection, String msg) {
    if (CollectionUtils.isEmpty(collection)) {
      throw new AppException(msg);
    }
  }

  public static void requireFalse(boolean flag, String msg) {
    if (flag) {
      throw new AppException(msg);
    }
  }

}
