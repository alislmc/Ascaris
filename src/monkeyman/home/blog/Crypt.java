package monkeyman.home.blog;

import java.util.Random;

public class Crypt {

    private static int getRandome(){
        Random r = new Random();
        int low = 97;
        int high = 122;
        return r.nextInt(high-low) + low;
    }


    public static String Encrypt(String input){
        StringBuilder temp = new StringBuilder("");

        for(char item : input.toCharArray()){
            temp.append((((int) ((char) item)) << 3) + 13 + "" + (char) Crypt.getRandome());
        }
        return temp.toString();
    }


    public static String Decrypt(String input){
        StringBuilder str = new StringBuilder("");
        StringBuffer answer = new StringBuffer("");
        int buffer = 0 ;
        for(char item : input.toCharArray()){
            if(Character.isDigit(item)) {str.append(item);}
            if(!Character.isDigit(item)) {
                buffer = Integer.valueOf(str.toString());
                buffer = (buffer - 13) >> 3;
                answer.append((char)buffer);
                str = new StringBuilder("");
            }
        }
        return answer.toString();
    }


}
