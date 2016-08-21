using System.IO;
using Microsoft.Extensions.Configuration;

namespace Maximus.IRD.Samples.Config
{
    public class Ini
    {
        public static IConfigurationRoot Load()
        {
            // pull configuration out of the local configuration file
            //
            var builder = new ConfigurationBuilder();
            var configPath = Path.Combine(Directory.GetCurrentDirectory(), "../..");
            builder.SetBasePath(configPath);

            builder.AddIniFile("configuration.ini");

            var config = builder.Build();

            return config;
        }
    }
}
