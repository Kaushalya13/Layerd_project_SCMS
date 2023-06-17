package lk.ijse.scms.bo.custom.impl;

import lk.ijse.scms.bo.custom.CompanyBO;
import lk.ijse.scms.dao.DAOFactory;
import lk.ijse.scms.dao.custom.CompanyDAO;
import lk.ijse.scms.dto.CompanyDTO;
import lk.ijse.scms.entity.Company;

import java.sql.SQLException;
import java.util.ArrayList;

public class CompanyBOImpl implements CompanyBO {

    CompanyDAO companyDAO = (CompanyDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.COMPANY);

    @Override
    public ArrayList<CompanyDTO> getAllCompany() throws SQLException, ClassNotFoundException {
        ArrayList<CompanyDTO> allCompany = new ArrayList<>();
        ArrayList<Company> all = companyDAO.getAll();
        for (Company company : all){
            allCompany.add(new CompanyDTO(company.getCompany_id(),company.getCompany_name(),company.getCompany_type()));
        }
        return allCompany;
    }

    @Override
    public boolean addCompany(CompanyDTO dto) throws SQLException, ClassNotFoundException {
        return companyDAO.add(new Company(dto.getCompany_id(),dto.getCompany_name(),dto.getCompany_type()));
    }

    @Override
    public boolean updateCompany(CompanyDTO dto) throws SQLException, ClassNotFoundException {
        return companyDAO.update(new Company(dto.getCompany_id(),dto.getCompany_name(),dto.getCompany_type()));
    }

    @Override
    public boolean deleteCompany(String id) throws SQLException, ClassNotFoundException {
        return companyDAO.delete(id);
    }

    @Override
    public CompanyDTO searchCompany(String id) throws SQLException, ClassNotFoundException {
        Company company = companyDAO.search(id);
        return new CompanyDTO(company.getCompany_id(),company.getCompany_name(),company.getCompany_type());
    }
}
