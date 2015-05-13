/**
 * Created by dongtao on 5/8/2+15.
 */
package dongtaoy.squatter;


public class Test {

    public static void main(String[] args) {
        Dongtaoy player = new Dongtaoy();
        char[][] contents = {
                {'+', 'W', 'W', 'W', 'B', '+'},
                {'W', '+', '+', '+', 'W', 'W'},
                {'W', '+', '+', '+', 'W', 'B'},
                {'W', '+', '+', '+', '+', '+'},
                {'+', 'W', '+', '+', '+', 'B'},
                {'+', '+', 'W', '+', 'B', '+'},
        };
        Board board = new Board(contents);
//        board.evaluate(player);
        board.findCycle();
        System.out.println(board);
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
