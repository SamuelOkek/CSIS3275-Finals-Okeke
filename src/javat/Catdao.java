package javat;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Component
@Service
public class Catdao {

    JdbcTemplate template;

    public Catdao(JdbcTemplate template) {
        this.template = template;
    }

    public void setTemplate(JdbcTemplate template) {
    }

    //This displays the data in the database.
    public List<SalesTable> display() throws ClassNotFoundException, SQLException {
        return template.query("select * from sales_table", (RowMapper) (rs, row) -> {
            SalesTable c = new SalesTable();
            c.setRecno(rs.getInt("recno"));
            c.setIcode(rs.getString("icode"));
            c.setQty(rs.getDouble("qty"));
            c.setDot(rs.getString("dot"));
            c.setId(rs.getString("id"));
            return c;
        });
    }

    public int insertData(final SalesTable salesTable) {
        return template.update("insert into sales_table values(?,?,?,?,?)",
                salesTable.getRecno(), salesTable.getIcode(), salesTable.getQty(), salesTable.getDot(), salesTable.getId());
    }

    public int deleteData(String recno) {
        return template.update("delete from sales_table where recno = ?", recno);
    }

    public int editData(final SalesTable salesTable, String recno) {
        return template.update("update sales_table set recno=?, icode=?, qty=?, dot=?, id=? where recno=?",
                salesTable.getRecno(), salesTable.getIcode(), salesTable.getQty(), salesTable.getDot(), salesTable.getId(), recno);
    }

    public List<Map<String, Object>> getSalesRecord(String recno) {
        return template.queryForList("SELECT * from sales_table where recno = ?", recno);
    }

    public List<Map<String, Object>> getcat(String recno) {

        return template.queryForList("SELECT * from savingstable where Custno = ?", recno);
    }

    public List<Map<String, Object>> getitem(int recno) {

        return template.queryForList("SELECT * from items where UserID = ?", recno);

    }

    // Add other methods as needed
}
