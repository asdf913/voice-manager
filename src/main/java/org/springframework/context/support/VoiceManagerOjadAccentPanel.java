package org.springframework.context.support;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserTypeUtil;
import com.microsoft.playwright.ElementHandle;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PageUtil;
import com.microsoft.playwright.Playwright;

public class VoiceManagerOjadAccentPanel {

	public static void main(final String[] args) throws IOException {
		//
		final Page page = newPage(BrowserTypeUtil.launch(chromium(Playwright.create())));
		//
		PageUtil.navigate(page, "https://www.gavo.t.u-tokyo.ac.jp/ojad/search/index/word:%E6%97%A5%E6%9C%AC");
		//
		final List<ElementHandle> ehs = querySelectorAll(page, ".katsuyo_accent");
		//
		byte[] bs = null;
		//
		File file = null;
		//
		ContentInfoUtil cic = null;
		//
		String[] fileExtensions = null;
		//
		for (int i = 0; i < IterableUtils.size(ehs); i++) {
			//
			FileUtils
					.writeByteArrayToFile(
							file = Util.toFile(Paths.get(StringUtils.joinWith(".", Integer.toString(i),
									StringUtils.defaultIfBlank((fileExtensions = getFileExtensions(
											findMatch(cic = ObjectUtils.getIfNull(cic, ContentInfoUtil::new),
													bs = screenshot(IterableUtils.get(ehs, i))))) != null
											&& fileExtensions.length == 1 ? fileExtensions[0] : null, "png")))),
							bs);
			//
			System.out.println(file.getAbsolutePath());
			//
		} // for
			//
	}

	private static String[] getFileExtensions(final ContentInfo instance) {
		return instance != null ? instance.getFileExtensions() : null;
	}

	private static ContentInfo findMatch(final ContentInfoUtil instance, final byte[] bytes) {
		return instance != null && bytes != null ? instance.findMatch(bytes) : null;
	}

	private static BrowserType chromium(final Playwright instance) {
		return instance != null ? instance.chromium() : null;
	}

	private static Page newPage(final Browser instance) {
		return instance != null ? instance.newPage() : null;
	}

	private static List<ElementHandle> querySelectorAll(final Page instance, final String selector) {
		return instance != null ? instance.querySelectorAll(selector) : null;
	}

	private static byte[] screenshot(final ElementHandle instance) {
		return instance != null ? instance.screenshot() : null;
	}

}