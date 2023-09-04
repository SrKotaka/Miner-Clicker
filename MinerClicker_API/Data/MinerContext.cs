using Microsoft.EntityFrameworkCore;
using MinerClicker_API.Models;
using System.Diagnostics.CodeAnalysis;

namespace MinerClicker_API.Data
{
public class MinerContext: DbContext
{
    protected readonly IConfiguration Configuration;
    
    
    public MinerContext(IConfiguration configuration)
    {
        Configuration = configuration;
    }
    protected override void OnConfiguring(DbContextOptionsBuilder options)
    {
        options.UseSqlServer(Configuration.GetConnectionString("StringConexaoSQLServer"));
    }
        public DbSet<Usuario> Usuario {get; set;}
    }
}