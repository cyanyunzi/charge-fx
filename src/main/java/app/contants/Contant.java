package app.contants;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author ：林雾
 * @date ：2020/09/08
 * @description :
 */
public class Contant {
  public static final List<Integer> YEAR_LIST = Lists.newArrayList(2020, 2021, 2022);

  public static final List<Integer> MONTH_LIST =
      Lists.newArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);

  public static final List<Integer> ROOM_LIST = Lists.newArrayList(4011, 4012, 4013, 4014);
  public static final List<String> STATUS_LIST = Lists.newArrayList("已结算","未结算");

  public static final String water = "水费账单";
  public static final String power = "电费账单";
}
