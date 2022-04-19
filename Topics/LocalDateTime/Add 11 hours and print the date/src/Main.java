import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String inputString = sc.nextLine();
        LocalDateTime dateTime = LocalDateTime.parse(inputString);
        System.out.println(dateTime.plusHours(11).toLocalDate());

    }
}