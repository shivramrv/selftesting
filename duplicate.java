import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HashMap<String,String> currentNames = new HashMap<>();
        List<String> result = new ArrayList<>();
        List<String> list = List.of("1 | Llama, Inc.","2 | llama inc","3 | The Llama", "1 | reclaim | Llama LLC","5 | LLC","6 | Lincoln INC");
        for(String compName: list){

            String[] splitComp = compName.split(" \\| ");
            boolean isReClaimRequest = false;
            if(splitComp.length > 2 && splitComp[1].equals("reclaim") ){
                isReClaimRequest = true;
            }
            String compId = splitComp[0];
            compName = isReClaimRequest? splitComp[2] : splitComp[1];
            compName = normalizeCompanyName(compName);
            boolean nameNotAvailable = currentNames.containsKey(compName) || compName.isBlank();
            if(isReClaimRequest){
                if(!currentNames.get(compName).equals(compId) ){
                    //name not found in system
                    result.add(compId + " | " + "Not Reclaimed");
                }else{
                    result.add(compId + " | " + "Reclaimed");
                }
                continue;
            }
            if(!nameNotAvailable){
                currentNames.put(compName,compId);
            }
            result.add(compId + " | " + (nameNotAvailable ? "Name Not Available": "Name Available"));
        }
        System.out.println(result);
    }

    private static String normalizeCompanyName(String compName) {
        compName = compName.strip();
        compName = compName.toLowerCase();
        compName = compName.replaceAll("&"," ");
        compName = compName.replaceAll(","," ");
        List<String> splitWords = new ArrayList<>(List.of(compName.split("\\s+")));
        List<String> wantedWords = new ArrayList<>();
        //skip unwanted words
        for(String word :splitWords){
            if(word.equals("inc") || word.equals("inc.")  || word.equals("llc") || word.equals("llc.")
                    || word.equals("corp.") || word.equals("l.l.c") || word.equals("llc.")){
                continue;
            }
            wantedWords.add(word);
        }
        compName = String.join(" ",wantedWords);
        compName = compName.replaceAll("\\s+"," ");
        if(compName.startsWith("the ")){
            compName = compName.replaceFirst("the","");
        }
        if(compName.startsWith("an ")){
            compName = compName.replaceFirst("an","");
        }
        else if(compName.startsWith("a ")){
            compName = compName.replaceFirst("a","");
        }
        //and replacement inside string
        if(compName.length() > 3 && compName.indexOf("and",3)!=-1){
            compName = compName.substring(0,2)+ compName.substring(2, compName.length()).replaceAll("and","");
        }
        compName = compName.strip();
        return compName;
    }
}
