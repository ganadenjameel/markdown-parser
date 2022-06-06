import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) throws IOException {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int openBracket = markdown.indexOf("[", currentIndex);
            int closeBracket = markdown.indexOf("]", openBracket);
            int openParen = markdown.indexOf("(", closeBracket);
            int closeParen = markdown.indexOf(")", openParen);
            if (openBracket > 0) {
                if (markdown.charAt(openBracket-1) == '!')
                {
                    currentIndex = closeParen + 1;
                    continue;
                }
            }
            if (openBracket == -1) {
                throw new IllegalArgumentException("Invalid Input!");
            }
            if (openParen == -1) {
                throw new IllegalArgumentException("Invalid Input!");
            }
            if (closeBracket < openBracket) {
                throw new IllegalArgumentException("Invalid Input!");
            }
            toReturn.add(markdown.substring(openParen + 1, closeParen));
            currentIndex = closeParen + 1;  
        }
        if (markdown.length() == 0) {
            throw new IOException("Empty File!");
        }
        //System.out.println("THIS IS CURRENT INDEX: " + currentIndex);
        return toReturn;
    }


    public static void main(String[] args) throws IOException {
        Path fileName = Path.of(args[0]);
        String content = Files.readString(fileName);
        ArrayList<String> links = getLinks(content);
	    System.out.println(links);
    }
}
