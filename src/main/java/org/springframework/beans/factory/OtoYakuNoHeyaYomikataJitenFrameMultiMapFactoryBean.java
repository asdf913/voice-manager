package org.springframework.beans.factory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.function.FailableFunction;
import org.apache.commons.lang3.function.FailableFunctionUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.ElementUtil;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.NodeUtil;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapUtil;
import com.google.common.reflect.Reflection;

interface Frame {

	String getSrc();

	String getName();

}

/*
 * https://hiramatu-hifuka.com/onyak/onyindx.html
 */
public class OtoYakuNoHeyaYomikataJitenFrameMultiMapFactoryBean implements FactoryBean<Multimap<String, Frame>> {

	private Multimap<String, String> links = null;

	public void setLinks(final Multimap<String, String> links) {
		this.links = links;
	}

	private static class IH implements InvocationHandler {

		private String name, src = null;

		@Override
		public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
			//
			final String methodName = Util.getName(method);
			//
			if (proxy instanceof Frame) {
				//
				if (Objects.equals(methodName, "getName")) {
					//
					return name;
					//
				} else if (Objects.equals(methodName, "getSrc")) {
					//
					return src;
					//
				} // if
					//
			} // if
				//
			throw new Throwable(methodName);
			//
		}

	}

	@Override
	public Multimap<String, Frame> getObject() throws Exception {
		//
		final Set<String> ls = testAndApply(Objects::nonNull, links != null ? links.values() : null, LinkedHashSet::new,
				null);
		//
		Multimap<String, Frame> multimap = null;
		//
		if (ls != null) {
			//
			for (final String l : ls) {
				//
				MultimapUtil
						.putAll(multimap = ObjectUtils.getIfNull(multimap,
								ArrayListMultimap::create), Util
										.collect(
												Util.stream(ElementUtil.select(testAndApply(Objects::nonNull,
														testAndApply(StringUtils::isNotBlank, l,
																x -> new URI(x).toURL(), null),
														x -> Jsoup.parse(x, 0), null), "frame")),
												ArrayListMultimap::create,
												(m, v) -> MultimapUtil.put(m, l, createFrame(v)), Multimap::putAll));
				//
			} // for
				//
		} // if
			//
		return multimap;
		//
	}

	private static Frame createFrame(final Node node) {
		//
		final IH ih = new IH();
		//
		ih.src = NodeUtil.absUrl(node, "src");
		//
		ih.name = NodeUtil.attr(node, "name");
		//
		return Reflection.newProxy(Frame.class, ih);
		//
	}

	private static <T, R, E extends Throwable> R testAndApply(final Predicate<T> predicate, final T value,
			final FailableFunction<T, R, E> functionTrue, final FailableFunction<T, R, E> functionFalse) throws E {
		return predicate != null && predicate.test(value) ? FailableFunctionUtil.apply(functionTrue, value)
				: FailableFunctionUtil.apply(functionFalse, value);
	}

	@Override
	public Class<?> getObjectType() {
		return Multimap.class;
	}

}