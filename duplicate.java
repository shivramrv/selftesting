import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Main {
    public static enum ACTION {
        RECLAIM,
        RECLAIM_REQUEST,
        REGISTER
    }

    public static void main(String[] args) {
        HashMap<String,String> currentNames = new HashMap<>();
        List<String> result = new ArrayList<>();
        List<String> list = List.of("1 | Llama, Inc.","RECLAIM | 1 | Llama1");

        for(String compName: list){

            String[] splitComp = compName.split(" \\| ");
            ACTION action = getAction(splitComp);
            String userId = getUserId(action, splitComp);
            compName = getCompName(action, splitComp);
            compName = normalizeCompanyName(compName);
            boolean nameNotAvailable = currentNames.containsKey(compName) || compName.isBlank();
            if(action == ACTION.RECLAIM_REQUEST){
                if(currentNames.get(compName) == null || !currentNames.get(compName).equals(userId) ){
                    result.add(userId + " | " + "Not Reclaimed");
                }else{
                    result.add(userId + " | " + "Reclaimed");
                }
                continue;
            }
            else if(action == ACTION.RECLAIM){
                if(currentNames.get(compName) == null || !currentNames.get(compName).equals(userId) ){
                    result.add(userId + " | " + "Not Reclaimed");
                }else{
                    currentNames.remove(compName);
                    result.add(userId + " | " + "Reclaimed");
                }
                continue;
            }
            //action: REGISTER
            if(!nameNotAvailable){
                currentNames.put(compName,userId);
            }
            result.add(userId + " | " + (nameNotAvailable ? "Name Not Available": "Name Available"));
        }
        System.out.println(result);
    }

    private static String getCompName(ACTION action, String[] splitComp) {
        String compName;
        compName = (action == ACTION.RECLAIM || action == ACTION.RECLAIM_REQUEST) ? splitComp[2] : splitComp[1];
        return compName;
    }

    private static String getUserId(ACTION action, String[] splitComp) {
        String userId =  action ==ACTION.RECLAIM ? splitComp[1]: splitComp[0];
        return userId;
    }

    private static ACTION getAction(String[] splitComp) {
        ACTION action = ACTION.REGISTER;
        if(splitComp.length > 2 && splitComp[0].equals("RECLAIM")){
            action= ACTION.RECLAIM;
        }
        if(splitComp.length > 2 && splitComp[1].equals("reclaim") ){
            action= ACTION.RECLAIM_REQUEST;
        }
        return action;
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
