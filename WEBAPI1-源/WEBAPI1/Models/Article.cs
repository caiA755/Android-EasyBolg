using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using WEBAPI1.DataHandler.DAL;

namespace WEBAPI1.Models
{
    public class Article
    {
        /// <summary>
        /// 
        /// </summary>
        public int AID { get; set; }
        /// <summary>
        /// 
        /// </summary>
        public string Content { get; set; }
        /// <summary>
        /// 
        /// </summary>
        public DateTime Time { get; set; }
        /// <summary>
        /// 
        /// </summary>
        public string Title { get; set; }
        /// <summary>
        /// 
        /// </summary>
        public string Author { get; set; }
        /// <summary>
        /// 
        /// </summary>
        public string Flag { get; set; }
        public string PostImg { get; set; }
        /// <summary>
        /// 
        /// </summary>

        public  User User { get; set; }
    }

   

}