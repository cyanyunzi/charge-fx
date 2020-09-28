package app.utils;

import app.model.dto.ChargeExCel;
import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import com.google.common.io.Files;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：林雾
 * @date ：2020/09/27
 * @description :
 */
public class ExcelUtils {

  public static String getPowerFilePath() {
    return System.getProperty("user.dir") + "\\excel\\电费账单.xlsx";
  }

  public static String getWaterFilePath() {
    return System.getProperty("user.dir") + "\\excel\\水费账单.xlsx";
  }

  public static void createParentDirs(String path) throws IOException {
    Files.createParentDirs(new File(path));
  }

  public static void main(String[] args) throws IOException {
    List<ChargeExCel> list = Lists.newArrayList();
    for (int i = 0; i < 10; i++) {
      ChargeExCel ex = new ChargeExCel();
      ex.setYear(i);
      ex.setMonth(i);
      ex.setType("" + i);
      ex.setRoom(i);
      ex.setPreCount(i);
      ex.setCurrentCount(i);
      ex.setUseCount(i);
      ex.setCharge(new BigDecimal(i));
      ex.setStatus("" + i);
      list.add(ex);
    }

    String fileName = getPowerFilePath();
    createParentDirs(fileName);
    EasyExcel.write(fileName, ChargeExCel.class).sheet("模板").doWrite(list);
  }
}
