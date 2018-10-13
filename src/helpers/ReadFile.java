package helpers;

import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    private String fileContent = "";

    public String readInputFile() throws IOException {
        FileReader fileReader = new FileReader(Config.PATH);
        int data;
        while((data = fileReader.read()) != Config.END_OF_FILE){
            char expression = (char)data;
            fileContent = fileContent + expression;
        }
        fileContent = fileContent + Config.END_TOKEN;
        return fileContent;
    }
}