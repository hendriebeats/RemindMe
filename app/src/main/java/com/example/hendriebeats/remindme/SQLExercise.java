package com.example.hendriebeats.remindme;

/**
 * Created by Peter on 4/13/2017.
 */

import java.sql.SQLException;
import java.util.Scanner;

public final class SQLExercise
{
    public static void main(String[] args) throws SQLException
    {
        Scanner       scan = new Scanner(System.in);
        MySQLDatabase db   = new MySQLDatabase("peter", "VanHemert20!!", "jdbc:mysql://54.213.169.117:3306/bookstore");


        db.connect();

        //System.out.println("Welcome to the bookstore!");
        //System.out.println("Enter a price to see a list of books that fit your budget.");

        String input = "";

        while(true)
        {
            System.out.print("Enter a price: ");
            input = scan.nextLine();

            if(input.toLowerCase().equals("quit"))
            {
                break;
            }else if(input.matches("^[0-9]*\\.?[0-9]*$")){

                String query = "SELECT title, author, price FROM books WHERE price < ?;";
                System.out.println(formatResults(db.getData(query, input)));
                break;
            }else{
                System.out.println("Invalid Input");
            }

        }

        scan.close();
        db.disconnect();
    }

    /*
     * ============== DON'T EDIT BELOW THIS LINE ======================================
     */
    private static String formatResults(String[][] res)
    {
        String formattedResults = "";
        if(res == null)
            return formattedResults;

        int numCols = res[0].length; // Index 0 is column headers
        int[] maxLengths = new int[numCols];
        for(int i=0; i<maxLengths.length; i++)
        {
            maxLengths[i] = 0;
        }

        for(String[] row: res)
        {
            for(int i=0; i<row.length; i++)
            {
                if(row[i].length() > maxLengths[i])
                    maxLengths[i] = row[i].length();
            }
        }

        int rowCharCount = numCols+1; // Pipe characters to divide
        for(int each: maxLengths)
        {
            rowCharCount += (each+2); // Longest plus 1 space padding each side
        }


        // Create the divider row.
        formattedResults += "+";
        for(int i=0; i<rowCharCount-2; i++)
            formattedResults += "-";
        formattedResults += "+";

        String divider = formattedResults;

        // Header row with divider underneath.
        formattedResults += ("\n" + formatRow(res[0], maxLengths) + "\n" + divider);

        // All other rows.
        for(int row=1; row<res.length; row++)
        {
            formattedResults += ("\n" + formatRow(res[row], maxLengths));
        }
        formattedResults += "\n" + divider + "\n";

        return formattedResults;
    }

    private static String formatRow(String[] row, int[] maxLengths)
    {
        String rowStr = "|";

        for(int i=0; i<row.length; i++)
        {
            String current = row[i];
            int leftover = maxLengths[i]-current.length();

            rowStr += " " + current;
            for(int j=0; j<leftover; j++) // Pad extra space on right.
                rowStr += " ";
            rowStr += " |";
        }

        return rowStr;
    }
}