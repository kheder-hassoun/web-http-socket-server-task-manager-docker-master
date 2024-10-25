package task;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class TaskWriteOnFile extends TaskImpl {

    @Override
    public void execute() {
        String inputText = input;

        try {
            writ(inputText);
            result = "The data has been written to out.txt file.";
        } catch (IOException e) {

            result = "An error occurred while writing to the file: " + e.getMessage();
        }
    }

    private void writ(String inputText) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"))) {
            writer.write("input is : " + inputText + "\n");
        }
    }
}
