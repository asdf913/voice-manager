import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import com.sun.jna.Function;
import com.sun.jna.Library;
import com.sun.jna.Memory;
import com.sun.jna.Pointer;
import com.sun.jna.WString;
import com.sun.jna.platform.win32.WinDef.HWND;

public class Test4 {

//	private interface User32 extends Library {

//		int GetClipboardFormatNameW(
//				  [in]  UINT   format,
//				  [out] LPWSTR lpszFormatName,
//				  [in]  int    cchMaxCount
//				);

//		int GetClipboardFormatNameW(final int format
////				point
//		);
//	}

	public static void main(final String[] args) {
		//
		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		//
		clipboard.getContents(null).getTransferDataFlavors();
		//
		System.out.println();
		//
//		Function function = Function.getFunction("user32", "CountClipboardFormats");
//		
//		//0, new WString("STATIC"), new WString(""), 0, 0, 0, 0,0, null,null, null,null
//		//
//		final HWND hwnd=
//		com.sun.jna.platform.win32.User32.INSTANCE.CreateWindowEx(0, "STATIC", "", 0, 0, 0, 0, 0, null, null, null, null);
//		//
//		final int CountClipboardFormats = function != null ? function.invokeInt(new Object[] {}) : 0;
//		//
//		System.out.println("CountClipboardFormats=" + CountClipboardFormats);
//		//
//		for (int i = 0; i < CountClipboardFormats; i++) {
//			//
//			if ((function = Function.getFunction("user32", "EnumClipboardFormats")) != null) {
//				//
//				System.out.println("OpenClipboard="
//						+ Function.getFunction("user32", "OpenClipboard").invokeInt(new Object[] { hwnd}));
//				//
//				System.out.println(Integer.toString(i) + " " + function.invokeInt(new Object[] { Integer.valueOf(i) }));
//				//
//				System.out.println("GetLastError1="+ Function.getFunction("kernel32", "GetLastError").invokeObject(new Object[] {}));
//				//
//				System.out.println("CloseClipboard="
//						+ Function.getFunction("user32", "CloseClipboard").invokeInt(new Object[] { hwnd }));
//				//
//				System.out.println();
//				//
//				if ((function = Function.getFunction("user32", "GetClipboardFormatNameW")) != null) {
//					//
//					System.out.println("OpenClipboard="
//							+ Function.getFunction("user32", "OpenClipboard").invokeInt(new Object[] { hwnd}));
//					//
//					Memory lpszFormatName = new Memory(100);
//					//
//					System.out.println(Integer.toString(i) + " " + function
//							.invokeInt(new Object[] { Integer.valueOf( 0xC000), lpszFormatName, lpszFormatName.size() }));
//					//
//					System.out.println(lpszFormatName.getWideString(0));
//					//
//					System.out.println("GetLastError2="+Function.getFunction("kernel32", "GetLastError").invokeObject(new Object[] {}));
//					//
//					System.out.println("CloseClipboard="
//							+ Function.getFunction("user32", "CloseClipboard").invokeInt(new Object[] { hwnd }));
//					//
//					System.out.println();
//					//
//				} // if
//					//
//			} // if
//				//
//		} // for
		//
		//
	}

}
