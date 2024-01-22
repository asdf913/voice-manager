package org.springframework.beans.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Collections;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.function.Predicate;

import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.nutch.util.DeflateUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.WorkbookUtil;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;

import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBeanTest {

	private static final String EMPTY = "";

	private static Method METHOD_CREATE_MULTI_MAP, METHOD_CREATE_MULTI_MAP2, METHOD_TO_URL, METHOD_TEST_AND_APPLY,
			METHOD_REMOVE, METHOD_TO_MULTI_MAP_MAP, METHOD_TO_MULTI_MAP_ITERABLE = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.class;
		//
		(METHOD_CREATE_MULTI_MAP = clz.getDeclaredMethod("createMultimap", Iterable.class, Multimap.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP2 = clz.getDeclaredMethod("createMultimap2", Iterable.class)).setAccessible(true);
		//
		(METHOD_TO_URL = clz.getDeclaredMethod("toURL", URI.class)).setAccessible(true);
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_REMOVE = clz.getDeclaredMethod("remove", Multimap.class, Object.class, Object.class))
				.setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_MAP = clz.getDeclaredMethod("toMultimap", Map.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP_ITERABLE = clz.getDeclaredMethod("toMultimap", Iterable.class)).setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		private Boolean remove = null;

		private Set<Entry<?, ?>> entrySet = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Multimap) {
				//
				if (Objects.equals(methodName, "remove")) {
					//
					return remove;
					//
				} // if
					//
			} else if (proxy instanceof Link) {
				//
				if (Objects.equals(methodName, "getText")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "getUrl")) {
					//
					return null;
					//
				} else if (Objects.equals(methodName, "getDescription")) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Map) {
				//
				if (Objects.equals(methodName, "entrySet")) {
					//
					return entrySet;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean();
		//
		ih = new IH();
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setText(null);
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(null));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final Link link = Reflection.newProxy(Link.class, ih);
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(link));
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.nCopies(2, link));
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		if (instance != null) {
			//
			instance.setLinks(Collections.singleton(link));
			//
		} // if
			//
		FieldUtils.writeDeclaredField(instance, "text", null, true);
		//
		Assertions.assertNull(getObject(instance));
		//
		if (instance != null) {
			//
			instance.setDescription(null);
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setLinks(null);
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.url")));
				//
				final Properties p = createProperties(
						OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBeanTest.class,
						"/configuration.properties");
				//
				if (p != null && Util.containsKey(p,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.toBeRemoved")) {
					//
					instance.setToBeRemoved(p.getProperty(
							"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.toBeRemoved"));
					//
				} else {
					//
					instance.setToBeRemoved(
							"[{\"後藤艮山\":\"もあり\"},{\"菅原ミネ嗣\":[\"はくさ\",\"に\"]},{\"本間ソウ軒\":[\"は\",\"を\",\"つ\",\"ねた\",\"です\"]}]");
					//
				} // if
					//
			} // if
				//
			final Multimap<String, String> multimap = getObject(instance);
			//
			final Iterable<Entry<String, String>> entries = MultimapUtil.entries(multimap);
			//
			final File file = new File("OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimap.xlsx");
			//
			try (final Workbook wb = WorkbookFactory.create(true); final OutputStream os = new FileOutputStream(file)) {
				//
				final Sheet sheet = WorkbookUtil.createSheet(wb);
				//
				if (Util.iterator(entries) != null) {
					//
					Row row = null;
					//
					for (final Entry<String, String> entry : entries) {
						//
						if (entry == null) {
							//
							continue;
						} // if
							//
						if (sheet != null && sheet.getPhysicalNumberOfRows() == 0) {
							//
							CellUtil.setCellValue(createCell(row = createRow(sheet)), "kanji");
							//
							CellUtil.setCellValue(createCell(row), "hiragana");
							//
						} // if
							//
						CellUtil.setCellValue(createCell(row = createRow(sheet)), Util.getKey(entry));
						//
						CellUtil.setCellValue(createCell(row), Util.getValue(entry));
						//
					} // for
						//
				} // if
					//
				WorkbookUtil.write(wb, os);
				//
			} // try
				//
			System.out.println(file.getAbsolutePath());
			//
		} // if
			//
		if (instance != null) {
			//
			instance.setResource(new ClassPathResource("/OtoYakuNoHeyaYomikataJiten.xlsx"));
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		if (instance != null) {
			//
			instance.setSheetName(null);
			//
		} // if
			//
		Assertions.assertNull(getObject(instance));
		//
		final Decoder decoder = Base64.getDecoder();
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(DeflateUtils.inflate(decode(decoder,
					"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"))));
			//
		} // if
			//
		Assertions.assertEquals("{a=[b]}", Util.toString(getObject(instance)));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(DeflateUtils.inflate(decode(decoder,
					"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"))));
			//
		} // if
			//
		Assertions.assertEquals("{a=[b]}", Util.toString(getObject(instance)));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(DeflateUtils.inflate(decode(decoder,
					"eJyFWHVYVOu3ZsCBoTsEJaRr6G4QpKW7YWhmYOiWUqmhQVpaUECRTkG6Do2EdMkhBEkR+MHx3ucC91zv3s+3/9j7WWvPetf7vevdo6aEco8ICQQCIcFB/HpINw7cq+XlyOEJgztYwGAOYC8nx2jNHugMJ17TMeu9OYjCnD1FMoouIhbpK15RT2q8HIlDBFH3XnLu5Rw+pIB5WgaXXwhzvjHV22Auw+Hbw1a9HO3+XKluUbU6cOh0x9aM5eZfr2zi3gYrlVJbk77fsj6Eeo9YZ9s1HW80VXC9xUuXxfvSGJw2EwSVVM8fK1yTL5wCVnN9wjQEmLGHmJOphqRhdgiHhp1SYRknP3oRPYjLx7QliSWPaJ/Tyf3gWMUWFlpURhMjMGBjQrgd2pcBfKs7bRqdsucXaKvRi9DvKQDaV44UcUx5dscJL2lwly+AhkUfHDMHStrmK7PIDE7s0S1YBXYZjtLM2dGaVEqDa+qGNTqxBhYNol/mdl8ST1WH+Kykbmeu2+Rg+EfcL93YXwuAT9qom8liZiCsl+c80Sx0zfuth/26RVemZETevFKR9tHBsBwmdmHKEBZCHndgt2fyGo2b0Q/06cUea4oyKHGbI9Wa+naP2Y1IxyqeLD3s9bM6DjmcDy9yPFzTWiQ5E7M3Qh5hqPf1SJ4qkN77wFDp8MtdSNcXhUAo7kGo+Vhb230JA7d1bQWN0XMOPoW4+xsHy/ntjSH4XGd7AcIZDB+/DVGV5cVRydYQXn62Fd0bneEtaKVPt8q6oNMXGgOexyOaZWXmu1ESZzBLj2YVHLeZgInj5yLWUuUvxznUlNBA/TOsWscAJCQoChKS2v/JGezfnHF183aEuF4zZlvvq+oMJ1nTvKZeM5WCEjNngVRUsa0AIbnsNO92G52qxWJ2t0fO4rdjIdXRUOQZYTuHIpMp8SMhcaG9ovrwQZLgPN8SbE14MBoajZwWAwuEO3shfepgG12ukyCVoIwucyGC3I+kJTyqVLAJzFQFIspPXWib3jfGe5X/OvgVirYGOk/qSLAe2aYoZ0EhHR+CzosT67MLNShBELFipBzGx6Tuatcqh0kz76cMeBDDuQ8nZQGypmhpqAjiLDHZ3lti0oQXuIaK4T1Zw/9KiPMoxxAc/jrglzsCnMSut7QQBepkleByk9TAc1pAF6ua6+5rAvuUpdaME2N5pl74kLsvV9hk1EzhfkBbekM3ocFexCmpEqiT9B2ZPoOJQ7P8rGfTKMGeU3QTbxP1qElbOIxwVkyMUpqZti4+dD6RMAMfygTNwd2UArcKLvF+pH6nb1I7UcLt/GiCBZBJ3+W0nTSD45itNlhuLOTXo/OUKnjjkaFEcwWlZJLQrsokKx+KRnJjGgujxWEG/6czHwVc4XW2lGBxfa+CpILw0rd1z5dfspHmStsdgUZ2gGsqh7VLNWwusYEmUWzGX/7qJShHzqLkZbZBqqbhTp2g+JzBzJvgycMH2eZt16rmx9PteaiSwoCZjFz1kVvP6mk8hq4QNv9A8d4p7xvwrIq9N5ZHV78zbA3IQzE5Mdf/3yeDtdyLmQMlIXLHpPRhnbr70IT00UBHhg2T+XNIs2uMxOwBeUv3RKbn+a+cQ+OG0cuCLxflEtJNQ3ItpNPluj2DNiS8fDOnjHMTu2SeaivNcOXvjMfY4Z4WDZk80NSDPLNM55xQ6r7DkDzKxe+yyD9JAmAKy5R2v9jsyviSFMbhCN+MYYeMORNGsbk910bWEkIvNPbVyid783o65m1mh3UcF4BrpvecsEuUIyMh8RD9ienkv5luCoc4ut7SSPD1nVINKfvPnHiN68QWu1Vb+aEabOsEGHWF31BCG6uVHRVsBZz2z0pUbB+ActCy2/vtbZ6b9427bHQwym02aqpZCYHcOiczHYj9tuayTMy0B3rZj9JGSSuMqeM8HjC/71/UVuQmQVNJxd0Yyd9zGcowc4QMWZcbJv4QNe0VnHi2KafpaA900SSl9ih6djl8pDJ638jXlWxo7yFmjx1pwmsEe0WwqRdDJFt4UGO2uTgEdUkzxg3bpTNmi1RzOvC0ljGye/JeoBbG8Q/RrSXvEfA7hF6Lq3IXX5mVsmlbv6K3K3xc4Bqn5iHh+wtXKBQj/wknwt84udlCnCC/r1zXKH1NMobNUBM93z1mxTtmdZl9rEBUlJYs2/CmMtKtSaL1SyHAEeCUEiiBpbpUXjzKa7fpR4gjutsX5GpRvhsgH2Tyir9RCx0BZSDjku1LfCvaOk/lQUVKdh+TBajOkrxAyHMC/WGOzWrI2RObwPox+B7phA6rNJb7qBm0N4K5JjnJINg9Z1ChZHF8SlGqMKEnQ34BQ8DnXOQnd58gZCZeXKnQkXNY+Zl7cXqwLiotcGT1ZCBkI0cVDzZYHID/biLVJxijqUj88nXtOtpAcD/miKXWJVkmlThmtIkkndcSlTK/AxwQ+hCeGkheBhhZPmBvsftOAReTwEUq2GH63JQHbE+oNTdUI0Os4P2VMrRqeLH37IWgtdjlSe88zklYnu9FjW4A/8I3uAKH50hwm7N429O8l+5euMTBb5c3oj/y+EtVaPg97LD5CCJ5gdqikT8uLlXuWJRq4E+3WDwmgnayVK+q3Z3Fkgxwn2UckDLTclGKfCgPVgXLRtkCpjn/jtCHYbOisg8ki7Uu80yHcMNYi0956L/Rg+t8iopqGVSnw/YhCdvPMD/UMLbuREvkMwOfekJH0CgbKmW1a2mOEClJeO6PL2eDsGLotQSky9Gzuj7+3HwNh5HrGgokaqeZ5cGjJFd28iRLjwaM7CxrzCJig+eZteThfW4/AjR2R1MPOF5A7ucdkfJMDpUCE4bHW3ATPvTjnO5cbs1ftMqvw5RQqY5+9K8xXnMMJJcxTnvFr0nQnzhG9F9Tx9YcDrHSdIPbQW3+GT7RQ60YbZxYqAsNT9Xhk6pS+HYNIQuEGyhj4PqltMFCip9HOlKNPQiJ03iq3M0nG+0vw/3psPtkgcg9HZ2j2p+/eb2CSTlXj30BE9h7PZJxso4QiqxQfJczUDev0FPWYeaN75vPXddO5AjmMqY/Rnf6zG6Bk2mkPS6H+jd6aCQKBNsBD+X07NkZjD+iST074uHxRNIaPIQrGrRWaMWv2NxBxaeq4REv/FRjp8jqNMb7H/GZLUrCLL8qxxjwp4Lxr5YVzFINDnN25bCEwSH/VKth/DSEk8j/WDe2WQazv4PvEwGfLydYfymqs8ZgR48xsXC7+aIJiJpejza/U7d+MFvOMCqj7SMyJDfGLWsuGYsWP1kXKxthksJziePdyZ36sSI5NpeWjHcASlDGVM7H5lYqaQjodJUbUSvTqKzWUY/CNpXpUF4gGol7a6DbL/wG8x3ZpQVw4WUsV9mnigz95xQyptiIDavUH60aTu5T5CsPZNCJ5R2cyUJP3lW9ZnU9lOozYmI1j8EoU/SFq1FFkYWenYt/6SodO6RaOj79oSRSwmH1kQWHSUk1Wq0sSL9bzEayAS72g0RCFGzDFCnPu6+w8WQ4XnljIzm+50lwYBMMq6F2Qv9zVlCkK6cRJuQrtsj+k7bF+B6Lv0mynF5dVtrSy5QIQXqiMhvyyu+tHD9Zd6Gf9NTKhF9SMNMltvNSmkaNUVQm8W09/nFcoW7o9kxl0vqsDgBtiENdsGB1RDeux7381bVFcd0kLCNSD+urBs38UfnwbjbJ3Nn5ukeZccawtt+yR15hqFSsKCLDpZRszD4FFq5pQF3U0u99guH6c/dFWvEkLeOK36o3Llr1bDrNTCtZ5UfsR95B3O9HmJ0EsH1g2Yx8vdJEOuGt+rZDAGSDfZEjfmFcyzG8isfvc6RmyM6Rl6lrzB53a04VKu7K0VpgdGF7+JfTiwqdOn2SMyIBdryPMSN+E96CNJxa+6XjUjopoeu0rUTspH35HXAZDT+Ix8/dkLRjyB6lUf6b0ifKJqpKIi6n0JaNQ1A5I7rRKm/26sqFjOFqgPSx/ihisCI9GSy2hGnVLkkZr3JWnxzRa2EyCCz+6Z4ExShBZSlUw5AiayYWYSkfNG3tEeUYEeL7tUrWDM28RL5G06XbdhnvCs36P1Ke8Bbl3V3dYE7/ADr44WqLE4Utfi0CTc1oocWNVEYKimgdx85VA1xh3SqR/r60wTuIF1TiqqNi5zLv8Kbw7es4WJmR2LFxGNlh5wWCCq09C2aUoa9XUdkaPCaNaIEvewCRbNQOQkVE9gVfBhewU4QswbSyMI0sFw1exGBb6tYSIEFYa1x9Kozw9JGf0OneJt+5Eo7V/O7JHIzG1ykvwGaG9LquxQmkZylXv3oP6U91YV6t3ybit3GIb1X9zIn1fNc3guWY76skKKewQVm+gYaX8hMdlKIqPK89ZP/oMSEBVnTFZ4RLQOCFRcGcMpRtjpNBySRcWz8cuYnB0r+ove/CxZuDfyA3oBNf8IkGTIYFHvNU8aB/Uao339ctVhFj44eK0jBg3XLLCklM8gXcWNuT+8Lq5Vqo/lYUgYuNTu6XwHuNoSpODnxQBaoHPuI8S7qPEmg8uhWV8/yKBBsJsCHPMbznIP51ffO7iTj3sTjlVfBTaNZ2uklGaxA054l93169IqyvW4M7WxygDjQShHLauPmq0PrLK+9kEz/kMVWMNMj61bdGeA1TWBpv2s4VCIP/r40wlIFB3SBQN1Mtb2eIq/F1+0u1+1SvfdYxa66R+lIyijSTE64oQi0cLtc5R1e4J1NtON1ikzkUkctboAj+sPQ1dWqqKsGxOUm7EvB3qM6oVzxiGUQ0avTGgryFPTt+xkucAtKXCSA1L4OP1h7MzeIv6aHH12tRqJH1VCWNLb9lf5njKItJPbm8h21u4K0mvhdEmwXBjR11X83rYocK1INi8s4YOORVLO2V6ckOyRhoFA1cSFgmAbNMF0X1K0UVRHHuCNYwF90i4iiWCxDofkbYuUd7UZCH/EPJfszqGQyc4WCDS2V6hlgn2eZKY3/bJsWG9OElhfU6s098hkqh+YN6r2eTkEx07JoK4zGqPL2l50+NgVDbw7SDqH1fsuW4S7eF1iy8I69nOd6qPhTldoMg1Bh3hwfFKRclXdvi37PT3RXYv+vC9tJwxffnZ43dqQPMq1xOgt47e+OfBaG5WcSC3pF+tdl+/XKsyVimdv/BScG043ZjctMqRVpghICA44IhX6/8ueErf5TrzpVzJ3p7X21aWdTrzgGQiZD+p3c3Pxdxke4ed/5WuBt804Fj3wrkRb7zfXk39KYpJb8VSgn8k2G/m+em8SC8lUcO9V8N7d0ENwc50a0EJaB/dyt3M9ycMvi3MqSj/8v4vxt+U1bxboVLYv7vwXQ3+qZ43a4fgfWvQnw3wc1tjXkrgTX2LcW7G3iTVbffHI3zrxqgpgREvX6McXX6Xr3tyT/V/geGE7on"))));
			//
		} // if
			//
		Assertions.assertThrows(IllegalStateException.class, () -> getObject(instance));
		//
		if (instance != null) {
			//
			instance.setResource(new ByteArrayResource(DeflateUtils.inflate(decode(decoder,
					"eJztWF1sFFUUPnd2t8xuS7strT9F6tDoUujWUHmwxmAXCiIJ2FpqFEOi02Xarmy7ZFmiJhqXIg8mkmgw8YXEkPTFaFRiog+a2L75oMGYkCBPxTeiD4PBGBLp+p0zM3S6ZXW2Gn9wz+TMvf3m3jln7znnu3f6zbnm+TNn2y9RmTxEIVooRanOhymo7v0RJ9JcbKFUKnlwqSb/KbnuthzDMOIXgXLMV7lxjbptTW5NGaYcrgIZtJOm0ObpxXIq+F25DRnjf1+QOQsBxwWVmv2V22f+Zh4PQcvrn7me6z8GrYc2QFdDG6FNJFsANUNboGugrdA24pwguh16h2ur3W1Z16Hf4f5tUI1b/mmJ6ohiXYQ+W/0Vh1xifwkZ8VF4TvLie+gBOsy5MWBm08bfJdvFB1OxD7NI0q3oKToNtJE+FPRzuW+V7IOkyOhzM/qqSonvJ+XeKfdG4vmfypyLgvTiyZdcPa+84RZBVO2no2DCXcKIE5ShNEa1+0dxhahtYMkMmZT9U087wg00w3W2y5qy8mZ2Xupnhn6WuvBk1mCcpwt+NRiuVYnT/w6fwdoZtCjeenY74+1yfGMFPFkB31QBjy7DT2morSKVuI0XQ9I2F+ukbSmGpV1TXCVtazFSelZy+AQyej8TNip234RlFXpfR40+jmtO6VCikZOKfqCnmantYbFVZzMjcyl7J3e/bqF6m2vpC/C+TscUmF+fRSWRnqKOK8z14H4de4AObtd/Af2n9TZK3/ggcLifReMNwyGW+BJiaYDhg3CIS7FZnIjD1K/vXvl27+hQ/zOCFxc/MeheTX6hOoYnc+FNMqNb7tMytpXuofVEiYFE50hm0jpiPGY9bwznJs2p5LA1fjRr5jsTvfcntrUhfIk/HDdkjltGYui4GHhV7gk4s0Xkcv8GX78L/eM9F6Z7Ltj9G339M+CUKH6ikmuakiqpvDleq+gJvFunl2U/JfVafZO7bI20VH6kIV4s+1FnoWzdjRRHsIlC9mbiNmx3CR6147RcNIrJPJ7De/vDWgt9Ih91Kd+odfw+p+syFY0jzOO6/0WK9wvuxnwzFXbwUMw5g8R1TbBZ59Eja3Hbm0nnc0dyYwVj5wtpK2s8+EDPiDlqZbOWZMT2zNhY303cDizKSeYVy0KJ37H8FbwM8yfe+ena4ET8vTd16t7w8Xe83i+Rk8r8nB3n81GKnDXZQ8456SlyzkoHyTkvHSbHycvXnbMP9zfT0ggs67+1/uy5PReVH6/Gf0a081+fP33f2vipt+F/8toHO4BFyrAD5JzTPBaI+3ythNfk1pG/8vtP8qSMA242h5km5VLLk7Cep0M0Kn4cqtr/FmQlW+SNwuOnINLn/mNL0QDsTqJGB+HDcyuyH6LFb7Agc+6E7ibP/iDOglbVdj2Jwb7/PB9kDvNyq9uP0D6ceSdxmRL73ciCMYkJIwWcVXNAKkuXa5/3lqD274K+f8P+DlhIiw+WZGB1/vSt4PffDS0EGRhQqrX/b/r+/w3g9SeV"))));
			//
		} // if
			//
		Assertions.assertNull(Util.toString(getObject(instance)));
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.sheetName")) {
			//
			if (instance != null) {
				//
				instance.setSheetName(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenNipponIkaJinmeiJitenMultimapFactoryBean.sheetName")));
				//
			} // if
				//
			Assertions.assertNull(getObject(instance));
			//
		} // if
			//
	}

	private static byte[] decode(final Decoder instance, final String src) {
		return instance != null ? instance.decode(src) : null;
	}

	private static Row createRow(final Sheet instance) {
		return instance != null ? instance.createRow(instance.getPhysicalNumberOfRows()) : null;
	}

	private static Cell createCell(final Row instance) {
		return instance != null ? instance.createCell(instance.getPhysicalNumberOfCells()) : null;
	}

	private static Properties createProperties(final Class<?> clz, final String url) throws IOException {
		//
		Properties p = null;
		//
		try (final InputStream is = clz != null ? clz.getResourceAsStream(url) : null) {
			//
			if ((p = is != null ? new Properties() : null) != null) {
				//
				p.load(is);
				//
			} // if
				//
		} // try
			//
		return p;
		//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, instance != null ? instance.getObjectType() : null);
		//
	}

	@Test
	void testSetToBeRemoved() {
		//
		Assertions.assertDoesNotThrow(() -> {
			//
			if (instance == null) {
				//
				return;
				//
			} // if
				//
			instance.setToBeRemoved(null);
			//
			instance.setToBeRemoved(EMPTY);
			//
			instance.setToBeRemoved(" ");
			//
		});
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			if (instance != null) {
				//
				instance.setToBeRemoved("[{\"\":{}}]");
				//
			} // if
				//
		});
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			if (instance != null) {
				//
				instance.setToBeRemoved("[1]");
				//
			} // if
				//
		});
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			if (instance != null) {
				//
				instance.setToBeRemoved("[{\"\":[{}]}]");
				//
			} // if
				//
		});
		//
		Assertions.assertThrows(IllegalStateException.class, () -> {
			//
			if (instance != null) {
				//
				instance.setToBeRemoved("1");
				//
			} // if
				//
		});
	}

	@Test
	void testCreateMultimap1() throws Throwable {
		//
		Assertions.assertNull(createMultimap(
				Arrays.asList(null, Util.cast(Element.class, Narcissus.allocateInstance(Element.class))), null));
		//
	}

	private static Multimap<String, String> createMultimap(final Iterable<Element> es,
			final Multimap<String, String> toBeRemoved) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP.invoke(null, es, toBeRemoved);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMultimap2() throws Throwable {
		//
		Assertions.assertNull(createMultimap2(Collections.singleton(null)));
		//
	}

	private static Multimap<String, String> createMultimap2(final Iterable<Element> es) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP2.invoke(null, es);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToURL() throws Throwable {
		//
		Assertions.assertNull(toURL(null));
		//
		final URI uri = Util.cast(URI.class, Narcissus.allocateInstance(URI.class));
		//
		Assertions.assertNull(toURL(uri));
		//
		Narcissus.setField(uri, URI.class.getDeclaredField("scheme"), EMPTY);
		//
		Assertions.assertThrows(MalformedURLException.class, () -> toURL(uri));
		//
	}

	private static URL toURL(final URI instance) throws Throwable {
		try {
			final Object obj = METHOD_TO_URL.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof URL) {
				return (URL) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testTestAndApply() throws Throwable {
		//
		Assertions.assertNull(testAndApply(null, null, null, null));
		//
		Assertions.assertNull(testAndApply(Predicates.alwaysTrue(), null, null, null));
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse)
			throws Throwable {
		try {
			return (R) METHOD_TEST_AND_APPLY.invoke(null, predicate, value, functionTrue, functionFalse);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testRemove() {
		//
		Assertions.assertDoesNotThrow(() -> remove(null, null, null));
		//
		Assertions.assertDoesNotThrow(() -> remove(LinkedHashMultimap.create(), null, null));
		//
		final IH ih = new IH();
		//
		ih.remove = Boolean.TRUE;
		//
		Assertions.assertDoesNotThrow(() -> remove(Reflection.newProxy(Multimap.class, ih), null, null));
		//
	}

	private static void remove(final Multimap<?, ?> instance, final Object key, final Object value) throws Throwable {
		try {
			METHOD_REMOVE.invoke(null, instance, key, value);
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimap() throws Throwable {
		//
		Assertions.assertNull(toMultimap((Map<?, ?>) null));
		//
		Assertions.assertNull(toMultimap((Iterable<?>) null));
		//
		Assertions.assertEquals("{=[]}", Util.toString(toMultimap(Collections.singletonMap(EMPTY, EMPTY))));
		//
		Assertions.assertEquals("{=[]}",
				Util.toString(toMultimap(Collections.singletonMap(EMPTY, Collections.singleton(EMPTY)))));
		//
		Assertions.assertThrows(IllegalStateException.class,
				() -> toMultimap(Collections.singletonMap(EMPTY, Collections.singleton(Collections.emptyMap()))));
		//
		Assertions.assertThrows(IllegalStateException.class,
				() -> toMultimap(Collections.singletonMap(EMPTY, Collections.emptyMap())));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertNull(toMultimap(Reflection.newProxy(Map.class, ih)));
		//
		Assertions.assertEquals("{=[]}",
				Util.toString(toMultimap(Collections.singleton(Collections.singletonMap(EMPTY, EMPTY)))));
		//
		Assertions.assertEquals("{=[]}", Util.toString(
				toMultimap(Collections.singleton(Collections.singletonMap(EMPTY, Collections.singleton(EMPTY))))));
		//
		if (ih != null) {
			//
			ih.entrySet = Collections.singleton(null);
			//
		} // if
			//
		Assertions.assertEquals(ImmutableMultimap.of(),
				toMultimap(Collections.singleton(Reflection.newProxy(Map.class, ih))));
		//
	}

	private static Multimap<String, String> toMultimap(final Iterable<?> iterable) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_ITERABLE.invoke(null, iterable);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	private static Multimap<String, String> toMultimap(final Map<?, ?> m) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP_MAP.invoke(null, m);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(Util.toString(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}