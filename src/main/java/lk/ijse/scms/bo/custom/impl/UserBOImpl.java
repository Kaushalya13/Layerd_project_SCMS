package lk.ijse.scms.bo.custom.impl;

import lk.ijse.scms.bo.custom.UserBO;
import lk.ijse.scms.dao.DAOFactory;
import lk.ijse.scms.dao.custom.UserDAO;
import lk.ijse.scms.dto.UserDTO;
import lk.ijse.scms.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public ArrayList<UserDTO> getAllUser() throws SQLException, ClassNotFoundException {
        ArrayList<UserDTO> allUser= new ArrayList<>();
        ArrayList<User> all = userDAO.getAll();
        for (User u : all) {
            allUser.add(new UserDTO(u.getUser_id(),u.getUser_name(),u.getPassword(),u.getRanks(),
                    u.getEmail(),u.getNic(),u.getContactno()));
        }
        return allUser;
    }

    @Override
    public boolean addUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.add(new User(dto.getUser_id(),dto.getUser_name(),dto.getPassword(),
                dto.getRanks(),dto.getEmail(),dto.getNic(),dto.getContactno()));
    }

    @Override
    public boolean updateUser(UserDTO dto) throws SQLException, ClassNotFoundException {
        return userDAO.update(new User(dto.getUser_id(),dto.getUser_name(),dto.getPassword(),
                dto.getRanks(),dto.getEmail(),dto.getNic(),dto.getContactno()));
    }

    @Override
    public boolean deleteUser(String id) throws SQLException, ClassNotFoundException {
        return userDAO.delete(id);
    }

    @Override
    public UserDTO searchUser(String id) throws SQLException, ClassNotFoundException {
        User user = userDAO.search(id);
        return new UserDTO(user.getUser_id(),user.getUser_name(),user.getPassword(),user.getRanks()
                ,user.getEmail(),user.getNic(),user.getContactno());
    }
}
