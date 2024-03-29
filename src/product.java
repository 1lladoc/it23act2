
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author uxer
 */
public class product {
    conn con = new conn();
    public int addProduct(String product, int quantity, float price){
        int r = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection(con.url, con.username, con.password);
            
            String sql = "insert into products values(null,?,?,?);";
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
            
            pstmt.setString(1, product);
            pstmt.setInt(2, quantity);
            pstmt.setFloat(3, price);
            
            r = pstmt.executeUpdate();
            //System.out.println(pstmt);
            pstmt.close();
            conn.close();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
    public int deleteProduct(Object id){
        int r = 0;
        
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection(con.url, con.username, con.password);
            
            String sql = "DELETE FROM products WHERE id = ?;";
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
            
            int newid = Integer.parseInt(id.toString());
            
            pstmt.setInt(1, newid);
            
            r = pstmt.executeUpdate();
            //System.out.println(pstmt);
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return r;
    }

    public int editProduct(Object id, String name, Object price){
        int r = 0;
        try{
            
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection(con.url, con.username, con.password);
            
            String sql = "UPDATE products SET product_name = ?, price = ? WHERE id = ?;";
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
           
            pstmt.setString(1, name);
            float newprice = Float.parseFloat(price.toString());
            pstmt.setFloat(2, newprice);
            String newid = id.toString();
            pstmt.setString(3, newid);
            
            r = pstmt.executeUpdate();
            //System.out.println(pstmt);
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }

    public int addQuantity(Object id, Object quantity){
        int r = 0;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection(con.url, con.username, con.password);
            
            String sql = "UPDATE products SET quantity = quantity + ? WHERE id = ?;";
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);

            int newqty = Integer.parseInt(quantity.toString());
            pstmt.setInt(1, newqty);
            pstmt.setString(2, id.toString());
            
            r = pstmt.executeUpdate();
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(product.class.getName()).log(Level.SEVERE, null, ex);
        }
        return r;
    }
    
    final void refresh(JTable table){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection(con.url, con.username, con.password);
            
            String sql = "select * from products;";
            Statement stmt = (Statement) conn.createStatement();
            
            ResultSet rs = stmt.executeQuery(sql);
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while(rs.next()){
                model.addRow(new Object[]{rs.getString("id"),rs.getString("product_name"),rs.getString("quantity"),rs.getString("price")});
            }
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(mainpage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(mainpage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    final void search(String keyword, JTable table){
        try{
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = (Connection) DriverManager.getConnection(con.url, con.username, con.password);
            
            String sql = "SELECT * FROM products WHERE id LIKE ? OR product_name LIKE ?;";
            PreparedStatement pstmt = (PreparedStatement) conn.prepareStatement(sql);
            
            pstmt.setString(1, "%"+keyword+"%");
            pstmt.setString(2, "%"+keyword+"%");
            
            ResultSet rs = pstmt.executeQuery();
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);
            while(rs.next()){
                model.addRow(new Object[]{rs.getString("id"),rs.getString("product_name"),rs.getString("quantity"),rs.getString("price")});
            }
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(mainpage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(mainpage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
