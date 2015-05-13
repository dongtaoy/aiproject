/**
 * Created by dongtao on 5/8/2+15.
 */
package dongtaoy.squatter;


import aiproj.squatter.Piece;

public class Test {

    public static void main(String[] args) {
        Dongtaoy player = new Dongtaoy();
        player.init(6,Piece.BLACK);

        char[][] contents = {
                {'+', 'W', 'W', 'W', 'B', '+'},
                {'W', '+', '+', '+', 'W', 'W'},
                {'W', '+', 'B', '+', 'W', 'B'},
                {'W', '+', '+', 'W', '+', '+'},
                {'+', 'W', '+', 'W', '+', 'B'},
                {'+', '+', 'W', '+', 'B', '+'},
        };
        Board board = new Board(contents);
        board.evaluate(player);
//        board.findCycle();
//        System.out.println(board.boardToString());
//        System.out.println(board.statusToString());
//
//        char[][] contents = {
//                {'+', 'W', '+', '+', 'W', '+'},
//                {'W', '+', 'W', 'W', '+', 'W'},
//                {'+', 'W', '+', '+', 'W', '+'},
//                {'+', 'B', '+', '+', '+', '+'},
//                {'B', '+', 'W', '+', 'W', '+'},
//                {'+', 'B', '+', 'W', '+', '+'},
//        };
    }

}
