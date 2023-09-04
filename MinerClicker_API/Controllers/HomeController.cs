using Microsoft.AspNetCore.Mvc;

namespace MinerClicker_API.Controllers
{
    [ApiController]
    [Route("/")]
    public class HomeController: ControllerBase
    {
       [HttpGet]
        public String Inicio()
        {
            return "Funcionou!";
        }
    }
}