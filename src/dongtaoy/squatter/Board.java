package dongtaoy.squatter;

import java.util.ArrayList;

/**
 * Created by dongtao on 3/25/2015.
 */


public class Board {

    private int dimension;
    private Cell[][] cells;

    /**
     * Create Board object
     * @param contents board layout in char[][] format
     */
    public Board(char[][] contents){
        this.dimension = contents.length;
        this.cells = new Cell[this.dimension][this.dimension];
        // Store cell in cell object;
        for(int i = 0; i < this.dimension ; i++)
            for(int j = 0; j < this.dimension ; j++)
                cells[i][j] = new Cell(contents[i][j]);
    }

    public Board(int dimension){
        this.dimension = dimension;
        this.cells = new Cell[this.dimension][this.dimension];
        for(int i = 0; i < this.dimension ; i++)
            for(int j = 0 ; j < this.dimension ;j++)
                cells[i][j] = new Cell('+');
    }

    public Board(Board board, Cell cell, char player){
        this.dimension = board.getDimension();
        this.cells = new Cell[this.dimension][this.dimension];
        for(int i = 0; i < this.dimension ; i++){
            for(int j = 0 ; j < this.dimension ; j ++){
                if(board.getCells()[i][j].equals(cell))
                    this.cells[i][j] = new Cell(player);
                else
                    this.cells[i][j] = new Cell(board.getCells()[i][j].getContent());
            }
        }
    }

    /**
     * Check win for this board
     */
    public void checkWin(){
        int whiteCaptured = 0;
        int blackCaptured = 0;
        boolean isFinished = true;

        for (int i = 0; i < this.dimension ; i ++){
            for (int j = 0; j < this.dimension ; j ++){
                // if cell is captured
                if (cells[i][j].getContent() == '-'){
                    // if the previous cell is captured
                    if(cells[i][j-1].getContent() != '-')
                        cells[i][j].setCapturedBy(cells[i][j - 1].getContent());
                    else
                        cells[i][j].setCapturedBy(cells[i][j - 1].getCapturedBy());

                    // increment counter
                    if (cells[i][j].getCapturedBy() == 'W')
                        whiteCaptured++;
                    else
                        blackCaptured++;
                }else if(cells[i][j].getContent() == '+'){
                    // if there is any '+' in cell, game is not finished
                    isFinished = false;
                }
            }
        }
        // if finished print who wins or a draw
        if(isFinished)
            System.out.println(blackCaptured > whiteCaptured ? "Black": (blackCaptured == whiteCaptured? "Draw": "White"));
        else
            System.out.println("None");
        System.out.println(whiteCaptured);
        System.out.println(blackCaptured);

    }

    /**
     *  toString function for Board
     * @return String representation of a board
     */
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        for (Cell[] row : this.cells){
            for (Cell c : row){
                stringBuilder.append(c.getContent());
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }


    public ArrayList<Cell> getEmptyCell(){
        ArrayList<Cell> value = new ArrayList<Cell>();
        for(int i = 0; i < this.dimension ; i++)
            for(int j = 0 ; j < this.dimension ;j++)
                if (cells[i][j].isEmpty())
                    value.add(cells[i][j]);
        return value;
    }

    public int getDimension() {
        return dimension;
    }

    public Cell[][] getCells() {
        return cells;
    }
}
