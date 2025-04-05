package game.waterpipe;

public class wayCheck {

    private int[][] pipeArray;

    public wayCheck(int[][] pipes) {
        this.pipeArray = pipes;
    }

    public boolean stupidCheck(int size){
        boolean result = true; // برنده میشه مگر خلافش ثابت بشه :)
        /*if(size == 5){
            if(pipeArray[1][0] != 3 ||
                    pipeArray[1][1] != 2 ||
                    pipeArray[1][2] != 2 ||
                    pipeArray[1][3] != 5 ||
                    pipeArray[2][0] != 4 ||
                    pipeArray[2][1] != 2 ||
                    pipeArray[2][2] != 2 ||
                    pipeArray[2][3] != 6 ||
                    pipeArray[3][0] != 1 ||
                    pipeArray[4][0] != 3 ||
                    pipeArray[4][1] != 2 ||
                    pipeArray[4][2] != 2 ||
                    pipeArray[4][3] != 2) result = false;
        }*/
        if(size == 7){
            System.out.println("This is pipeArray:");
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    System.out.print(pipeArray[i][j]+" ");
                }
                System.out.println();
            }
            System.out.println();
            if(pipeArray[1][0] != 1 ||
                    pipeArray[2][0] != 1 ||
                    pipeArray[3][0] != 3 ||
                    pipeArray[3][1] != 2 ||
                    pipeArray[3][2] != 2 ||
                    pipeArray[3][3] != 2 ||
                    pipeArray[3][4] != 5 ||
                    pipeArray[4][1] != 4 ||
                    pipeArray[4][2] != 2 ||
                    pipeArray[4][3] != 2 ||
                    pipeArray[4][4] != 6 ||
                    pipeArray[5][1] != 1 ||
                    pipeArray[6][1] != 3 ||
                    pipeArray[6][2] != 2 ||
                    pipeArray[6][3] != 2 ||
                    pipeArray[6][4] != 2 ||
                    pipeArray[6][5] != 2) result = false;
        }
        return result;
    }
}
