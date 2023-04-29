package wordpuzzle;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Coepoe {
    public static void main(String[] args) throws IOException {
        // Rules
        System.out.println("Coepoe Word Puzzle");
        System.out.println("==================\n");
        System.out.println("Rules:");
        System.out.println("1. Susun sebuah kata dalam bahasa Inggris dengan menggunakan huruf-huruf yang diberikan, " +
                "setiap kata yang disusun harus memiliki banyak karakter minimal 3 karakter dan maksimal 6 karakter.");
        System.out.println("2. Setiap levelnya, user diberi 10 kesempatan untuk menyusun kata dengan benar.");
        System.out.println("3. Setiap jawaban yang benar akan diberikan skor 10.");
        System.out.println("4. Untuk dapat melanjutkan ke level berikutnya, user harus mencapai skor minimal 70 setiap levelnya.");
        System.out.println("5. User tidak dapat menginput kata yang sama.");

        // Get words from dictionary
        // Source: https://github.com/dwyl/english-words/blob/master/words_alpha.txt
        Path filePath = new File("src/wordpuzzle/words_alpha.txt").toPath();
        List<String> dictionary = Files.readAllLines(filePath);
        List<String> eligibleWords = new ArrayList<>();
        for (String word: dictionary) {
            if ((word.length() >= 3) & (word.length() <= 6)) {
                eligibleWords.add(word);
            }
        }

        Scanner input = new Scanner(System.in);

        int levels = 3;
        int finalScore = 0;

        for (int lvl = 1; lvl <= levels; lvl++) {
            System.out.println();
            System.out.printf("Level %d%n", lvl);
            System.out.println("=======");
            System.out.println();

            // Generate random letters
            String[] letters = generateLetters();
            String txtLetters = String.join("  ", letters);
            System.out.println("\t" + txtLetters + "\n");

            // Count occurrence of each letter
            Map<String, Integer> numberOfLetters = new HashMap<String, Integer>();
            for (String letter : letters) {
                if (numberOfLetters.containsKey(letter)) {
                    numberOfLetters.put(letter, numberOfLetters.get(letter) + 1);
                } else {
                    numberOfLetters.put(letter, 1);
                }
            }

            // Get correct answers
            List<String> correctAnswers = new ArrayList<>();
            for (String word : eligibleWords) {
                String pattern = String.join("", letters);
                if (word.matches(String.format("[%s]+", pattern))) {
                    boolean wordIsValid = checkWordValidity(numberOfLetters, word);
                    if (wordIsValid) {
                        correctAnswers.add(word);
                    }
                }
                ;
            }

            // Answer
            List<String> answers = new ArrayList<>();
            String answer = "";
            int trial = 1;
            int rights = 0;
            while (trial <= 10) {
                System.out.printf("%d> Your Answer: ", trial);
                answer = input.nextLine();
                answer = answer.toLowerCase();
                if (answers.contains(answer)) {
                    System.out.println("You had already type this word before..");
                } else {
                    if (correctAnswers.contains(answer)) {
                        rights++;
                        System.out.printf("#Right. Score: %d%n", rights * 10);
                    } else {
                        System.out.println("#Wrong.");
                    }

                    answers.add(answer);
                    trial++;
                }

            }

            // Result
            System.out.printf("%nYou had answered 10 times with %d right answers..%n%n", rights);
            System.out.println("Correct answers:");
            int n = 0;
            for (String rightAnswer : correctAnswers) {
                System.out.printf("%-8s", rightAnswer);
                n++;
                if (n % 10 == 0) {
                    System.out.println();
                }
            }
            System.out.println("\n");

            // Repeat prompt
            if (rights < 7) {
                System.out.println("You Lose!! Try Again..");
                System.out.print("Do you want to retry [y/t]: ");
                boolean confirming = true;
                while (confirming) {
                    String confirmation = input.nextLine();
                    if (confirmation.equalsIgnoreCase("y")) {
                        confirming = false;
                        lvl--;
                    } else if (confirmation.equalsIgnoreCase("t")) {
                        System.exit(0);
                    } else {
                        System.out.print("Please answer [y/t]: ");
                    }
                }
            } else {
                finalScore += rights*10;
            }
        }

        // Final result
        System.out.printf("Overall score : %d%n", finalScore);
        System.out.println("You Win!!");
        System.out.println("Press ENTER to exit..");
        if (input.hasNextLine()) {
            System.exit(0);
        }
    }

    static String[] generateLetters() {
        String[] letters = new String[6]; 
        String[] consonants = {"b", "c", "d", "f", "g", "h", "j", "k", "l", "m", "n", "p", "q", "r", "s", "t", "v", "w", "x", "z"};
        String[] vocals = {"a", "e", "i", "o", "u", "y"};

        // Set n number of vocals between 2 and 4 to ensure valid words are constructable
        Random r = new Random();
        int minVocal = 2;
        int maxVocal = 4;
        int numberOfVocal = r.nextInt(maxVocal-minVocal) + minVocal;
        for (int i = 0; i < numberOfVocal; i++) {
            // Get random vocal
            letters[i] = vocals[r.nextInt(vocals.length)];
        }

        // Get random consonant
        for (int i = numberOfVocal; i < 6; i++) {
            letters[i] = consonants[r.nextInt(consonants.length)];
        }

        // Shuffle letters
        Collections.shuffle(Arrays.asList(letters));

        return  letters;
    }

    static boolean checkWordValidity(Map<String, Integer> numberOfLetters, String word) {
        // Set initial validity to true
        boolean validity = true;

        // Check for each character in word
        Map<String, Integer> letterCounts = new HashMap<String, Integer>(numberOfLetters);
        for (char wordLetter: word.toCharArray()) {

            String key = "" + wordLetter;
            if (letterCounts.containsKey(key)) {
                // If the number of certain character in the word less than given letters, return false
                if (letterCounts.get(key) < 1) {
                    validity =  false;
                    break;
                }
                letterCounts.put(key, letterCounts.get(key) - 1);
            } else {
                // If word contains character not in given letters, return false
                System.out.printf("INVALID %s%n", key);
                validity = false;
                break;
            }
        }
        return validity;
    }
}
