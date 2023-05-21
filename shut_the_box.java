MyProgram.java:
--------------
public class MyProgram
{
    public static void main(String[] args)
    {
        tray startGame = new tray();
        startGame.inputPrompts();
      
    }
}

die.java:
--------
public class die {
   
    public die(){
        
    }
  
    
   public int roll(){
    
   int roll1 = (int)(Math.random()*6+1);
   int roll2 = (int)(Math.random()*6+1);
   int out = roll1 + roll2;
   System.out.println("\nYour dice rolled " + roll1 + " and " + roll2);
   System.out.println("The sum is: " +out );
   return out;
   }
   
   public int rollOnce(){
       int roll = (int)(Math.random()*6+1);
       int out = roll;
        System.out.println("\nYour dice rolled " + roll);
        System.out.println("The sum is: " +out );
         return out;
   }
   
}

tile.java:
---------
import java.util.*;
public class tile{
   private boolean[] tiles;
   
   
   public tile(){
       tiles = new boolean[]{true, true, true, true,true,true,true,true, true};
   }
   public boolean[] getTiles(){
       
       return tiles;
   }
   public void printTiles(){
       boolean [] temp = tiles;
       System.out.println("");
            for(int x = 0; x < temp.length; x++){
                
                if(temp[x]){
                    System.out.print(x+1 + " ");
                }
                
            }
            System.out.println("\n");
       }
    
     public void closeTiles(ArrayList <Integer> val){
         
         for(int x = 0; x< val.size();x++){
             tiles[(val.get(x)-1)] = false;
         }
         
         
     }
      public boolean tileChecker(ArrayList <Integer> values, int sum){
           int temp = 0;
          for(int x = 0; x < values.size(); x++){
              
              if(tiles[values.get(x)-1] == false){
                 
                return false;  
              }
              temp = temp + values.get(x);
              
          }
          if(duplicateChecker(values) == false){
             
              return false;
          }
          if(temp == sum){
              
              return true;
          }else{
             
              return false;
          }
       }
       
     public boolean duplicateChecker(ArrayList <Integer> values){
         for(int x = 0; x< values.size(); x++){
             for(int y = x+1; y < values.size(); y++ ){
                 
                 if(values.get(x) == values.get(y)){
                     
                     return false;
                 }
             }
             
         }
         return true;
         
     }
     public ArrayList<Integer> arrayOfAvaliableValues(){
         
         ArrayList <Integer> numbers = new ArrayList <>();
         for(int x = 0; x < tiles.length; x++){
             
             if(tiles[x]){
                 numbers.add(x+1);
             }
         }
         
        return numbers; 
     }
     public int scoreCalc(){
         int temp = 0;
            for(int x = 0; x < tiles.length; x++){
                
                if(tiles[x]){
                    temp = temp + x + 1;
                }
                
            }
            return temp;
       }
      public boolean oneDieCheck(){
         if(tiles[6] == false && tiles[7] == false && tiles[8] == false){
             return true;
             
         }else{
             return false;
         } 
          
          
      }  
         
     
       
  
}

tray.java:
---------
import java.util.Scanner;
import java.util.*;
public class tray {
      private die dice;
      private tile board;
      public tray(){
          
       dice = new die();
       board = new tile();
        
          
      }
    public void inputPrompts(){
        Scanner keyboard = new Scanner (System.in);
        System.out.println("Pick an option");
        System.out.println("R for roll, D to display the tiles still open");
        String input = keyboard.nextLine();
        if(input.equals("R") && board.oneDieCheck() == true){
            System.out.println("Do you want to roll 1 or 2 die, please type one or two");
            input = keyboard.nextLine();
            if(input.equals("one")){
                oneDiceRoll();
                
            }else if(input.equals("two")){
                input = "R";
                turns(input);
            }
        }else{
          turns(input);  
        }
        
    }
    
    public void turns(String s){
        if(s.equals("R")){
            
            int temp = dice.roll();
          if(gameEndCheck(temp)){
              pickTiles(temp);
          }else{
              System.out.println("You are unable to close tiles that add up to the sum, the game is over");
              System.out.println("The remaining tile were");
              board.printTiles();
             int a = board.scoreCalc();
             System.out.println("Your final score is: " + a);
          }
          
        }else if(s.equals("D")){
            board.printTiles();
            
            inputPrompts();
        }else{
            System.out.println("\nthat is not a valid input, please try again\n");
            inputPrompts();
        }
        
     
    }
     public void oneDiceRoll(){
        
            
            int temp = dice.rollOnce();
          if(gameEndCheck(temp)){
              pickTiles(temp);
          }else{
              System.out.println("You are unable to close tiles that add up to the sum, the game is over");
              board.printTiles();
             int a = board.scoreCalc();
             if(a != 0){
             System.out.println("Your final score is: " + a);
             }
          }
        
     
    }
    
    public void pickTiles(int sum){
       
        System.out.println("\nPick tiles to close that add up to the sum, hit enter after each number");
        System.out.println("Your avaliable tiles are: ");
        board.printTiles();
         Scanner keyboard = new Scanner (System.in);
         ArrayList <Integer> inputValues = new ArrayList <>();
         String response = "y";
         while(response.equals("y")){
         
             System.out.println("Pick a tile, then hit enter");
             inputValues.add(keyboard.nextInt());
             System.out.println("If you want to enter more numbers type y, if not type n");
            response = keyboard.next();
           
         }
        
         if(board.tileChecker(inputValues,sum)){
             
             System.out.println("Your chosen tiles have been closed");
             board.closeTiles(inputValues);
              inputPrompts();
         
             
          
         }else{
             
             System.out.println("That is not a valid input");
             System.out.println("Please make sure that the tiles add up to the sum and that the tiles are not already closed");
             System.out.println("\nRemember, your sum is: " + sum);
             pickTiles(sum);
         }
    }
    public  boolean gameEndCheck(int sum){
        ArrayList <Integer> temp = board.arrayOfAvaliableValues();
        if(temp.size() == 0){
            
         System.out.println("You have won the game by closing all of the tiles!");
         return false;
        
        }else if(temp.size() == 1){
            if(temp.get(0) == sum){
            
                return true;
            }
        }else if(temp.size() == 2){
            if(temp.get(0) == sum || temp.get(1) == sum){
                return true;
            }
            if((temp.get(0) + temp.get(1)) == sum){
                return true;
            }
        }else if(temp.size() == 3){
              if(temp.get(0) == sum || temp.get(1) == sum || temp.get(2) == sum){
                return true;
            }
           if((temp.get(0) + temp.get(1)) == sum || (temp.get(1) + temp.get(2)) == sum || (temp.get(0) + temp.get(2)) == sum){
                return true;
            }
            if((temp.get(0) + temp.get(1) + temp.get(2)) == sum){
                return true;
            }
        }else{
            for(int x = 0; x< temp.size()-1; x++){
                for(int y = x+1; y < temp.size(); y++ ){
                
                if((temp.get(x) + temp.get(y)) == sum){
                    return true;
                }
              }
            }
            for(int x = 0; x < temp.size()-2; x++){
                for(int y = x+1; y < temp.size()-1; y++){
                    for(int z = y+1; z < temp.size(); z++){
                        if((temp.get(x) + temp.get(y) + temp.get(z)) == sum){
                            return true;
                        }
                    }
                    
                }
                
            }
            for(int x = 0; x < temp.size(); x++){
                if(temp.get(x) == sum){
                    return true;
                }
            }
            if((temp.get(0) + temp.get(1) + temp.get(2) + temp.get(3)) == sum){
                return true;
            }
            
        }
        
        return false;
        
    }      

   
    

    
    
}

