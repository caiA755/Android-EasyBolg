using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace WEBAPI1.Models
{

    /**
     * Auto-generated: 2021-12-15 18:43:40
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
   
    
    /**
     * Auto-generated: 2021-12-15 18:43:40
     *
     * @author bejson.com (i@bejson.com)
     * @website http://www.bejson.com/java2pojo/
     */
    public class User
    {

        public int UID;
        public String NickName;
        public String Sex;
        public DateTime Birth;
        public String UserName;
        public String Password;
        public String Phone;
        public String Email;
        public DateTime RegisterTime;
        public void setUID(int UID)
        {
            this.UID = UID;
        }
        public int getUID()
        {
            return UID;
        }

        public void setNickName(String NickName)
        {
            this.NickName = NickName;
        }
        public String getNickName()
        {
            return NickName;
        }

        public void setSex(String Sex)
        {
            this.Sex = Sex;
        }
        public String getSex()
        {
            return Sex;
        }

        public void setBirth(DateTime Birth)
        {
            this.Birth = Birth;
        }
        public DateTime getBirth()
        {
            return Birth;
        }

        public void setUserName(String UserName)
        {
            this.UserName = UserName;
        }
        public String getUserName()
        {
            return UserName;
        }

        public void setPassword(String Password)
        {
            this.Password = Password;
        }
        public String getPassword()
        {
            return Password;
        }

        public void setPhone(String Phone)
        {
            this.Phone = Phone;
        }
        public String getPhone()
        {
            return Phone;
        }
        public void setEmail(String Email)
        {
            this.Email = Email;
        }
        public String getEmail()
        {
            return Email;
        }
        public void setRegisterTime(DateTime rgtime)
        {
            this.RegisterTime = rgtime;
        }
        public DateTime getRegisterTime()
        {
            return RegisterTime;
        }

    }
}