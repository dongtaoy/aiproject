/**
 * Created by dongtao on 5/8/2+15.
 */
package dongtaoy.squatter;


import aiproj.squatter.Move;
import aiproj.squatter.Piece;

public class Test {

    public static void main(String[] args) {
        Dongtaoy player = new Dongtaoy();
        player.init(6, Piece.BLACK);

        char[][] contents = {
                {'+', 'B', '+', '+', '+', '+'},
                {'+', 'B', '+', '+', '+', '+'},
                {'+', 'B', 'B', 'W', '+', 'W'},
                {'+', 'W', 'W', '+', 'W', '+'},
                {'+', 'W', '+', '+', 'B', '+'},
                {'+', 'W', '+', '+', '+', '+'},
        };
//        player.makeMove();
        Board board = new Board(contents);
//        board.
        board.evaluate(player);
//        System.out.println(board);
//        board.placeCell(new Move(Piece.WHITE, 4, 2));
//        board.evaluate(player);
//        System.out.println(board);


//        char[][] contents = {
//                {'+', 'B', 'B', 'B', '+', '+'},
//                {'B', '+', 'W', '+', 'B', '+'},
//                {'B', 'W', '+', 'W', 'B', '+'},
//                {'B', '+', 'W', '+', 'B', '+'},
//                {'+', 'B', '+', 'B', '+', '+'},
//                {'+', '+', '+', '+', '+', '+'},
//        };
//        Board board = new Board(contents);
//        board.evaluate(player);
//        System.out.println(board);
//        board.placeCell(new Move(Piece.BLACK, 4, 2));
//        board.evaluate(player);
//        System.out.println(board);
    }

}
