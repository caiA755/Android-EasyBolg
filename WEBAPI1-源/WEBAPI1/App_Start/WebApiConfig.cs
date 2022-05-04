using System;
using System.Collections.Generic;
using System.Linq;
using System.Web.Http;

namespace WEBAPI1
{
    public static class WebApiConfig
    {
        public static void Register(HttpConfiguration config)
        {
            // Web API 配置和服务

               config.Routes.MapHttpRoute(
                 name: "DefaultApi",
                 routeTemplate: "api/{controller}/{action}/{id}",
                 defaults: new { id = RouteParameter.Optional },
                 constraints: new { id = @"\d*" }
             );
            config.Routes.MapHttpRoute(
               name: "ArticleApi",
               routeTemplate: "api/{controller}/{action}/{id}",
               defaults: new { id = RouteParameter.Optional },
               constraints: new { id = @"\d*" }
           );
            //config.Routes.MapHttpRoute(
            //    name: "UserApi",
            //    routeTemplate: "userapi/{controller}/{action}/{id}",
            //    defaults: new { id = RouteParameter.Optional }
            //);

        }
    }
}
