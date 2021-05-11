package com.company;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class ColomboToBadulla extends Application{

    private static final int SEATING_CAPACITY = 42;
    private static String[] seatsByOder = new String[SEATING_CAPACITY];
    private static String date ;
    private static boolean answer;
    private static int lastClickedIndex = 0;
    private static String customerName ;
    private static Database database ;
    private static Button toMenu;
    private static Stage window , window2,window3,window4,windowGetName;
    private static final String FROM_TO = "Colombo to Badulla";

    public static void main(String[] args) { launch(args);}

    @Override
    public void start(Stage stage)
    {   Scanner scanner = new Scanner(System.in);
        selectingTheRoute(scanner); }

        //Selecting the Route
    public static void selectingTheRoute(Scanner scanner){
        System.out.print(" \n...Denuwara Menike train ..." +
                         " \nColombo to Badulla       (C)" +
                         " \nBaduula to Colombo       (B)" +
                         " \nSelect the Route            " +
                         " \n:- ");
        String input = scanner.next().toLowerCase();
        switch (input){
            case "c":
                menu(scanner);
                break;
            case "b" :
                BadullaToColombo.menu(scanner);
                break;
            default:
                System.out.println("Invalid input");
                selectingTheRoute(scanner);
        }
    }


    //check if seats available or not to this date
    private static void checkTheAvailableSeats(Scanner scanner)
    {
        int count = 0;
        for(int x= 0 ;x < SEATING_CAPACITY;x++){
            if(seatsByOder[x] != null){
                count++;
            }
        }
        if(count < 42){
            getName(scanner);
        }
        else {
            System.out.println("Seats not available for this route..");
        }

    }

    //menu method
    private static void menu(Scanner scanner)
    {

        String database = "colomboToBadulla";
        System.out.print(
                "\n"+ FROM_TO +" -----"+
                        "\nAdd Customer to seat      (A)" +
                        "\nView All the seats        (V)" +
                        "\nDelete Customer From Seat (D)" +
                        "\nFind the Seat             (F)" +
                        "\nDisplay Empty Seats       (E)" +
                        "\nStore the Data            (S)" +
                        "\nLoad the Data             (L)" +
                        "\nSeats by Oder             (O)" +
                        "\nQuit the Programme        (Q)" +
                        "\nSelect the route          (R)" +
                        "\n:");

        String input = scanner.next().toLowerCase();
        switch (input){

            case "a":
                checkTheAvailableSeats(scanner);
                break;

            case "v":
                viewSeats(scanner);
                break;
            case "d":
                deleteCustomerFromSeat(seatsByOder,scanner);
                break;
            case "f":
                findTheSeat(seatsByOder,scanner);
                break;
            case "e":
                viewEmptySeats(scanner);
                break;
            case "s":
                selectTheDate(scanner);
                storeTheData(scanner,database);
                break;
            case "l":
                selectTheDate(scanner);
                loadTheData(scanner,database);
                break;
            case "o":
                sortTheSeats(scanner);
                break;
            case "r":
                selectingTheRoute(scanner);
                break;
            case "q":
                System.exit(0);

            default:
                System.out.println("Invalid Input");
                menu(scanner);

        }
    }

    //delete customer from from seat using user name
    private static void deleteCustomerFromSeat(String[] seats,Scanner scanner)
    {
        scanner.nextLine();
        System.out.print("Enter User name : ");
        String name = scanner.nextLine().trim();
        int count = 0;
        for (int x = 0; x < SEATING_CAPACITY; x++) {
            if (name.equals(seats[x])) {
                seats[x] = null;
                count += 1;
                System.out.println(name + " got Deleted from the seats .....");
                break;
            }
        }
        if (count == 0) {
            System.out.println("User name not found : Check the name and try again..");
        }

        menu(scanner);
    }
    //find the seat from customer name
    private static void findTheSeat(String[] seats,Scanner scanner)
    {
        scanner.nextLine();
        System.out.print("Enter User Name :");
        String name = scanner.nextLine().trim().toLowerCase();
        int count = 0;
        for(int x = 0;x < SEATING_CAPACITY; x++) {
            if (name.equals(seats[x])) {
                System.out.println(FROM_TO+"------" + "\n" + name.toUpperCase() +"\nSeat Number is " + (x + 1) + "\nBooked on " + date);
                count += 1;
                break;
            }
        }
        if (count == 0){
            System.out.println("User name not found : Check the name and try again..");
        }
        menu(scanner);
    }


    //Alert box
    private static boolean display(String title,String message)
    {
        window2 = new Stage();
        window2.initModality(Modality.APPLICATION_MODAL);
        window2.setTitle(title);
        window2.setMaxWidth(270);
        window2.setMaxHeight(150);
        window2.setResizable(false);

        Label label = new Label(message);
        label.setFont(new Font("Arial", 12));

        Button yesB = new Button("yes");
        Button noB = new Button("no");

        yesB.setOnAction(e -> {
            answer = true;
            window2.close();
        });

        noB.setOnAction(e -> {
            answer = false;
            window2.close();
        });

        HBox boxForButtons = new HBox(20);
        boxForButtons.getChildren().addAll(yesB,noB);
        VBox layout = new VBox(20);
        boxForButtons.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label,boxForButtons);
        layout.setAlignment(Pos.CENTER);
        layout.styleProperty().set("-fx-background-color:Gray");
        Scene scene = new Scene(layout,300,300);
        window2.setScene(scene);
        window2.showAndWait();

        return answer;
    }

    //GUI  for add customer to the seats
    private static void selectTheSeat(Scanner scanner)
    {
        window = new Stage();
        window.setTitle(FROM_TO);
        window.setMaxHeight(750);
        window.setMaxWidth(500);
        window.setResizable(false);
        toMenu = new Button("Go to menu");

        //scene 1\]0
        Label bookTheSeat = new Label("Book The Seat");
        bookTheSeat.setFont(new Font("Arial", 18));
        Button subB = new Button("Book now");
        GridPane grid = new GridPane();
        Button[] buttons = new Button[42];
        for (int b = 0; b < SEATING_CAPACITY;b++){
            buttons[b] = new Button();
            buttons[b].setText(String.valueOf(b+1));
            buttons[b].setPrefHeight(40);
            buttons[b].setPrefWidth(40);
            int buttonInd = b;
            buttons[buttonInd].setOnAction(e -> {
                if (seatsByOder[buttonInd] == null) {
                    lastClickedIndex = buttonInd;
                    for(int x = 0;x < SEATING_CAPACITY;x++) {
                        if((seatsByOder[x] == null))
                        buttons[x].setStyle("-fx-background-color:white");
                    }
                    buttons[buttonInd].setStyle("-fx-background-color:Gray");
                }
                else {
                    display("Seat is already booked","Book another seat");
                }
            });
        }
        int r = 0;
        int c = 0;
        for (Button button:buttons){
            if(c==4){
                r++;
                c=0;
            }
            c++;
            grid.add(button,c,r);
        }
        grid.setHgap(7);
        grid.setVgap(4);
        HBox menuBox = new HBox(25);
        HBox box = new HBox(30);
        box.getChildren().addAll(grid);
        VBox box1 = new VBox(20);
        menuBox.getChildren().addAll(toMenu);
        menuBox.setAlignment(Pos.CENTER);
        box.setAlignment(Pos.CENTER);
        box1.setAlignment(Pos.CENTER);
        box1.styleProperty().set("-fx-background-color:LightGray");
        Button whiteB = new Button("Available");
        whiteB.setFont(new Font("Arial", 10));
        whiteB.setAlignment(Pos.CENTER);
        whiteB.setStyle("-fx-background-color:white");
        Button grayB = new Button("Booked");
        whiteB.setFont(new Font("Arial", 10));
        grayB.setFont(new Font("Arial", 10));
        grayB.setStyle("-fx-background-color:Grey");
        VBox colorBox = new VBox(10);
        colorBox.getChildren().addAll(whiteB,grayB);
        colorBox.setAlignment(Pos.CENTER);
        box1.getChildren().addAll(bookTheSeat,box,colorBox,subB,menuBox);
        Scene scene2 = new Scene(box1,500,750);

        toMenu.setOnAction(e ->{
            answer = display("Quit","Are you want to go to menu ? ");
            if(answer){
                window.close();
                menu(scanner);
            }
        });

        subB.setOnAction(e ->{
            boolean answer = display("Add Customer","Are you want to add the Customer to the seat? ");
            if(answer){
                if(seatsByOder[lastClickedIndex] == null){
                    buttons[lastClickedIndex].setText(String.valueOf(lastClickedIndex+1));
                }
                else {
                    buttons[lastClickedIndex].setStyle("-fx-background-color:Grey");
                }
                int seat_number = lastClickedIndex ;
                seatsByOder[seat_number] = customerName;
                System.out.println("Customer added Successfully");
                window.close();
                menu(scanner);
            }
            else {
                answer = display("Quit","Are you want to exit the programme ? ");
                if(answer){
                    window.close();
                    menu(scanner);
                }

            }
        });
        for(int x = 0;x < SEATING_CAPACITY;x++){
            if(seatsByOder[x] == null){
                buttons[x].setText(String.valueOf(x+1));
                buttons[x].setStyle("-fx-background-color:white");
            }
            else {
                buttons[x].setStyle("-fx-background-color:Grey");
            }
        }
        window.setOnCloseRequest(e ->{
            boolean answer = display("Quit","Are you want to exit the programme ? ");
            if(answer){
                window.close();
                menu(scanner);
            }

        });
        window.setScene(scene2);
        window.show();
    }

    //GUI for viewing all the seats
    public static void viewSeats(Scanner scanner)
    {

        window3 = new Stage();
        window3.setMaxHeight(700);
        window3.setTitle(FROM_TO);
        window3.setMaxWidth(450);
        window3.setResizable(false);
        Label label = new Label("View All Seats");
        label.setFont(new Font("Arial", 22));
        GridPane grid = new GridPane();
        Button[] buttons = new Button[42];
        for (int b = 0; b < SEATING_CAPACITY; b++){
            buttons[b] = new Button();
            buttons[b].setMaxWidth(200);
            buttons[b].setPrefHeight(40);
            buttons[b].setPrefWidth(40);
        }
        int r = 0;
        int c = 0;
        for (Button button:buttons){
            if(c==4){
                r++;
                c=0;
            }
            c++;
            grid.add(button,c,r);
        }
        grid.setHgap(7);
        grid.setVgap(4);
        toMenu = new Button("Go to menu");
        for(int x = 0;x < SEATING_CAPACITY;x++){
            if (seatsByOder[x] != null) {
                buttons[x].setStyle("-fx-background-color:Grey");
            }
            else {
                buttons[x].setStyle("-fx-background-color:white");
            }
            buttons[x].setText(String.valueOf(x + 1));
        }

        VBox box = new VBox(25);
        HBox  pane = new HBox(20);
        pane.getChildren().add(grid);


        Button whiteB = new Button("Available");
        whiteB.setAlignment(Pos.CENTER);
        whiteB.setStyle("-fx-background-color:white");
        Button grayB = new Button("Booked");
        grayB.setStyle("-fx-background-color:Grey");
        VBox colorBox = new VBox(10);
        colorBox.getChildren().addAll(whiteB,grayB);
        pane.setAlignment(Pos.CENTER);
        box.getChildren().addAll(label,pane,colorBox,toMenu);
        box.setAlignment(Pos.CENTER);
        whiteB.setFont(new Font("Arial", 10));
        grayB.setFont(new Font("Arial", 10));
        colorBox.setAlignment(Pos.CENTER);
        box.styleProperty().set("-fx-background-color:LightGray");
        Scene scene3 = new Scene(box,450,700);


        window3.setOnCloseRequest(e ->{
            boolean answer = display("Quit","Are you want to exit the programme ? ");
            if(answer){
                window3.close();
                menu(scanner);
            }
        });


        toMenu.setOnAction(e ->{
            answer = display("Quit","Are you want to go to menu ? ");
            if(answer){
                window3.close();
                menu(scanner);
            }
        });
        window3.setScene(scene3);
        window3.show();

    }

    //GUI for viewing empty seats
    public static void viewEmptySeats(Scanner scanner)
    {

        window4 = new Stage();
        window4.setTitle(FROM_TO);
        window4.setMaxHeight(700);
        window4.setMaxWidth(450);
        window4.setResizable(false);
        Label label = new Label("View All Empty seats");
        label.setFont(new Font("Arial", 22));
        GridPane grid = new GridPane();
        Button[] buttons = new Button[42];

        for (int b = 0; b < SEATING_CAPACITY;b++){
            buttons[b] = new Button();
            buttons[b].setMaxWidth(200);
            buttons[b].setPrefHeight(40);
            buttons[b].setPrefWidth(40);
        }
        int r = 0;
        int c = 0;
        for (Button button:buttons){
            if(c==4){
                r++;
                c=0;
            }
            c++;
            grid.add(button,c,r);
        }
        grid.setHgap(7);
        grid.setVgap(4);
        toMenu = new Button("Go to menu");
        for(int x = 0;x < SEATING_CAPACITY;x++){
            if(seatsByOder[x] == null){
                buttons[x].setText(String.valueOf(x+1));
                buttons[x].setStyle("-fx-background-color:Grey");
            }
            else{
                buttons[x].setVisible(false);
            }
        }
        VBox box = new VBox(25);
        HBox pane = new HBox(20);
        pane.getChildren().add(grid);
        pane.setAlignment(Pos.CENTER);
        Button grayB = new Button("Available");
        grayB.setStyle("-fx-background-color:Grey");
        VBox colorBox = new VBox(10);
        colorBox.getChildren().addAll(grayB);
        box.getChildren().addAll(label,pane,colorBox,toMenu);
        box.setAlignment(Pos.CENTER);
        colorBox.setAlignment(Pos.CENTER);
        box.styleProperty().set("-fx-background-color:LightGray");
        box.setAlignment(Pos.CENTER);
        Scene scene = new Scene(box,450,700);

        window4.setOnCloseRequest(e ->{
            boolean answer = display("Quit","Are want to exit the programme ? ");
            if(answer){
                window4.close();
                menu(scanner);
            }
            window4.setScene(scene);
            window4.show();
        });
        toMenu.setOnAction(e ->{
            answer = display("Quit","Are you want to go to menu ? ");
            if(answer){
                window4.close();
                menu(scanner);
            }
        });
        window4.setScene(scene);
        window4.show();
    }

    //getting the Customer name using GUI
    public static void getName(Scanner scanner){

        windowGetName = new Stage();
        windowGetName.setMaxHeight(400);
        windowGetName.setMaxWidth(400);
        windowGetName.setResizable(false);

        windowGetName.setTitle(FROM_TO);
        Label train_booking = new Label("Create User name \n      (Unique ID)");
        train_booking.setFont(new Font("Arial", 18));
        TextField textName = new TextField();
        Label name = new Label("User name :");
        name.setFont(new Font("Arial", 15));
        textName.setMaxWidth(230);
        Button nameButton = new Button("Submit");
        HBox nameBox = new HBox(18);
        toMenu = new Button("Go to menu");
        nameBox.getChildren().addAll(name,textName,nameButton);
        nameBox.setAlignment(Pos.CENTER);
        VBox mainBox = new VBox(60);
        mainBox.getChildren().addAll(train_booking,nameBox,toMenu);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.styleProperty().set("-fx-background-color:LightGray");
        Scene scene = new Scene(mainBox,475,300);


        windowGetName.setOnCloseRequest(e ->{
            answer = display("Quit","Are want to exit the programme ? ");
            if (answer) {
                windowGetName.close();
                menu(scanner);
            }
        });

        nameButton.setOnAction(e ->{
            String cName = textName.getText().trim();
            int count = 0;
            for (int x = 0; x < SEATING_CAPACITY; x++)
                if (cName.equals(seatsByOder[x]) || cName.equals("")) {
                    count++;
                }
            if (count > 0) {
                answer = display("User name Error","\nUser name Has already taken\n       or Invalid ,Reenter new \n                       one ...");
                if(answer){
                    windowGetName.setScene(scene);
                }
                else {windowGetName.close();
                    menu(scanner);}

            }
            else {
                windowGetName.close();
                customerName = cName;
                selectTheSeat(scanner);
            }

        });
        toMenu.setOnAction(e ->{
            answer = display("Quit","Are you want to go to menu ? ");
            if(answer){
                windowGetName.close();
                menu(scanner);
            }
        });
        windowGetName.setResizable(false);
        windowGetName.setScene(scene);
        windowGetName.show();
    }

    //sorting the names using bubble sort.
    public  static void sortTheSeats(Scanner scanner)
    {
        ArrayList<String> names = new ArrayList<>();
        for(String name: seatsByOder){
            if(!(name == null)){
                names.add(name);
            }
        }
        int x = 0;
        int len = names.size();
        String[] sort = new String[len];
        for(String name :names){
            sort[x++] = name;
        }
        String temp;
        for(int i = 1;i < len;i++){
            for(int j = 0;j < len-i;j++){
                if(sort[j].compareTo(sort[j+1]) > 0){
                    temp = sort[j];
                    sort[j] = sort[j+1];
                    sort[j+1] = temp;
                }
            }
        }
        System.out.println("Sorted names including seat number .....");
        for(String name : sort){
            for(int y = 0;y < SEATING_CAPACITY;y++){
                if(name.equals(seatsByOder[y])){
                    System.out.println( date +" "+(y +1) + " Seat booked by "+ name );
                }
            }
        }
        menu(scanner);
    }

    public static void getCurrentDate()
    {
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Date dateobj = new Date();
        date = (df.format(dateobj));
    }

    //storing the data from array to Database
    public static void storeTheData(Scanner scanner,String database_n)
    {
        System.out.println("data is getting stored..please wait ..");
        database = new Database(date ,database_n);
        database.clear();
        database.addNames(seatsByOder);
        menu(scanner);
    }


    //loading the data from dataBase to array
    public static void loadTheData(Scanner scanner,String database_n)
    {
        System.out.println("Downloading...Please wait...");
        database = new Database(date,database_n);
        seatsByOder = database.load();
        System.out.println("Data Downloaded successfully..");
        menu(scanner);
    }

    //selecting the FutureDate
    public static void selectFutureDate(Scanner sc){
        int fdate = 0;
        int fmonth= 0;
        int fyear = 0;
        System.out.print("Enter a date (dd/MM/yy) : ");
        String futureDate = sc.next().trim();
        try {
        fdate = Integer.parseInt(futureDate.substring(0,2));
        fmonth = Integer.parseInt(futureDate.substring(3,5));
        fyear = Integer.parseInt(futureDate.substring(6,8));
        }
        catch (Exception e){
            System.out.println("Invalid Date or Check the Date format.");
        }

        if(fdate <= 31 & fmonth <=12 ) {
            DateFormat df = new SimpleDateFormat("dd/MM/yy");
            Date dateobj = new Date();
            String todayDate = (df.format(dateobj));

            int todate = Integer.parseInt(todayDate.substring(0, 2));
            int tomonth = Integer.parseInt(todayDate.substring(3, 5));
            int toyear = Integer.parseInt(todayDate.substring(6, 8));

            if (fdate > todate & fmonth >= tomonth & fyear >= toyear) {
                date = futureDate;
            } else {
                System.out.println("--- Enter a Future date");
                selectFutureDate(sc);
            }
        }
        else{
            System.out.println("Invalid Date Found , Select a correct Date.");
            selectTheDate(sc);
        }

    }


    //selecting the date
    public static void selectTheDate(Scanner sc)
    {
        System.out.print("\nToday  Date   (T) " +
                "\nFuture Date   (F) " +
                "\nSelected Date (S) " +
                "\n: ");
        String input = sc.next().toLowerCase().trim();
        switch (input){
            case "t":
                getCurrentDate();
                break;
            case "f":
                selectFutureDate(sc);
                break;
            case "s":
                if(date==null){
                    System.out.println("Date is not selected..");
                    selectTheDate(sc);
                }
                break;
            default:
                System.out.println("Invalid input..");
                selectTheDate(sc);
        }
    }
}
