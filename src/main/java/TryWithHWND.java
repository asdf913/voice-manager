import java.util.Objects;

import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import com.sun.jna.win32.StdCallLibrary;

public class TryWithHWND {
   public interface User32 extends StdCallLibrary {
      User32 INSTANCE = (User32) Native.load("user32", User32.class);
      boolean EnumWindows(WinUser.WNDENUMPROC lpEnumFunc, Pointer arg);
      int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);

		HWND GetClipboardOwner();
   }

   public static void main(String[] args) {
      final User32 user32 = User32.INSTANCE;
      final HWND clipboardOwner = user32.GetClipboardOwner();
      System.out.println(clipboardOwner.getPointer());
      user32.EnumWindows(new WNDENUMPROC() {
         int count = 0;
         @Override
         public boolean callback(HWND hWnd, Pointer arg1) {
            byte[] windowText = new byte[512];
            user32.GetWindowTextA(hWnd, windowText, 512);
            String wText = Native.toString(windowText);

            // get rid of this if block if you want all windows regardless of whether
            // or not they have text
            if (wText.isEmpty()) {
               return true;
            }
//            if(Objects.equals(clipboardOwner.getPointer(),hWnd.getPointer())) {
            System.out.println("Found window with text " + hWnd + ", total " + ++count
                  + " Text: " + wText);
//            }
            return true;
         }
      }, null);
   }
}