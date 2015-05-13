package dongtaoy.squatter;

import aiproj.squatter.Move;
import aiproj.squatter.Piece;
import aiproj.squatter.Player;
import javafx.util.Pair;

import java.io.PrintStream;
import java.util.ArrayList;


public class Dongtaoy implements Player {

    private int piece;
    private Board board;


    public int init(int n, int piece) {
        this.board = new Board(n);
        this.piece = piece;
        return 0;
    }

    public int getPiece(){return this.piece;}
    public int getOpponentPiece(){
        return piece == Piece.BLACK ? Piece.WHITE : Piece.BLACK;
    }

    public Move makeMove() {
        Cell cell = minimax(board, null, 6, Integer.MIN_VALUE, Integer.MAX_VALUE, true).getValue();
        Move move = new Move(this.piece, cell.getRow(), cell.getCol());
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
            return new Pair<>(board.evaluate(this), move);
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
                Board newBoard = new Board(board, cell, this.getOpponentPiece());
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






    public String toString(){
        return new StringBuilder()
                .append("PLAYER: ")
                .append(piece)
                .append('\n').toString();
    }
}
