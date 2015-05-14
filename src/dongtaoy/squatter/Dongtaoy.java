package dongtaoy.squatter;

import aiproj.squatter.Move;
import aiproj.squatter.Piece;
import aiproj.squatter.Player;
import javafx.util.Pair;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;


public class Dongtaoy implements Player {

    private int piece;
    private Board board;
    static final int depth = 5;

    public int init(int n, int piece) {
        this.board = new Board(n);
        this.piece = piece;
        return 0;
    }

    public int getPiece() {
        return this.piece;
    }

    public int getOpponentPiece() {
        return piece == Piece.BLACK ? Piece.WHITE : Piece.BLACK;
    }

    public Move makeMove() {
        Cell cell = minimax(board, null, depth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true).getValue();
        Move move = new Move(this.piece, cell.getRow(), cell.getCol());
        board.placeCell(move);
        return move;
    }

    public int opponentMove(Move m) {
        return board.placeCell(m);
    }

    public int getWinner() {
//        HashMap<Integer, Integer> map = this.board.checkWin(this);
//        if (map.get(-5) == 1) {
//            if (map.get(this.getPiece()) > map.get(this.getOpponentPiece())) {
//                return this.getPiece();
//            } else if (map.get(this.getPiece()) == map.get(this.getOpponentPiece())) {
//                return Piece.DEAD;
//            } else {
//                return this.getOpponentPiece();
//            }
//        }

        return Piece.EMPTY;
    }


    public void printBoard(PrintStream output) {
        output.print(board);
    }

    public Pair<Double, Cell> minimax(Board board, Cell move, int depth, Double alpha, Double beta, boolean isMax) {
        ArrayList<Cell> symmetricCells = board.getSymmetricCells();
        if (depth == 0 || symmetricCells.size() == 0) {
            return new Pair<>(board.evaluate(this), move);
        }
        if (isMax) {
            Pair<Double, Cell> bestValue = new Pair<>(Double.NEGATIVE_INFINITY, move);
            for (Cell cell : symmetricCells) {
                Board newBoard = new Board(board, cell, this.piece);
                Pair<Double, Cell> value = minimax(newBoard, cell, depth - 1, alpha, beta, false);
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
            Pair<Double, Cell> bestValue = new Pair<>(Double.POSITIVE_INFINITY, move);
            for (Cell cell : symmetricCells) {
                Board newBoard = new Board(board, cell, this.getOpponentPiece());
                Pair<Double, Cell> value = minimax(newBoard, cell, depth - 1, alpha, beta, true);
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


    public String toString() {
        return new StringBuilder()
                .append("PLAYER: ")
                .append(piece)
                .append('\n').toString();
    }
}
