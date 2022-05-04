using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WEBAPI1.DataHandler.DAL;
using WEBAPI1.Models;

namespace WEBAPI1.DataHandler.BLL
{
    public class UserBLL
    {
        private static UserDAL userDAL = new UserDAL();
        /// <summary>
        /// 获取所有用户信息
        /// </summary>
        /// <returns></returns>
        public List<User> GetUsers()
        {
            return userDAL.GetUsers();
        }
        public List<User> GetUsers(String UID)
        {
            return userDAL.GetUsers(UID);
        }
            /// <summary>
            /// 修改用户信息
            /// </summary>
            /// <param name="user"></param>
            /// <returns></returns>
            public bool InsertUser(User user)
        {
            return userDAL.InsertUser(user);
        }
        /// <summary>
        /// 更新用户信息
        /// </summary>
        /// <param name="user"></param>
        /// <returns></returns>
        public bool UpdateUser(User user)
        {
            return userDAL.UpdateUser(user);
        }
        /// <summary>
        /// 删除用户信息
        /// </summary>
        /// <param name="UID"></param>
        /// <returns></returns>
        public bool DelUser(int UID)
        {
            return userDAL.DelUser(UID);
        }
        public bool UserLogin(User user)
        {
            return userDAL.UserLogin(user);
        }
        public int GetUID(User user)
        {
            return userDAL.GetUID(user);
        }
        public bool UserIsExist(User user)
        {
            return userDAL.UserIsExist(user);
        }
        public String GetNickName(User user)
        {
            return userDAL.GetNickName(user);
        }
      
        }
}