/**
 * Created by dongtao on 5/8/2015.
 */
package dongtaoy.squatter;


public class Test {

    public static void main(String[] args){
        Board board = new Board(6);
        System.out.println(board);
        minmax(board, 2);
    }

    public static void minmax(Board board, int depth){
        if (depth == 0)
            return;

//        Node root = new Node<Board>(board);
        System.out.printf("DEPTH: %d---------------------------------\n", depth);
        for(Cell cell: board.getEmptyCell()){
            Board newBoard = new Board(board, cell, 'B');
            System.out.println(newBoard);
            minmax(newBoard, depth - 1);
        }
    }
}
