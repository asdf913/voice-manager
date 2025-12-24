<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8"/>
	</head>
	<body>
		<table>
			<tbody>
			<#list .data_model?keys as key>
				<#if .data_model[key]??>
					<#if .data_model[key]?is_string>
						<tr>	
							<th>${key}</th>
							<td>${.data_model[key]}</td>
						</tr>
					<#elseif .data_model[key]?is_number>
						<tr>	
							<th>${key}</th>
							<td>${.data_model[key]}</td>
						</tr>
					<#elseif .data_model[key]?is_enumerable>
						<tr>	
							<th>${key}</th>
							<td>
							<#assign firstKey="">
							<#list .data_model[key] as item>
								<#if item?is_hash_ex>
									<#list item?keys as k2>
										<#if item?index==0>
											<#assign firstKey=k2>
											<#break>
										</#if>
									</#list>
									<details>
									<#if firstKey=="version">
										<#list item?keys as k2>
											<#if k2==firstKey>
				    							<summary>${item[k2]}</summary>
										    <#elseif item[k2]?? && item[k2]?is_enumerable>
										   		<details style="position:relative;left:10px;">
										   			<summary>${k2}</summary>
										   			<oi style="position:relative;left:20px;list-style:none">
											  			<#list item[k2] as item2>
										   				<li>${item2}</li>
										   			</#list>
										   			</ol>
										   		</details>
										    </#if>
										</#list>
									<#else>
				    					<summary>${item[firstKey]}</summary>
				    					<table>
				    					<tbody>
										<#list item?keys as k2>
											<#if k2==firstKey><#continue></#if>
											<tr>
											<th>${k2}</th>
											<td>${item[k2]!""}</td>
											</tr>
										</#list>
										</tbody>
										</table>
									</#if>
									</details>
								</#if>
							</#list>
							</td>
						</tr>
					<#elseif .data_model[key]?is_hash_ex>
						<tr>	
							<th>${key}</th>
							<td>
								<table>
									<tbody>
									<#list .data_model[key]?keys as k2>
										<tr>
											<th>${k2}</th>
											<td>${.data_model[key][k2]?string}</td>
										</tr>
									</#list>
									</tbody>
								</table>
							</td>
						</tr>
					</#if>
				</#if>
			</#list>
			</tbody>
		</table>
	</body>
</html>