using Microsoft.EntityFrameworkCore;
using MinerClicker_API.Models;

namespace MinerClicker_API.Data
{
    public class MinerContext : DbContext
    {
        private readonly IConfiguration _configuration;

        public MinerContext(DbContextOptions<MinerContext> options, IConfiguration configuration) : base(options)
        {
            _configuration = configuration;
        }

        protected override void OnConfiguring(DbContextOptionsBuilder optionsBuilder)
        {
            optionsBuilder.UseSqlServer(_configuration.GetConnectionString("StringConexaoSQLServer"));
        }

        public DbSet<Usuario> Usuario { get; set; }
    }
}
