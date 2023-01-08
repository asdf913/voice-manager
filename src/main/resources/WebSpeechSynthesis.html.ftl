<!DOCTYPE html>
<html>
	<head>
	<meta charset="UTF-8"/>
	</head>
	<body>
		<table>
			<tbody>
				<tr>
					<td>Language</td>
					<td><input type="text" id="language" onchange="optionChange(this)"/></td>
					<td style="width:1px">Local Service</td>
					<td><input type="checkbox" id="localService" onclick="optionChange(this)"/></td>
				</tr>
				<tr>
					<td>Default Voice</td>
					<td colspan="3">
						<table>
							<thead>
								<tr><th>lang</th><th>localService</th><th>name</th><th>voiceURI</th></tr>
							</thead>
							<tbody id="tbodyDefaultValue">
							</tbody>
						</table>
					</td>
				</tr>
				<tr>
					<td>Voice</td>
					<td colspan="3">
						<select id="voices">
							<option></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>Speech Rate</td>
					<td style="width:1px">Speech Pitch</td>
					<td>Speech Volume</td>
				</tr>
				<tr>
					<td><input type="range" id="speechRate" min="0.1" max="10" value="1" step="0.1" /></td>
					<td><input type="range" id="speechPitch" min="0" max="2" value="1" step="0.1" /></td>
					<td><input type="range" id="speechVolume" min="0" max="1" value="1" step="0.1" /></td>
				</tr>
				<tr>
					<td>text</td>
					<td><input type="text" id="text"/></td>
					<td><button onclick="speak(document.getElementById('text').value)">Speak</button></td>
				</tr>
			</tbody>
		</table>
		<table>
			<tbody>
				<#if voices?? && voices?is_enumerable>
					<#list voices as voice>
						<#if voice??>
							<tr>
								<td>${voice.text!""}&nbsp;(${voice.romaji!""})</td>
								<td><button onclick="speak('${voice.text!""}')">Speak</button></td>
							</tr>
						</#if>
					</#list>
				</#if>
			</tbody>
		</table>
	<script type="text/javascript">
	//
	var elText      =document.getElementById("text");
	var speechRate  =document.getElementById("speechRate");
	var speechPitch =document.getElementById("speechPitch");
	var speechVolume=document.getElementById("speechVolume");
	//
	if(elText!==null){
		var v=getParameter("text")
		elText.value=typeof v==="string"?v:"";
	}
	function getParameter(name){
	   if(name=(new RegExp('[?&]'+encodeURIComponent(name)+'=([^&]*)')).exec(location.search))
		  return decodeURIComponent(name[1]);
	}
	//
	// https://stackoverflow.com/questions/21513706/getting-the-list-of-voices-in-speechsynthesis-web-speech-ap
	//
	// wait on voices to be loaded before fetching list
	//
	var elVoices=null;
	var originalVoices=null;
	var voices=null;
	
		//
		//https://bugzilla.mozilla.org/show_bug.cgi?id=1237082
		//
		async function getVoices() {
		  let voices = window.speechSynthesis.getVoices();
		  if (voices.length) return voices;

		  await new Promise(r => window.speechSynthesis.addEventListener("voiceschanged", r, { once: true }));
		  return window.speechSynthesis.getVoices();
		}

	if(typeof window==="object"&&window!==null&&typeof window.speechSynthesis==="object"&&window.speechSynthesis!==null){
		getVoices().then(vs=>{
			if(elVoices===null){elVoices=document.getElementById("voices");}
			if(elVoices!==null){
				if(originalVoices===null){originalVoices=[]};
				originalVoices=Object.freeze(originalVoices.concat(vs));
				voices=JSON.parse(JSON.stringify(originalVoices));
				var o=null;
				var v=null;
				for(var i=0;vs!==null&&i<vs.length;i++){
					if((o=document.createElement("option"))===null||(v=vs[i])===null){continue;}
					o.text=o.value=v.name;
					elVoices.append(o);
				}
				//
				vs=originalVoices.filter(v=>v!==null&&typeof v.default==="boolean"&&v.default);
				var defaultVoice=vs!==null&&typeof vs.length==="number"&&vs.length>0?vs[0]:null;
				var tbodyDefaultValue=document.getElementById("tbodyDefaultValue");
				if(tbodyDefaultValue!==null&&defaultVoice!==null){
					tbodyDefaultValue.innerHTML="<tr>"+[defaultVoice.lang,defaultVoice.localService,defaultVoice.name,defaultVoice.voiceURI].map(x=>"<td>"+x+"</td>").join("")+"</tr>";
				}
				//
				v=getParameter("voice");
				<#if voice??>if(typeof v==="undefined"){v="${voice}";}</#if>
				if(typeof v==="string"&&originalVoices!==null&&(vs=originalVoices.filter(x=>x!==null&&x.name!==null
				&&x.name.toLowerCase().indexOf(v.toLowerCase())>=0))!==null
				&&typeof vs.length==="number"&&vs.length===1&&vs[0]!==null){
					if(typeof elVoices==="object"&&elVoices!==null&&typeof elVoices.options==="object"){
						for(var i=elVoices.options.length-1;i>0;i--){
							if(elVoices.options[i].value===vs[0].name){
								elVoices.selectedIndex=i;
								break;
							}
						}
					}
				}
				//
				// rate
				//
				if(!isNaN(parseFloat(v=getParameter("rate"))) &&speechRate!==null   ){speechRate .value=v;}//if
				//
				// pitch
				//
				if(!isNaN(parseFloat(v=getParameter("pitch")))&&speechPitch!==null  ){speechPitch.value=v;}//if
				//
				// pitch
				//
				if(!isNaN(parseFloat(v=getParameter("volume")))&&speechVolume!==null){speechVolume.value=v;}//if
				//
			}
		});
	}
	</script>
	<script type="text/javascript">
	function optionChange(){
		//
		var elLanguage    =document.getElementById("language");
		//
		var elLocalService=document.getElementById("localService");
		//
		if(elLocalService!==null&&typeof elLocalService.checked==="boolean"&&typeof originalVoices==="object"&&originalVoices!==null&&typeof originalVoices.filter==="function"){
			voices=originalVoices.filter(v=>typeof v.localService==="boolean"&&(!elLocalService.checked||v.localService===elLocalService.checked)&&elLanguage!==null&&(elLanguage.value===""||v.lang===null
			||v.lang.toLowerCase().startsWith(elLanguage.value.toLowerCase())
			||v.lang.toLowerCase().endsWith  (elLanguage.value.toLowerCase()
			)));
			var v=null;
			if(typeof elVoices==="object"&&elVoices!==null&&typeof elVoices.options==="object"){
				for(var i=elVoices.options.length-1;i>0;i--){
					elVoices.options.remove(i);
				}
			}
			for(var i=0;voices!==null&&i<voices.length;i++){
				if((o=document.createElement("option"))===null||(v=voices[i])===null){continue;}
				o.text=o.value=v.name;
				elVoices.append(o);
			}
		}
	}
		
	function speak(text){
		//
		var msg=new SpeechSynthesisUtterance();
		//
		msg.voice=originalVoices!==null?originalVoices.filter(v=>v!=null&&v.name===elVoices.options[elVoices.selectedIndex].value)[0]:null;
		//
		msg.text=text;
		//
		msg.rate=speechRate!==null?speechRate.value:null;
		//
		msg.pitch=speechPitch!==null?speechPitch.value:null;
		//
		msg.volume=speechVolume!==null?speechVolume.value:null;
		//
		window.speechSynthesis.speak(msg);
		//
	}
	//
	var elementIds=["language","text","speechPitch"];
	//
	var minOffsetWidth=elementIds.map(x=>{var el=document.getElementById(x);return el!==null?el.offsetWidth:null}).reduce(function(a,b){return Math.min(a,b);});
	//
	elementIds.forEach(function(x){var el=document.getElementById(x);if(el!=null){el.style.width=minOffsetWidth}});
	//
	</script>
	</body>
</html>