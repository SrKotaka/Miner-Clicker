using Microsoft.AspNetCore.Mvc;
using MinerClicker_API.Data;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
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
        [HttpGet("{Userid}")]
        public ActionResult<Usuario> Get(int Userid)
        {
        try
            {
            var result = _context.Usuario.Find(Userid);
            if (result == null)
            {
                return NotFound();
            }
                return Ok(result);
            }
            catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
        }
        [HttpPost]
        public async Task<ActionResult> post(Usuario model)
        {
            
            try{
                _context.Usuario.Add(model);
                await _context.SaveChangesAsync();
                return Created($"/api/usuario/{model.id}", model);
            }
            catch{
                return this.StatusCode(StatusCodes.Status500InternalServerError,"Falha no acesso ao banco de dados.");
            }
        }
        [HttpDelete("{Userid}")]
        public async Task<ActionResult> delete(int Userid)
        {
            try{
                var result = _context.Usuario.Find(Userid);
                if (result == null)
                {
                    return NotFound();
                }
                _context.Usuario.Remove(result);
                await _context.SaveChangesAsync();
                return Ok();
            }
            catch{
                return this.StatusCode(StatusCodes.Status500InternalServerError,"Falha no acesso ao banco de dados.");
            }
        }
        [HttpPut("{UsuarioId}")]
        public async Task<IActionResult> put(int UsuarioId, Usuario dadosUsuarioAlt)
        {
            try{
                var result = _context.Usuario.Find(UsuarioId);
                if (result == null)
                {
                    return NotFound();
                }
                result.nome = dadosUsuarioAlt.nome;
                result.email = dadosUsuarioAlt.email;
                result.senha = dadosUsuarioAlt.senha;
                await _context.SaveChangesAsync();
                return Ok();
            }
            catch{
                return this.StatusCode(StatusCodes.Status500InternalServerError,"Falha no acesso ao banco de dados.");
            }
        }
    }
}