using Xunit;
using Maximus.IRD.Samples.KeycloakClient;

namespace Tests
{
    public class Tests
    {
        const string source32   = "A bit of textXXXXXXXXXXXXXXXXXXX";
        const string sourceLess = "Real Short";
        const string sourceMore = "A bit of textXXXXXXXXXXXXXXXXXXX and some more";

        private void TestEncryptDecryptString(string source)
        {
            var cipherText = AsciiCrypt.EncryptText(source);
            var plainText = AsciiCrypt.DecryptText(cipherText);
            Assert.Equal(source, plainText);
        }

        [Fact]
        public void TestEncryptDecrypt32()
        {
            TestEncryptDecryptString(source32);
        }

        [Fact]
        public void TestEncryptDecryptLess()
        {
            TestEncryptDecryptString(sourceLess);
        }

        [Fact]
        public void TestEncryptDecryptMore()
        {
            TestEncryptDecryptString(sourceMore);
        }

        [Fact]
        public void TestIVDiffers()
        {
            var s1 = AsciiCrypt.EncryptText(source32);
            var s2 = AsciiCrypt.EncryptText(source32);
            Assert.NotEqual(s1, s2);
        }

        [Fact]
        public void TestDecryptSame()
        {
            var s1 = AsciiCrypt.EncryptText(source32);
            var s2 = AsciiCrypt.EncryptText(source32);

            var p1 = AsciiCrypt.DecryptText(s1);
            var p2 = AsciiCrypt.DecryptText(s2);
            Assert.Equal(p1, p2);
            Assert.Equal(source32, p1);
            Assert.Equal(source32, p2);
        }
    }
}
