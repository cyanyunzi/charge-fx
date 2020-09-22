package app.controller;

import app.Start;
import app.contants.Contant;
import app.entity.Charge;
import app.service.ChargeService;
import app.utils.ExUtils;
import app.utils.FxUtils;
import app.view.AddView;
import app.view.DetailView;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sun.javafx.collections.ObservableListWrapper;
import de.felixroske.jfxsupport.FXMLController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Modality;
import javafx.util.converter.BigDecimalStringConverter;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author ：林雾
 * @date ：2020/09/08
 * @description :
 */
@FXMLController
@Getter
@Setter
public class ListController implements Initializable {
  @FXML private ChoiceBox<Integer> yearSelect;
  @FXML private ChoiceBox<Integer> monthSelect;
  @FXML private ChoiceBox<String> statusSelect;

  @FXML private TableView<Charge> table;
  @FXML private TableColumn<Charge, String> columnYear;
  @FXML private TableColumn<Charge, String> columnMonth;
  @FXML private TableColumn<Charge, String> columnType;
  @FXML private TableColumn<Charge, String> columnRoom;
  @FXML private TableColumn<Charge, String> columnPre;
  @FXML private TableColumn<Charge, String> columnCurrent;
  @FXML private TableColumn<Charge, String> columnCount;
  @FXML private TableColumn<Charge, String> columnCharge;
  @FXML private TableColumn<Charge, BigDecimal> columnRealCharge;
  @FXML private TableColumn<Charge, String> columnStatus;
  @FXML private TableColumn<Charge, String> columnProfit;

  @Autowired private AddController addController;
  @Autowired private DetailController detailController;
  @Autowired private ChargeService chargeService;
  @Autowired private MainController mainController;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    yearSelect.setItems(new ObservableListWrapper(Contant.YEAR_LIST));
    monthSelect.setItems(new ObservableListWrapper(Contant.MONTH_LIST));
    statusSelect.setItems(new ObservableListWrapper(Contant.STATUS_LIST));
    table.setEditable(true);

    initColumn();
    fushTable();
  }

  @FXML
  public void addView() {
    Start.showView(AddView.class, Modality.WINDOW_MODAL);
  }

  @FXML
  public void detailView() {
    Charge charge = table.getSelectionModel().getSelectedItem();
    ExUtils.requireNotNull(charge, "请选择一行数据");

    if (detailController.getTable() != null) {
      detailController.rest();
      QueryWrapper wrapper = new QueryWrapper();
      wrapper.eq(Charge.PARENT_ID, charge.getId());
      List list = chargeService.list(wrapper);
      detailController.getTable().getItems().addAll(list);
    }

    // 弹出视图
    Start.showView(DetailView.class, Modality.WINDOW_MODAL);
  }

  @FXML
  public void updateStatus() {
    Charge charge = table.getSelectionModel().getSelectedItem();
    ExUtils.requireNotNull(charge, "请选择一行数据");

    QueryWrapper wrapper = new QueryWrapper();
    wrapper.eq(Charge.PARENT_ID, charge.getId());
    List<Charge> list = chargeService.list(wrapper);
    for (Charge child : list) {
      String format = String.format("房间号:%s 未结算,主账单不允许结算", child.getRoom());
      ExUtils.requireFalse("未结算".equals(child.getStatus()), format);
    }

    Alert alert = FxUtils.alertConfirm("结算账单", "确认房东已结算账单?");
    Optional<ButtonType> alertResult = alert.showAndWait();
    if (alertResult.get() == ButtonType.OK) {
      charge.setStatus("已结算");
      chargeService.updateById(charge);
      fushTable();
    } else {
      alert.close();
    }
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
    columnProfit.setCellValueFactory(new PropertyValueFactory("profit"));
    columnRealCharge.setCellValueFactory(new PropertyValueFactory("realCharge"));
    columnRealCharge.setCellFactory(
        TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
  }

  @FXML
  public void updateRealCharge(TableColumn.CellEditEvent<Charge, BigDecimal> value) {
    Charge charge = value.getRowValue();
    BigDecimal newValue = value.getNewValue();
    charge.setRealCharge(newValue);
    charge.setProfit(charge.getCharge().subtract(charge.getRealCharge()));
    chargeService.updateById(charge);
    fushTable();
  }

  @FXML
  public void fushTable() {
    QueryWrapper<Charge> wrapper = new QueryWrapper();
    if (mainController.getCurrentSelectTab() != null
        && StringUtils.isNotBlank(mainController.getCurrentSelectTab().getText())) {
      wrapper.eq(Charge.TYPE, mainController.getCurrentSelectTab().getText());
    }
    if (StringUtils.isNotBlank(statusSelect.getValue())) {
      wrapper.eq(Charge.STATUS, statusSelect.getValue());
    }
    if (yearSelect.getValue() != null) {
      wrapper.eq(Charge.YEAR, yearSelect.getValue());
    }
    if (monthSelect.getValue() != null) {
      wrapper.eq(Charge.MONTH, monthSelect.getValue());
    }
    wrapper.eq(Charge.ROOM, 401);
    wrapper.eq(Charge.PARENT_ID, 0);

    List<Charge> charges = chargeService.list(wrapper);
    getTable().getItems().clear();
    getTable().getItems().addAll(charges);
  }

  @FXML
  public void deleteMain() {
    Charge charge = table.getSelectionModel().getSelectedItem();
    ExUtils.requireNotNull(charge, "请选择一行数据");
    Integer chargeId = charge.getId();
    chargeService.removeById(chargeId);

    QueryWrapper wrapper = new QueryWrapper();
    wrapper.eq(Charge.PARENT_ID, chargeId);
    chargeService.remove(wrapper);

    fushTable();
  }

  @FXML
  public void rest() {
    yearSelect.setValue(null);
    monthSelect.setValue(null);
    statusSelect.setValue(null);
    fushTable();
  }
}
