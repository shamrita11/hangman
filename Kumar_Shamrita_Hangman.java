/*
 * File Name: Kumar_Shamrita_Hangman
 * Name: Shamrita Saravanakumar
 * Description: a game of hangman, users try to figure out an unknown word by guessing letters (characters)
 * If 7 letters which do not appear in the word are guessed, the player loses. Must prompt the user if they would like to play again. 
 * Also must give an updated look of the word as letters are guessed.
 * Date Created: March 9, 2022
 */
 
import java.util.Scanner;
import java.util.Random;


public class Kumar_Shamrita_Hangman

{
    public static void main(String[] args)
    {
        Scanner s = new Scanner (System.in);
        Random r = new Random();
        boolean play = true; //this is so the whole game loops
        
        do {//do-loop to have the "Play Again" feature
        
        String word = "", blank = "", spaces = "", oppBlank = "", Playagain = "", letters = "", usedl = "";
        int i = 0, index = -1, lives = 7, wordlen = 0;
        char letter = ' ';
        char cletter[] = new char [wordlen + 6]; //array for the amount of letters being guessed
        String sports [] = {"SPORTS", "BOBBY ORR", "BASKETBALL", "RACQUETBALL", "SIDNEY CROSBY"};
        String movies [] = {"MOVIES", "AVATAR", "STAR WARS", "THE GODFATHER", "TERMINATOR"};
        String tvshows [] = {"TV SHOWS", "SPONGEBOB SQUAREPANTS","FAMILY GUY", "LOST", "HOUSE", "SURVIVOR"};
        String [][] words = {sports, movies, tvshows}; //2D array to put all the words into one section
        String category [] = {sports [0], movies[0], tvshows [0]};//to just get category
            
        //Introduction
        System.out.println("\nWelcome to Hangman!");
        System.out.println("\nHangman is a simple word guessing game. Players try to figure out an unknown word by guessing letters. If 7 letters which do not appear in the word are guessed, the player loses!");
        System.out.println("\nAlright, Let's Play the Game\n");
        //function to pick the word    
        word = word_picker(category, words, word);
        
        //intializes the variable after picking word
        wordlen = word.length ();
        //function to change the word into blanks
        blank = blank_word (spaces, word, wordlen);
        //this is to initializes what oppBlank is (which will be the word picked previously)
        oppBlank = word;
        
        do { //loop for choosing the letter
            
            //output of blank and how many words
            System.out.println("\n**************************************\n");
            System.out.println("Number of Words: " + Wordcount(word, wordlen));
            System.out.println(blank);
            System.out.print("\nChoose a letter: ");
            letter = s.next().charAt(0);
            
            //determines if the character entered is a letter
            if((letter >= 'A' && letter <= 'Z') || (letter >= 'a' && letter <= 'z')){
               cletter [i] = Character.toUpperCase(letter);
               letters = cletter[i] + ""; //makes characters in a string in order to find the index
               
               for (int j = 0; j < cletter.length; j++) {
               usedl += cletter[j] + " ";
               }
               System.out.println("Used Letter: " + usedl);
               
                index = word.indexOf (letters); //looks for the location of the letter within the word
            
                if (index >= 0) { //index of > 0, means that the letter is in the word 
               
                while ((index >= 0)){ 
                /* This while loop, repeats the same function of looking for the letter inputted
                by the user in the word. This will keep looping until there are no more occurences of that character
                in the word.*/  
                
                    blank = Word_Fill (blank, index, letters, word, wordlen); //function to output the updated blank string
                    oppBlank = Blank_Fill(oppBlank, index, letters, word, wordlen);//this function stores the opposite of blank
                    index = changeIndex(index, oppBlank, letters); //updates the index as it finds one occurence of the letter
                }
            
                if (blank.equals (word)) { //if the user guesses the word
                System.out.println("\nCongratulations!\n");
                lives = Playagain (lives, Playagain); //calls the playagain function
                    if (lives == -1) {
                        play = false;
                    }
                }
            
                else
                    System.out.println ("\n" + blank + "\n");
                }
             
                else {
                  System.out.println ("\n" + blank + "\n");
                  //this calls the subtract lives function
                  lives = Subtract_Lives (lives, letters, i, word);
                    if (lives == 0) {
                        lives = Playagain (lives, Playagain);
                        //basically if the live returned is == -1 the game will end
                        if (lives == -1) {
                            play = false;
                        }
                    }
                }
               
            }
            
            else
                System.out.println("\nError: Please enter a letter\n");
            
        } while (!blank.equalsIgnoreCase(word) || lives == 0); //condition for the choosing letter loop
    } while (play == true); //condition for playing the game
} 

/**
     * Description: This method is to choose what category and then what word the user 
     * has to guess
     * Precondition: double array and arrays must be declared and initialized 
     * Postcondition: the word and category is chosen 
     * @param - the double array for words, and array for categories
     * @return the word that was randomly chosen
     */
    
public static String word_picker (String [] c, String [][] arr, String word) {
    int random = 0;
    int random2 = 0;
    Random r = new Random();
         
    random = r.nextInt(c.length);
    System.out.println("Category: " + c[random]);
    
    //looks for the location of the category and then picks a random word from set caetgory array    
    random2 = r.nextInt(arr[random].length - 1) + 1;
    word = arr[random][random2];
     
    return word;
}

/**
     * Description: This method is to turn the word into blanks "-" to output for the user
     * Precondition: String word must be declared and initialized 
     * Postcondition: the string of blanks is determined  
     * @param - the string word (which was chosen from the last method), the integer of word length
     * @return the string spaces which is the word hyphenated 
     */

public static String blank_word (String spaces, String word, int wordlen)  {
    
        char ch[]= new char[wordlen];     
        for(int i = 0; i < wordlen; i++) {  
        ch[i]= word.charAt(i);  
            if((ch[i] != ' '))  
                spaces += "-";
            else    
                spaces += " ";
        //through a for loop it looks for when the word has letters and replaces it with -
        //if there is a space then it will print out a space
        }
        
        return spaces;
}

/**
     * Description: this method determines how many words there are 
     * Precondition: String word must be declared and initialized 
     * Postcondition: the number of words, count must be determined  
     * @param - the string word (which was chosen from previous method), the integer of word length
     * @return the int of how many words the user needs to guess  
     */
     
public static int Wordcount (String word, int wordlen){
    int count = 0;  
      
    char ch[]= new char[wordlen];     
        for(int i = 0; i < wordlen; i++) {  
            ch[i]= word.charAt(i);  
            if( ((i > 0) && (ch[i] != ' ') && (ch[i-1] ==' ')) || ((ch[i] != ' ') && (i==0)))  
                count++;
            //if there is a space in the word, the count will increase showing how many words are there
        } 
        
        return count;  
}

/**
     * Description: this method updates the blank string and replaces "-", with any letters guessed 
     * Precondition: String blank, String word must be declared and initialized, and index must be greater than 0, and
     * the letter that was guessed is expressed through the string letters
     * Postcondition: the blank string must be updated to show the letters guessed 
     * @param - the string word (which was chosen from previous method), the integer of word length, the string blank
     * (which was determined from previous method), integer of the index 
     * @return the updated blank string   
     */
     
public static String Word_Fill (String blank, int index, String letters, String word, int wordlen)
        
