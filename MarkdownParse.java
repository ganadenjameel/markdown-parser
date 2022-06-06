import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class MarkdownParse {

    public static ArrayList<String> getLinks(String markdown) throws IOException {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then read link upto next )
        int currentIndex = 0;
        boolean condition = true;
        while(currentIndex < markdown.length() && condition) {
            int openBracket = markdown.indexOf("[", currentIndex);
            int closeBracket = markdown.indexOf("]", openBracket);
            int openParen = markdown.indexOf("(", closeBracket);
            int closeParen = markdown.indexOf(")", openParen);
            if(openBracket != -1 && closeBracket != -1 && openParen != -1 && closeParen != -1) {
                try {
                    if(!(markdown.substring(openBracket - 1, openBracket).equals("!")) && !(markdown.substring(openBracket - 1, openBracket).equals("\\")) && markdown.substring(closeBracket + 1, closeBracket + 2).equals("[")) {      
                        toReturn.add(markdown.substring(openParen + 1, closeParen));
                    }
                } catch(Exception e) {     
                    toReturn.add(markdown.substring(openParen + 1, closeParen));
                }
            }
            currentIndex = closeParen + 1;
            if(markdown.indexOf("[", closeParen) == -1 || markdown.indexOf("]", closeParen) == -1 || markdown.indexOf("(", closeParen) == -1 || markdown.indexOf(")", closeParen) == -1) {
                condition = false;
            }
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
