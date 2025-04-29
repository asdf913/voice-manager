using System.Runtime.InteropServices;

namespace ClassLibrary1
{
    public class Class1
    {
        [DllImport("wininet.dll")]
        private extern static bool InternetGetConnectedState(out int Description, int ReservedValue);

        [RGiesecke.DllExport.DllExport]
        public static bool InternetGetConnectedState(out int desc)
        {
            return InternetGetConnectedState(out desc, 0);
        }
    }
}
