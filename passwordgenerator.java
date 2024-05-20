import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class passwordgenerator {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter the length of the letters: ");
        int charlength = input.nextInt();
        System.out.println("Enter the length of numbers: ");
        int numberlength = input.nextInt();
        System.out.println("Enter the length of symbols: ");
        int symbollength = input.nextInt();
        input.close();
        passwordgenerator e = new passwordgenerator();
        String pw = e.generatePassword(charlength, numberlength, symbollength);
        System.out.println("The password is " + pw);
    }

    String generatePassword(int charlength, int numberlength, int symbollength) {
        Random random = new Random();
        String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        String number = "0123456789";
        String symbol = "!@#$%^&*-_+=?";
        String pw = "";
        for (int i = 0; i < charlength; i++) {
            pw += letters.charAt(random.nextInt(letters.length()));
        }
        for (int i = 0; i < symbollength; i++) {
            pw += symbol.charAt(random.nextInt(symbol.length()));
        }
        for (int i = 0; i < numberlength; i++) {
            pw += number.charAt(random.nextInt(number.length()));
        }
        return shufflePassword(pw);

    }

    String shufflePassword(String pw) {
        List<Character> characters = new ArrayList<>();
        for (char c : pw.toCharArray()) {
            characters.add(c);
        }
        Collections.shuffle(characters);
        StringBuilder sb = new StringBuilder();
        for (char c : characters) {
            sb.append(c);
        }
        return sb.toString();
    }
}