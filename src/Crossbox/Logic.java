package Crossbox;

import java.util.ArrayList;

public class Logic {

    static int turn = 0;
    static int[][] board = {
            {1,1,1},
            {0,0,0},
            {7,7,7}
            };


    public  String is_valid_move(int cur_i,int cur_j,int next_i, int next_j){
        boolean in_range = false;
        int curr = cur_i + cur_j;
        int next = next_i + next_j;
        boolean not_same_color = board[next_i][next_j] == 0;
        if (board[cur_i][cur_j] == 0)
            return "FF";

        if (Math.abs(next_i - cur_i) <= 1
                && Math.abs(next_j - cur_j) <= 1
                && (curr%2==0 || next%2==0))
            in_range = true;

        if (in_range && not_same_color)
            return "T0";
        else if (!not_same_color)
            return "FS";
        return "F0";
    }

    public int is_Game_finished(){
            //row sum
            for(int i = 1; i<3; i++){
                int row_sum = 0;
                for(int j = 0; j<3; j++){
                    row_sum += board[i][j];
                }
                if (row_sum == 3) {
                    if (i == 0)
                        continue;

                    return 1;

                } else if(row_sum == 21){
                    if (i == 2)
                        continue;
                    return 2;
                }

            }

            //column checker
            for (int i = 0; i < 3; i++){
                int col_sum = 0;
                for (int j = 0; j < 3; j++){
                    col_sum += board[j][i];
                }
                if (col_sum == 3)
                    return 1;
                else if (col_sum == 21)
                    return 2;
            }

            //left Diagonal checker
            int left_diagonal_sum = 0;
            int right_diagonal_sum = 0;
            int j1 = 0;
            int j2 = 2;
            for(int i = 0; i<3;i++){
                left_diagonal_sum +=board[i][j1];
                j1++;
                right_diagonal_sum +=board[i][j2];
                j2--;
            }
            if(left_diagonal_sum ==3 || right_diagonal_sum==3)
                return 1;
            if (right_diagonal_sum == 21 || left_diagonal_sum ==21)
                return 2;

            return 0;
    }

    public void move(int cur_i, int cur_j, int next_i, int next_j){

                board[cur_i][cur_j] = 0;
                if (turn ==0)
                    board[next_i][next_j] = 1;
                else
                    board[next_i][next_j] = 7;
                changeTurn();


    }

    // Turn changer
    public void changeTurn(){
        turn = 1 - turn;
    }

    public int getTurn(){
        return turn;
    }



//    public String randomCodeGenerator(){
//        String[] pool = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "p", "m","R",
//                        "1", "2", "3", "4", "5", "6", "7", "8", "9"};
//        ArrayList<String > code = new ArrayList<>();
//        for (int i = 0; i < 32;i++){
//            int idx =(int)Math.floor(Math. random()*(pool.length - 0+1)+ 0);
//            code.add(pool[idx]);
//        }
//        return String.join("",code);
//    }

}

