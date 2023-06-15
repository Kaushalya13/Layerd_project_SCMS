package lk.ijse.scms.dao.custom.impl;

import lk.ijse.scms.dao.custom.UserDAO;
import lk.ijse.scms.entity.User;
import lk.ijse.scms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {

    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ArrayList<User> allUser = new ArrayList<>();
        ResultSet rst = CrudUtil.execute("SELECT * FROM User");
        while (rst.next()) {
            User user = new User(rst.getString("user_id"), rst.getString("user_name"), rst.getString("password"),
                    rst.getString("ranks"),rst.getString("email"),rst.getString("nic"),rst.getString("contact_no"));
            allUser.add(user);
        }
        return allUser;
    }

    @Override
    public boolean add(User dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("INSERT INTO User (user_id,user_name,password,ranks,email,nic,contact_no) VALUES (?,?,?,?,?,?,?)",
                dto.getUser_id(),dto.getUser_name(),dto.getPassword(),dto.getRanks(),dto.getEmail(),dto.getNic(),dto.getContactno());
    }

    @Override
    public boolean update(User dto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("UPDATE User SET user_name=?, password=?, ranks=?, email=?, nic=?, contact_no=? WHERE user_id=?",
                dto.getUser_name(),dto.getPassword(),dto.getRanks(),dto.getEmail(),dto.getNic(),dto.getContactno(),dto.getUser_id());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("DELETE FROM User WHERE user_id=?", id);
    }

    @Override
    public User search(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM User WHERE user_id=?", id + "");
        rst.next();
        return new User(id + "", rst.getString("user_name"), rst.getString("password"),rst.getString("ranks"),
                rst.getString("email"),rst.getString("nic"),rst.getString("contact_no"));
    }
}
