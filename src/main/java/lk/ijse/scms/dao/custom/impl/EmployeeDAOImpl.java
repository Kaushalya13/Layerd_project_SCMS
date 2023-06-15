package lk.ijse.scms.dao.custom.impl;

import lk.ijse.scms.dao.custom.EmployeeDAO;
import lk.ijse.scms.entity.Employee;
import lk.ijse.scms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> allEmployee = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employee");
        while (rst.next()){
            Employee employee = new Employee(rst.getString("employee_id"), rst.getString("employee_name"), rst.getString("nic"),
                    rst.getString("address"), rst.getString("ranks"), rst.getString("contact_no"));
            allEmployee.add(employee);
        }
        return allEmployee;
    }

    @Override
    public boolean add(Employee dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Employee (employee_id,employee_name,nic,address,ranks,contact_no) VALUES (?,?,?,?,?,?)",
                dto.getEmployee_id(),dto.getEmployee_name(),dto.getNic(),dto.getAddress(),dto.getRanks(),dto.getContactno());
    }

    @Override
    public boolean update(Employee dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Employee SET employee_name=?, nic=?, address=?, ranks=?, contact_no=? WHERE employee_id=?",
                dto.getEmployee_name(),dto.getNic(),dto.getAddress(),dto.getRanks(),dto.getContactno(),dto.getEmployee_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Employee WHERE employee_id=?", id);
    }

    @Override
    public Employee search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employee WHERE employee_id=?", id + "");
        rst.next();
        return new Employee(id + "", rst.getString("employee_name"), rst.getString("nic"),rst.getString("address"),
                rst.getString("ranks"),rst.getString("contact_no"));

    }
}
