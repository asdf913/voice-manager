
import java.util.Map;

import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

public class Test6 {

	public static void main(final String[] args) {
		// TODO Auto-generated method stub
		final ExpressionParser parser = new SpelExpressionParser();
		final Expression exp = parser.parseExpression("T(java.lang.String).format('Online (%1$s)',url)");
		System.out.println();
		final StandardEvaluationContext context = new StandardEvaluationContext();
		context.addPropertyAccessor(new MapAccessor());
		System.out.println(exp.getValue(context, Map.of("url", "URL")));
	}

}
