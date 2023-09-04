namespace MinerClicker_API.Models
{
public class Usuario
    {
        public int id { get; set; }
        public string? nome { get; set; }
        public string? email { get; set; }
        public string? senha { get; set; }
        public int soulscoins { get; set; }
        public int coins { get; set; }
    }
}