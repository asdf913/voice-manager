<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"/>
		<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1"/>
	</head>
	<body>
		<table>
			<caption>Audio</caption>
			<tbody>
				<#if voices?? && voices?is_enumerable>
					<#list voices as voice>
						<#if voice??>
							<tr>
								<td>
									${voice.text!""}&nbsp;(${voice.romaji!""})
								</td>
								<td>
									<#if statics??>
										<#assign mimeTypeAndBase64EncodedString=statics["org.springframework.context.support.VoiceManager"].getMimeTypeAndBase64EncodedString(folder,voice.filePath!"")>
										<#if mimeTypeAndBase64EncodedString??>
											<audio controls>
												<source type="${mimeTypeAndBase64EncodedString.key}" src="data:${mimeTypeAndBase64EncodedString.key};base64,${mimeTypeAndBase64EncodedString.value}"/>
												Your browser does not support the audio element.
											</audio>
										</#if>
									</#if>
								</td>
							</tr>
						</#if>
					</#list>
				</#if>
			</tbody>
		</table>
	</body>
</html>
