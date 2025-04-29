import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.ptr.IntByReference;

import j2html.tags.specialized.ATagUtil;

import com.sun.jna.platform.win32.Kernel32;

public class Test3 {

//	public interface WinInet extends Library {
//
//		WinInet INSTANCE = (WinInet) Native.load("Wininet", WinInet.class);
//
//		public boolean InternetGetConnectedState(final IntByReference lpdwFlags, final int dwReserved);
//
//	}

	public static void main(final String[] args) throws Exception {
//		final IntByReference lpdwFlags = new IntByReference();
//		System.out.println(WinInet.INSTANCE.InternetGetConnectedState(lpdwFlags, 0));
//		System.out.println(lpdwFlags.getValue());
//		System.out.println(Kernel32.INSTANCE.GetLastError());
		final String microsoftSpeechPlatformRuntimeDownloadPageUrl = "https://www.microsoft.com/en-us/download/details.aspx?id=27225";
		System.out.println(ATagUtil.createByUrl(microsoftSpeechPlatformRuntimeDownloadPageUrl));
		final String microsoftWindowsCompatibilitySettingsPageUrl = "https://support.microsoft.com/en-us/windows/make-older-apps-or-programs-compatible-with-windows-10-783d6dd7-b439-bdb0-0490-54eea0f45938";
		System.out.println(ATagUtil.createByUrl(microsoftWindowsCompatibilitySettingsPageUrl));
	}

}
