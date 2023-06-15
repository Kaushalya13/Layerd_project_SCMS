package lk.ijse.scms.bo.custom.impl;

import lk.ijse.scms.bo.custom.CustomerBO;
import lk.ijse.scms.dao.DAOFactory;
import lk.ijse.scms.dao.custom.CustomerDAO;
import lk.ijse.scms.dto.CustomerDTO;
import lk.ijse.scms.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public ArrayList<CustomerDTO> getAllCustomer() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> allCustomer = new ArrayList<>();
        ArrayList<Customer> all = customerDAO.getAll();
        for (Customer customer : all){
            allCustomer.add(new CustomerDTO(customer.getCustId(),customer.getCustName(),customer.getNic(),
                    customer.getAddress(),customer.getEmail(),customer.getContactno()));
        }
        return allCustomer;
    }

    @Override
    public boolean addCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.add(new Customer(dto.getCustId(),dto.getCustName(),dto.getNic(),
                dto.getAddress(),dto.getEmail(),dto.getContactno()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(dto.getCustId(),dto.getCustName(),
                dto.getNic(),dto.getAddress(),dto.getEmail(),dto.getContactno()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = customerDAO.search(id);
        return new CustomerDTO(customer.getCustId(),customer.getCustName(),customer.getNic(),
                customer.getAddress(),customer.getEmail(),customer.getContactno());
    }
}