// You can experiment here, it won’t be checked

import java.util.Arrays;

public class Task {
  static int y;

  public static void main(String[] args) {
    int[] x = {10, 11, 12};
    myMethod(x);
    System.out.println(Arrays.toString(x));
    System.out.println(y);
  }

  public static void myMethod(int[] x) {
    y = 10;
    x[0] += x[1];
    if (y == 1) {
      return;
    } else {
      System.out.println("Salaam");
    }
  }
}
