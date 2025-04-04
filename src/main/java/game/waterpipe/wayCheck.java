package game.waterpipe;

public class wayCheck {

    private int[][] pipeArray;

    public wayCheck(int[][] pipes) {
        this.pipeArray = pipes;
    }

    public boolean stupidCheck(int size){
        boolean result = true; // برنده میشه مگر خلافش ثابت بشه :)
        if(size == 5){
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
        }
        else if(size == 7){
            System.out.println("in future updates انشالا");
        }
        return result;
    }
}
