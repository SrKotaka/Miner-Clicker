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
        try
            {
                _context.Usuario.Add(model);
                if (await _context.SaveChangesAsync() == 1)
                {
                    return Created($"/api/usuario/{model.id}",model);
                }
            }
        catch
            {
                return this.StatusCode(StatusCodes.Status500InternalServerError, "Falha no acesso ao banco de dados.");
            }
            return BadRequest();
        }
        [HttpDelete("{Userid}")]
        public async Task<ActionResult> delete(int Userid)
        {
            try{
                var usuario = await _context.Usuario.FindAsync(Userid);
                if(usuario == null)
                {
                    return NotFound();
                }
                _context.Remove(usuario);
                await _context.SaveChangesAsync();
                return NoContent();
            }
            catch{
                return this.StatusCode(StatusCodes.Status500InternalServerError,"Falha no acesso ao banco de dados.");
            }
        }
        [HttpPut("{UsuarioId}")]
        public async Task<IActionResult> put(int UsuarioId, Usuario dadosUsuarioAlt)
        {
            try {
          
            var result = await _context.Usuario.FindAsync(UsuarioId);
            if (UsuarioId != result.id)
            {
                
                return BadRequest();
            }
            result.id = dadosUsuarioAlt.id;
            result.nome = dadosUsuarioAlt.nome;
            result.email = dadosUsuarioAlt.email;
            result.senha = dadosUsuarioAlt.senha;
            await _context.SaveChangesAsync();
            return Created($"/api/usuario/{dadosUsuarioAlt.id}", dadosUsuarioAlt);
            }
            catch
            {
            return this.StatusCode(StatusCodes.Status500InternalServerError,"Falha no acesso ao banco de dados.");
            }
        }
    }
}