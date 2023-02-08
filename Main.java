import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);
        int n;
        do{
            System.out.println("1. Add User");
            System.out.println("2. Catalog");
            System.out.println("3. Make a purchase");
            System.out.println("4. Profile");
            System.out.println("5. Exit");
            n = scanner.nextInt();
            switch (n) {
                case 1 : electronics.add_user();break;
                case 2 : electronics.show_catalog();break;
                case 3 : electronics.purchase();break;
                case 4 : electronics.user_info();break;
                default : {break;
                }
            }

        }while(n!=5);
    }
}