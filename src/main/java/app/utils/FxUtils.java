package app.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Optional;

/**
 * @author ：林雾
 * @date ：2020/09/08
 * @description :
 */
public class FxUtils {
  private static Stage stage = null;

  public static void setRoot(Stage primaryStage) {
    stage = primaryStage;
  }

  public static Stage getRoot() {
    return stage;
  }


  public static FXMLLoader getFXMLLoader(String fxml) {
    ClassLoader classLoader = FxUtils.class.getClassLoader();
    URL location = classLoader.getResource(fxml);
    FXMLLoader fxmlLoader = new FXMLLoader();
    fxmlLoader.setLocation(location);
    return fxmlLoader;
  }

  public static Alert alertConfirm(String title,String ContentText){
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle(title);
    alert.setHeaderText("确认信息!!!");
    alert.setContentText(ContentText);
    return alert;
  }

  public static Alert alertWarning(String title,String ContentText){
    Alert alert = new Alert(Alert.AlertType.WARNING);
    alert.setTitle(title);
    alert.setHeaderText("警告信息");
    alert.setContentText(ContentText);
    alert.showAndWait();
    return alert;
  }

  public static Alert alertError(String ContentText){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setHeaderText("错误信息");
    alert.setContentText(ContentText);
    alert.showAndWait();
    return alert;
  }

}
