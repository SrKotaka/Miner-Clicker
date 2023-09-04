using Microsoft.AspNetCore.Mvc;
using MinerClicker_API.Data;
using MinerClicker_API.Models;

namespace MinerClicker_API.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UsuarioController: ControllerBase
    {
        private MinerContext _context;
        public UsuarioController(MinerContext context)
        {
            _context = context;
        }
        [HttpGet]
        public ActionResult<List<Usuario>> GetAll()
        {
            return _context.Usuario.ToList();
        }
    }
}