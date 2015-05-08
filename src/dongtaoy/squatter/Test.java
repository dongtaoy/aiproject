/**
 * Created by dongtao on 5/8/2015.
 */
package dongtaoy.squatter;


public class Test {

    public static void main(String[] args) {
        Board board = new Board(6);
        System.out.println(board);
        Node<Board> root = new Node<>(board);
        minmax(root, 5);

    }

    public static void minmax(Node node, int depth) {

        Board board = (Board) node.getData();
        char player;

        if (depth == 0)
            return;

        if (depth % 2 == 0)
            player = '1';
        else
            player = '2';

        System.out.println(board.getAvaliableCells().size());
        for (Cell cell : board.getAvaliableCells()) {
            Board newBoard = new Board(board, cell, player);
            Node<Board> newNode = new Node<>(newBoard);
            node.addChild(newNode);
//            System.out.println(newBoard);
            minmax(newNode, depth - 1);
        }
    }
}
