using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using WEBAPI1.DataHandler.BLL;
using WEBAPI1.Models;

namespace WEBAPI1.Controllers
{
    public class HomeController : Controller
    {
        private ArticleBLL articleBLL = new ArticleBLL();
        private UserBLL UserBLL = new UserBLL();

        public ActionResult Index()
        {
            //User U = new User();
            //U.UserName = "CAUC";
            //object sa = UserBLL.InsertUser(U);
            return View();
        }
    }
}
