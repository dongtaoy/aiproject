/**
 * Created by dongtao on 5/8/2+15.
 */
package dongtaoy.squatter;


import aiproj.squatter.Piece;

public class Test {

    public static void main(String[] args) {
        Dongtaoy player = new Dongtaoy();
        player.init(6, Piece.BLACK);

        char[][] contents = {
                {'+', '+', '+', '+', '+', '+'},
                {'+', 'B', '+', '+', '+', '+'},
                {'+', '+', 'B', '+', '+', '+'},
                {'+', '+', '+', 'B', '+', '+'},
                {'+', '+', '+', '+', '+', '+'},
                {'+', '+', '+', '+', '+', '+'},
        };
        Board board = new Board(contents);
        System.out.println(board.evaluate(player));
//        board.findCycle();
//        System.out.println(board.boardToString());
        System.out.println(board.statusToString());
//
//        char[][] contents = {
//                {'+', '+', '+', '+', '+', '+'},
//                {'+', '+', '+', '+', '+', '+'},
//                {'+', '+', '+', '+', '+', '+'},
//                {'+', '+', '+', '+', '+', '+'},
//                {'+', '+', '+', '+', '+', '+'},
//                {'+', '+', '+', '+', '+', '+'},
//        };
    }

}
