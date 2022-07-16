class Application {

  // write a field here
  String name;
  // write a constructor here
  public Application(String name) {
    this.name = name;
  }
  public void run(String[] args) {
    System.out.println(this.name);
    for (String arg : args) {
      System.out.println(arg);
    }
  }
}