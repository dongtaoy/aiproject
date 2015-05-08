/**
 * Created by dongtao on 5/8/2015.
 */
package dongtaoy.squatter;


public class Test {

    public static void main(String[] args) {
        Dongtaoy player = new Dongtaoy();
        Board board = new Board(6);
        player.init(6, 1);
        System.out.println(player.minimax(board, null, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true).getValue());

    }

}
