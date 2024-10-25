package task;
import java.io.*;

public class TaskFileStatistics extends TaskImpl {

    @Override
    public void execute() {
        try {
            // input must be like this "TaskFileStatistics&path/to/input.txt""
            String[] params = input.split("&");
            if (params.length != 2) {
                result = "Invalid input. Please provide task name and file path.";
                return;
            }

            String filePath = params[1];


            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            int lineCount = 0;
            int wordCount = 0;
            int charCount = 0;
            String line;

            // caculate somw statistices
            while ((line = reader.readLine()) != null) {
                lineCount++;
                wordCount += line.split("\\s+").length;
                charCount += line.length();
            }

            reader.close();

            System.out.println("Lines: " + lineCount);
            System.out.println("Words: " + wordCount);
            System.out.println("Characters: " + charCount);

            BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
            writer.write("Lines: " + lineCount + "\n");
            writer.write("Words: " + wordCount + "\n");
            writer.write("Characters: " + charCount + "\n");

            writer.close();

            result = "File statistics written to output.txt";

        } catch (IOException e) {
            result = "Error: " + e.getMessage();
        }
    }
}
