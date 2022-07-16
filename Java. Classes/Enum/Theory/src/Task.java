// You can experiment here, it won’t be checked

public class Task {
  public static void main(String[] args) {
    TransactionStatus status = TransactionStatus.COMPLETED;
    System.out.println(Integer.valueOf("123"));
  }
}

enum TransactionStatus {
  WAITING(false),
  PROCESSING(false),
  COMPLETED(true),
  ERROR(true);

  private boolean done;

  TransactionStatus(boolean done) {
    this.done = done;
  }

  public boolean isDone() {
    return done;
  }
}
