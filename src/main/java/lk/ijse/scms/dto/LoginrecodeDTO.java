package lk.ijse.scms.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class LoginrecodeDTO {
    private String login_id;
    private LocalDate date;
    private LocalTime time;
    private String user_id;

    public LoginrecodeDTO() {
    }

    public LoginrecodeDTO(String login_id, LocalDate date, LocalTime time, String user_id) {
        this.login_id = login_id;
        this.date = date;
        this.time = time;
        this.user_id = user_id;
    }

    public String getLogin_id() {
        return login_id;
    }

    public void setLogin_id(String login_id) {
        this.login_id = login_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return "LoginrecodeDTO{" +
                "login_id='" + login_id + '\'' +
                ", date=" + date +
                ", time=" + time +
                ", user_id='" + user_id + '\'' +
                '}';
    }
}
