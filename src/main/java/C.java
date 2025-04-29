import java.math.BigDecimal;

public class C {

	static interface Account{
		BigDecimal balance = new BigDecimal(0.00);
	}
	static class SavingsAccount implements Account{
		public SavingsAccount(BigDecimal initialValue) {
//			balance = initialValue;
		}
		public String toString() {
			return balance.toEngineeringString();
		}
	}
	public static void main(String[] args) {
		SavingsAccount instance = new SavingsAccount(new BigDecimal(50.00));
		
	}
	
	
}
