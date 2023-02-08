
import javax.sound.sampled.spi.AudioFileReader;
import java.sql.*;
import java.time.Period;
import java.util.Scanner;

public class electronics {
    private static final String url="jdbc:postgresql://localhost:5432/electronics";
    private static final String username = "postgres";
    private static final String password = "180204";
    static Scanner scanner = new Scanner(System.in);
    static int uid=0;




    public static void add_user() throws Exception{
        Connection connection = DriverManager.getConnection(url,username,password);
        System.out.println("Enter your name:");
        String name = scanner.nextLine();
        System.out.println("Balance:");
        int balance = scanner.nextInt();

        String q = "insert into users(name,balance) values(?,?);";
        PreparedStatement prep = connection.prepareStatement(q);
        prep.setString(1,name);
        prep.setInt(2,balance);
        prep.executeUpdate();

        String getid = "select id from users where name = ?";
        PreparedStatement p = connection.prepareStatement(getid);
        p.setString(1,name);
        ResultSet res = p.executeQuery();
        if(res.next()){
            uid = res.getInt("id");
        }
        user_info();
        connection.close();

    }


    public static void show_catalog()throws Exception{
        int c;
        do{
            System.out.println("1. Phones");
            System.out.println("2. Headphones");
            System.out.println("3. Accessories");
            System.out.println("4. Exit");

            c = scanner.nextInt();

            switch (c){
                case 1:show_phones();break;
                case 2:show_headphones();break;
                case 3:show_accessories();break;
                default:break;
            }

        }while(c!=4);
        scanner.nextLine();
    }
    public static void show_phones() throws Exception{
        Connection connection = DriverManager.getConnection(url,username,password);

        System.out.println("1. iPhone");
        System.out.println("2. Samsung");
        System.out.println("3. Oppo");
        System.out.println("4. Exit");
        System.out.println("Choose brand:");
        int c = scanner.nextInt();
        String q="";
        switch (c){
            case 1:  q = "select * from phones where brand='IPhone'";break;
            case 2:  q = "select * from phones where brand='Samsung'";break;
            case 3:  q = "select * from phones where brand='Xiaomi'";break;
        }

      
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(q);
        while (res.next()){
            System.out.println("ID: "+res.getInt("id")+" Brand: "+res.getString("brand")+" Model: "+res.getString("model")+" Memory: "+res.getInt("ROM")+" Color: "+res.getString("color")+" Price: "+res.getInt("price"));
        }
        connection.close();
        scanner.nextLine();
    }
    public static void show_headphones() throws Exception{
        Connection connection = DriverManager.getConnection(url,username,password);
        String q = "select * from headphones";
        Statement stmt = connection.createStatement();
        ResultSet res = stmt.executeQuery(q);
        while (res.next()){
            System.out.println("ID: "+res.getInt("id")+" Brand: "+res.getString("brand")+" Model: "+res.getString("model")+" Colour: "+res.getString("colour")+" Price: "+res.getInt("price"));
        }
        connection.close();
    }
    public static void show_accessories() throws Exception{
        Connection connection = DriverManager.getConnection(url,username,password);
        int c;
        do {
            System.out.println("1. Chargers");
            System.out.println("2. Power-banks");
            System.out.println("3. Exit");
            c = scanner.nextInt();
            switch (c){
                case 1:
                    String charger = "select * from chargers";
                    Statement stmt = connection.createStatement();
                    ResultSet res = stmt.executeQuery(charger);
                    while (res.next()){
                        System.out.println("ID: "+res.getInt("id")+" Type: "+res.getString("type")+" Length: "+res.getString("length")+" Colour: "+res.getString("colour")+ " Price: "+res.getInt("price"));
                    }
                    break;
                case 2:
                    String power = "select * from powerbank";
                    Statement stmt1 = connection.createStatement();
                    ResultSet res1 = stmt1.executeQuery(power);
                    while(res1.next()){
                        System.out.println("ID: "+res1.getInt("id")+" Model: "+res1.getString("model")+" Colour: "+res1.getString("colour")+" Volume: "+res1.getInt("volume")+" Price: "+res1.getInt("price"));
                    }
                    break;
                default:break;
            }
        }while(c!=3);

        connection.close();
        scanner.nextLine();
    }

 
    public static void purchase() throws Exception{
        Connection connection = DriverManager.getConnection(url,username,password);

        String ui = "select balance from users where id=?";
        PreparedStatement prep = connection.prepareStatement(ui);
        prep.setInt(1,uid);
        ResultSet r = prep.executeQuery();
        int balance=0;
        if(r.next()){
            balance=r.getInt("balance");
        }


        System.out.println("Choose category");
        System.out.println("1.Phone");
        System.out.println("2.Headphones");
        System.out.println("3.Charger");
        System.out.println("4.Powerbank");



        int cat = scanner.nextInt();
        switch (cat){
            case 1:
                show_phones();
                String q="select price from phones where id =?";
                PreparedStatement p = connection.prepareStatement(q);
                System.out.println("Enter product id: ");
                int pid= scanner.nextInt();
                p.setInt(1,pid);
                ResultSet res = p.executeQuery();
                int sum=0;
                if(res.next()) {sum =res.getInt("price");
                }
                int remainder = balance - sum;

                String q1 = "update users set balance=? where id=?";
                PreparedStatement p1 = connection.prepareStatement(q1);
                p1.setInt(1,remainder);
                p1.setInt(2,uid);
                p1.executeUpdate();

                String q2 = "insert into purchases(userid,total) values(?,?);";
                PreparedStatement p2 = connection.prepareStatement(q2);
                p2.setInt(1,uid);
                p2.setInt(2,sum);
                p2.executeUpdate();

                purchase_info(sum);
                break;
            case 2:
                show_headphones();
                String q3="select price from headphones where id =?";
                PreparedStatement p3 = connection.prepareStatement(q3);
                System.out.println("Enter product id: ");
                int pid3= scanner.nextInt();
                p3.setInt(1,pid3);
                ResultSet res3 = p3.executeQuery();
                int sum3=0;
                if(res3.next()){sum3= res3.getInt("price");}
                int remainder3 = balance - sum3;

                String q4 = "update users set balance=? where id=?";
                PreparedStatement p4 = connection.prepareStatement(q4);
                p4.setInt(1,remainder3);
                p4.setInt(2,uid);
                p4.executeUpdate();

                String q5 = "insert into purchases(userid,total) values(?,?);";
                PreparedStatement p5 = connection.prepareStatement(q5);
                p5.setInt(1,uid);
                p5.setInt(2,sum3);
                p5.executeUpdate();
                purchase_info(sum3);
                break;

            case 3:

                String charger = "select * from chargers";
                Statement stmt = connection.createStatement();
                ResultSet result = stmt.executeQuery(charger);
                while (result.next()){
                    System.out.println("ID: "+result.getInt("id")+" Type: "+result.getString("type")+" Length: "+result.getString("length")+" Colour: "+result.getString("colour")+ " Price: "+result.getInt("price"));
                }

                String q6="select price from chargers where id =?";
                PreparedStatement p6 = connection.prepareStatement(q6);
                System.out.println("Enter product id: ");
                int pid4= scanner.nextInt();
                p6.setInt(1,pid4);
                ResultSet res4 = p6.executeQuery();
                int sum4=0;
                if(res4.next()){sum4= res4.getInt("price");}
                int remainder4 = balance - sum4;

                String q7 = "update users set balance=? where id=?";
                PreparedStatement p7 = connection.prepareStatement(q7);
                p7.setInt(1,remainder4);
                p7.setInt(2,uid);
                p7.executeUpdate();

               
                String q8 = "insert into purchases(userid,total) values(?,?);";
                PreparedStatement p8 = connection.prepareStatement(q8);
                p8.setInt(1,uid);
                p8.setInt(2,sum4);
                p8.executeUpdate();
                purchase_info(sum4);
                break;
            case 4:
                String power = "select * from powerbank";
                Statement stmt1 = connection.createStatement();
                ResultSet res1 = stmt1.executeQuery(power);
                while(res1.next()){
                    System.out.println("ID: "+res1.getInt("id")+" Model: "+res1.getString("model")+" Colour: "+res1.getString("colour")+" Volume: "+res1.getInt("volume")+" Price: "+res1.getInt("price"));
                }

                String qu="select price from powerbank where id =?";
                PreparedStatement pr = connection.prepareStatement(qu);
                System.out.println("Enter product id: ");
                int poid= scanner.nextInt();
                pr.setInt(1,poid);
                ResultSet re = pr.executeQuery();
                int summ=0;
                if(re.next()){summ= re.getInt("price");}
                int remain = balance - summ;

                String qu1 = "update users set balance=? where id=?";
                PreparedStatement pr1 = connection.prepareStatement(qu1);
                pr1.setInt(1,remain);
                pr1.setInt(2,uid);
                pr1.executeUpdate();

                String qu2 = "insert into purchases(userid,total) values(?,?);";
                PreparedStatement pr2 = connection.prepareStatement(qu2);
                pr2.setInt(1,uid);
                pr2.setInt(2,summ);
                pr2.executeUpdate();
                purchase_info(summ);
                break;
            default:break;
        }
        connection.close();
        scanner.nextLine();
    }

    public static void user_info()throws Exception{
        Connection connection = DriverManager.getConnection(url,username,password);

        String q = "select * from users where id=?";
        PreparedStatement p = connection.prepareStatement(q);
        p.setInt(1,uid);
        ResultSet r = p.executeQuery();
        if (r.next()){
            System.out.println("ID: "+r.getInt("id")+" Name: "+r.getString("name")+" Balance: "+r.getInt("balance"));
        }
        connection.close();
    }
    public static void purchase_info(int n)throws Exception{
        Connection connection = DriverManager.getConnection(url,username,password);
        String a = "select * from purchases where userid=? and total=?";
        PreparedStatement pa = connection.prepareStatement(a);
        pa.setInt(1,uid);
        pa.setInt(2,n);
        ResultSet ra = pa.executeQuery();
        if(ra.next()){
            System.out.println("Receipt ID: "+ra.getString("id")+" Total: "+ra.getString("total")+" User ID: "+ra.getInt("userid"));
        }
    }
}