package app.exception;

/**
 * @author ：林雾
 * @date ：2020/09/21
 * @description :
 */
public class AppException extends RuntimeException {
  private String msg;

  public AppException(String msg) {
    super(msg);
  }
}
