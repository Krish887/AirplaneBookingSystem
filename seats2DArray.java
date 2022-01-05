
import java.util.Scanner;

public class seats2DArray {
    public static void main(String[] args) {
        int capacity = 48;
        int columns = capacity/4;
        int rows = capacity/columns;
        String letters = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z";
        Scanner in = new Scanner(letters);
        
        String[][] seatLayout = new String[rows][columns];
        for (int i = 0; i < seatLayout.length; i++){ //row
            String letter = in.next();
            for (int j = 0; j < seatLayout[i].length; j++){ //column
                seatLayout[i][j] =  (j + 1) + letter;
                }
            }
        

        for (int i = 0; i < seatLayout.length; i++){
            for (int j = 0; j < seatLayout[i].length; j++){
                System.out.print(seatLayout[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}
