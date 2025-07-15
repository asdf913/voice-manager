<html>
	<body>
		<#if textAndImages?? && textAndImages?is_sequence>
			<table>
				<tbody>
					<#list textAndImages as textAndImage>
						<#if textAndImage??>
							<tr>
								<td><#if static??>${static["org.apache.commons.lang3.reflect.FieldUtils"].readDeclaredField(textAndImage,"conjugation",true)!""}</#if></td>
								<td><#if static??>${static["org.apache.commons.lang3.reflect.FieldUtils"].readDeclaredField(textAndImage,"kanji",true)}</#if></td>
								<td><#if static??>${static["org.apache.commons.lang3.reflect.FieldUtils"].readDeclaredField(textAndImage,"hiragana",true)}</#if></td>
								<td><#if static?? && static["org.apache.commons.lang3.reflect.FieldUtils"].readDeclaredField(textAndImage,"accentImage",true)??>
									<img src="data:image/png;base64,${static["org.apache.commons.codec.binary.Base64"].encodeBase64String(static["org.springframework.context.support.VoiceManagerOjadAccentPanel"].toByteArray(static["org.apache.commons.lang3.reflect.FieldUtils"].readDeclaredField(textAndImage,"accentImage",true),"png"))}"/>
								</#if></td>
								<td><#if static?? && static["org.apache.commons.lang3.reflect.FieldUtils"].readDeclaredField(textAndImage,"curveImage",true)??>
									<img src="data:image/png;base64,${static["org.apache.commons.codec.binary.Base64"].encodeBase64String(static["org.springframework.context.support.VoiceManagerOjadAccentPanel"].toByteArray(static["org.apache.commons.lang3.reflect.FieldUtils"].readDeclaredField(textAndImage,"curveImage",true),"png"))}"/>
								</#if></td>
								<#if static??><#assign voiceUrlImages=static["org.apache.commons.lang3.reflect.FieldUtils"].readDeclaredField(textAndImage,"voiceUrlImages",true)!""/></#if>
								<#if voiceUrlImages?? && voiceUrlImages?is_hash_ex>
									<#list voiceUrlImages as key,value>
										<#if key?is_string && key?ends_with("mp3") && key?contains("/female/")>
										   <td>
												<#if static?? && value??>
													<img src="data:image/png;base64,${static["org.apache.commons.codec.binary.Base64"].encodeBase64String(value)}"/>
												</#if>
										   </td>
										   <td style="width:10px">&nbsp;</td>
										</#if>
									</#list>
									<#list voiceUrlImages as key,value>
										<#if key?is_string && key?ends_with("mp3") && key?contains("/male/")>
										   <td>
												<#if static?? && value??>
													<img src="data:image/png;base64,${static["org.apache.commons.codec.binary.Base64"].encodeBase64String(value)}"/>
												</#if>
										   </td>
										</#if>
									</#list>  
									<td style="width:10px">&nbsp;</td>
								</#if>
							</tr>
						</#if>
					</#list>
				</tbody>
			</table>
		</#if>
	</body>
</html>