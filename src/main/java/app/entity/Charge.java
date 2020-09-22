package app.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 林雾
 * @since 2020-09-21
 */
@Data
public class Charge implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 主键 */
  @TableId(value = "id", type = IdType.AUTO)
  private Integer id;

  /** 年份 */
  private Integer year;

  /** 月份 */
  private Integer month;

  private Integer room;

  /** 水费:电费 */
  private String type;

  /** 房间总收费 */
  private BigDecimal charge = BigDecimal.ZERO;

  /** APP真实缴费 */
  private BigDecimal realCharge = BigDecimal.ZERO;

  /** 盈亏 */
  private BigDecimal profit = BigDecimal.ZERO;

  /** 状态 */
  private String status;

  /** 上次抄表总值 */
  private Integer preCount = 0;

  /** 本次抄表总值 */
  private Integer currentCount = 0;

  /** 本次使用量 */
  private Integer useCount = 0;

  private Integer parentId;

  @TableField(exist = false)
  private List<Charge> children;

  public static final String ID = "id";

  public static final String YEAR = "year";

  public static final String MONTH = "month";

  public static final String ROOM = "room";

  public static final String TYPE = "type";

  public static final String CHARGE = "charge";

  public static final String REAL_CHARGE = "real_charge";

  public static final String PROFIT = "profit";

  public static final String STATUS = "status";

  public static final String PRE_COUNT = "pre_count";

  public static final String CURRENT_COUNT = "current_count";

  public static final String USE_COUNT = "use_count";

  public static final String PARENT_ID = "parent_id";
}
