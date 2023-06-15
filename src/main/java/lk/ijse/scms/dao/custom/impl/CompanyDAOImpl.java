package lk.ijse.scms.dao.custom.impl;

import lk.ijse.scms.dao.custom.CompanyDAO;
import lk.ijse.scms.entity.Company;
import lk.ijse.scms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyDAOImpl implements CompanyDAO {
    @Override
    public ArrayList<Company> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<Company> allCompany = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM Company");
        while (rst.next()){
            Company company = new Company(rst.getString("company_id"), rst.getString("company_name"), rst.getString("company_type"));
            allCompany.add(company);
        }
        return allCompany;
    }

    @Override
    public boolean add(Company dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO Company (company_id,company_name,company_type) VALUES (?,?,?)",
                dto.getCompany_id(),dto.getCompany_name(),dto.getCompany_type());
    }

    @Override
    public boolean update(Company dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE Company SET company_name=?,company_type=? WHERE company_id=?",
                dto.getCompany_name(),dto.getCompany_type(),dto.getCompany_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM Company WHERE company_id=?",id);
    }

    @Override
    public Company search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM Company WHERE company_id=?",id +"");
        rst.next();
        return new Company(id + "",rst.getString("company_name"),rst.getString("company_type"));
    }
}
