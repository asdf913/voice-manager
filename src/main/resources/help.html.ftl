<#if statics??>
	<#assign ATagUtil=statics['j2html.tags.specialized.ATagUtil']>
</#if>
<!DOCTYPE html>
<html lang="en">
	<head>
		<title></title>
		<meta content="text/html; charset=utf-8"/>
	</head>
	<body>
		<h1>Import (Single)</h1>
		<table>
			<tbody>
				<tr>
					<td>学年別漢字</td>
					<td>
						<#assign url="https://ja.wikipedia.org/wiki/%E5%AD%A6%E5%B9%B4%E5%88%A5%E6%BC%A2%E5%AD%97%E9%85%8D%E5%BD%93%E8%A1%A8">
						<#if statics??>
							${statics["j2html.tags.specialized.ATagUtil"].createByUrl(url)}
						<#else>
							<a href="${url}">学年別漢字配当表 - Wikipedia</a>
						</#if>
					</td>
				</tr>
				<tr>
					<td>常用漢字</td>
					<td>
						<#assign url="https://ja.wikipedia.org/wiki/%E5%B8%B8%E7%94%A8%E6%BC%A2%E5%AD%97%E4%B8%80%E8%A6%A7">
						<#if statics??>
							${statics["j2html.tags.specialized.ATagUtil"].createByUrl(url)}
						<#else>
							<a href="${url}">常用漢字一覧 - Wikipedia</a>
						</#if>
					</td>
				</tr>
				<tr>
					<td>IPA</td>
					<td>
						<#assign url="https://www.internationalphoneticassociation.org">
						<#if statics??>
							${statics["j2html.tags.specialized.ATagUtil"].createByUrl(url)}
						<#else>
							<a href="${url}">International Phonetic Association | ɪntəˈnæʃənəl fəˈnɛtɪk əsoʊsiˈeɪʃn</a>
						</#if>
					</td>
				</tr>
				<tr>
					<td>JLPT</td>
					<td>
						<#assign url="https://www.jlpt.jp/about/levelsummary.html">
						<#if statics??>
							${statics["j2html.tags.specialized.ATagUtil"].createByUrl(url)}
						<#else>
							<a href="${url}">N1～N5:認定の目安 | 日本語能力試験　JLPT</a>
						</#if>
					</td>
				</tr>
			</tbody>
		</table>
		<h1>Import (Batch)</h1>
		<h2>Spreadsheet</h2>
		<a href="https://en.wikipedia.org/wiki/Office_Open_XML">Office Open XML (*.xlsx)</a> is required.
		<h1>Export</h1>
		<h2>Presentation</h2>
		Export Format is <a href="https://en.wikipedia.org/wiki/OpenDocument">Open Document Presentation (*.odp)</a><br/>
		<a href="https://www.libreoffice.org/">LibreOffice - Free Office Suite - Based on OpenOffice - Compatible with Microsoft</a> is recommended.<br/>
		<a href="https://xiph.org/flac">Free Lossless Audio Codec (FLAC)</a> Audio Format could not be played by the LibreOffice.<br/>
		Please visit the list of <a href="https://help.libreoffice.org/latest/en-US/text/shared/01/moviesound.html"> supported media format(s)</a> by LibreOffice.
		<#if mediaFormatLink??><br/>${mediaFormatLink}</#if>
	</body>
</html>