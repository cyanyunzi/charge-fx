package app.controller;

import app.entity.Charge;
import app.service.ChargeService;
import app.utils.ExUtils;
import app.utils.FxUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author ：林雾
 * @date ：2020/09/08
 * @description :
 */
@FXMLController
@Getter
public class DetailController implements Initializable {
  @FXML private TableView<Charge> table;
  @FXML private TableColumn<Charge, String> columnYear;
  @FXML private TableColumn<Charge, String> columnMonth;
  @FXML private TableColumn<Charge, String> columnType;
  @FXML private TableColumn<Charge, String> columnRoom;
  @FXML private TableColumn<Charge, String> columnPre;
  @FXML private TableColumn<Charge, String> columnCurrent;
  @FXML private TableColumn<Charge, String> columnCount;
  @FXML private TableColumn<Charge, String> columnCharge;
  @FXML private TableColumn<Charge, String> columnStatus;

  @Autowired private MainController mainController;
  @Autowired private ListController listController;
  @Autowired private ChargeService chargeService;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    initColumn();
    // 填充数据
    Charge charge = listController.getTable().getSelectionModel().getSelectedItem();
    ExUtils.requireNotNull(charge, "请选择一行数据");

    QueryWrapper wrapper = new QueryWrapper();
    wrapper.eq(Charge.PARENT_ID, charge.getId());
    List list = chargeService.list(wrapper);
    table.getItems().addAll(list);
  }

  @FXML
  public void settleDetail() {
    Charge charge = table.getSelectionModel().getSelectedItem();
    ExUtils.requireNotNull(charge, "请选择一行数据");

    charge.setStatus("已结算");
    chargeService.updateById(charge);

    QueryWrapper wrapper = new QueryWrapper();
    wrapper.eq(Charge.PARENT_ID, charge.getParentId());
    List list = chargeService.list(wrapper);

    table.getItems().clear();
    table.getItems().addAll(list);
  }

  public void rest() {
    table.getItems().clear();
  }

  public void initColumn() {
    columnYear.setCellValueFactory(new PropertyValueFactory("year"));
    columnMonth.setCellValueFactory(new PropertyValueFactory("month"));
    columnType.setCellValueFactory(new PropertyValueFactory("type"));
    columnRoom.setCellValueFactory(new PropertyValueFactory("room"));
    columnPre.setCellValueFactory(new PropertyValueFactory("preCount"));
    columnCurrent.setCellValueFactory(new PropertyValueFactory("currentCount"));
    columnCount.setCellValueFactory(new PropertyValueFactory("useCount"));
    columnCharge.setCellValueFactory(new PropertyValueFactory("charge"));
    columnStatus.setCellValueFactory(new PropertyValueFactory("status"));
  }
}
