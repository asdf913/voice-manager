class SuggestionBox{
	private void onSelection() {
		System.out.println("Item Selected");
	}
	public void onRemoveSelection() {
		System.out.println("Item Removed");
	}
}

public class TestDemo {
	public static void main(String[] args) {
		SuggestionBox officeSuggestionBox = new SuggestionBox() {
			public void onSelection() {
				System.out.println("Office Selected");
			}
			public void onRemoveSelection() {
				System.out.println("Office Removed");
			}
};
	officeSuggestionBox.onRemoveSelection();
	}
}
