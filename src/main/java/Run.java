import java.util.List;

public class Run {
    public static void main(String[] args) {
        DAO dao = new DAO();
        setup();

        /*List<String> rows;
        rows = dao.processRequestFetch("SELECT * from \"fish\";");
        System.out.println(rows);
        rows = dao.processRequestFetch("SELECT * from \"price\";");
        System.out.println(rows);
        rows = dao.processRequestFetch("SELECT * from \"quality\";");
        System.out.println(rows);
        rows = dao.processRequestFetch("SELECT * from \"fish\" full outer join price on fish.fish_id=price.fish_id full outer join quality on price.quality_id=quality.quality_id;");
        System.out.println(rows);*/

        ChatGPTAPI gpt = new ChatGPTAPI();

        String prompt = gpt.initalPrompt + "What is pufferfish's id?";
        String response = gpt.query(prompt);
        System.out.println(response);

        String sql = gpt.extractSQL(response);

        System.out.println(sql);
    }

    public static void setup(){
        DAO dao = new DAO();
        dao.processRequestPush("drop table \"fish\";");
        dao.processRequestPush("drop table \"price\";");
        dao.processRequestPush("drop table \"quality\";");
        dao.processRequestPush("drop table \"inventory\";");

        dao.processRequestPush("CREATE TABLE \"fish\" (\n" +
                "\t\"fish_id\"\tINTEGER UNIQUE,\n" +
                "\t\"name\"\tVARCHAR(20) NOT NULL UNIQUE,\n" +
                "\t\"experience\"\tINTEGER DEFAULT 0,\n" +
                "\tPRIMARY KEY(\"fish_id\" AUTOINCREMENT)\n" +
                ");");
        dao.processRequestPush("CREATE TABLE \"price\" (\n" +
                "\t\"fish_id\"\tINTEGER NOT NULL,\n" +
                "\t\"quality_id\"\tINTEGER NOT NULL,\n" +
                "\t\"price\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"quality_id\") REFERENCES \"quality\"(\"quality_id\"),\n" +
                "\tFOREIGN KEY(\"fish_id\") REFERENCES \"fish\"(\"fish_id\"),\n" +
                "\tPRIMARY KEY(\"fish_id\",\"quality_id\")\n" +
                ");");
        dao.processRequestPush("CREATE TABLE \"quality\" (\n" +
                "\t\"quality_id\"\tINTEGER NOT NULL,\n" +
                "\t\"name\"\tVARCHAR(20) NOT NULL,\n" +
                "\tPRIMARY KEY(\"name\")\n" +
                ");");
        dao.processRequestPush("CREATE TABLE \"inventory\" (\n" +
                "\t\"fish_id\"\tINTEGER NOT NULL,\n" +
                "\t\"quality_id\"\tINTEGER NOT NULL,\n" +
                "\t\"count\"\tINTEGER NOT NULL,\n" +
                "\tFOREIGN KEY(\"quality_id\") REFERENCES \"quality\"(\"quality_id\"),\n" +
                "\tFOREIGN KEY(\"fish_id\") REFERENCES \"fish\"(\"fish_id\"),\n" +
                "\tPRIMARY KEY(\"fish_id\",\"quality_id\")\n" +
                ");");

        dao.processRequestPush("insert into \"quality\" (quality_id, name) values (1,\"base\");");
        dao.processRequestPush("insert into \"quality\" (quality_id, name) values (2,\"silver\");");
        dao.processRequestPush("insert into \"quality\" (quality_id, name) values (3,\"gold\");");
        dao.processRequestPush("insert into \"quality\" (quality_id, name) values (4,\"iridium\");");

        dao.processRequestPush("insert into \"fish\" (name, experience) values (\"pufferfish\",29);");
        dao.processRequestPush("insert into \"fish\" (name, experience) values (\"anchovy\",13);");
        dao.processRequestPush("insert into \"fish\" (name, experience) values (\"tuna\",26);");
        dao.processRequestPush("insert into \"fish\" (name, experience) values (\"sardine\",13);");
        dao.processRequestPush("insert into \"fish\" (name, experience) values (\"bream\",14);");

        dao.processRequestPush("insert into \"price\" (fish_id,quality_id,price) values (1,1,200);");
        dao.processRequestPush("insert into \"price\" (fish_id,quality_id,price) values (1,2,250);");
        dao.processRequestPush("insert into \"price\" (fish_id,quality_id,price) values (1,3,300);");
        dao.processRequestPush("insert into \"price\" (fish_id,quality_id,price) values (1,4,400);");

        dao.processRequestPush("insert into \"price\" (fish_id,quality_id,price) values (2,1,30);");
        dao.processRequestPush("insert into \"price\" (fish_id,quality_id,price) values (2,2,37);");
        dao.processRequestPush("insert into \"price\" (fish_id,quality_id,price) values (2,3,45);");
        dao.processRequestPush("insert into \"price\" (fish_id,quality_id,price) values (2,4,60);");

        dao.processRequestPush("insert into \"inventory\" (fish_id,quality_id,count) values (4,4,2);");
    }
}
