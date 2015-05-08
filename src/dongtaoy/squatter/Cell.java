package dongtaoy.squatter;

/**
 * Created by dongtao on 3/25/2015.
 */
public class Cell {
    private char content;
    private char capturedBy;

    public Cell(char content){
        this.content = content;
    }

    public char getContent() {
        return content;
    }

    public void setContent(char content) {
        this.content = content;
    }

    public char getCapturedBy() {
        return capturedBy;
    }

    public void setCapturedBy(char capturedBy) {
        this.capturedBy = capturedBy;
    }

    public boolean isEmpty(){
        return content == '+';
    }
}
