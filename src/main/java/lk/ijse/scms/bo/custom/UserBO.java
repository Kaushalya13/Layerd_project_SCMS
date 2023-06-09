package lk.ijse.scms.bo.custom;

import lk.ijse.scms.bo.SuperBO;
import lk.ijse.scms.dto.UserDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBO {
    public ArrayList<UserDTO> getAllUser() throws SQLException, ClassNotFoundException;

    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException ;

    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException;

    public UserDTO searchUser(String id) throws SQLException, ClassNotFoundException;
}
