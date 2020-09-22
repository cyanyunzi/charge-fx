package app;

import app.utils.FxUtils;
import app.view.MainView;
import de.felixroske.jfxsupport.AbstractJavaFxApplicationSupport;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ：林雾
 * @date ：2020/09/08
 * @description :
 */
@SpringBootApplication
@MapperScan("app.mapper")
public class Start extends AbstractJavaFxApplicationSupport {

  public static void main(String[] args) {
    Thread.setDefaultUncaughtExceptionHandler(
        (Thread t, Throwable e) -> {
          Throwable cause = e.getCause().getCause();
          FxUtils.alertError(cause.getMessage());
        });
    launch(Start.class, MainView.class, args);
  }
}
