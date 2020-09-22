package app.model.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author ：林雾
 * @date ：2020/09/08
 * @description :
 */
@Data
public class ChargeDTO {
  private Integer year;
  private Integer month;
  private String type;
  private Integer room;
  private Integer preCount;
  private Integer currentCount;
  private Integer useCount;
  private BigDecimal charge;
  private String status ;

  private List<ChargeDTO> children = Lists.newArrayList();
}
