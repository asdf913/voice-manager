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
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.TableUtil;

class OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_LENGTH, METHOD_GET_UNICODE_BLOCKS, METHOD_TO_MULTI_MAP1,
			METHOD_TO_MULTI_MAP2 = null;

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
		(METHOD_TO_MULTI_MAP1 = clz.getDeclaredMethod("toMultimap1", String.class, String.class)).setAccessible(true);
		//
		(METHOD_TO_MULTI_MAP2 = clz.getDeclaredMethod("toMultimap2", String.class, String.class)).setAccessible(true);
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
	void testToMultimap1() throws Throwable {
		//
		Assertions.assertNull(toMultimap1(null, null));
		//
		final Table<String, String, String> table = HashBasedTable
				.create(ImmutableTable.of("藻岩山観光自動車道", "もいわやまかんこうじどうしゃどう", "{藻岩山観光自動車道=[もいわやまかんこうじどうしゃどう]}"));
		//
		TableUtil.put(table, "みちのく有料道路", "みちのくゆうりょうどうろ", "{有料道路=[ゆうりょうどうろ]}");
		//
		TableUtil.put(table, "第二みちのく有料道路", "だいにみちのくゆうりょうどうろ", "{第二=[だいに], 有料道路=[ゆうりょうどうろ]}");
		//
		TableUtil.put(table, "津軽岩木スカイライン", "つがるいわきすかいらいん", "{津軽岩木=[つがるいわき]}");
		//
		TableUtil.put(table, "三陸自動車道 （仙台松島道路）", "さんりくじどうしゃどう （せんだいまつしまどうろ）",
				"{三陸自動車道=[さんりくじどうしゃどう], 仙台松島道路=[せんだいまつしまどうろ]}");
		//
		TableUtil.put(table, "嵐山-高雄パークウェイ", "あらしやま-たかおぱーくうぇい", "{嵐山=[あらしやま], 高雄=[たかお]}");
		//
		TableUtil.put(table, "六甲有料道路 （表六甲ドライブウェイ）", "ろっこうゆうりょうどうろ （おもてろっこうどらいぶうぇい）",
				"{六甲有料道路=[ろっこうゆうりょうどうろ], 表六甲=[おもてろっこう]}");
		//
		TableUtil.put(table, "日塩道路（もみじライン）", "にちえんどうろ", "{日塩道路=[にちえんどうろ]}");
		//
		final Iterable<Cell<String, String, String>> cellSet = TableUtil.cellSet(table);
		//
		if (cellSet != null && cellSet.iterator() != null) {
			//
			for (final Cell<String, String, String> cell : cellSet) {
				//
				if (cell == null) {
					//
					continue;
					//
				} // if
					//
				Assertions.assertEquals(cell.getValue(),
						Util.toString(toMultimap1(cell.getRowKey(), cell.getColumnKey())), Util.toString(cell));
				//
			} // for
				//
		} // if
			//
	}

	private static Multimap<String, String> toMultimap1(final String s1, final String s2) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP1.invoke(null, s1, s2);
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

	@Test
	void testToMultimap2() throws Throwable {
		//
		final Table<String, String, String> table = HashBasedTable.create(ImmutableTable.of("万座ハイウェイ （万座温泉〜三原）",
				"まんざはいうぇい （まんざおんせん〜みはら）", "{万座=[まんざ], 万座温泉=[まんざおんせん], 三原=[みはら]}"));
		//
		TableUtil.put(table, "パールロード （奥志摩ライン）", "ぱーるろーど （おくしまらいん）", "{奥志摩=[おくしま]}");
		//
		TableUtil.put(table, "那須高原道路 （ボルケーノハイウェイ）", "なすこうげんどうろ", "{那須高原道路=[なすこうげんどうろ]}");
		//
		TableUtil.put(table, "西吾妻スカイバレー", "にしあづますかいばれー", "{西吾妻=[にしあづま]}");
		//
		final Iterable<Cell<String, String, String>> cellSet = TableUtil.cellSet(table);
		//
		if (cellSet != null && cellSet.iterator() != null) {
			//
			for (final Cell<String, String, String> cell : cellSet) {
				//
				if (cell == null) {
					//
					continue;
					//
				} // if
					//
				Assertions.assertEquals(cell.getValue(),
						Util.toString(toMultimap2(cell.getRowKey(), cell.getColumnKey())), Util.toString(cell));
				//
			} // for
				//
		} // if
			//
	}

	private static Multimap<String, String> toMultimap2(final String s1, final String s2) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP2.invoke(null, s1, s2);
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