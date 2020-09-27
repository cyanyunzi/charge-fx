package app.utils;

import com.google.common.io.Files;

/**
 * @author ：林雾
 * @date ：2020/09/27
 * @description :
 */
public class ExcelUtils {
  public static final String FILE_PATH = System.getProperty("user.dir")+"/excel";

  public static void create() {
    // 获取项目路径
    String property = System.getProperty("user.dir");
    System.out.println(property);
//    Files.createParentDirs(FILE_PATH);
    // 是否有文件

    // 加载文件
    // 构建表格
  }

  public static void main(String[] args) {
    create();
  }
}
