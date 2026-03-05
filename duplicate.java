import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HashSet<String> currentNames = new HashSet<>();
        List<String> result = new ArrayList<>();
        List<String> list = List.of("1 | Llama, Inc.","2 | llama inc","3 | The Llama", "4 | Llama & Friends LLC","5 | LLC","6 | Lincoln INC");
        for(String compName: list){

            String[] splitComp = compName.split(" \\| ");

            compName = splitComp[1];
            compName = compName.strip();
            compName = compName.toLowerCase();
            List<String> splitWords = new ArrayList<>(List.of(compName.split("\\s+")));
            List<String> wantedWords = new ArrayList<>();
            //skip unwanted words
            for(String word :splitWords){
                if(word.equals("inc") || word.equals("inc.") || word.equals("inc") || word.equals("llc") || word.equals("llc.")
                        || word.equals("corp.") || word.equals("l.l.c") || word.equals("llc.")){
                    continue;
                }
                wantedWords.add(word);
            }
            compName = String.join(" ",wantedWords);
            compName = compName.replaceAll("&"," ");
            compName = compName.replaceAll(","," ");
            compName = compName.replaceAll("\\s+"," ");
            if(compName.startsWith("the")){
                compName=compName.replaceFirst("the","");
            }
            if(compName.startsWith("an")){
                compName=compName.replaceFirst("an","");
            }
            else if(compName.startsWith("a")){
                compName=compName.replaceFirst("a","");
            }
            //and replacement inside string
            if(compName.length() > 3 && compName.indexOf("and",3)!=-1){
                compName= compName.substring(0,2)+ compName.substring(2, compName.length()).replaceAll("and","");
            }
            compName = compName.strip();
            boolean nameNotAvailable = currentNames.contains(compName) || compName.isBlank();
            if(!nameNotAvailable){
                currentNames.add(compName);
            }
            System.out.println(compName);
            result.add(splitComp[0] + " | " + (nameNotAvailable ? "Name Not Available": "Name Available"));
        }
        System.out.println(result);
    }
}
