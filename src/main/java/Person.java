public class Person {
    String name;
    String surname;
    String pesel;
    String city;

    public Person(String name, String surname, String pesel, String city) {//constructor
        this.name = name;//wskaznik do zmiennych
        this.surname = surname;
        this.pesel = pesel;
        this.city = city;
    }
}
