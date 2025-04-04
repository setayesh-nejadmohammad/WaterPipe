package game.waterpipe;

public class testClass {
    private int[][] thisClassPipes;

    public testClass(int[][] pipes){
        this.thisClassPipes = pipes;
    }

    public void modifyArray(){
        for(int i = 0; i< 5; i++){
            for(int j = 0; j< 5; j++){
                System.out.print(thisClassPipes[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
