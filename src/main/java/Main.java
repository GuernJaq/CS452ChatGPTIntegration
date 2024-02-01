// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        ChatGPTAPI gpt = new ChatGPTAPI();

        String prompt = "Write me a SQLite prompt to create a table of wizards.";
        String response = gpt.query(prompt);
        System.out.println(response);

        String sql = gpt.extractSQL(response);

        //main function

        //populate gpt with data

        //ask gpt to create a sql query

        //query the database

        //give gpt the data back and ask it to say it nicely



    }


}