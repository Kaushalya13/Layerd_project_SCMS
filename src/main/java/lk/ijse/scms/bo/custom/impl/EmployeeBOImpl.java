package lk.ijse.scms.bo.custom.impl;

import lk.ijse.scms.bo.custom.EmployeeBO;
import lk.ijse.scms.dao.DAOFactory;
import lk.ijse.scms.dao.custom.EmployeeDAO;
import lk.ijse.scms.dto.EmployeeDTO;
import lk.ijse.scms.dto.UserDTO;
import lk.ijse.scms.entity.Employee;
import lk.ijse.scms.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);

    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> allEmployee= new ArrayList<>();
        ArrayList<Employee> all = employeeDAO.getAll();
        for (Employee employee : all) {
            allEmployee.add(new EmployeeDTO(employee.getEmployee_id(),employee.getEmployee_name(),employee.getNic(),
                    employee.getAddress(),employee.getRanks(),employee.getContactno()));
        }
        return allEmployee;
    }

    @Override
    public boolean addEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(new Employee(dto.getEmployee_id(),dto.getEmployee_name(),dto.getNic(),dto.getAddress(),dto.getRanks(),dto.getContactno()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(dto.getEmployee_id(),dto.getEmployee_name(),dto.getNic(),dto.getAddress(),dto.getRanks(),dto.getContactno()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public EmployeeDTO searchEmployee(String id) throws SQLException, ClassNotFoundException {
        Employee employee = employeeDAO.search(id);
        return new EmployeeDTO(employee.getEmployee_id(),employee.getEmployee_name(),employee.getNic(),
                employee.getAddress(),employee.getRanks(),employee.getContactno());
    }
}
