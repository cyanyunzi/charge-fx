package app.service;

import app.contants.Contant;
import app.entity.Charge;
import app.mapper.ChargeMapper;
import app.model.dto.ChargeDTO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javafx.collections.ObservableList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 服务实现类
 *
 * @author 林雾
 * @since 2020-09-21
 */
@Service
public class ChargeService extends ServiceImpl<ChargeMapper, Charge> {

  public Charge selectOneByParams(Integer year, Integer month, Integer room, String type) {
    QueryWrapper wrapper = new QueryWrapper();
    wrapper.eq(Charge.YEAR, year);
    wrapper.eq(Charge.MONTH, month);
    wrapper.eq(Charge.ROOM, room);
    wrapper.eq(Charge.TYPE, type);

    return getOne(wrapper);
  }

  public Charge selectPre(Integer year, Integer month, Integer room, String type) {
    int currentYear = LocalDate.now().getYear();

    if (year < currentYear) {
      return null;
    }

    if (month == 0) {
      month = 12;
      year = year - 1;
    }

    QueryWrapper wrapper = new QueryWrapper();
    wrapper.eq(Charge.YEAR, year);
    wrapper.eq(Charge.MONTH, month);
    wrapper.eq(Charge.ROOM, room);
    wrapper.eq(Charge.TYPE, type);

    Charge pre = getOne(wrapper);
    if (pre == null) {
      return selectPre(year, month - 1, room, type);
    }
    return pre;
  }

  public Charge addChildToTable(
      Integer year, Integer month, Integer room, Integer currentCountValue, String type) {

    Charge tableChild = new Charge();
    tableChild.setYear(year);
    tableChild.setMonth(month);
    tableChild.setType(type);
    tableChild.setRoom(room);
    tableChild.setCurrentCount(currentCountValue);
    tableChild.setUseCount(currentCountValue);
    tableChild.setStatus("未结算");

    // 获取上一次流水
    Charge preCharge = selectPre(year, month, room, type);
    if (preCharge == null) {
      if (Contant.water.equals(type)) {
        tableChild.setCharge(new BigDecimal(6).multiply(new BigDecimal(tableChild.getUseCount())));
      }

      if (Contant.power.equals(type)) {
        tableChild.setCharge(new BigDecimal(1).multiply(new BigDecimal(tableChild.getUseCount())));
      }
      return tableChild;
    } else {
      tableChild.setPreCount(preCharge.getCurrentCount());
      tableChild.setUseCount(currentCountValue - preCharge.getCurrentCount());

      if (Contant.water.equals(type)) {
        tableChild.setCharge(new BigDecimal(6).multiply(new BigDecimal(tableChild.getUseCount())));
      }

      if (Contant.power.equals(type)) {
        tableChild.setCharge(new BigDecimal(1).multiply(new BigDecimal(tableChild.getUseCount())));
      }
    }
    return tableChild;
  }

  public ChargeDTO saveMainCharge(
      Integer year, Integer month, String type, ObservableList<Charge> children) {
    ChargeDTO mainDTO = new ChargeDTO();
    mainDTO.setYear(year);
    mainDTO.setMonth(month);
    mainDTO.setType(type);
    mainDTO.setRoom(401);
    mainDTO.setPreCount(
        children.stream().map(bean -> bean.getPreCount()).reduce((a, b) -> a + b).orElse(0));
    mainDTO.setCurrentCount(
        children.stream().map(bean -> bean.getCurrentCount()).reduce((a, b) -> a + b).orElse(0));
    mainDTO.setUseCount(
        children.stream().map(bean -> bean.getUseCount()).reduce((a, b) -> a + b).orElse(0));
    mainDTO.setCharge(
        children.stream()
            .map(bean -> bean.getCharge())
            .reduce((a, b) -> a.add(b))
            .orElse(BigDecimal.ZERO));
    mainDTO.setStatus("未结算");

    // 保存主流水
    Charge parent_charge = new Charge();
    BeanUtils.copyProperties(mainDTO, parent_charge);

    BigDecimal realCharge = children.stream().map(bean -> bean.getCharge()).reduce((a, b) -> a.add(b)).orElse(BigDecimal.ZERO);
    parent_charge.setRealCharge(realCharge);
    save(parent_charge);

    // 保存子流水
    for (Charge child : children) {
      child.setParentId(parent_charge.getId());
      save(child);
    }

    return mainDTO;
  }
}
