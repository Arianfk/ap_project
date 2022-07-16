class Cat {

  // write static and instance variables
  public String name;
  public int age;
  public static int counter;

  public Cat(String name, int age) {
    // implement the constructor
    // do not forgot to check the number of cats
    this.name = name;
    this.age = age;
    counter++;
    if (counter > 5) {
      System.out.println("You have too many cats");
    }
  }

  public static int getNumberOfCats() {
    // implement the static method
    return counter;
  }
}