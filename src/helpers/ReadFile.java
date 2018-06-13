package helpers;

import java.io.FileReader;
import java.io.IOException;

public class ReadFile {
    private String fileContent = "";

    public String readInputFile() throws IOException {
        FileReader fileReader = new FileReader(Config.PATH);
        int data;
        final int endOfFile = -1;

        while((data = fileReader.read()) != endOfFile){
            char ch = (char)data;
            fileContent = fileContent + ch;
        }
        fileContent = fileContent + "$";
        return fileContent;
    }
}