    {
        if (index == 0) {
        blank = letters + blank.substring (index + 1);
        //if the letter is in the first location then it replaces it with letters and prints out blanks for the rest of the string
        }
        
        else {
        blank = blank.substring(0, index) + letters + blank.substring(index + 1, wordlen);
        } //in this case it will bring out the blanks until the location of the letter guessed, and then print blanks from the location +1 to the end of the word
        
        return blank;
    }
  
/**
     * Description: this method is suppose to do the the opposite of the previous method. It changes whichever letter that was guessed to "-"
     * and the letters that are not guessed are shown as letters. This is suppose to help with getting the index.
     * Precondition: String oppblank, String word must be declared and initialized, and index must be greater than 0, and
     * the letter that was guessed is expressed through the string letters
     * Postcondition: the oppblank string must be updated to show change the letters that were guessed into "-" 
     * @param - the string word (which was chosen from previous method), the integer of word length, the string oppblank
     * (which was determined from previous method), integer of the index, the string of the letter guessed 
     * @return the updated oppblank string   
     */
     
public static String Blank_Fill (String oppBlank, int index, String letters, String word, int wordlen)
        
    {
        if (index == 0) {
        oppBlank = "-" + oppBlank.substring (index + 1, wordlen);
        } 
        
        else {
        oppBlank = oppBlank.substring (0, index) + "-" + oppBlank.substring(index + 1, wordlen);
        }
        
        //same theory is used from previous method but the opposite is done so when the letter is guessed the letter location turn into a hyphen and the string of letters will be printed
        
        return oppBlank;
    }

/**
     * Description: this method is suppose to change the index number (the location of the letter in the word) using
     * the the oppblank string determined before.
     * Precondition: String oppblank, and index must be greater than 0, and
     * the letter that was guessed is expressed through the string letters
     * Postcondition: the index must be updated so that it can locate where the letter is (if there is more than one occurence)
     * @param - the integer of index, the string oppblank (determined from previous method), the string of the letter guessed 
     * @return the updated index number   
     */
     
public static int changeIndex (int index, String oppBlank, String letters) {
    return index = oppBlank.indexOf(letters);
}

/**
     * Description: this method subtracts the amount of "lives" that the user has, when a wrong letter
     * is guessed then the hangman image is printed out
     * Precondition: String word, and index must be greater than 0, and
     * the letter that was guessed is expressed through the string letters
     * Postcondition: the lives must be subracted if the user guesses the wrong letter
     * @param - the integer of lives, the string of the letter guessed, the string of the word that was determined before
     * @return the updated amount of lives   
     */
public static int Subtract_Lives (int lives, String l, int i, String word)
       
