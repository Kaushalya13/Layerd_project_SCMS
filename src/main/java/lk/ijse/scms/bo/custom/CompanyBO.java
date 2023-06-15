package lk.ijse.scms.bo.custom;

import lk.ijse.scms.bo.SuperBO;
import lk.ijse.scms.dto.CompanyDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CompanyBO extends SuperBO {
    public ArrayList<CompanyDTO> getAllCompany() throws SQLException, ClassNotFoundException;

    public boolean addCompany(CompanyDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean updateCompany(CompanyDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean deleteCompany(String id) throws SQLException, ClassNotFoundException;

    public CompanyDTO searchCompany(String id) throws SQLException, ClassNotFoundException;
}
