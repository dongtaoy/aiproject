package dongtaoy.squatter;

import aiproj.squatter.Move;
import aiproj.squatter.Player;
import javafx.util.Pair;

import java.io.PrintStream;
import java.util.ArrayList;


public class Dongtaoy implements Player {

    private char piece;
    private char opponentPiece;
    private int p;
    private Board board;


    public int init(int n, int p) {
        this.board = new Board(n);
        this.p = p;
        if (p == 1)
            this.piece = 'W';
        else if (p == 2)
            this.piece = 'B';
        else
            return -1;
        this.opponentPiece = (this.piece == 'W') ? 'B' : 'W';
        return 0;
    }

    public Move makeMove() {
        Cell cell = minimax(board, null, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true).getValue();
        Move move = new Move(this.p, cell.getRow(), cell.getCol());
        board.placeCell(move);
        return move;
    }

    public int opponentMove(Move m) {
        return board.placeCell(m);
    }

    public int getWinner() {
        return 0;
    }


    public void printBoard(PrintStream output) {
        output.print(board);
    }

    public Pair<Integer, Cell> minimax(Board board, Cell move, int depth, int alpha, int beta, boolean isMax) {
        ArrayList<Cell> avaliableCells = board.getAvaliableCells();
        if (depth == 0 || avaliableCells.size() == 0) {
            return new Pair<>(evaluate(board), move);
        }
        if (isMax) {
            Pair<Integer, Cell> bestValue = new Pair<>(Integer.MIN_VALUE, move);
            for (Cell cell : avaliableCells) {
                Board newBoard = new Board(board, cell, this.piece);
                Pair<Integer, Cell> value = minimax(newBoard, cell, depth - 1, alpha, beta, false);
                if (value.getKey() > bestValue.getKey()) {
                    bestValue = new Pair<>(value.getKey(), cell);
                    if (bestValue.getKey() > alpha)
                        alpha = bestValue.getKey();
                    if (beta <= alpha)
                        break;
                }
            }
            return bestValue;
        } else {
            Pair<Integer, Cell> bestValue = new Pair<>(Integer.MAX_VALUE, move);
            for (Cell cell : avaliableCells) {
                Board newBoard = new Board(board, cell, this.opponentPiece);
                Pair<Integer, Cell> value = minimax(newBoard, cell, depth - 1, alpha, beta, true);
                if (value.getKey() < bestValue.getKey()) {
                    bestValue = new Pair<>(value.getKey(), cell);
                    if (bestValue.getKey() < beta)
                        beta = bestValue.getKey();
                    if (beta <= alpha)
                        break;
                }
            }
            return bestValue;
        }

    }

    private int evaluate(Board board) {
        return 1;
    }

    public String toString(){
        return new StringBuilder()
                .append("PLAYER: ")
                .append(piece)
                .append('\n').toString();
    }
}
