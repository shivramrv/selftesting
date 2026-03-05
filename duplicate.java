import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        HashSet<String> currentNames = new HashSet<>();
        List<String> result = new ArrayList<>();
        List<String> list = List.of("1 | Llama, Inc.","2 | llama inc","3 | The Llama", "4 | Llama & Friends LLC","5 | LLC");
        for(String compName: list){

            String[] splitComp = compName.split(" \\| ");

            compName = splitComp[1];
            compName = compName.strip();
            compName = compName.toLowerCase();
            compName = compName.replaceAll(" & "," ");
            compName = compName.replaceAll(","," ");
            compName = compName.replaceAll("  "," ");
            compName = compName.replaceAll("inc.","");
            compName = compName.replaceAll("inc","");
            compName = compName.replaceAll("corp.","");
            compName = compName.replaceAll("llc.","");
            compName = compName.replaceAll("llc","");
            compName = compName.replaceAll("l.l.c","");
            compName = compName.replaceAll("llc.","");
            if(compName.startsWith("the")){
                compName=compName.replaceFirst("the","");
            }
            if(compName.startsWith("a")){
                compName=compName.replaceFirst("a","");
            }
            if(compName.startsWith("an")){
                compName=compName.replaceFirst("an","");
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
