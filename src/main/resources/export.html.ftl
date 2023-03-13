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
						<input type="button" id="btnPlayAll" onclick="playAll();" value="Play All"/>
					</td>
					<td id="currentIndex" style="width:1px"/>
					<td style="width:1px">/</td>
					<td id="itemCount" style="width:1px"/>
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
									<#if voice.pitchAccentImage??>
										<#if Base64Encoder??><img src="data:${voice.pitchAccentImage.mimeType};base64,${Base64Encoder.encodeToString(voice.pitchAccentImage.content)}"/><#else><!--Base64Encoder NOT FOUND--></#if>
									</#if>
								</td>
								<td colspan="4">
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
						 "index" :${voice?counter}
						,"text"  :"${voice.text  !""}"
						,"romaji":"${voice.romaji!""}"
					}
					<#sep>,</#sep>
				</#if>
			</#list>
			];
		</#if>
		//
		var btnPlayAll  =typeof document==="object"&&document!=null&&typeof document.getElementById==="function"?document.getElementById("btnPlayAll")  :null;
		//
		var currentText =typeof document==="object"&&document!=null&&typeof document.getElementById==="function"?document.getElementById("currentText"):null;
		//
		var currentIndex=typeof document==="object"&&document!=null&&typeof document.getElementById==="function"?document.getElementById("currentIndex"):null;
		//
		if(typeof document==="object"&&document!=null&&typeof document.addEventListener==="function"){
			//
			document.addEventListener('DOMContentLoaded',function (){
				//
				var itemCount=typeof document==="object"&&document!=null&&typeof document.getElementById==="function"?document.getElementById("itemCount")   :null;
				//
				if(itemCount!==null&&typeof items==="object"&&items!==null&&typeof items.length==="number"){
					itemCount.innerHTML=items.length;
				}
				//
			},
			false);
		}//if
		//
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
					if(c<as.length&&item!==null&&currentText!==null){
						//
						if(currentText!==null){
							//
							currentText.innerHTML=item.text+"&nbsp;"+"("+item.romaji+")";
							//
						}//if
						//
						if(currentIndex!==null){
							//
							currentIndex.innerHTML=item.index;
							//
						}//if
						//
					}//if
					//
				},{"once":true});
				//
				a.addEventListener("ended",function(){
					//
					if(c<as.length-1){
						//
						var temp=as[c+1];
						//
						if(temp!==null&&typeof temp.play==="function"){
							//
							temp.play();
							//
						}//if
						//
					}else{
						//
						if(currentText!==null){
							//
							currentText.innerHTML="";
							//
						}//if
						//
						if(currentIndex!==null){
							//
							currentIndex.innerHTML="";
							//
						}//if
						//
						if(btnPlayAll!=null&&typeof btnPlayAll.disabled==="boolean"){
							//
							btnPlayAll.disabled=false;
							//
						}//if
						//
					}//if
					//
				},{"once":true});
				//
			}//for
			//
			if(as!=null&&typeof as.length==="number"&&as.length>0&&(a=as[0])!=null){
				//
				if(typeof a.play==="function"){
					//
					a.play();
					//
				}//if
				//
				if(btnPlayAll!=null&&typeof btnPlayAll.disabled==="boolean"){
					//
					btnPlayAll.disabled=true;
					//
				}//if
				//
			}//if
			//
		}
		</script>
	</body>
</html>
