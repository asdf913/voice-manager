<html>
	<body>
		<#assign _captionStyle="">
		<#if captionStyle??>
			<#if captionStyle?is_string>
				<#assign _captionStyle=captionStyle>
			<#elseif captionStyle?is_hash>
				<#list captionStyle?keys as key>
					<#if key?? && captionStyle[key]??>
						<#if _captionStyle?length gt 0><#assign _captionStyle=_captionStyle+";"></#if>
						<#assign _captionStyle=_captionStyle+key+":"+captionStyle[key]>
					</#if>
				</#list>
			</#if>
		</#if>
		<div style="${_captionStyle}">
			<#if captionHtml??>
				<#if captionHtml?is_boolean>
					${captionHtml?string}
				<#elseif captionHtml?is_sequence>
					${captionHtml?join(",")}
				<#elseif captionHtml?is_date>
					${captionHtml?string("yyyy-MM-dd HH:mm:ss")}
				<#elseif captionHtml?is_hash>
					<#assign _string="">
					<#list captionHtml?keys as key>
						<#if key?? && captionHtml[key]??>
							<#if _string?length==0>{</#if>
							<#if _string?length gt 1><#assign _string=_string+","></#if>
							<#assign _string=_string+key+":"+captionHtml[key]>
						</#if>
					</#list>
					<#if _string?length gt 0>}</#if>
				<#else>
					${captionHtml}
				</#if>
			</#if>
		</div>
		<#--description-->
		<#assign _descriptionStyle="">
		<#if descriptionStyle??>
			<#if descriptionStyle?is_string>
				<#assign _descriptionStyle=descriptionStyle>
			<#elseif descriptionStyle?is_hash>
				<#list descriptionStyle?keys as key>
					<#if key?? && descriptionStyle[key]??>
						<#if _descriptionStyle?length gt 0><#assign _descriptionStyle=_descriptionStyle+";"></#if>
						<#assign _descriptionStyle=_descriptionStyle+key+":"+descriptionStyle[key]>
					</#if>
				</#list>
			</#if>
		</#if>
		<div style="${_descriptionStyle}">
			<#if descriptionHtml??>
				<#if descriptionHtml?is_boolean>
					${descriptionHtml?string}
				<#elseif descriptionHtml?is_sequence>
					${descriptionHtml?join(",")}
				<#elseif descriptionHtml?is_date>
					${descriptionHtml?string("yyyy-MM-dd HH:mm:ss")}
				<#elseif descriptionHtml?is_hash>
					<#assign _string="">
					<#list descriptionHtml?keys as key>
						<#if key?? && descriptionHtml[key]??>
							<#if _string?length==0>{</#if>
							<#if _string?length gt 1><#assign _string=_string+","></#if>
							<#assign _string=_string+key+":"+descriptionHtml[key]>
						</#if>
					</#list>
					<#if _string?length gt 0>}</#if>
				<#else>
					${descriptionHtml}
				</#if>
			</#if>
		</div>
	</body>
</html>