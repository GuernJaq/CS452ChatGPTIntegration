import java.util.ArrayList;
import java.util.List;

public class Run {
    public static void main(String[] args) {
        DAO dao = new DAO();
        setup();

        List<String> rows;
        rows = dao.processRequestFetch("SELECT * from \"fish\";");
        System.out.println(rows);
        rows = dao.processRequestFetch("SELECT * from \"price\";");
        System.out.println(rows);
        rows = dao.processRequestFetch("SELECT * from \"quality\";");
        System.out.println(rows);

        //TODO: ADD CHATGPT INTEGRATION
    }

    public static void setup(){
        DAO dao = new DAO();
        dao.addRow("drop table \"fish\";");
        dao.addRow("drop table \"price\";");
        dao.addRow("drop table \"quality\";");

        dao.addRow("CREATE TABLE \"fish\" (\n" +
                "\t\"fish_id\"\tINTEGER UNIQUE,\n" +
                "\t\"name\"\tVARCHAR(20) NOT NULL UNIQUE,\n" +
                "\t\"experience\"\tINTEGER DEFAULT 0,\n" +
                "\tPRIMARY KEY(\"fish_id\" AUTOINCREMENT)\n" +
                ");");
        dao.addRow("CREATE TABLE \"price\" (\n" +
                "\t\"fish_id\"\tINTEGER NOT NULL,\n" +
                "\t\"quality_id\"\tINTEGER NOT NULL,\n" +
                "\t\"price\"\tINTEGER,\n" +
                "\tFOREIGN KEY(\"quality_id\") REFERENCES \"quality\"(\"quality_id\"),\n" +
                "\tFOREIGN KEY(\"fish_id\") REFERENCES \"fish\"(\"fish_id\"),\n" +
                "\tPRIMARY KEY(\"fish_id\",\"quality_id\")\n" +
                ");");
        dao.addRow("CREATE TABLE \"quality\" (\n" +
                "\t\"quality_id\"\tINTEGER NOT NULL,\n" +
                "\t\"name\"\tVARCHAR(20) NOT NULL,\n" +
                "\tPRIMARY KEY(\"name\")\n" +
                ");");

        dao.addRow("insert into \"quality\" (quality_id, name) values (1,\"base\");");
        dao.addRow("insert into \"quality\" (quality_id, name) values (2,\"silver\");");
        dao.addRow("insert into \"quality\" (quality_id, name) values (3,\"gold\");");
        dao.addRow("insert into \"quality\" (quality_id, name) values (4,\"iridium\");");

        dao.addRow("insert into \"fish\" (name, experience) values (\"pufferfish\",29);");
        dao.addRow("insert into \"fish\" (name, experience) values (\"anchovy\",13);");
        dao.addRow("insert into \"fish\" (name, experience) values (\"tuna\",26);");
        dao.addRow("insert into \"fish\" (name, experience) values (\"sardine\",13);");
        dao.addRow("insert into \"fish\" (name, experience) values (\"bream\",14);");

        dao.addRow("insert into \"price\" (fish_id,quality_id,price) values (1,1,200);");
        dao.addRow("insert into \"price\" (fish_id,quality_id,price) values (1,2,250);");
        dao.addRow("insert into \"price\" (fish_id,quality_id,price) values (1,3,300);");
        dao.addRow("insert into \"price\" (fish_id,quality_id,price) values (1,4,400);");

        dao.addRow("insert into \"price\" (fish_id,quality_id,price) values (2,1,30);");
        dao.addRow("insert into \"price\" (fish_id,quality_id,price) values (2,2,37);");
        dao.addRow("insert into \"price\" (fish_id,quality_id,price) values (2,3,45);");
        dao.addRow("insert into \"price\" (fish_id,quality_id,price) values (2,4,60);");
    }
}
