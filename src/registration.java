/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uxer
 */
public class registration {
    
    public void register(String firstname,String lastname,String username, String password){
        String sql = "insert into users values(null,'"+username+"',md5('"+password+"'),'"+firstname+"','"+lastname+"',0)";
        System.out.println(sql);
    }
    
}
