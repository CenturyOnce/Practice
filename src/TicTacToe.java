import java.util.InputMismatchException;
import java.util.Scanner;

public class TicTacToe{
        public static void printMatrix(String[][] matrix){
            System.out.println("  1 2 3");
            for (int i = 0; i < 3; i++) {  
                System.out.print(i+1 + " ");      // по строкам
                for (int j = 0; j < 3; j++) { // по столбцам
                    System.out.print(matrix[i][j] + " ");
                }
            System.out.println();
            }
        }

        public static String askReplay(String replay){
            Scanner scanner = new Scanner(System.in);
            System.out.println("\nХотите сыграть снова (да/нет)?");
            replay = scanner.nextLine();
            return replay;
        }

        public static boolean winCheck(String[][] matrix, String player){
            for (int i = 0; i < 3; i++) {
                if (matrix[i][0].equals(player) && matrix[i][1].equals(player) && matrix[i][2].equals(player)) return true;
                if (matrix[0][i].equals(player) && matrix[1][i].equals(player) && matrix[2][i].equals(player)) return true;
            }
            if (matrix[0][0].equals(player) && matrix[1][1].equals(player) && matrix[2][2].equals(player)) return true;
            if (matrix[0][2].equals(player) && matrix[1][1].equals(player) && matrix[2][0].equals(player)) return true;

            return false;
        }

	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String replay = "да";
        System.out.println("Вы играете в крестики-нолики!");
        while(replay.equals("да")){
            String[][] matrix = {
                {"-", "-", "-"},
                {"-", "-", "-"},
                {"-", "-", "-"}
            };
            int row = 0, col = 0;
            String player;

            for(int turn = 1; turn < 10; turn++){

                if(turn%2==0) player = "O";
                else player = "X";

                try{
                    do{
                        System.out.println("Игрок "+ player +", введите строку и столбец (через пробел, от 1 до 3): ");
                        row = scanner.nextInt() - 1;
                        col = scanner.nextInt() - 1;
                    } while(!matrix[row][col].equals("-"));
                } catch(ArrayIndexOutOfBoundsException e){
                    System.out.println("Ошибка, неверное значение столбца/строки! Введите цифры от 1 до 3!");
                    turn--;
                    scanner.nextLine();
                    continue;
                } catch (InputMismatchException e) {
                    System.out.println("Введите числа!");
                    turn--;
                    scanner.nextLine();
                    continue;
                }

                matrix[row][col] = player;
                printMatrix(matrix);
                if(winCheck(matrix, player)){
                    System.out.println("Победил игрок " + player + "!");
                    replay = askReplay(replay);
                    break;
                } else if(turn == 9){
                    System.out.println("Ничья!");
                    replay = askReplay(replay);
                    break;
                }
                
            }
        }
    }
}
