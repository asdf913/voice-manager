package org.springframework.expression;

public interface ExpressionParserUtil {

	static Expression parseExpression(final ExpressionParser instance, final String expressionString) {
		return instance != null ? instance.parseExpression(expressionString) : null;
	}

}