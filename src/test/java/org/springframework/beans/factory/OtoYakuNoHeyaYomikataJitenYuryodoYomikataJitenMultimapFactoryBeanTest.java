package org.springframework.beans.factory;

import java.io.File;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.base.Predicates;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;

class OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_LENGTH, METHOD_GET_UNICODE_BLOCKS, METHOD_TO_MULTI_MAP = null;

	@BeforeAll
	static void beforeAll() throws NoSuchMethodException {
		//
		final Class<?> clz = OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean.class;
		//
		(METHOD_TEST_AND_APPLY = clz.getDeclaredMethod("testAndApply", Predicate.class, Object.class,
				FailableFunction.class, FailableFunction.class)).setAccessible(true);
		//
		(METHOD_LENGTH = clz.getDeclaredMethod("length", Object[].class)).setAccessible(true);
		//
		(METHOD_GET_UNICODE_BLOCKS = clz.getDeclaredMethod("getUnicodeBlocks", String.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP = clz.getDeclaredMethod("toMultimap", String.class, String.class)).setAccessible(true);
		//
	}

	private OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean instance = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean();
		//
	}

	@Test
	void testGetObjectType() {
		//
		Assertions.assertEquals(Multimap.class, FactoryBeanUtil.getObjectType(instance));
		//
	}

	@Test
	void testGetObject() throws Exception {
		//
		Assertions.assertNull(getObject(instance));
		//
		final Map<Object, Object> properties = System.getProperties();
		//
		if (Util.containsKey(properties,
				"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean.url")) {
			//
			if (instance != null) {
				//
				instance.setUrl(Util.toString(Util.get(properties,
						"org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean.url")));
				//
			} // if
				//
			final File file = new File("OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean.txt");
			//
			FileUtils.writeLines(file, MultimapUtil.entries(getObject(instance)));
			//
			System.out.println(file.getAbsolutePath());
			//
		} // if
			//
	}

	private static <T> T getObject(final FactoryBean<T> instance) throws Exception {
		return instance != null ? instance.getObject() : null;
	}

	@Test
	void testTestAndApply() throws Throwable {
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
	void testLength() throws Throwable {
		//
		Assertions.assertEquals(0, length((Object[]) null));
		//
		final int size = 1;
		//
		Assertions.assertEquals(size, length(new Object[size]));
		//
	}

	private static int length(final Object[] instance) throws Throwable {
		try {
			final Object obj = METHOD_LENGTH.invoke(null, (Object) instance);
			if (obj instanceof Integer) {
				return (Integer) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testGetUnicodeBlocks() throws Throwable {
		//
		Assertions.assertNull(getUnicodeBlocks(null));
		//
		Assertions.assertEquals(Collections.singletonList(UnicodeBlock.BASIC_LATIN), getUnicodeBlocks("  "));
		//
	}

	private static List<UnicodeBlock> getUnicodeBlocks(final String string) throws Throwable {
		try {
			final Object obj = METHOD_GET_UNICODE_BLOCKS.invoke(null, string);
			if (obj == null) {
				return null;
			} else if (obj instanceof List) {
				return (List) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testToMultimap() throws Throwable {
		//
		Assertions.assertNull(toMultimap(null, null));
		//
		Assertions.assertEquals("{藻岩山観光自動車道=[もいわやまかんこうじどうしゃどう]}",
				Util.toString(toMultimap("藻岩山観光自動車道", "もいわやまかんこうじどうしゃどう")));
		//
		Assertions.assertEquals("{有料道路=[ゆうりょうどうろ]}", Util.toString(toMultimap("みちのく有料道路", "みちのくゆうりょうどうろ")));
		//
		Assertions.assertEquals("{第二=[だいに], 有料道路=[ゆうりょうどうろ]}",
				Util.toString(toMultimap("第二みちのく有料道路", "だいにみちのくゆうりょうどうろ")));
		//
		Assertions.assertEquals("{津軽岩木=[つがるいわき]}", Util.toString(toMultimap("津軽岩木スカイライン", "つがるいわきすかいらいん")));
		//
		Assertions.assertEquals("{三陸自動車道=[さんりくじどうしゃどう], 仙台松島道路=[せんだいまつしまどうろ]}",
				Util.toString(toMultimap("三陸自動車道 （仙台松島道路）", "さんりくじどうしゃどう （せんだいまつしまどうろ）")));
		//
		Assertions.assertEquals("{嵐山=[あらしやま], 高雄=[たかお]}", Util.toString(toMultimap("嵐山-高雄パークウェイ", "あらしやま-たかおぱーくうぇい")));
		//
		Assertions.assertEquals("{六甲有料道路=[ろっこうゆうりょうどうろ], 表六甲=[おもてろっこう]}",
				Util.toString(toMultimap("六甲有料道路 （表六甲ドライブウェイ）", "ろっこうゆうりょうどうろ （おもてろっこうどらいぶうぇい）")));
		//
		Assertions.assertEquals("{西吾妻=[にしあづま]}", Util.toString(toMultimap("西吾妻スカイバレー", "にしあづますかいばれー")));
		//
		Assertions.assertEquals("{日塩道路=[にちえんどうろ]}", Util.toString(toMultimap("日塩道路（もみじライン）", "にちえんどうろ")));
		//
		Assertions.assertEquals("{那須高原道路=[なすこうげんどうろ]}", Util.toString(toMultimap("那須高原道路 （ボルケーノハイウェイ）", "なすこうげんどうろ")));
		//
		Assertions.assertEquals("{万座=[まんざ], 万座温泉=[まんざおんせん], 三原=[みはら]}",
				Util.toString(toMultimap("万座ハイウェイ （万座温泉〜三原）", "まんざはいうぇい （まんざおんせん〜みはら）")));
		//
		Assertions.assertEquals("{奥志摩=[おくしま]}", Util.toString(toMultimap("パールロード （奥志摩ライン）", "ぱーるろーど （おくしまらいん）")));
		//
	}

	private static Multimap<String, String> toMultimap(final String s1, final String s2) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP.invoke(null, s1, s2);
			if (obj == null) {
				return null;
			} else if (obj instanceof Multimap) {
				return (Multimap) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

}