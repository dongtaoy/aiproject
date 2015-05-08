package dongtaoy.squatter;

import aiproj.squatter.Move;
import aiproj.squatter.Player;
import javafx.util.Pair;

import java.io.PrintStream;


public class Dongtaoy implements Player {

    private int dimensions;
    private char piece;
    private char opponentPiece;


    public int init(int n, int p) {
        this.dimensions = n;
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
        return new Move();
    }

    public int opponentMove(Move m) {
        return 0;
    }

    public int getWinner() {
        return 0;
    }


    public void printBoard(PrintStream output) {

    }

    public Pair<Integer, Board> minimax(Board board, int depth, int alpha, int beta, boolean isMax) {
        if (depth == 0)
            return new Pair<>(evaluate(board), board);

        if (isMax) {
            Pair<Integer, Board> bestValue = new Pair<>(Integer.MIN_VALUE, null);
            for (Cell cell : board.getAvaliableCells()) {
                Board newBoard = new Board(board, cell, this.piece);
                Pair<Integer, Board> value = minimax(newBoard, depth - 1, alpha, beta, false);
                if (value.getKey() > bestValue.getKey()) {
                    bestValue = value;
                    if (bestValue.getKey() > alpha)
                        alpha = bestValue.getKey();
                    if (beta <= alpha)
                        break;
                }
            }
            return bestValue;
        } else {
            Pair<Integer, Board> bestValue = new Pair<>(Integer.MAX_VALUE, null);
            for (Cell cell : board.getAvaliableCells()) {
                Board newBoard = new Board(board, cell, this.opponentPiece);
                Pair<Integer, Board> value = minimax(newBoard, depth - 1, alpha, beta, true);
                if (value.getKey() < bestValue.getKey()) {
                    bestValue = value;
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
}
