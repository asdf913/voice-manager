package org.springframework.core.io;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import javax.annotation.Nullable;

import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.ClassParserUtil;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.JavaClassUtil;
import org.apache.bcel.generic.ARETURN;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionListUtil;
import org.apache.bcel.generic.LDC;
import org.apache.bcel.generic.LDCUtil;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.MethodGenUtil;
import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Strings;
import org.apache.commons.lang3.StringsUtil;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DirectoryNode;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerUtil;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;

import io.github.toolfactory.narcissus.Narcissus;

public abstract class XlsUtil {

	private static final Logger LOG = LoggerFactory.getLogger(XlsUtil.class);

	private XlsUtil() {
	}

	public static boolean isXls(final InputStreamSource iss) throws IOException {
		//
		ContentInfo ci = null;
		//
		try (final InputStream is = InputStreamSourceUtil.getInputStream(iss)) {
			//
			ci = testAndApply(Objects::nonNull, is, x -> new ContentInfoUtil().findMatch(x), null);
			//
		} // try
			//
		if (!Objects.equals(getMessage(ci), "OLE 2 Compound Document")) {
			//
			return false;
			//
		} // if
			//
		DirectoryNode directoryNode = null;
		//
		try (final InputStream is = InputStreamSourceUtil.getInputStream(iss);
				final POIFSFileSystem poifsFileSystem = new POIFSFileSystem(is)) {
			//
			directoryNode = poifsFileSystem.getRoot();
			//
		} // try
			//
		Class<?> clz = null;
		//
		try {
			//
			clz = Class.forName("org.apache.poi.hssf.usermodel.HSSFWorkbook");
			//
		} catch (final ClassNotFoundException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		final List<Object> entryNames = getEntryNames(clz);
		//
		for (int i = 0; i < IterableUtils.size(entryNames); i++) {
			//
			if (hasEntryCaseInsensitive(directoryNode, toString(IterableUtils.get(entryNames, i)))) {
				//
				return true;
				//
			} // if
				//
		} // for
			//
		return false;
		//
	}

	@Nullable
	private static List<Object> getEntryNames(@Nullable final Class<?> clz) throws IOException {
		//
		List<Object> entryNames = null;
		//
		try (final InputStream is = getResourceAsStream(clz,
				String.format("/%1$s.class", StringsUtil.replace(Strings.CS, getName(clz), ".", "/")))) {
			//
			JavaClass javaClass = null;
			//
			final Instruction[] instructions = InstructionListUtil
					.getInstructions(MethodGenUtil.getInstructionList(testAndApply(Objects::nonNull,
							JavaClassUtil.getMethod(
									javaClass = ClassParserUtil.parse(
											testAndApply(Objects::nonNull, is, x -> new ClassParser(x, null), null)),
									getDeclaredMethod(clz, "getWorkbookDirEntryName", DirectoryNode.class)),
							x -> new MethodGen(x, null, null), null)));
			//
			Instruction instruction = null;
			//
			ConstantPoolGen cpg = null;
			//
			final ConstantPool cp = getConstantPool(javaClass);
			//
			for (int i = 0; instructions != null && i < instructions.length; i++) {
				//
				if ((instruction = instructions[i]) == null) {
					//
					continue;
					//
				} // if
					//
				if (instruction instanceof ARETURN && i > 0 && instructions[i - 1] instanceof LDC ldc) {
					//
					if (cpg == null) {
						//
						cpg = ObjectUtils.getIfNull(cpg,
								() -> testAndApply(Objects::nonNull, cp, ConstantPoolGen::new, null));
						//
					} // if
						//
					add(entryNames = ObjectUtils.getIfNull(entryNames, ArrayList::new), LDCUtil.getValue(ldc, cpg));
					//
				} // if
					//
			} // for
				//
		} catch (final NoSuchMethodException e) {
			//
			LoggerUtil.error(LOG, e.getMessage(), e);
			//
		} // try
			//
		return entryNames;
		//
	}

	@Nullable
	private static InputStream getResourceAsStream(@Nullable final Class<?> clz, @Nullable final String name) {
		return clz != null && name != null ? clz.getResourceAsStream(name) : null;
	}

	private static boolean hasEntryCaseInsensitive(@Nullable final DirectoryEntry instance,
			@Nullable final String name) {
		//
		if (instance == null) {
			//
			return false;
			//
		} // if
			//
		final Class<?> clz = getClass(instance);
		//
		final String className = getName(clz);
		//
		if (Objects.equals(className, "org.apache.poi.poifs.filesystem.DirectoryNode") && name != null) {
			//
			final List<Field> fs = FieldUtils.getAllFieldsList(clz).stream()
					.filter(f -> Objects.equals(getName(f), "_byUCName")).toList();
			//
			if (IterableUtils.size(fs) > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			if (Narcissus.getField(instance,
					testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) == null) {
				//
				return false;
				//
			} // if
				//
		} else if (Objects.equals(className, "org.apache.poi.poifs.filesystem.FilteringDirectoryNode")) {
			//
			final List<Field> fs = FieldUtils.getAllFieldsList(clz).stream()
					.filter(f -> Objects.equals(getName(f), "excludes")).toList();
			//
			if (IterableUtils.size(fs) > 1) {
				//
				throw new IllegalStateException();
				//
			} // if
				//
			if (Narcissus.getField(instance,
					testAndApply(x -> IterableUtils.size(x) == 1, fs, x -> IterableUtils.get(x, 0), null)) == null) {
				//
				return false;
				//
			} // if
				//
		} // if
			//
		return instance.hasEntryCaseInsensitive(name);
		//
	}

	@Nullable
	private static String getName(@Nullable final Member instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static Class<?> getClass(@Nullable final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	@Nullable
	private static String getName(@Nullable final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	@Nullable
	private static ConstantPool getConstantPool(@Nullable final JavaClass instance) {
		return instance != null ? instance.getConstantPool() : null;
	}

	@Nullable
	private static String toString(@Nullable final Object instance) {
		return instance != null ? instance.toString() : null;
	}

	private static <E> void add(@Nullable final Collection<E> items, final E item) {
		if (items != null) {
			items.add(item);
		}
	}

	@Nullable
	private static Method getDeclaredMethod(@Nullable final Class<?> clz, final String name,
			final Class<?>... parameterTypes) throws NoSuchMethodException {
		return clz != null ? clz.getDeclaredMethod(name, parameterTypes) : null;
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, @Nullable final T value,
			final FailableFunction<T, R, E> functionTrue, @Nullable final FailableFunction<T, R, E> functionFalse)
			throws E {
		return test(predicate, value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	private static <T> boolean test(@Nullable final Predicate<T> instance, @Nullable final T value) {
		return instance != null && instance.test(value);
	}

	@Nullable
	private static String getMessage(@Nullable final ContentInfo instance) {
		return instance != null ? instance.getMessage() : null;
	}

}