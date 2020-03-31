package by.epam.service;

import by.epam.dao.AdminDao;
import by.epam.entities.Admin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AdminService {

    private AdminDao adminDao;

    public AdminService() {
        adminDao = new AdminDao();
    }

    public ArrayList<Admin> ReadAll() {
        ArrayList<Admin> admins = adminDao.readAll();
        return admins;
    }

    public void Create(Admin admin) {
        int newId = adminDao.getMaxId() + 1;
        admin.setId(newId);
        adminDao.create(admin);
    }

    public void Update(int id, Admin newAdmin) {
        adminDao.Update(id, newAdmin);
    }

    public Admin ReadAdmin(int id) {
        return adminDao.readAdmin(id);
    }

    public void deleteAdmin(int id) {
        Logger log = LogManager.getLogger();
        try {
            if (id >= 0) {
                adminDao.readAdmin(id);
                adminDao.delete(id);
                log.info("Admin deleted");
            } else {
                log.error("Wrong id");
            }
        } catch (Exception e){
            log.error("No such admin id");
        }
    }
}

