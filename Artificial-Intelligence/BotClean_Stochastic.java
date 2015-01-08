package Artificial_Intelligence;
 
import java.util.Scanner;
 
public class BotClean_Stochastic {
    private static enum Move {
        RIGHT, LEFT, UP, DOWN, CLEAN
    }
 
    private static final char DIRTY = 'd';
 
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int[] pos = new int[2];
        String[] board = new String[5];
 
        for (int i = 0; i < 2; i++) {
            pos[i] = scn.nextInt();
        }
 
        for (int i = 0; i < 5; i++) {
            board[i] = scn.next();
        }
 
        nextMove(pos[0], pos[1], board);
        scn.close();
    }
 
    private static void nextMove(int posx, int posy, String[] board) {
        if (board[posx].charAt(posy) == DIRTY) {
            System.out.println(Move.CLEAN);
 
            return;
        }
 
        int d_x = 0;
        int d_y = 0;
 
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (board[i].charAt(j) == DIRTY) {
                    d_x = i;
                    d_y = j;
                }
            }
        }
 
        if (posx < d_x) {
            System.out.println(Move.DOWN);
        } else if (posx > d_x) {
            System.out.println(Move.UP);
        } else if (posy < d_y) {
            System.out.println(Move.RIGHT);
        } else if (posy > d_y) {
            System.out.println(Move.LEFT);
        }
    }
}
