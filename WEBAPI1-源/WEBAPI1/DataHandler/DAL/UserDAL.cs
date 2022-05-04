using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using Demo_One.DataHandler;
using WEBAPI1.Models;

namespace WEBAPI1.DataHandler.DAL
{
    public class UserDAL
    {
        //查询所有数据
        public List<User> GetUsers()
        {
            string sql = "select * from [User]";
            SqlDataReader reader = new DBhelper().GetSqlDataReader(sql);
            List<User> users = new List<User>();
            while (reader.Read())
            {
                User user = new User();
                user.UID = (int)reader["UID"];
                user.UserName = reader["UserName"].ToString();
                user.Sex = reader["Sex"].ToString();
                user.Birth = (DateTime)(reader["Birth"]);
                user.NickName = reader["NickName"].ToString();
                user.Phone = reader["Phone"].ToString();
                user.Password = reader["Password"].ToString();
                users.Add(user);
            }
            reader.Close();
            return users;
        }
        //根据UID获取用户信息
        public List<User> GetUsers(String UID)
        {
            string sql = "select * from [User] where UID="+UID;
            SqlDataReader reader = new DBhelper().GetSqlDataReader(sql);
            List<User> users = new List<User>();
            while (reader.Read())
            {
                User user = new User();
                user.UID = (int)reader["UID"];
                user.UserName = reader["UserName"].ToString();
                user.Sex = reader["Sex"].ToString();
                user.Birth = (DateTime)(reader["Birth"]);
                user.NickName = reader["NickName"].ToString();
                user.Phone = reader["Phone"].ToString();
                user.Password = reader["Password"].ToString();
                users.Add(user);
            }
            reader.Close();
            return users;
        }
        //添加用户信息
        public bool InsertUser(User user)
        {
            string sql = String.Format("insert into [User](Username,Password,NickName,SEX,BIRTH,PHONE,EMAIL,REGISTERTIME,Lastlogintime) values('{0}','{1}','{2}','{3}','{4}','{5}','{6}','{7}','{8}')",user.UserName,user.Password,user.NickName,user.Sex,DateTime.Now,user.Phone,user.Email, DateTime.Now,DateTime.Now);
            bool result = new DBhelper().ExecuteNonQuery(sql);
            return result;
        }
        //修改用户信息
        public bool UpdateUser(User user)
        {
            String sql = String.Format("update [User] set Username='{0}',Password='{1}',Nickname='{2}',Sex='{3}',birth='{4}',Phone='{5}',EMAIL='{6}',REGISTERTIME='{7}' where UID='{8}'", user.UserName, user.Password, user.NickName, user.Sex, user.Birth, user.Phone, user.Email, user.RegisterTime,user.UID);
            bool result = new DBhelper().ExecuteNonQuery(sql);
            return result;
        }
        //删除用户信息
        public bool DelUser(int userID)
        {
            String sql=String.Format("delete from [User] where UID='{0}'",userID);
            bool result = new DBhelper().ExecuteNonQuery(sql);
            return result;
        }
        //用户登录
        public bool UserLogin(User user) {
            String sql = String.Format("select count(*) from [User] where Username='{0}' and Password='{1}'" ,user.UserName,user.Password);
            int result = (int)new DBhelper().GetScalar(sql);
            return result>0;
        }
        //根据用户名密码获取UID
        public int GetUID(User user) {
            string sql = string.Format("select UID from [USER] where username='{0}'",user.UserName);
            SqlDataReader reader = new DBhelper().GetSqlDataReader(sql);
            User u = new User();
            while (reader.Read())
            {
                u.UID = (int)reader["UID"];
            }
            reader.Close();
            return u.UID;
          }
        //获取用户名
        public String GetNickName(User user)
        {
            string sql = string.Format("select username from [USER] where UID='{0}'", user.UID);
            SqlDataReader reader = new DBhelper().GetSqlDataReader(sql);
            User u = new User();
            while (reader.Read())
            {
                u.UserName = reader["username"].ToString();
            }
            reader.Close();
            return u.UserName;
        }
        //判断用户是否已经存在
        public bool UserIsExist(User user) {
            String sql = String.Format("select count(*) from [User] where Username='{0}'", user.UserName);
            int result = (int)new DBhelper().GetScalar(sql);
            return result > 0;
        }
    }
  }