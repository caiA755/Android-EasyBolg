using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using WEBAPI1.DataHandler.BLL;
using WEBAPI1.Models;

namespace WEBAPI1.Content
{
    public class ArticleController : ApiController
    {
        // GET api/<controller>
        private ArticleBLL articleBLL = new ArticleBLL();
        // GET api/<controller>
        [HttpGet]
        public IHttpActionResult Get()
        {
            return Json<List<Article>>(articleBLL.GetArticle());
        }

        [HttpGet]
        public IHttpActionResult GetTop()
        {
            return Json<List<Article>>(articleBLL.GetArticleTop());
        }
        [HttpGet]
        public IHttpActionResult GetArticleBykey(String keyword)
        {
            return Json<List<Article>>(articleBLL.GetArticleByKey(keyword));
        }

        [HttpGet]
        public IHttpActionResult GetArticleByUID(String UID)
        {
            return Json<List<Article>>(articleBLL.GetArticleByUID(UID));
        }

        [HttpGet]
        public IHttpActionResult GetArticleByUidTit(String UID,String Title)
        {
            return Json<List<Article>>(articleBLL.GetArticleByUidTit(UID,Title));
        }


        [HttpGet]
        public IHttpActionResult InsertArticle(String Content, String Title, String Flag,int UID,String PostImg)
        {
            Article article = new Article();
            article.Content = Content;
            article.Title = Title;
            article.Flag = Flag;
            User u = new User();
            u.UID = UID;
            article.User = u;
            article.PostImg = PostImg;
            article.PostImg = "http://101.35.54.74/myimg/a.jpg";
            return Json<Boolean>(articleBLL.InsertArticle(article));
        }

        [HttpGet]
        public IHttpActionResult EditArticle(String Content, String Title, int AID)
        {
            Article article = new Article();
            article.Content = Content;
            article.Title = Title;
            article.AID = AID;
         //   article.PostImg = PostImg;
            article.Time = System.DateTime.Now;
            //if (article.PostImg==null||article.PostImg.Trim().Equals(""))
            //{
            //    article.PostImg = "http://101.35.54.74/myimg/a.jpg";
            //}
            return Json<Boolean>(articleBLL.UpdateArticle(article));
        }

        [HttpGet]
        public IHttpActionResult GetArticleDetail(String AID)
        {
            return Json<Article>(articleBLL.GetArticleDetail(AID));
        }


        [HttpGet]
        public IHttpActionResult GetArticleDel(int AID)
        {
            Article article = new Article();
            article.AID = AID;
            return Json<Boolean>(articleBLL.DelArticle(article));
        }
        // GET api/<controller>/5
        //public string Get(int id)
        //{
        //    return "value";
        //}
        // POST api/<controller>
        public void Post([FromBody] string value)
        {

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