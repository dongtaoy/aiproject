package aiproj.squatter;
/*
 *   Move:
 *      Define possible Squatter move
 *      
 *   @author lrashidi
 *   
 */

public class Move{
	
	public int P;
	public int Row;
	public int Col;
    public Move(){

    }

	public Move(int p, int row, int col){
        this.P = p;
        this.Row = row;
        this.Col = col;
    }
		
}