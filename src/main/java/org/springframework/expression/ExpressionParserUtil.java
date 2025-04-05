package org.springframework.expression;

import javax.annotation.Nullable;

public interface ExpressionParserUtil {

	@Nullable
	static Expression parseExpression(@Nullable final ExpressionParser instance, final String expressionString) {
		return instance != null ? instance.parseExpression(expressionString) : null;
	}

}