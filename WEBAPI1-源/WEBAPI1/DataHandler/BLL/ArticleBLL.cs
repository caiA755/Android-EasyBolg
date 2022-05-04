using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WEBAPI1.DataHandler.DAL;
using WEBAPI1.Models;

namespace WEBAPI1.DataHandler.BLL
{
    public class ArticleBLL
    {
        private static ArticleDAL artDAL = new ArticleDAL();
        public List<Article> GetArticle()
        {
            return artDAL.GetArticle();
        }
        public List<Article> GetArticleTop()
        {
            return artDAL.GetArticleTop();
        }
         public List<Article> GetArticleByKey(String keyword)
        {
            return artDAL.GetArticleByKey(keyword);
        }
        public List<Article> GetArticleByUID(String UID)
        {
            return artDAL.GetArticleByUID(UID);
        }
        public List<Article> GetArticleByUidTit(String UID, String Title)
        {
            return artDAL.GetArticleByUidTit(UID,Title);
        }
        public bool InsertArticle(Article article)
        {
            User user = new User();
            user=article.User;
            article.Author =new UserBLL().GetNickName(user);
            return artDAL.InsertArticle(article);
        }
        public bool DelArticle(Article article)
        {
            return artDAL.DelArticle(article);
        }
        public Article GetArticleDetail(String aid)
        {
            return artDAL.GetArticleDetail(aid);
        }
        public bool UpdateArticle(Article article)
        {
            return artDAL.UpdateArticle(article);
        }
        }
}