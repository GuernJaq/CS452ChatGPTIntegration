import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;

public class ChatGPTAPI {
    public String initalPrompt = "CREATE TABLE \"fish\" (\n" +
            "\t\"fish_id\"\tINTEGER UNIQUE,\n" +
            "\t\"name\"\tVARCHAR(20) NOT NULL UNIQUE,\n" +
            "\t\"experience\"\tINTEGER DEFAULT 0,\n" +
            "\tPRIMARY KEY(\"fish_id\" AUTOINCREMENT)\n" +
            ");" +
            "CREATE TABLE \"inventory\" (\n" +
            "\t\"fish_id\"\tINTEGER NOT NULL,\n" +
            "\t\"quality_id\"\tINTEGER NOT NULL,\n" +
            "\t\"count\"\tINTEGER NOT NULL,\n" +
            "\tFOREIGN KEY(\"quality_id\") REFERENCES \"quality\"(\"quality_id\"),\n" +
            "\tFOREIGN KEY(\"fish_id\") REFERENCES \"fish\"(\"fish_id\"),\n" +
            "\tPRIMARY KEY(\"fish_id\",\"quality_id\")\n" +
            ");" +
            "CREATE TABLE \"price\" (\n" +
            "\t\"fish_id\"\tINTEGER NOT NULL,\n" +
            "\t\"quality_id\"\tINTEGER NOT NULL,\n" +
            "\t\"price\"\tINTEGER,\n" +
            "\tFOREIGN KEY(\"quality_id\") REFERENCES \"quality\"(\"quality_id\"),\n" +
            "\tFOREIGN KEY(\"fish_id\") REFERENCES \"fish\"(\"fish_id\"),\n" +
            "\tPRIMARY KEY(\"fish_id\",\"quality_id\")\n" +
            ")CREATE TABLE \"quality\" (\n" +
            "\t\"quality_id\"\tINTEGER NOT NULL,\n" +
            "\t\"name\"\tVARCHAR(20) NOT NULL,\n" +
            "\tPRIMARY KEY(\"name\")\n" +
            ");" +
            "CREATE TABLE \"quality\" (\n" +
            "\t\"quality_id\"\tINTEGER NOT NULL,\n" +
            "\t\"name\"\tVARCHAR(20) NOT NULL,\n" +
            "\tPRIMARY KEY(\"name\")\n" +
            ");" +
            "Write me a SLQLITE prompt that answers the following question:";
    public String query(String prompt){
        String url = "https://api.openai.com/v1/chat/completions";
        String apiKey = "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";
        String model = "gpt-3.5-turbo";

        try{
            System.out.println("building url");
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + apiKey);
            connection.setRequestProperty("Content-Type", "application/json");

            // The request body
            System.out.println("Building request body");
            String body = "{\"model\": \"" + model + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + prompt + "\"}]}";
            connection.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(body);
            writer.flush();
            writer.close();

            // Response from ChatGPT
            System.out.println("Getting response");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            StringBuffer response = new StringBuffer();

            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            br.close();

            // calls the method to extract the message.
            System.out.println("Extract response");
            return extractMessageFromJSONResponse(response.toString());
        } catch(Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String extractMessageFromJSONResponse(String response) {
        int start = response.indexOf("content")+ 11;

        int end = response.indexOf("\"", start);

        System.out.println("Return response");
        return response.substring(start, end);

    }

    public String extractSQL(String gptResponse){
        int start = gptResponse.indexOf("```sql\n");
        String sql = gptResponse.substring(start + 7);
        System.out.println(sql);

        int end = sql.indexOf("```");
        sql = sql.substring(0, end);

        sql = sql.replace('\n', ' ');

        return sql;
    }
}


