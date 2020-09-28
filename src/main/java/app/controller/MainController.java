package app.controller;

import app.view.ListView;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author ：林雾
 * @date ：2020/09/08
 * @description :
 */
@FXMLController
@Getter
public class MainController implements Initializable {

  @FXML private Tab shui;
  @FXML private Tab dian;
  @FXML private TabPane tabPane;
  @Autowired private ListController listController;
  @Autowired private ListView listView;

  private Tab currentSelectTab;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    // 默认第一个展示列表
    shui.setContent(listView.getView());

    // tab切换展示列表
    tabPane
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldTab, tab) -> {
              if (oldTab != null) {
                oldTab.setContent(null);
              }
              if (tab != null) {
                tab.setContent(listView.getView());
              }
              currentSelectTab = tab;

              listController.fushTable();
            });
    currentSelectTab = shui;
  }
}
