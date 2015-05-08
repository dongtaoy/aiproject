/**
 * Created by dongtao on 5/8/2015.
 */
package dongtaoy.squatter;


public class Test {

    public static void main(String[] args) {
        Board board = new Board(6);
        System.out.println(board);
        minmax(board, 5);

    }

    public static void minmax(Board board, int depth) {

        char player;
        if (depth == 0)
            return;

        if (depth % 2 == 0) {
            player = '1';
            for (Cell cell : board.getAvaliableCells()) {
                Board newBoard = new Board(board, cell, player);
                int value = eval(board);
                minmax(newBoard, depth - 1);
            }
        }
        else {
            player = '2';
            for (Cell cell : board.getAvaliableCells()) {
                Board newBoard = new Board(board, cell, player);
                int value = eval(board);
                minmax(newBoard, depth - 1);
            }
        }


    }
    public static int eval(Board board){
        return 1;
    }
}
