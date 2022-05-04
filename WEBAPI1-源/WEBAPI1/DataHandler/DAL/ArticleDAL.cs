using Demo_One.DataHandler;
using System;
using System.Collections.Generic;
using System.Data.SqlClient;
using System.Linq;
using System.Web;
using WEBAPI1.Models;

namespace WEBAPI1.DataHandler.DAL
{
    public class ArticleDAL
    {
        public List<Article> GetArticle()
        {
            string sql = "select * from  article,[user] where article.UID=[user].UID order by article.time desc";
            SqlDataReader reader = new DBhelper().GetSqlDataReader(sql);
            List<Article> articles = new List<Article>();
            while (reader.Read())
            {
                Article artc = new Article();
                artc.AID = (int)reader["AID"];
                artc.Content = reader["Content"].ToString();
                artc.Title = reader["Title"].ToString();
                artc.Time = (DateTime)(reader["Time"]);
                artc.Author = reader["Author"].ToString();
                artc.Flag = reader["Flag"].ToString();
                User user = new User();
                user.UID = (int)reader["UID"];
                user.UserName = reader["USERNAME"].ToString();
                artc.User = user;
                artc.PostImg = reader["postimg"].ToString();
                articles.Add(artc);
            }
            reader.Close();
            return articles;
        }
        public List<Article> GetArticleTop()
        {
            string sql = "select top 8 * from  article,[user] where article.UID=[user].UID";
            SqlDataReader reader = new DBhelper().GetSqlDataReader(sql);
            List<Article> articles = new List<Article>();
            while (reader.Read())
            {
                Article artc = new Article();
                artc.AID = (int)reader["AID"];
                artc.Content = reader["Content"].ToString();
                artc.Title = reader["Title"].ToString();
                artc.Time = (DateTime)(reader["Time"]);
                artc.Author = reader["Author"].ToString();
                artc.Flag = reader["Flag"].ToString();
                User user = new User();
                user.UID = (int)reader["UID"];
                user.UserName = reader["USERNAME"].ToString();
                artc.User = user;
                artc.PostImg = reader["postimg"].ToString();
                articles.Add(artc);
            }
            reader.Close();
            return articles;
        }
        public List<Article> GetArticleByKey(String keyword)
        {
            string sql = string.Format("select  * from  article  join [user]  on article.UID=[user].UID and  Title like '%{0}%' order by article.time desc", keyword);
            SqlDataReader reader = new DBhelper().GetSqlDataReader(sql);
            List<Article> articles = new List<Article>();
            while (reader.Read())
            {
                Article artc = new Article();
                artc.AID = (int)reader["AID"];
                artc.Content = reader["Content"].ToString();
                artc.Title = reader["Title"].ToString();
                artc.Time = (DateTime)(reader["Time"]);
                artc.Author = reader["Author"].ToString();
                artc.Flag = reader["Flag"].ToString();
                User user = new User();
                user.UID = (int)reader["UID"];
                user.UserName = reader["USERNAME"].ToString();
                artc.User = user;
                artc.PostImg = reader["postimg"].ToString();
                articles.Add(artc);
            }
            reader.Close();
            return articles;
        }
        public List<Article> GetArticleByUID(String UID)
        {
            string sql = string.Format("select  * from  article  join [user]  on article.UID=[user].UID and [user].UID='{0}' order by article.time desc", UID);
            SqlDataReader reader = new DBhelper().GetSqlDataReader(sql);
            List<Article> articles = new List<Article>();
            while (reader.Read())
            {
                Article artc = new Article();
                artc.AID = (int)reader["AID"];
                artc.Content = reader["Content"].ToString();
                artc.Title = reader["Title"].ToString();
                artc.Time = (DateTime)(reader["Time"]);
                artc.Author = reader["Author"].ToString();
                artc.Flag = reader["Flag"].ToString();
                User user = new User();
                user.UID = (int)reader["UID"];
                user.UserName = reader["USERNAME"].ToString();
                artc.User = user;
                artc.PostImg = reader["postimg"].ToString();
                articles.Add(artc);
            }
            reader.Close();
            return articles;
        }
        public List<Article> GetArticleByUidTit(String UID, String Title)
        {
            string sql = string.Format("select  * from  article  join [user]  on article.UID=[user].UID and [user].UID='{0}' and article.title like '%{1}%'", UID,Title);
            SqlDataReader reader = new DBhelper().GetSqlDataReader(sql);
            List<Article> articles = new List<Article>();
            while (reader.Read())
            {
                Article artc = new Article();
                artc.AID = (int)reader["AID"];
                artc.Content = reader["Content"].ToString();
                artc.Title = reader["Title"].ToString();
                artc.Time = (DateTime)(reader["Time"]);
                artc.Author = reader["Author"].ToString();
                artc.Flag = reader["Flag"].ToString();
                User user = new User();
                user.UID = (int)reader["UID"];
                user.UserName = reader["USERNAME"].ToString();
                artc.User = user;
                artc.PostImg = reader["postimg"].ToString();
                articles.Add(artc);
            }
            reader.Close();
            return articles;
        }
        //添加文章
        public bool InsertArticle(Article article)
        {
            string sql = String.Format("insert into Article(Content,Time,Title,Author,Flag,UID,PostImg) values('{0}','{1}','{2}','{3}','{4}','{5}','{6}')",article.Content,System.DateTime.Now,article.Title,article.Author,article.Flag,article.User.UID,article.PostImg);
            bool result = new DBhelper().ExecuteNonQuery(sql);
            return result;
        }
        public bool DelArticle(Article article)
        {
            string sql = String.Format("delete from article where aid='{0}'",  article.AID);
            bool result = new DBhelper().ExecuteNonQuery(sql);
            return result;
        }
        public Article GetArticleDetail(String aid) {
            string sql = string.Format("select * from  article where AID='{0}'", aid);
            SqlDataReader reader = new DBhelper().GetSqlDataReader(sql);
            Article artc = new Article();
            while (reader.Read())
            {
                artc.AID = (int)reader["AID"];
                artc.Content = reader["Content"].ToString();
                artc.Title = reader["Title"].ToString();
                artc.Time = (DateTime)(reader["Time"]);
                artc.Author = reader["Author"].ToString();
                artc.Flag = reader["Flag"].ToString();
                artc.PostImg = reader["postimg"].ToString();
               
            }
            reader.Close();
            return artc;
        }
        public bool UpdateArticle(Article article)
        {
            String sql = String.Format("update article set Title='{0}',content='{1}',Time='{2}'  where aid='{3}'",article.Title,article.Content,article.Time,article.AID );
            bool result = new DBhelper().ExecuteNonQuery(sql);
            return result;
        }
    }
}