    {
        i = 0;
        i++;
        lives--;

       System.out.println ("\nSorry, the letter is not in the word");
        if (lives == 6)
        {
           System.out.println (" ____________");
           System.out.println ("|     |     |");
           System.out.println ("|     O     |");
           System.out.println ("|           |");
           System.out.println ("|           |");
           System.out.println ("|           |");
           System.out.println (" ____________\n");
        }

        if (lives == 5)
        {
           System.out.println (" ____________");
           System.out.println ("|     |     |");
           System.out.println ("|     O     |");
           System.out.println ("|     |     |");
           System.out.println ("|           |");
           System.out.println ("|           |");
           System.out.println (" ____________\n");
        }

        if (lives == 4)
        {
           System.out.println (" ____________");
           System.out.println ("|     |     |");
           System.out.println ("|     O     |");
           System.out.println ("|     |     |");
           System.out.println ("|     |     |");
           System.out.println ("|           |");
           System.out.println (" ____________\n");
        }

        if (lives == 3)
        {
           System.out.println (" ____________");
           System.out.println ("|     |     |");
           System.out.println ("|     O     |");
           System.out.println ("|    /|     |");
           System.out.println ("|     |     |");
           System.out.println ("|           |");
           System.out.println (" ____________\n");
        }
        if (lives == 2)
        {
           System.out.println (" ____________");
           System.out.println ("|     |     |");
           System.out.println ("|     O     |");
           System.out.println ("|    /|\\    |");
           System.out.println ("|     |     |");
           System.out.println ("|           |");
           System.out.println (" ____________\n");
        }

        if (lives == 1)
        {
            System.out.println(" ____________");
           System.out.println ("|     |     |");
           System.out.println ("|     O     |");
           System.out.println ("|    /|\\    |");
           System.out.println ("|     |     |");
           System.out.println ("|    /      |");
           System.out.println (" ____________\n");
        }

        if (lives == 0)
        {
           System.out.println(" ____________");
           System.out.println ("|     |     |");
           System.out.println ("|     O     |");
           System.out.println ("|    /|\\    |");
           System.out.println ("|     |     |");
           System.out.println ("|    / \\    |");
           System.out.println (" ____________\n");
           
        System.out.println ("\n\nGame Over :( Your word was " + word);
        }

        return lives;
    }

/**
     * Description: the method is to determine if the game should be played again
     * Precondition: the lives must be at 0, and the user must input yes or no to determine if the game is run again  
     * Postcondition: the game must loop again if the user wants, or the game must end if the loop doesn't want too
     * @param - the integer of lives, the string of playagain
     * @return the amount of lives again (which will determine whether the game should be played again)   
     */
     
public static int Playagain (int lives, String Playagain)  
    {
        Scanner s = new Scanner(System.in);
        
        do{
        
        System.out.println ("\nDo you want to play again? (yes or no)\n");
        Playagain = s.nextLine ();
        
        if (Playagain.equalsIgnoreCase ("yes"))
            lives = 7;
            
        else if (Playagain.equalsIgnoreCase ("no")) {
            System.out.println ("\nThanks for playing!");
            lives = -1; // makes lives -1 and therefore ends the code
        }
        
        } while ((!Playagain.equalsIgnoreCase ("yes")) && (!Playagain.equalsIgnoreCase ("no")));
        
        return lives;
    }
    
}
