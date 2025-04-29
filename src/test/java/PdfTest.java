import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;

public class PdfTest {

	public static void main(final String[] args)
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, IOException {
		//
		// org.springframework.context.support.VoiceManagerPdfPanel.pdf(Path)
		//
		final Method method = org.springframework.context.support.VoiceManagerPdfPanel.class.getDeclaredMethod("pdf",
				Path.class);
		//
		if (method != null) {
			//
			method.setAccessible(true);
			//
			final Object object = method != null ? method.invoke(null, new File("test20250220.html").toPath()) : null;
			//
			final byte[] bs = object instanceof byte[] ? (byte[]) object : null;
			//
			final File file = new File("result20250220.pdf");
			//
			System.out.println(file.getAbsolutePath());
			//
			FileUtils.writeByteArrayToFile(file, bs);
			//
		} // if
			//
	}

}