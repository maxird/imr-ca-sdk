using System;
using System.Security.Cryptography;
using System.IO;
using System.Text;

// derived: https://msdn.microsoft.com/en-us/library/system.security.cryptography.aescryptoserviceprovider.aspx
// as starting point
//
namespace Maximus.IRD.Samples.KeycloakClient
{

    public class AsciiCrypt
    {
        // simple app-embedded secret key. Not ideal but
        // sufficient to require some effort to attack
        // on-disk storage
        //
        private static byte[] key = {
            136, 165, 135, 167, 120,  87,  35, 223,
            112, 170, 185,  94, 150, 213, 124, 100,
            102, 167, 123, 141,  71, 204,  86, 160,
            245, 201,  47,  56, 180, 155,  47, 200
        };

        private static Aes CreateAes()
        {
            // provides a crypto engine with an already seeded IV
            //
            var aes = System.Security.Cryptography.Aes.Create();
            aes.Mode = CipherMode.CBC;
            aes.Padding = PaddingMode.None;
            return aes;
        }

        /// Provides a simplistic per-machine/user value used to
        /// salt the static encryption key
        ///
        private static byte[] GetSalt()
        {
            var hostName = Environment.GetEnvironmentVariable("HOSTNAME");
            var computerName = Environment.GetEnvironmentVariable("COMPUTERNAME");
            var username = Environment.GetEnvironmentVariable("USERNAME");

            string saltText;
            if (hostName != null)
                saltText = $"{hostName}{username}";
            else
                saltText = $"{computerName}{username}";

            var hasher = SHA256.Create();
            var bytes = Encoding.UTF8.GetBytes(saltText);
            return hasher.ComputeHash(bytes);
        }

        /// applies the salt against the static key
        ///
        private static byte[] SaltKey()
        {
            var salt = GetSalt();
            var saltedKey = new byte[32];
            for (int i = 0; i < saltedKey.Length; i++)
                saltedKey[i] = (byte)(key[i] ^ salt[i]);

            return saltedKey;
        }

        /// Encrypt the given text using AES/CBC with a unique IV
        ///
        public static string EncryptText(string plainText)
        {
            var aes = CreateAes();
            string cryptedText = null;
            using (var cryptor = aes.CreateEncryptor(SaltKey(), aes.IV))
            {
                using (var ms = new MemoryStream())
                {
                    using (var cs = new CryptoStream(ms, cryptor, CryptoStreamMode.Write))
                    {
                        using (StreamWriter sw = new StreamWriter(cs))
                        {
                            sw.Write(plainText);
                            var blockSizeBytes = aes.BlockSize / 8;
                            var padding = blockSizeBytes - (plainText.Length % blockSizeBytes);
                            padding = (padding == blockSizeBytes) ? 0 : padding;
                            while (padding > 0)
                            {
                                sw.Write('\0');
                                padding--;
                            }
                        }
                    }
                    cryptedText = Convert.ToBase64String(ms.ToArray());
                }
            }

            var iv = Convert.ToBase64String(aes.IV);

            return $"{iv}\n{cryptedText}";
        }

        /// Decrypt the given cipher text assuming it has been encrypted
        /// with the sister method
        ///
        public static string DecryptText(string cipherText)
        {
            if (cipherText == null)
                throw new Exception("No ciphertext supplied");

            var parts = cipherText.Split('\n');
            if (parts.Length < 2)
                throw new Exception("Ciphertext is wrong format");
            var ivText = parts[0];
            var cipherBody = parts[1];

            var cipherBytes = Convert.FromBase64String(cipherBody);
            string plainText = null;

            var aes = CreateAes();
            var iv = Convert.FromBase64String(ivText);

            using (var cryptor = aes.CreateDecryptor(SaltKey(), iv))
            {
                using (var ms = new MemoryStream(cipherBytes))
                {
                    using (var cs = new CryptoStream(ms, cryptor, CryptoStreamMode.Read))
                    {
                        using (StreamReader sr = new StreamReader(cs))
                        {
                            plainText = sr.ReadToEnd();
                        }
                    }
                }
            }

            return plainText.TrimEnd('\0');
        }
    }
}
