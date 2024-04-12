package org.springframework.beans.factory;

import java.io.File;
import java.lang.Character.UnicodeBlock;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.OptionalInt;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.OtoYakuNoHeyaYomikataJitenLinkListFactoryBean.Link;

import com.google.common.base.Predicates;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;
import com.google.common.collect.TableUtil;
import com.google.common.reflect.Reflection;

import io.github.toolfactory.narcissus.Narcissus;

class OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBeanTest {

	private static Method METHOD_TEST_AND_APPLY, METHOD_LENGTH, METHOD_GET_UNICODE_BLOCKS, METHOD_TO_MULTI_MAP1,
			METHOD_TO_MULTI_MAP2, METHOD_TO_MULTI_MAP3, METHOD_TO_ENTRY, METHOD_OR_ELSE, METHOD_MAX, METHOD_MIN,
			METHOD_MAP_TO_INT, METHOD_CREATE_MULTI_MAP1, METHOD_CREATE_MULTI_MAP2 = null;

	private static int ZERO = 0;

	private static int ONE = 1;

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
		(METHOD_TO_MULTI_MAP3 = clz.getDeclaredMethod("toMultimap3", String.class, String.class)).setAccessible(true);
		//
		(METHOD_TO_ENTRY = clz.getDeclaredMethod("toEntry", Integer.TYPE, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_OR_ELSE = clz.getDeclaredMethod("orElse", OptionalInt.class, Integer.TYPE)).setAccessible(true);
		//
		(METHOD_MAX = clz.getDeclaredMethod("max", IntStream.class)).setAccessible(true);
		//
		(METHOD_MIN = clz.getDeclaredMethod("min", IntStream.class)).setAccessible(true);
		//
		(METHOD_MAP_TO_INT = clz.getDeclaredMethod("mapToInt", Stream.class, ToIntFunction.class)).setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP1 = clz.getDeclaredMethod("createMultimap1", String.class, String.class))
				.setAccessible(true);
		//
		(METHOD_CREATE_MULTI_MAP2 = clz.getDeclaredMethod("createMultimap2", String.class, String.class))
				.setAccessible(true);
		//
	}

	private static class IH implements InvocationHandler {

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = method != null ? method.getName() : null;
			//
			if (proxy instanceof IntStream) {
				//
				if (Util.contains(Arrays.asList("max", "min"), methodName)) {
					//
					return null;
					//
				} // if
					//
			} else if (proxy instanceof Stream) {
				//
				if (Objects.equals(methodName, "mapToInt")) {
					//
					return null;
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
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	private OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean instance = null;

	private IH ih = null;

	@BeforeEach
	void beforeEach() {
		//
		instance = new OtoYakuNoHeyaYomikataJitenYuryodoYomikataJitenMultimapFactoryBean();
		//
		ih = new IH();
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
		if (instance != null) {
			//
			instance.setText(null);
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
		Assertions.assertNull(getObject(instance));
		//
		FieldUtils.writeDeclaredField(instance, "text", null, true);
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
		Assertions.assertEquals(ZERO, length((Object[]) null));
		//
		Assertions.assertEquals(ONE, length(new Object[ONE]));
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
		final Iterable<Cell<String, String, String>> cellSet = TableUtil
				.cellSet(ImmutableTable.of("第二みちのく有料道路", "だいにみちのくゆうりょうどうろ", "{第二=[だいに], 有料道路=[ゆうりょうどうろ]}"));
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

	@Test
	void testToMultimap3() throws Throwable {
		//
		final Table<String, String, String> table = HashBasedTable.create(ImmutableTable.of("六甲有料道路 （表六甲ドライブウェイ）",
				"ろっこうゆうりょうどうろ （おもてろっこうどらいぶうぇい）", "{六甲有料道路=[ろっこうゆうりょうどうろ], 表六甲=[おもてろっこう]}"));
		//
		TableUtil.put(table, "津軽岩木スカイライン", "つがるいわきすかいらいん", "{津軽岩木=[つがるいわき]}");
		//
		TableUtil.put(table, "三陸自動車道 （仙台松島道路）", "さんりくじどうしゃどう （せんだいまつしまどうろ）",
				"{三陸自動車道=[さんりくじどうしゃどう], 仙台松島道路=[せんだいまつしまどうろ]}");
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
						Util.toString(toMultimap3(cell.getRowKey(), cell.getColumnKey())), Util.toString(cell));
				//
			} // for
				//
		} // if
			//
	}

	private static Multimap<String, String> toMultimap3(final String s1, final String s2) throws Throwable {
		try {
			final Object obj = METHOD_TO_MULTI_MAP3.invoke(null, s1, s2);
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
	void testToEntry() throws Throwable {
		//
		Assertions.assertNull(toEntry(ZERO, ONE));
		//
		Assertions.assertEquals(Pair.of(Integer.valueOf(ONE), Integer.valueOf(2)), toEntry(ZERO, ZERO));
		//
		Assertions.assertEquals(Pair.of(Integer.valueOf(ZERO), Integer.valueOf(ONE)), toEntry(2, ZERO));
		//
	}

	private static Entry<Integer, Integer> toEntry(final int childrenSize, final int maxChildrenSize) throws Throwable {
		try {
			final Object obj = METHOD_TO_ENTRY.invoke(null, childrenSize, maxChildrenSize);
			if (obj == null) {
				return null;
			} else if (obj instanceof Entry) {
				return (Entry) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testOrElse() throws Throwable {
		//
		Assertions.assertEquals(ZERO, orElse(OptionalInt.of(ZERO), ONE));
		//
		Assertions.assertEquals(ONE,
				orElse(Util.cast(OptionalInt.class, Narcissus.allocateInstance(OptionalInt.class)), ONE));
		//
	}

	private static int orElse(final OptionalInt instance, final int other) throws Throwable {
		try {
			final Object obj = METHOD_OR_ELSE.invoke(null, instance, other);
			if (obj instanceof Integer) {
				return ((Integer) obj).intValue();
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMax() throws Throwable {
		//
		Assertions.assertNull(max(null));
		//
		Assertions.assertNull(max(Reflection.newProxy(IntStream.class, ih)));
		//
	}

	private static OptionalInt max(final IntStream instance) throws Throwable {
		try {
			final Object obj = METHOD_MAX.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof OptionalInt) {
				return (OptionalInt) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMin() throws Throwable {
		//
		Assertions.assertNull(min(null));
		//
		Assertions.assertNull(min(Reflection.newProxy(IntStream.class, ih)));
		//
	}

	private static OptionalInt min(final IntStream instance) throws Throwable {
		try {
			final Object obj = METHOD_MIN.invoke(null, instance);
			if (obj == null) {
				return null;
			} else if (obj instanceof OptionalInt) {
				return (OptionalInt) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testMapToInt() throws Throwable {
		//
		Stream<?> stream = Stream.of((Object) null);
		//
		Assertions.assertNull(mapToInt(stream, null));
		//
		Assertions.assertNotNull(mapToInt(stream, x -> 0));
		//
		Assertions.assertNull(mapToInt(stream = Reflection.newProxy(Stream.class, ih), null));
		//
	}

	private static <T> IntStream mapToInt(final Stream<T> instance, final ToIntFunction<? super T> mapper)
			throws Throwable {
		try {
			final Object obj = METHOD_MAP_TO_INT.invoke(null, instance, mapper);
			if (obj == null) {
				return null;
			} else if (obj instanceof IntStream) {
				return (IntStream) obj;
			}
			throw new Throwable(Util.getName(Util.getClass(obj)));
		} catch (final InvocationTargetException e) {
			throw e.getTargetException();
		}
	}

	@Test
	void testCreateMultimap1() throws Throwable {
		//
		Assertions.assertNull(Util.toString(createMultimap1(null, null)));
		//
		final Table<String, String, String> table = HashBasedTable
				.create(ImmutableTable.of("パールロード （奥志摩ライン）", "ぱーるろーど （おくしまらいん）", "{奥志摩=[おくしま]}"));
		//
		TableUtil.put(table, "那須高原道路 （ボルケーノハイウェイ）", "なすこうげんどうろ", "{那須高原道路=[なすこうげんどうろ]}");
		//
		TableUtil.put(table, "日塩道路（もみじライン）", "にちえんどうろ", "{日塩道路=[にちえんどうろ]}");
		//
		TableUtil.put(table, "嵐山-高雄パークウェイ", "あらしやま-たかおぱーくうぇい", "{嵐山=[あらしやま], 高雄=[たかお]}");
		//
		TableUtil.put(table, "万座ハイウェイ （万座温泉〜三原）", "まんざはいうぇい （まんざおんせん〜みはら）", "{万座=[まんざ], 万座温泉=[まんざおんせん], 三原=[みはら]}");
		//
		TableUtil.put(table, "三才山トンネル有料道路", "みさやまとんねるゆうりょうどうろ", "{三才山=[みさやま], 有料道路=[ゆうりょうどうろ]}");
		//
		TableUtil.put(table, "奈良奥山ドライブウェイ （奈良奥山ドライブコース） （新若草山コース） （高円山コース）",
				"ならおくやまどらいぶうぇい （ならおくやまどらいぶこーす） （しんわかくさやまこーす） （たかまどやまこーす）",
				"{奈良奥山=[ならおくやま], 新若草山=[しんわかくさやま], 高円山=[たかまどやま]}");
		//
		TableUtil.put(table, "第二みちのく有料道路", "だいにみちのくゆうりょうどうろ", "{第二=[だいに], 有料道路=[ゆうりょうどうろ]}");
		//
		TableUtil.put(table, "箱根ターンパイク （大観山線）（十国線）", "はこねたーんぱいく （だいかんざんせん）（じゅっこくせん）",
				"{箱根=[はこね], 大観山線=[だいかんざんせん], 十国線=[じゅっこくせん]}");
		//
		TableUtil.put(table, "銚子新大橋有料道路 （利根かもめ大橋有料道路）", "ちょうししんおおはしゆうりょうどうろ （とねかもめおおはしゆうりょうどうろ）",
				"{銚子新大橋有料道路=[ちょうししんおおはしゆうりょうどうろ], 利根=[とね], 大橋有料道路=[おおはしゆうりょうどうろ]}");
		//
		TableUtil.put(table, "六甲有料道路 （六甲トンネル線）", "ろっこうゆうりょうどうろ （ろっこうとんねるせん）",
				"{六甲有料道路=[ろっこうゆうりょうどうろ], 六甲=[ろっこう], 線=[せん]}");
		//
		TableUtil.put(table, "火の山パークウェイ", "ひのやまぱーくうぇい", "{火=[ひ], 山=[やま]}");
		//
		TableUtil.put(table, "芦ノ湖スカイライン", "あしのこすかいらいん", "{芦=[あし], 湖=[こ]}");
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
						Util.toString(createMultimap1(cell.getRowKey(), cell.getColumnKey())), Util.toString(cell));
				//
			} // for
				//
		} // if
			//
	}

	private static Multimap<String, String> createMultimap1(final String s1, final String s2) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP1.invoke(null, s1, s2);
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
	void testCreateMultimap2() throws Throwable {
		//
		Assertions.assertNull(Util.toString(createMultimap2(null, null)));
		//
		final Iterable<Cell<String, String, String>> cellSet = TableUtil.cellSet(ImmutableTable.of("蓼科スカイライン （林道夢の平線）",
				"たてしなすかいらいん （りんどうゆめのだいらせん）", "{蓼科=[たてしな], 林道夢=[りんどうゆめ], 平線=[だいらせん]}"));
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
						Util.toString(createMultimap2(cell.getRowKey(), cell.getColumnKey())), Util.toString(cell));
				//
			} // for
				//
		} // if
			//
	}

	private static Multimap<String, String> createMultimap2(final String s1, final String s2) throws Throwable {
		try {
			final Object obj = METHOD_CREATE_MULTI_MAP2.invoke(null, s1, s2);
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