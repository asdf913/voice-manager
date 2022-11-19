<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8"/>
		<meta name="viewport" content="width=device-width, height=device-height, initial-scale=1"/>
	</head>
	<body>
		<table>
			<caption>Audio</caption>
			<thead>
				<tr>
					<td>
						<input type="button" onclick="playAll();" value="Play All"/>
					</td>
					<td id="currentText"/>
				</tr>
			</thead>
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
		<script>
		<#if voices?? && voices?is_enumerable>
			var items=[
			<#list voices as voice>
				<#if voice??>
					{
						 "text"  :"${voice.text  !""}"
						,"romaji":"${voice.romaji!""}"
					}
					<#sep>,</#sep>
				</#if>
			</#list>
			];
		</#if>
		function playAll(){
			//
			var as=typeof document==="object"&&document!=null&&typeof document.querySelectorAll==="function"?document.querySelectorAll("audio"):null;
			//
			var a=null;
			//
			for(var i=0;as!=null&&typeof as.length==="number"&&i<as.length;i++){
				//
				if((a=as[i])==null||typeof a.addEventListener!=="function"){continue;}
				//
				const c=i;
				//
				a.addEventListener("playing",function(){
					//
					var item=items[c];
					//
					var currentText=document.getElementById("currentText");
					//
					if(c<as.length&&item!==null&&currentText!==null){
						currentText.innerHTML=item.text+"&nbsp;"+"("+item.romaji+")";
					}//if
					//
				});
				//
				a.addEventListener("ended",function(){
					//
					if(c<as.length-1){
						as[c+1].play();
					}else{
						document.getElementById("currentText").innerHTML="";
					}//if
					//
				});
				//
			}//for
			//
			if(as!=null&&typeof as.length==="number"&&as.length>0&&as[0]!=null){as[0].play();}
			//
		}
		</script>
	</body>
</html>
