package app.controller;

import app.contants.Contant;
import app.entity.Charge;
import app.service.ChargeService;
import app.utils.ExUtils;
import app.utils.FxUtils;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.sun.javafx.collections.ObservableListWrapper;
import de.felixroske.jfxsupport.FXMLController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author ：林雾
 * @date ：2020/09/08
 * @description :
 */
@FXMLController
@Getter
public class AddController implements Initializable {
  @FXML private ChoiceBox<Integer> yearSelect;
  @FXML private ChoiceBox<Integer> monthSelect;
  @FXML private ChoiceBox<Integer> roomSelect;
  @FXML private TextField currentCount;
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
    yearSelect.setItems(new ObservableListWrapper(Contant.YEAR_LIST));
    monthSelect.setItems(new ObservableListWrapper(Contant.MONTH_LIST));
    roomSelect.setItems(new ObservableListWrapper(Contant.ROOM_LIST));
    initColumn();
  }

  @FXML
  public void addChildToTable() {
    Integer year = yearSelect.getValue();
    ExUtils.requireNotNull(year, "请选择年份");

    Integer month = monthSelect.getValue();
    ExUtils.requireNotNull(month, "请选择月份");

    Integer room = roomSelect.getValue();
    ExUtils.requireNotNull(room, "请选择房间号");

    Charge next = null;
    if (!CollectionUtils.isEmpty(table.getItems())) {
      next = table.getItems().get(0);
    }

    if (next != null) {
      ExUtils.requireEquals(year, next.getYear(), "流水年份不一致");
      ExUtils.requireEquals(month, next.getMonth(), "流水月份不一致");
    }

    Set<Integer> rooms =
        table.getItems().stream().map(bean -> bean.getRoom()).collect(Collectors.toSet());
    ExUtils.requireNotContains(rooms, room, "房间号有重复数据");

    String text = currentCount.getText();
    Integer currentCountValue = Ints.tryParse(text);
    ExUtils.requireNotNull(currentCountValue, "抄表值有误");

    Charge charge =
        chargeService.addChildToTable(
            year, month, room, currentCountValue, mainController.getCurrentSelectTab().getText());
    table.getItems().add(charge);
  }

  @FXML
  public void deleteDetail() {
    Charge charge = table.getSelectionModel().getSelectedItem();
    ExUtils.requireNotNull(charge, "请选择一行数据");
    table.getItems().remove(charge);
  }

  @FXML
  public void saveMainCharge() {
    ObservableList<Charge> items = table.getItems();
    ExUtils.notEmpty(items, "流水表格中没有数据可以生成主账单");

    Charge next = table.getItems().iterator().next();
    // 主张单是否重复
    Charge distint =
        chargeService.selectOneByParams(next.getYear(), next.getMonth(), 401, next.getType());
    ExUtils.requireNull(distint, "已存在相同年月的主账单");

    chargeService.saveMainCharge(
        next.getYear(),
        next.getMonth(),
        mainController.getCurrentSelectTab().getText(),
        table.getItems());

    listController.fushTable();

    // 保存完清空子流水
    table.getItems().clear();
  }

  @FXML
  public void settleDetail() {
    Charge chargeDTO = table.getSelectionModel().getSelectedItem();
    if (chargeDTO == null) {
      FxUtils.alertWarning("结算账单流水", "请选择一行数据");
      return;
    }

    List<Charge> dtos = Lists.newArrayList(table.getItems());
    int index = dtos.indexOf(chargeDTO);

    chargeDTO.setStatus("已结算");
    dtos.set(index, chargeDTO);

    table.getItems().clear();
    table.getItems().addAll(dtos);
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
