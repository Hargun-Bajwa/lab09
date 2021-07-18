
package services;


import models.Role;
import dataaccess.RoleDB;

public class RoleService {

    public Role get(int roleID) throws Exception {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(roleID);
        return role;
    }}