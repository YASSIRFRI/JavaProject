public class Player {
    private String userName;
    private boolean color;

    public Player(String userName,boolean color){
        this.userName = userName;
        this.color = color;
    }
    public Player(String userName){
        this.userName = userName;
    }
    
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public boolean getColor() {
        return color;
    }
    public void setColor(boolean color) {
        this.color = color;
    }
}
    
    
