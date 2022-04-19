import java.time.LocalDate;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String dateString = sc.nextLine();
        LocalDate date = LocalDate.of(Integer.parseInt(dateString.split("-")[0]),
                Integer.parseInt(dateString.split("-")[1]),
                Integer.parseInt(dateString.split("-")[2]));
        System.out.println(date.plusWeeks(2));
    }
}