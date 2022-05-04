using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WEBAPI1.DataHandler.BLL;
using WEBAPI1.DataHandler.DAL;
using WEBAPI1.Models;

namespace WEBAPI1.Controllers
{

    public class UserController : ApiController
    {
        private UserBLL userBLL = new UserBLL();
        // GET api/<controller>
        [HttpGet]
        public IHttpActionResult Get()
        {
            return Json<List<User>>(userBLL.GetUsers());
        }

        [HttpGet]
        public IHttpActionResult GetUserByUID(String Uid)
        {
            return Json<List<User>>(userBLL.GetUsers(Uid));
        }
        // GET api/<controller>/5
        //public string Get(int id)
        //{
        //    return "value";
        //}
        [HttpGet]
        public IHttpActionResult InsertUser(String username,String password,String email)
        {
            User u = new User();
            u.UserName = username;
            u.Password = password;
            u.Email = email;
            return Json<bool>(userBLL.InsertUser(u));
        }
        // POST api/<controller>
        //public void Post([FromBody] string value)
        //{

        //}

        //[ActionName("UserLogin")]
        [HttpGet]
        public IHttpActionResult UserLogin(String username,String pwd)
        {
            User u = new User();
            u.Password = pwd;
            u.UserName = username;
            return Json<Boolean>(userBLL.UserLogin(u));
        }

        [HttpGet]
        public IHttpActionResult GetUID(String username)
        {
            User u = new User();
            //u.Password = password;
            u.UserName = username;
            //u.Email = email;
            return Json<int>(userBLL.GetUID(u));
        }

        [HttpGet]
        public IHttpActionResult UserIsExist(String username)
        {
            User u = new User();
            u.UserName = username;
            return Json<Boolean>(userBLL.UserLogin(u));
        }

        [HttpGet]
        public IHttpActionResult GetNickName(int UID)
        {
            User u = new User();
            u.UID=UID;
            return Json<String>(userBLL.GetNickName(u));
        }

        // PUT api/<controller>/5
        public void Put(int id, [FromBody] string value)
        {
        }

        // DELETE api/<controller>/5
        public void Delete(int id)
        {
        }
    }
}