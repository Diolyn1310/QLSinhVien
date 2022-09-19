/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import helper.jdbcHelper;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.HocVien;

/**
 *
 * @author Sieu Nhan Bay
 */
public class HocVienDAO {
    
    //đọc 1 nhân viên từ 1 bản ghi (1 ResultSet)
    public HocVien readFromResultSet(ResultSet rs) throws SQLException{
        HocVien model = new HocVien();
        model.setMaHV(rs.getInt("MaHV"));
        model.setMaKH(rs.getInt("KH")); 
        model.setMaNH(rs.getString("MaNH"));
        model.setDiem(rs.getDouble("Diem"));
        return model;
    }
    
    //thực hiện truy vấn lấy về 1 tập ResultSet rồi điền tập ResultSet đó vào 1 List
    public List<HocVien> select(String sql,Object...args){
//        List<HocVien> list=new ArrayList<>();
//        try {
//            ResultSet rs = null;
//            try{
//                rs=jdbcHelper.executeQuery(sql, args);
//                while(rs.next()){
//                    list.add(readFromResultSet(rs));
//                }
//            }finally{
////                rs.getStatement().getConnection().close();      //đóng kết nối từ resultSet
//            }
//        } catch (SQLException ex) {
//            throw new RuntimeException();
//        }
//        return list;
     List<HocVien> arrHV = new ArrayList<>();
        ResultSet rs = null;
        try {
            rs = jdbcHelper.executeQuery(sql, args);
            while(rs.next()) {
                HocVien hv = new HocVien();
                hv.setMaHV(rs.getInt(1));
                hv.setMaKH(rs.getInt(2));
                hv.setMaNH(rs.getString(3));
                hv.setDiem(rs.getFloat(4));
                arrHV.add(hv);
            }
//            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrHV;
    }
    

    public void insert(HocVien model) {
        String sql="INSERT INTO HocVien(MaKH, MaNH, Diem) VALUES(?, ?, ?)";
        jdbcHelper.executeUpdate(sql,
                model.getMaKH(),
                 model.getMaNH(),
                 model.getDiem());
    }


    public void update(HocVien model) {
        String sql="UPDATE HocVien SET MaKH=?, MaNH=?, Diem=? WHERE MaHV=?";
        jdbcHelper.executeUpdate(sql,
                model.getMaKH(),
                 model.getMaNH(),
                 model.getDiem(),
                 model.getMaHV());
    }


     public void delete(Integer MaHV){
     String sql="DELETE FROM HocVien WHERE MaHV=?";
     jdbcHelper.executeUpdate(sql, MaHV);
     }



    public List<HocVien> select() {
        String sql="SELECT * FROM HocVien";
        return select(sql);             //trong 1 class có thể có 2 method trùng tên (nhưng param khác nhau)
    }


    public HocVien findById(String id) {
        String sql="SELECT * FROM HocVien WHERE MaHV=?";
        List<HocVien> list=select(sql, id);
        return list.size()>0?list.get(0):null;               //có thể trả về là null
    }
     public List<HocVien> selectByCourse(int idKH) {
        List<HocVien> lstHV = new ArrayList<>();
        String sql = "SELECT * FROM HocVien Where MaKH = ?";
        lstHV = this.select(sql, idKH);
        if(lstHV.isEmpty()) {
            lstHV = null;
        }
        return lstHV;
    }
     public static void main(String[] args) {
         HocVienDAO hvDAO = new HocVienDAO();
         List<HocVien> lstHV = hvDAO.selectByCourse(1);
//         List<HocVien> lstHV = hvDAO.select("SELECT * FROM HocVien");
         if(lstHV != null) {
              System.out.println("Success");
         } else {
              System.out.println("Error");
         }
        
    }
}
