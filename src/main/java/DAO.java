import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DAO {
    public List<String> processRequestFetch(String queryString){
        Database db = new Database();
        queryString = queryString.toLowerCase();
        try {
            Connection conn = db.getConnection();
            PreparedStatement statement = conn.prepareStatement(queryString);
            ResultSet resultSet = statement.executeQuery();
            List<String> rows = new ArrayList<>();
            ResultSetMetaData data = resultSet.getMetaData();
            int numColumns = data.getColumnCount();
            List<String> types = new ArrayList<>();
            for(int i = 1; i <= numColumns; i++){
                types.add(data.getColumnTypeName(i));
                //System.out.println(data.getColumnTypeName(i));
            }
            while (resultSet.next()){
                List<String> currRow = new ArrayList<>();
                currRow.add("(");
                for(int i = 1; i <= numColumns; i++){
                    if(Objects.equals(types.get(i - 1), "VARCHAR")) {
                        currRow.add(resultSet.getString(i));
                    }else{
                        currRow.add(String.valueOf(resultSet.getInt(i)));
                    }
                    if(i == 1){
                        String combine = String.join("",currRow);
                        currRow.clear();
                        currRow.add(combine);
                    }
                }
                String fullRow = String.join(", ",currRow);
                currRow.clear();
                currRow.add(fullRow);
                currRow.add(")");
                rows.add(String.join("",currRow));
            }
            db.closeConnection(true);
            return rows;
        }catch(Exception e){
            db.closeConnection(false);
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Boolean addRow(String queryString){
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            PreparedStatement statement = conn.prepareStatement(queryString);
            statement.executeUpdate();
            db.closeConnection(true);
            return true;
        }catch(Exception e){
            db.closeConnection(false);
            System.out.println(e.getMessage());
            return false;
        }
    }
}
