package app.model.dto;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * @author ：林雾
 * @date ：2020/09/08
 * @description :
 */
@Data
public class ChargeDetailDTO {
  private String year;
  private String month;
  private String room;
  private String value;
}
