package j2html.tags;

public interface TagUtil {

	static <T extends Tag<T>> T attr(final Tag<T> instance, final String attribute, final Object value) {
		return instance != null ? instance.attr(attribute, value) : null;
	}

}