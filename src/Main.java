import java.util.Scanner;
import java.util.Random;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);


        System.out.println("""
                   
                 
                    ____        __       _    ___            \s
                   / __ \\____ _/ /_____ | |  / (_)__ _      __
                  / / / / __ `/ __/ __ `/ | / / / _ \\ | /| / /
                 / /_/ / /_/ / /_/ /_/ /| |/ / /  __/ |/ |/ /\s
                /_____/\\__,_/\\__/\\__,_/ |___/_/\\___/|__/|__/ \s
               
                ---------- < Welcome to DataView > ----------
                       
                    HERE YOU CAN CREATE POINTS, PLOT THEM
                       ON A GRAPH, DISPLAY THE TABLE OF
                           VALUES, MODIFY THE DATA
                             THE PREDICTION TOOL


                       THE STAR OF THIS PROGRAM IS OUR
                      PREDICTION TOOL, WHICH CAN CREATE
                      A FUNCTION FOR A LINE OF BEST FIT
                   ACCORDING TO THE DATA GIVEN BY THE USER
                    AND IS ABLE TO PREDICT VALUES BASED ON
                   THE TREND LINE. THE MORE DATA IS ENTERED,
                    THE MORE ACCURATE THE FUNCTION AND THE
                              PREDICTION WILL BE
               """);


        int[][] data = CreateData();


        fillData(data);


        String menuChoice;


        while (true) {
            System.out.print("""
                  
                      |-----------------------------|    |-----------------------------|
                      |   DISPLAY TABLE OF VALUES   |    |      DISPLAY THE GRAPH      |
                      |                             |    |                             |
                      |          enter [1]          |    |          enter [2]          |
                      |-----------------------------|    |-----------------------------|
                     
                      |-----------------------------|    |-----------------------------|
                      |   MODIFY TABLE OF VALUES    |    |       PREDICTING TOOL       |
                      |                             |    |                             |
                      |          enter [3]          |    |          enter [4]          |
                      |-----------------------------|    |-----------------------------|
                     
                                         enter anything else to quit.
                  
                    >>>\s""");


            menuChoice = scanner.nextLine();
            System.out.println();

            if (menuChoice.equals("1")) {
                printTable(data);
            } else if (menuChoice.equals("2")) {
                printGraph(data);
            } else if (menuChoice.equals("3")) {
                modifyTable(data);
            } else if (menuChoice.equals("4")) {
                predictValue(data);
            } else {
                System.out.println(" ---------- < Thank You > ---------- \n");
                System.exit(0);
            }
        }
    }

    //pre: takes in a string
    //post: true/false
    //Checks if a string is NOT numeric
    public static boolean nonNumeric(String input) {
        for (int i = 0; i < input.length(); i++) {
            if ((int) input.charAt(i) < 48 || (int) input.charAt(i) > 57) {
                return true;
            }
        }
        return input.length() > 2;
    }

    //pre: Takes in two strings
    //post: Returns an int array
    //Gets user input and converts it to an integer inside an int array to be accessed later
    public static int[] getValues(String a, String b) {
        Scanner scanner = new Scanner(System.in);

        int aVal = 0, bVal = 0;


        String input;


        System.out.print("Enter " + a + " value: ");
        input = scanner.nextLine();

        while (nonNumeric(input) || input.equals("0") || input.isEmpty()) {
            System.out.println("\nINPUT ERROR\nTRY AGAIN\n\nEnter " + a + " value: ");
            input = scanner.nextLine();
        }

        if (input.length() == 1) {
            aVal += (int) input.charAt(0) - 48;
        } else {
            aVal += ((int) input.charAt(0) - 48) * 10;
            aVal += (int) input.charAt(1) - 48;
        }


        System.out.print("Enter " + b + " value: ");
        input = scanner.nextLine();


        while (nonNumeric(input) || input.equals("0") || input.isEmpty()) {
            System.out.println("\nINPUT ERROR\nTRY AGAIN\n\nEnter " + b + " value: ");
            input = scanner.nextLine();
        }


        if (input.length() == 1) {
            bVal += (int) input.charAt(0) - 48;
        } else {
            bVal += ((int) input.charAt(0) - 48) * 10;
            bVal += (int) input.charAt(1) - 48;
        }


        return new int[]{aVal, bVal};
    }


    //pre: takes nothing
    //post: returns a 2d int array with the data
    //Creates the size of the data array by grabbing user input and is loaded in the main first thing when the program runs
    public static int[][] CreateData() {
        Scanner scanner = new Scanner(System.in);


        boolean valid = false;
        String rowsInput = null;
        int i;
        while (!valid) {
            valid = true;
            System.out.print("How many points would you like to graph?\n >>> ");
            rowsInput = scanner.nextLine();
            if (rowsInput.isEmpty()) {
                System.out.println("\nINPUT ERROR\nTRY AGAIN\n");
                valid = false;
            }
            for (i = 0; i < rowsInput.length() && valid; i++) {
                if (nonNumeric(rowsInput)) {
                    System.out.println("\nINPUT ERROR\nTRY AGAIN\n");
                    valid = false;
                    i = rowsInput.length();
                }
            }
        }


        return new int[toInt(rowsInput)][2];
    }

    //pre: takes in a 2d array (data)
    //post: returns nothing
    //Creates a random point with a range chosen by the user
    public static void randomGeneratePoints(int[][] data) {
        Random random = new Random();

        int[] bounds;


        System.out.println();
        do {
            bounds = getValues("lower bound", "upper bound");
            if (bounds[0] > bounds[1]) {
                System.out.println("\nINPUT ERROR\nTRY AGAIN\n");
            }
        } while (bounds[0] > bounds[1]);


        for (int i = 0; i < data.length; i++) {
            data[i][0] = random.nextInt(bounds[0], bounds[1]);
            data[i][1] = random.nextInt(bounds[0], bounds[1]);
        }
    }


    //pre: takes in 2d array (data)
    //post: returns nothing
    //loads in each point in data
    public static void getPoints(int[][] data) {
        int[] point;


        int number;


        for (int i = 0; i < data.length; i++) {
            number = i + 1;
            System.out.println("Point " + number + ":");
            point = getValues("x", "y");
            data[i] = point;
            System.out.println("Successfully plotted point " + number + " to (" + point[0] + ", " + point[1] + ")\n");
        }
    }

    //pre: takes in 2d array (data)
    //post: returns nothing
    //loads in data when program runs
    public static void fillData(int[][] data) {
        Scanner scanner = new Scanner(System.in);

        String randomPromptInput;
        boolean randomPromptInputValid = false;

        while (!randomPromptInputValid) {
            randomPromptInputValid = true;
            System.out.print("\nWould you like to randomly generate all points (y/n): ");
            randomPromptInput = scanner.nextLine();
            if (randomPromptInput.equals("y")) {
                randomGeneratePoints(data);
            } else if (randomPromptInput.equals("n")) {
                getPoints(data);
            } else {
                randomPromptInputValid = false;
                System.out.println("\nINPUT ERROR\nTRY AGAIN\n");
            }
        }
        sort(data);
        System.out.println("Successfully generated all " + data.length + " points");
    }


    //pre: takes in 2d array (data) and a float x for the input
    //post: returns the answer of the function
    // Uses the input and the data to figure out the answer to the function
    public static float functionInput(int[][] data, float x) {
        float median;
        if (data.length % 2 == 0) {
            int value1 = data[((int) (double) (data.length / 2)) - 1][0];
            int value2 = data[(int) (double) (data.length / 2)][0];
            median = (float) (value1 + value2) /2;
        } else {
            median = data[data.length/2][0];
        }


        float slope = getSlope(data, median);


        float totalMeanX = 0, totalMeanY = 0;


        for (int[] datum : data) {
            totalMeanX += datum[0];
            totalMeanY += datum[1];
        }


        totalMeanX /= data.length;
        totalMeanY /= data.length;


        float yInt = (float) Math.round((totalMeanY - slope * totalMeanX) * 100) /100;


        System.out.println("\ny = " + ((float) Math.round(slope*100)/100) + "x + " + yInt);


        return (slope * x) + yInt;
    }


    //pre: takes in 2d array (data) and a float
    //post: returns a float
    //creates the slope for the function
    private static float getSlope(int[][] data, float median) {
        float lowerMeanX = 0, lowerMeanY = 0, upperMeanX = 0, upperMeanY = 0;
        int amountInLower = 0, amountInUpper = 0;


        for (int[] datum : data) {
            if (datum[0] < median) {
                lowerMeanX += datum[0];
                lowerMeanY += datum[1];
                amountInLower++;
            } else if (datum[0] > median) {
                upperMeanX += datum[0];
                upperMeanY += datum[1];
                amountInUpper++;
            }
        }
        lowerMeanX /= amountInLower;
        lowerMeanY /= amountInLower;
        upperMeanX /= amountInUpper;
        upperMeanY /= amountInUpper;


        return ((upperMeanY - lowerMeanY) / (upperMeanX - lowerMeanX));
    }


    //pre: takes in 2d array (data)
    //post: returns an int
    //Finds the greatest value of the data
    public static int greatestValue(int[][] data) {
        int greatest = 0;
        for (int[] datum : data) {
            for (int i : datum) {
                if (i > greatest) {
                    greatest = i;
                }
            }
        }
        return greatest;
    }


    //pre: takes in 2d array (data)
    //post: returns nothing
    //Interface for predicting value
    public static void predictValue(int[][] data) {
        Scanner scanner = new Scanner(System.in);
        String input = "";
        boolean validInput = false;
        boolean validFunction = true;

        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data.length; j++) {
                if (i != j && data[i][0] == data[j][0]) {
                    validFunction = false;
                    System.out.println(data[i][0] + " " + data[j][0]);
                    System.out.println("\nERROR\nNOT A FUNCTION\n");
                    i = data.length;
                    j = data.length;
                }
            }
        }

        while (validFunction && (!validInput || input.length() > 4)) {
            validInput = true;
            System.out.print("Enter an x value:\n >>> ");
            input = scanner.nextLine();


            for (int i = 0; i < input.length(); i++) {
                if (!Character.isDigit(input.charAt(i))) {
                    System.out.println("\nINPUT ERROR\nTRY AGAIN\n");
                    validInput = false;
                    i = input.length();
                }
            }
        }
        if (validFunction) {
            System.out.println("\nWhen x is " + input + ", y will likely be approximately " + functionInput(data, toInt(input)));
        }
    }


    //pre: takes in 2d array (data)
    //post: returns nothing
    //Prints out the graph
    public static void printGraph(int[][] data) {
        char[][] charArr = new char[(int) (greatestValue(data)+1)][(int) (greatestValue(data)+1)];


        for (int i = 0; i < charArr.length; i++) {
            for (int j = 0; j < charArr[i].length; j++) {
                charArr[i][j] = ' ';
            }
        }


        for (int[] datum : data) {
            for (int j = 0; j < datum.length; j++) {
                charArr[datum[1] - 1][datum[0] - 1] = '*';
            }
        }


        int number;
        for (int i = charArr.length-1; i >= 0; i--) {
            number = i + 1;
            if (number > 9) {
                System.out.print(number + " |");
            } else {
                System.out.print(number + "  |");
            }
            for (int j = 0; j < charArr.length; j++) {
                System.out.print(" " + charArr[i][j] + " ");
            }
            System.out.println();
        }


        System.out.print("    -");
        for (int i = 0; i < greatestValue(data); i++) {
            System.out.print("---");
        }
        System.out.println();


        System.out.print("     ");
        for (int i = 1; i < greatestValue(data); i++) {
            System.out.print(" " + String.valueOf(i).charAt(0) + " ");
        }
        System.out.println();


        if (greatestValue(data) > 10) {
            System.out.print("     ");
            for (int i = 0; i < 9; i++) {
                System.out.print("   ");
            }
            for (int i = 10; i < greatestValue(data); i++) {
                System.out.print(" " + String.valueOf(i).charAt(1) + " ");
            }
        }
        System.out.println();
    }


    //pre: takes in 2d array (data)
    //post: returns nothing
    //Interface for modifying data
    public static void modifyTable(int[][] data) {
        Scanner scanner = new Scanner(System.in);


        boolean valid;
        String input;


        printTable(data);


        do {
            valid = true;
            System.out.print("""
                   Which point would you like to change?
                    >>>\s""");
            input = scanner.nextLine();


            for (int i = 0; i < input.length(); i++) {
                if (nonNumeric(input)) {
                    valid = false;
                    System.out.println("\nINPUT ERROR\nTRY AGAIN\n");
                }
            }


            if (valid) {
                if (toInt(input) <= 0 || toInt(input) > data.length) {
                    valid = false;
                    System.out.println("\nINPUT ERROR\nTRY AGAIN\n");
                }
            }
        } while (!valid);


        int row = toInt(input);


        System.out.println("\nModifying row " + row + " ("+data[row-1][0]+ ", "+data[row-1][1]+")");


        data[row - 1] = getValues("x", "y");


        System.out.println("Successfully changed to (" + data[row - 1][0] + ", " + data[row - 1][1] + ")");
        sort(data);
    }


    //pre: takes in 2d array (data)
    //post: returns nothing
    //Prints out the table
    public static void printTable(int[][] data) {
        System.out.print("""
                    X     |     Y
               -----------------------
               """);


        for (int i = 0; i < data.length; i++) {
            System.out.print("     " + data[i][0]);
            if (data[i][0] >= 10) {
                System.out.print("    |");
            } else {
                System.out.print("     |");
            }
            System.out.print("     " + data[i][1]);
            int number = i + 1;
            if (data[i][1] >= 10) {
                System.out.println("    (" + number + ")");
            } else {
                System.out.println("     (" + number + ")");
            }
        }
    }


    //pre: takes in 2d array (data)
    //post: returns nothing
    //Uses selection sort to sort out the data according to x values
    public static void sort(int[][] data) {
        int gLoc;
        int[] tempArr;


        for (int i = data.length; i > 1; i--) {
            gLoc = 0;
            for (int j = 0; j < i; j++) {
                if (data[j][0] > data[gLoc][0]) {
                    gLoc = j;
                }
            }


            tempArr = data[gLoc];
            data[gLoc] = data[i-1];
            data[i-1] = tempArr;
        }
    }


    //pre: takes in a string
    //post: returns an int
    //Converts string to an int
    public static int toInt(String text) {
        int number = 0;


        if (text.length() == 1) {
            number += (int) text.charAt(0) - 48;
        } else if (text.length() == 2){
            number += ((int) text.charAt(0) - 48) * 10;
            number += ((int) text.charAt(1) - 48);
        } else if (text.length() == 3) {
            number += ((int) text.charAt(0) - 48) * 100;
            number += ((int) text.charAt(1) - 48) * 10;
            number += ((int) text.charAt(2) - 48);
        } else {
            number += ((int) text.charAt(0) - 48) * 1000;
            number += ((int) text.charAt(1) - 48) * 100;
            number += ((int) text.charAt(2) - 48) * 10;
            number += ((int) text.charAt(2) - 48);
        }


        return number;
    }
}