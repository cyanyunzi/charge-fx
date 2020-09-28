package app.model.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author ：林雾
 * @date ：2020/09/08
 * @description :
 */
@Data
public class ChargeExCel {
  @ExcelProperty("年份")
  private Integer year;

  @ExcelProperty("月份")
  private Integer month;

  @ExcelProperty("缴费类型")
  private String type;

  @ExcelProperty("房间号")
  private Integer room;

  @ExcelProperty("上次抄表值")
  private Integer preCount;

  @ExcelProperty("本次抄表值")
  private Integer currentCount;

  @ExcelProperty("使用量")
  private Integer useCount;

  @ExcelProperty("收费")
  private BigDecimal charge;

  @ExcelProperty("状态")
  private String status;
}
