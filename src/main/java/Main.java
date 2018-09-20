import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class Main {
    public static void main(String[] args) {

        boolean isCssStarted=false;
        File importedFile = new File("/home/huber/NetBeansProjects/shape-testing/src/test/resources/application-test.yml");
        List<IndexedField> fieldsList = new ArrayList<IndexedField>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(importedFile));
            String line = "";
            String csvSplitBy = ",";
            int i=0;
            while ((line = br.readLine()) != null) {
                if (line.contains("mappings:")) {
                    isCssStarted = true;
                }

                String[] names = line.split(":");
                if (isCssStarted) {

                    IndexedField newField = new IndexedField();
                    newField.setIndexNumber(0);
                    newField.setNameOfField(names[0]);

                    fieldsList.add(newField);
                    while (names[0].startsWith("    ")) {
                        names[0] = names[0].substring(4, names[0].length());
                        fieldsList.get(i).setIndexNumber(fieldsList.get(i).getIndexNumber()+1);
                        fieldsList.get(i).setNameOfField(names[0]);
                    }
                    if (names[0].replaceAll(" ", "").equals("")){
                        fieldsList.remove(i);
                    } else  {
                        i++;
                    }
                }
            }
            } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        List<String> levels = new ArrayList<>();
        StringBuffer temporaryPath = new StringBuffer();
        for (int i = 0; i < fieldsList.size(); i++) {
            if (i!=0 && fieldsList.get(i).getIndexNumber()<fieldsList.get(i-1).getIndexNumber()) {
                for (int j = 0; j < fieldsList.get(i - 1).getIndexNumber() - fieldsList.get(i).getIndexNumber(); j++) {
                    levels.remove(levels.size() - 1);
                    temporaryPath = new StringBuffer();
                    for (int i1 = 0; i1 < levels.size(); i1++) {
                        temporaryPath.append(".").append(levels.get(i1));
                    }
                    //System.out.println(" ");
                }
            }
            if (i!=fieldsList.size()-1 && fieldsList.get(i+1).getIndexNumber()>fieldsList.get(i).getIndexNumber()){
                levels.add(fieldsList.get(i).getNameOfField());
                temporaryPath.append(".").append(levels.get(levels.size()-1));
            } else {
                System.out.println("");
                StringBuffer firstLineToPrint = new StringBuffer();
                if (fieldsList.get(i).getNameOfField().startsWith("inside-css")){
                    firstLineToPrint.append("@Value(\"${").append(temporaryPath.substring(1));
                } else {
                    firstLineToPrint.append("@FindBy(css = \"${").append(temporaryPath.substring(1));
                }
                firstLineToPrint.append(".").append(fieldsList.get(i).getNameOfField()).append("}\")");
                System.out.println(firstLineToPrint.toString());

                StringBuffer secondLineToPrint = new StringBuffer();
                secondLineToPrint.append("private ");
                if (fieldsList.get(i).getNameOfField().startsWith("inside-css")){
                    secondLineToPrint.append("String ");
                } else {
                    if (fieldsList.get(i).getNameOfField().endsWith("list")) {
                        secondLineToPrint.append("List<WebElement> ");
                    } else {
                        secondLineToPrint.append("WebElement ");
                    }
                }
                String fullPath = (temporaryPath.toString() + "."+ fieldsList.get(i).getNameOfField()).substring(1);
                String[] variableNameArray = fullPath.split("\\.");
                StringBuffer variableName = new StringBuffer();
                for (int k=2; k<variableNameArray.length; k++){
                    variableName.append(variableNameArray[k]);
                    if (k!=variableNameArray.length-1){
                        variableName.append("-");
                    }
                }
                variableNameArray = variableName.toString().split("-");
                variableName = new StringBuffer();
                for (int k=0; k<variableNameArray.length; k++){
                    if (k!=0) {
                        variableName.append(variableNameArray[k].substring(0, 1).toUpperCase());
                        variableName.append(variableNameArray[k].substring(1));
                    } else {
                        variableName.append(variableNameArray[k]);
                    }
                }
                secondLineToPrint.append(variableName).append(";");
                System.out.println(secondLineToPrint.toString());
            }
        }
    }
}
