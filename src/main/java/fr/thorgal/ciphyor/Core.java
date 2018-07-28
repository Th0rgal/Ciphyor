package fr.thorgal.ciphyor;

import java.io.UnsupportedEncodingException;
import java.util.Scanner;

public class Core {

    public static void main(String[] args) throws UnsupportedEncodingException {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("Enter a key: ");
            String key = scanner.nextLine();
            System.out.println("Enter a message: ");
            String input = scanner.nextLine();

            System.out.println("Type 0 for exit, 1 for cipher or 2 for decipher");
            switch (scanner.nextLine()) {
                case "0":
                    return;
                case "1":
                    System.out.println("Ciphered message: " + Ciphyor.encode(key, input));
                    break;
                case "2":
                    System.out.println("Deciphered message: " + Ciphyor.decode(key, input));
                    break;
                default:
                    System.out.println("You can only type 0, 1 or 2 !");
                    break;
            }
        }
    }
}
