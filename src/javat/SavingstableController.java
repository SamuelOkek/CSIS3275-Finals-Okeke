package javat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@SessionAttributes({"id", "errorMessage", "recordsList"})
@RequestMapping
@Controller
public class SavingstableController {

    @Autowired
    Catdao dao;


    //This is used to display the home page
    @GetMapping(path = "/savingstable")
    public String showCategoryPage(ModelMap model) throws ClassNotFoundException, SQLException {

        List<SalesTable> list = dao.display();
        model.addAttribute("categorylist", list);

        return "savingstable";
    }

    //This is used to display the home page
    @GetMapping(path = "/")
    public String showCategoryPage2(ModelMap model) throws ClassNotFoundException, SQLException {

        List<SalesTable> list = dao.display();
        model.addAttribute("categorylist", list);

        return "savingstable";
    }


    @GetMapping(path = "/add-todo")
    public String showTodoPage() {
        return "addtable";
    }

    //This is used to add data to the database
    @PostMapping(path = "/add-todo")
    public String addTodo(ModelMap model, @RequestParam int recno, @RequestParam String icode, @RequestParam double qty, @RequestParam String dot, @RequestParam String id) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> x = dao.getcat(String.valueOf(recno));

        if (!x.isEmpty()) {
            model.put("errorMessage", "The record you are trying to add already exists. Choose a different receipt number.");
            return "redirect:/savingstable";
        }

        SalesTable salesTable = new SalesTable();
        salesTable.setRecno(recno);
        salesTable.setIcode(icode);
        salesTable.setQty(qty);
        // Parse String to Date, adjust the logic based on your date format
        // Example: salesTable.setDot(LocalDate.parse(dot, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        salesTable.setDot(dot);
        salesTable.setId(id);

        dao.insertData(salesTable);

        return "redirect:/savingstable";
    }


    @GetMapping(path = "/update-todo")
    public String showUpdateTodoPage(ModelMap model, @RequestParam(defaultValue = "") String id) throws SQLException, ClassNotFoundException {

        model.put("id", id);
        List<Map<String, Object>> x = dao.getSalesRecord(id);

        if (!x.isEmpty()) {
            model.addAttribute("salesRecord", x.get(0));
        }

        return "edit";
    }

    /*@PostMapping(path = "/update-todo")
    public String showUpdate(ModelMap model, @RequestParam int recno, @RequestParam String icode, @RequestParam double qty, @RequestParam String dot, @RequestParam String id) throws SQLException, ClassNotFoundException {
        SalesTable salesTable = new SalesTable();
        salesTable.setRecno(recno);
        salesTable.setIcode(icode);
        salesTable.setQty(qty);
        // Parse String to Date, adjust the logic based on your date format
        // Example: salesTable.setDot(LocalDate.parse(dot, DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        salesTable.setDot(dot);
        salesTable.setId(id);

        dao.editData(salesTable, recno);

        return "redirect:/";
    }*/

    //This is used to delete data
    @GetMapping(path = "/delete-todo")
    public String deleteTodo(ModelMap model, @RequestParam String recno) throws SQLException, ClassNotFoundException {
        dao.deleteData(recno);
        return "redirect:/";
    }

    @GetMapping(path = "/see-todo")
    public String seeTodo(ModelMap model, @RequestParam(defaultValue = "") int recno) throws SQLException, ClassNotFoundException {
        List<Map<String, Object>> x = dao.getitem(recno);

        if (x.isEmpty()) {
            model.put("errorMessage", "No items in this record");
            return "redirect:/category";
        }

        model.addAttribute("itemlist", x);
        return "items";
    }

    @PostMapping(path = "/see-todo")
    public String seeTodo2(ModelMap model) throws SQLException, ClassNotFoundException {
        return "redirect:/";
    }
}
