package com.membercrud.dao;

import com.membercrud.bean.MemberVO;
import com.membercrud.util.JDBCUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private Connection conn=null;
    private PreparedStatement stmt=null;
    private ResultSet rs=null;

    private final String M_INSERT="insert into member(userid, username, password, email, blogurl, photo, detail) values(?,?,?,?,?,?,?)";
    private final String M_UPDATE="update member set userid=?, username=?, password=?, email=?, blogurl=?, photo=?, detail=? where sid=?";
    private final String M_DELETE="delete from member where sid=?";
    private final String M_SELECT="select * from member where sid=?";
    private final String M_LIST="select * from member order by regdate desc";

    public int insertMember(MemberVO data){
        int result=0;
        conn= JDBCUtil.getConnection();
        try {
            stmt=conn.prepareStatement(M_INSERT);
            stmt.setString(1,data.getUserid());
            stmt.setString(2,data.getUsername());
            stmt.setString(3,data.getPassword());
            stmt.setString(4,data.getEmail());
            stmt.setString(5,data.getBlogurl());
            stmt.setString(6,data.getPhoto());
            stmt.setString(7,data.getDetail());
            result=stmt.executeUpdate();
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public MemberVO getOne(int sid){
        MemberVO one=null;
        conn=JDBCUtil.getConnection();
        try {
            stmt= conn.prepareStatement(M_SELECT);
            stmt.setInt(1,sid);
            rs=stmt.executeQuery();
            if(rs.next()){
                one=new MemberVO();
                one.setSid(rs.getInt("sid"));
                one.setUserid(rs.getString("userid"));
                one.setUsername(rs.getString("username"));
                one.setPassword(rs.getString("password"));
                one.setEmail(rs.getString("email"));
                one.setBlogurl(rs.getString("blogurl"));
                one.setPhoto(rs.getString("photo"));
                one.setDetail(rs.getString("detail"));
                one.setRegdate(rs.getDate("regdate"));
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return one;
    }
    public int updateBoard(MemberVO vo) {
        System.out.println("===> JDBC로 updateBoard() 기능 처리");
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement(M_UPDATE);
            stmt.setString(1, vo.getUserid());
            stmt.setString(2, vo.getUsername());
            stmt.setString(3, vo.getPassword());
            stmt.setString(4, vo.getEmail());
            stmt.setString(5, vo.getBlogurl());
            stmt.setString(6, vo.getPhoto());
            stmt.setString(7, vo.getDetail());
            stmt.setInt(8, vo.getSid());

            //System.out.println(vo.getUserid() + "-" + vo.getUsername() + "-" + vo.getDetail() + "-" + vo.getSid());
            stmt.executeUpdate();
            return 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void deleteMember(MemberVO vo) {
        System.out.println("===> JDBC로 deleteBoard() 기능 처리");
        try {
            conn = JDBCUtil.getConnection();
            stmt = conn.prepareStatement(M_DELETE);
            stmt.setInt(1, vo.getSid());
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<MemberVO> getList(){
        List<MemberVO> list=new ArrayList<MemberVO>();

        try {
            conn=JDBCUtil.getConnection();
            stmt=conn.prepareStatement(M_LIST);
            rs=stmt.executeQuery();
            while(rs.next()){
                MemberVO one=new MemberVO();
                one.setSid(rs.getInt("sid"));
                one.setUserid(rs.getString("userid"));
                one.setUsername(rs.getString("username"));
                one.setPassword(rs.getString("password"));
                one.setEmail(rs.getString("email"));
                one.setBlogurl(rs.getString("blogurl"));
                one.setPhoto(rs.getString("photo"));
                one.setDetail(rs.getString("detail"));
                one.setRegdate(rs.getDate("regdate"));
                list.add(one);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String getPhotoFilename(int sid){
        String filename=null;
        try {
            conn=JDBCUtil.getConnection();
            stmt=conn.prepareStatement(M_SELECT);
            stmt.setInt(1,sid);
            rs=stmt.executeQuery();
            if(rs.next()) {
                filename=rs.getString("photo");
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("===> JDBC로 getPhotoFilename() 기능 처리");
        return filename;
    }
}