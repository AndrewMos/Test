import java.util.*;
import java.io.*;

public class main {

    public static void refreshfile() {

    }

    public static Person inputinfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your city: ");
        String city = scanner.nextLine();
        System.out.print("Enter your data: ");
        String data = scanner.nextLine();
        String name = data.split(" ")[0];
        String surname = data.split(" ")[1];
        String pesel = data.split(" ")[2];
        Person person = new Person(name, surname, pesel, city);
        if (checkpesel(pesel)) {
            return person;
        } else {
            return null;
        }



    }

    public static boolean checkpesel(String pesel) {
        int[] check = {1, 3, 7, 9, 1, 3, 7, 9, 1, 3};

        String peselarray[] = pesel.split("");
        int numpesel[] = new int[peselarray.length];
        for (int i = 0; i < peselarray.length; i++) {
            numpesel[i] = Integer.parseInt(peselarray[i]);
        }

        if (numpesel.length != 11) {
            System.out.println("Error");
            return false;
        } else {
            int temp = 0;
            for (int i = 0; i < 10; i ++) {
                temp += numpesel[i] * check[i];
            }
            temp = temp % 10;
            if (temp != 0) {
                temp = 10 - temp;
            }
            if (temp != numpesel[10]) {
                System.out.println("ERROR");
                return false;
            } else {
                return true;
            }
        }
    }


    public static void main(String[] args){

        List<Person> people = new ArrayList<Person>();
        while (true) {
            Person person;
            person = inputinfo();
            if (person != null) {
                for (Person p:people) {
                    if (person.pesel.equals(p.pesel)) {
                        people.remove(p);
                        break;
                    }
                }
                people.add(person);

            }
        }
    }
}
