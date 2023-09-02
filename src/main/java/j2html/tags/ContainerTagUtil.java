package j2html.tags;

public interface ContainerTagUtil {

	static <T extends ContainerTag<T>> T withText(final ContainerTag<T> instance, final String text) {
		return instance != null ? instance.withText(text) : null;
	}

}