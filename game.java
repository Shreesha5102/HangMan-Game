import java.util.*;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;



class work {
	JFrame frame = new JFrame();
	JLabel title = new JLabel("!!!Welcome to HangMan!!!",JLabel.CENTER);
	
	
	JTextArea Area = new JTextArea();
	
	
	JPanel bpanel = new JPanel();
	JLabel lab = new JLabel("Enter a Character");
	JTextField l = new JTextField(10);
	JButton check = new JButton("Check");
	
	
	//Varaibles For Game
	String Word = "";
	char[] GuessedWord = new char[50];
	int sizeOfWord;
	int guesses = 5,sizeOfGuessed=0;
	String ch; 
	char letter;
	
	//Constructor
	public work() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,500);
		title.setFont(new Font("TimesRoman",Font.BOLD,40));
		JPanel cpanel = new JPanel();
		cpanel.setLayout(new BorderLayout(0,0));
		cpanel.add(Area,BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(Area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		cpanel.add(scrollPane);
		Area.setFont(new Font("TimesRoman",Font.BOLD,20));
		Area.setEditable(false);
		
		
		
		lab.setFont(new Font("TimesRoman",Font.PLAIN,20));
		l.setFont(new Font("TimesRoman",Font.PLAIN,20));
		check.setFont(new Font("TimesRoman",Font.PLAIN,20));
		bpanel.add(lab);
		bpanel.add(l);
		bpanel.add(check);
		
		
		frame.add(BorderLayout.NORTH,title);
		frame.add(BorderLayout.CENTER,cpanel);
		frame.add(BorderLayout.SOUTH,bpanel);
		frame.setVisible(true);
	}
	
	
	public String getWord() {
		//getting a word From Dictionary
				try{
					String Word1 = "";
					BufferedReader reader = new BufferedReader(new FileReader("Words.txt"));
				    String line = reader.readLine();
				    List<String> words = new ArrayList<String>();
				    while(line != null) {
				        String[] wordsLine = line.split(" ");
				        for(String word : wordsLine) {
				            words.add(word);
				        }
				        line = reader.readLine();
				    }

				    Random rand = new Random(System.currentTimeMillis());
				    Word1 = words.get(rand.nextInt(words.size()));
				    Word1 = Word1.toLowerCase();
				    sizeOfWord = Word1.length();
				    System.out.print(Word1);
				    reader.close();
				    return(Word1);
				    } 
				    catch (Exception ex) 
				    {
				    	System.out.println("error:" + ex);
				    	return("");
				    }
	}
	
	public void getLetter() {
		check.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				ch = l.getText();
				letter = ch.charAt(0);
				int pos = Word.indexOf(letter);
				l.setText("");
				
				if(pos>=0) {
					GuessedWord = fillGuessedWord();
					displayGuessedWord();
				}else {
					Area.append("\n\""+letter+"\" not in Word\nWrong Guess");
					guesses -= 1;
					displayGuessedWord();
				}
				if(sizeOfGuessed==sizeOfWord) {
					Area.append("\nCongratulations You Guessed the Word\n" + Word);
				}
				
				if(guesses == 0) {
					Area.append("\nOops!!! Game-Over");
				}	
				
				Area.setCaretPosition(Area.getDocument().getLength()-1);
			}
    	});
		
	}
	
	public char[] fillGuessedWord() {
		char[] secretWord = new char[sizeOfWord];
		int i = 0;
		for(i=0;i<sizeOfWord;i++) {
			secretWord[i] = Word.charAt(i);
		}
		int elem = Word.indexOf(letter);
		if( letter == GuessedWord[elem]) {
			Area.append("\nLetter Already Present\nPlease Guess another Letter");
		}else {
			Area.append("\nGuessed \""+letter+"\" is present in Word");
			for(i=elem;i<sizeOfWord;i++) {
				if(secretWord[i] == letter) {
					GuessedWord[i] = letter;
					sizeOfGuessed += 1;
				}
		}
		
		}
		return GuessedWord;
	}
	
	public void displayGuessedWord() {
		Area.append("\n");
		for(int i=0;i<sizeOfWord;i++) {
	    	Area.append(GuessedWord[i] + " ");
	    }
		Area.append("\nRemaining Guesses: " + guesses);
	}
	
	public void start() {
		Word = getWord();
		Area.append("Here is the word Filled with Dashes \n Number of Guesses:"+ guesses +"\n");
		//Intialise GuessesdWord with Dashes 
		for(int i=0;i<sizeOfWord;i++) {
	    	Area.append("_ ");
	    	GuessedWord[i] = '_';
	    }
		
		Area.append("\nGuess a Letter: ");
		
		if(guesses>0 && sizeOfGuessed < sizeOfWord) {
			getLetter();
		}
		
		frame.setVisible(true);
	}
}


class game {
	public static void main(String[] args) {
		work w = new work();
		w.start();
	}
}