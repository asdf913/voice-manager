package io.github.classgraph;

import java.awt.GraphicsEnvironment;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.apache.commons.collections4.CollectionUtils;

public interface ClassInfoUtil {

	static List<ClassInfo> getClassInfos() {
		//
		final List<ClassInfo> classInfos = getAllClasses(scan(new ClassGraph().enableClassInfo()));
		//
		final Collection<String> classToBeExcluded = new ArrayList<>(Arrays.asList(
				//
				"com.helger.css.parser.ParserCSS30TokenManager"
				//
				, "com.j256.simplemagic.logger.backend.Log4jLogBackend",
				"com.microsoft.schemas.office.visio.x2012.main.SheetType",
				"com.microsoft.schemas.office.visio.x2012.main.TriggerType", "com.sun.jna.platform.linux.LibC",
				"com.sun.jna.platform.linux.LibRT", "com.sun.jna.platform.linux.Udev",
				"com.sun.jna.platform.mac.Carbon", "com.sun.jna.platform.mac.CoreFoundation",
				"com.sun.jna.platform.mac.DiskArbitration", "com.sun.jna.platform.mac.IOKit",
				"com.sun.jna.platform.mac.IOKitUtil", "com.sun.jna.platform.mac.MacFileUtils$FileManager",
				"com.sun.jna.platform.mac.SystemB", "com.sun.jna.platform.unix.LibC",
				"com.sun.jna.platform.unix.LibCUtil", "com.sun.jna.platform.unix.X11",
				"com.sun.jna.platform.unix.X11$XTest", "com.sun.jna.platform.unix.X11$Xevie",
				"com.sun.jna.platform.unix.X11$Xext", "com.sun.jna.platform.unix.X11$Xrender",
				"com.sun.jna.platform.unix.aix.Perfstat", "com.sun.jna.platform.unix.solaris.Kstat2",
				"com.sun.jna.platform.unix.solaris.LibKstat", "com.sun.jna.platform.wince.CoreDLL",
				"freemarker.ext.ant.FreemarkerXmlTask", "freemarker.ext.ant.JythonAntTask",
				"freemarker.ext.ant.UnlinkedJythonOperationsImpl", "freemarker.ext.jdom.NodeListModel",
				"freemarker.ext.jsp.EventForwarding", "freemarker.ext.jsp.FreemarkerTag",
				"freemarker.ext.jsp._FreeMarkerPageContext2", "freemarker.ext.jsp._FreeMarkerPageContext21",
				"freemarker.ext.jython.JythonHashModel", "freemarker.ext.jython.JythonModel",
				"freemarker.ext.jython.JythonNumberModel", "freemarker.ext.jython.JythonSequenceModel",
				"freemarker.ext.jython.JythonWrapper", "freemarker.ext.rhino.RhinoFunctionModel",
				"freemarker.ext.rhino.RhinoScriptableModel", "freemarker.ext.rhino.RhinoWrapper",
				"freemarker.ext.servlet.FreemarkerServlet", "freemarker.ext.servlet.IncludePage",
				"freemarker.ext.xml._JaxenNamespaces", "freemarker.ext.xml._JdomNavigator",
				"freemarker.template.utility.JythonRuntime", "io.micrometer.observation.aop.ObservedAspect",
				"io.micrometer.observation.contextpropagation.ObservationThreadLocalAccessor",
				"net.rootdev.javardfa.jena.JenaStatementSink", "net.rootdev.javardfa.jena.RDFaReader",
				"net.rootdev.javardfa.jena.RDFaReader$HTMLRDFaReader",
				"net.rootdev.javardfa.jena.RDFaReader$XHTMLRDFaReader", "net.rootdev.javardfa.query.QueryCollector",
				"net.rootdev.javardfa.query.QueryUtilities",
				"org.apache.commons.compress.compressors.brotli.BrotliCompressorInputStream",
				"org.apache.commons.compress.compressors.lzma.LZMACompressorInputStream",
				"org.apache.commons.compress.compressors.xz.XZCompressorInputStream",
				"org.apache.commons.compress.compressors.xz.XZCompressorOutputStream",
				"org.apache.commons.compress.compressors.zstandard.ZstdCompressorInputStream",
				"org.apache.commons.logging.impl.Log4JLogger", "org.apache.commons.logging.impl.ServletContextCleaner",
				"org.apache.ibatis.logging.log4j.Log4jImpl", "org.apache.jena.atlas.logging.LogCtlLog4j2",
				"org.apache.logging.log4j.util.Activator", "org.apache.logging.log4j.util.ProviderActivator",
				"org.apache.nutch.api.NutchServer", "org.apache.nutch.api.impl.JobFactory",
				"org.apache.nutch.api.misc.ErrorStatusService", "org.apache.nutch.api.resources.AbstractResource",
				"org.apache.nutch.api.resources.AdminResource", "org.apache.nutch.api.resources.ConfigResource",
				"org.apache.nutch.api.resources.DbResource", "org.apache.nutch.api.resources.JobResource",
				"org.apache.nutch.api.resources.SeedResource", "org.apache.nutch.api.security.SecurityUtils",
				"org.apache.nutch.crawl.AbstractFetchSchedule", "org.apache.nutch.crawl.AdaptiveFetchSchedule",
				"org.apache.nutch.crawl.DbUpdateMapper", "org.apache.nutch.crawl.DbUpdateReducer",
				"org.apache.nutch.crawl.DbUpdaterJob", "org.apache.nutch.crawl.DefaultFetchSchedule",
				"org.apache.nutch.crawl.FetchSchedule", "org.apache.nutch.crawl.GeneratorJob",
				"org.apache.nutch.crawl.GeneratorJob$SelectorEntry",
				"org.apache.nutch.crawl.GeneratorJob$SelectorEntryComparator", "org.apache.nutch.crawl.GeneratorMapper",
				"org.apache.nutch.crawl.GeneratorReducer", "org.apache.nutch.crawl.InjectType",
				"org.apache.nutch.crawl.InjectorJob", "org.apache.nutch.crawl.InjectorJob$UrlMapper",
				"org.apache.nutch.crawl.MD5Signature", "org.apache.nutch.crawl.NutchWritable",
				"org.apache.nutch.crawl.Signature", "org.apache.nutch.crawl.TextMD5Signature",
				"org.apache.nutch.crawl.TextProfileSignature", "org.apache.nutch.crawl.URLPartitioner",
				"org.apache.nutch.crawl.URLPartitioner$FetchEntryPartitioner",
				"org.apache.nutch.crawl.URLPartitioner$SelectorEntryPartitioner", "org.apache.nutch.crawl.UrlWithScore",
				"org.apache.nutch.crawl.UrlWithScore$UrlOnlyPartitioner",
				"org.apache.nutch.crawl.UrlWithScore$UrlScoreComparator",
				"org.apache.nutch.crawl.UrlWithScore$UrlScoreComparator$UrlOnlyComparator",
				"org.apache.nutch.crawl.WebTableReader", "org.apache.nutch.crawl.WebTableReader$WebTableRegexMapper",
				"org.apache.nutch.crawl.WebTableReader$WebTableStatCombiner",
				"org.apache.nutch.crawl.WebTableReader$WebTableStatMapper",
				"org.apache.nutch.crawl.WebTableReader$WebTableStatReducer", "org.apache.hadoop.conf.Configured",
				"org.apache.nutch.fetcher.FetchEntry", "org.apache.nutch.fetcher.FetcherJob",
				"org.apache.nutch.fetcher.FetcherJob$FetcherMapper", "org.apache.nutch.fetcher.FetcherReducer",
				"org.apache.nutch.host.HostDb", "org.apache.nutch.host.HostDbReader",
				"org.apache.nutch.host.HostDbUpdateJob", "org.apache.nutch.host.HostDbUpdateJob$Mapper",
				"org.apache.nutch.host.HostDbUpdateReducer", "org.apache.nutch.host.HostInjectorJob",
				"org.apache.nutch.host.HostInjectorJob$UrlMapper", "org.apache.nutch.indexer.CleaningJob",
				"org.apache.nutch.indexer.CleaningJob$CleanMapper", "org.apache.nutch.indexer.CleaningJob$CleanReducer",
				"org.apache.nutch.indexer.IndexCleaningFilter", "org.apache.nutch.indexer.IndexWriter",
				"org.apache.nutch.indexer.IndexerOutputFormat", "org.apache.nutch.indexer.IndexingFilter",
				"org.apache.nutch.indexer.IndexingFiltersChecker", "org.apache.nutch.indexer.IndexingJob",
				"org.apache.nutch.indexer.IndexingJob$IndexerMapper", "org.apache.nutch.indexer.NutchDocument",
				"org.apache.nutch.indexer.solr.SolrDeleteDuplicates",
				"org.apache.nutch.indexer.solr.SolrDeleteDuplicates$SolrInputFormat",
				"org.apache.nutch.indexer.solr.SolrDeleteDuplicates$SolrInputSplit",
				"org.apache.nutch.indexer.solr.SolrDeleteDuplicates$SolrRecord",
				"org.apache.nutch.indexer.solr.SolrDeleteDuplicates$SolrRecordReader",
				"org.apache.nutch.indexer.solr.SolrUtils", "org.apache.nutch.metadata.MetaWrapper",
				"org.apache.nutch.metadata.Metadata", "org.apache.nutch.metadata.Nutch",
				"org.apache.nutch.metadata.SpellCheckedMetadata", "org.apache.nutch.net.URLFilter",
				"org.apache.nutch.net.URLNormalizer", "org.apache.nutch.parse.NutchSitemapParser",
				"org.apache.nutch.parse.Outlink", "org.apache.nutch.parse.ParseFilter",
				"org.apache.nutch.parse.ParseStatusUtils", "org.apache.nutch.parse.ParseUtil",
				"org.apache.nutch.parse.Parser", "org.apache.nutch.parse.ParserChecker",
				"org.apache.nutch.parse.ParserJob", "org.apache.nutch.parse.ParserJob$ParserMapper",
				"org.apache.nutch.protocol.Content", "org.apache.nutch.protocol.Protocol",
				"org.apache.nutch.protocol.ProtocolStatusUtils", "org.apache.nutch.protocol.RobotRulesParser",
				"org.apache.nutch.scoring.ScoreDatum", "org.apache.nutch.scoring.ScoringFilter",
				"org.apache.nutch.scoring.ScoringFilters", "org.apache.nutch.storage.Host",
				"org.apache.nutch.storage.Host$Builder", "org.apache.nutch.storage.Host$Tombstone",
				"org.apache.nutch.storage.Mark", "org.apache.nutch.storage.ParseStatus",
				"org.apache.nutch.storage.ParseStatus$Builder", "org.apache.nutch.storage.ParseStatus$Tombstone",
				"org.apache.nutch.storage.ProtocolStatus", "org.apache.nutch.storage.ProtocolStatus$Builder",
				"org.apache.nutch.storage.ProtocolStatus$Tombstone", "org.apache.nutch.storage.WebPage",
				"org.apache.nutch.storage.WebPage$Builder", "org.apache.nutch.storage.WebPage$Tombstone",
				"org.apache.nutch.tools.Benchmark", "org.apache.nutch.tools.arc.ArcInputFormat",
				"org.apache.nutch.tools.arc.ArcRecordReader", "org.apache.nutch.tools.proxy.AbstractTestbedHandler",
				"org.apache.nutch.tools.proxy.DelayHandler", "org.apache.nutch.tools.proxy.FakeHandler",
				"org.apache.nutch.tools.proxy.LogDebugHandler", "org.apache.nutch.tools.proxy.NotFoundHandler",
				"org.apache.nutch.tools.proxy.TestbedProxy", "org.apache.nutch.util.Bytes",
				"org.apache.nutch.util.Bytes$ByteArrayComparator", "org.apache.nutch.util.EncodingDetector",
				"org.apache.nutch.util.GenericWritableConfigurable", "org.apache.nutch.util.HadoopFSUtil",
				"org.apache.nutch.util.IdentityPageReducer", "org.apache.nutch.util.MimeUtil",
				"org.apache.nutch.util.NutchJob", "org.apache.nutch.util.NutchJobConf",
				"org.apache.nutch.util.NutchTool", "org.apache.nutch.util.WebPageWritable",
				"org.apache.nutch.util.domain.DomainStatistics",
				"org.apache.nutch.util.domain.DomainStatistics$DomainStatisticsCombiner",
				"org.apache.nutch.util.domain.DomainStatistics$DomainStatisticsMapper",
				"org.apache.nutch.util.domain.DomainStatistics$DomainStatisticsReducer",
				"org.apache.nutch.webui.NutchUiApplication", "org.apache.nutch.webui.NutchUiServer",
				"org.apache.nutch.webui.client.impl.NutchClientImpl",
				"org.apache.nutch.webui.config.SpringConfiguration", "org.apache.nutch.webui.pages.AbstractBasePage",
				"org.apache.nutch.webui.pages.DashboardPage", "org.apache.nutch.webui.pages.LogOutPage",
				"org.apache.nutch.webui.pages.SchedulingPage", "org.apache.nutch.webui.pages.SearchPage",
				"org.apache.nutch.webui.pages.StatisticsPage", "org.apache.nutch.webui.pages.UrlsUploadPage",
				"org.apache.nutch.webui.pages.UserSettingsPage",
				"org.apache.nutch.webui.pages.assets.NutchUiCssReference",
				"org.apache.nutch.webui.pages.auth.AuthorizationStrategy",
				"org.apache.nutch.webui.pages.auth.SignInPage", "org.apache.nutch.webui.pages.auth.SignInSession",
				"org.apache.nutch.webui.pages.components.ColorEnumLabel",
				"org.apache.nutch.webui.pages.components.CpmIteratorAdapter",
				"org.apache.nutch.webui.pages.crawls.CrawlPanel", "org.apache.nutch.webui.pages.crawls.CrawlsPage",
				"org.apache.nutch.webui.pages.instances.InstancePanel",
				"org.apache.nutch.webui.pages.instances.InstancesPage",
				"org.apache.nutch.webui.pages.menu.VerticalMenu", "org.apache.nutch.webui.pages.seed.SeedListsPage",
				"org.apache.nutch.webui.pages.seed.SeedPage", "org.apache.nutch.webui.pages.settings.SettingsPage",
				"org.apache.nutch.webui.service.impl.NutchServiceImpl",
				"org.apache.poi.poifs.crypt.dsig.services.TSPTimeStampService",
				"org.apache.poi.xslf.draw.SVGImageRenderer", "org.apache.poi.xslf.draw.SVGPOIGraphics2D",
				"org.apache.poi.xslf.draw.SVGRenderExtension", "org.apache.poi.xslf.draw.SVGUserAgent",
				"org.apache.poi.xslf.util.PDFFontMapper", "org.apache.poi.xslf.util.PDFFormat",
				"org.apache.poi.xslf.util.SVGFormat", "org.apache.xerces.util.XMLCatalogResolver",
				"org.apache.xmlbeans.impl.schema.TypeSystemHolder", "org.apache.xmlbeans.impl.tool.MavenPlugin",
				"org.apache.xmlbeans.impl.tool.MavenPluginHelp", "org.apache.xmlbeans.impl.tool.XMLBean",
				"org.apache.xmlbeans.impl.xpath.saxon.SaxonXPath", "org.apache.xmlbeans.impl.xpath.saxon.SaxonXQuery",
				"org.eclipse.jetty.http.BadMessageException", "org.eclipse.jetty.http.CompressedContentFormat",
				"org.eclipse.jetty.http.CookieCache", "org.eclipse.jetty.http.DateGenerator",
				"org.eclipse.jetty.http.GZIPContentDecoder", "org.eclipse.jetty.http.HostPortHttpField",
				"org.eclipse.jetty.http.HttpCookie$SameSite", "org.eclipse.jetty.http.HttpException",
				"org.eclipse.jetty.http.HttpException$IllegalArgumentException",
				"org.eclipse.jetty.http.HttpException$RuntimeException", "org.eclipse.jetty.http.HttpField",
				"org.eclipse.jetty.http.HttpField$IntValueHttpField",
				"org.eclipse.jetty.http.HttpField$LongValueHttpField", "org.eclipse.jetty.http.HttpFields",
				"org.eclipse.jetty.http.HttpFields$ImmutableHttpFields",
				"org.eclipse.jetty.http.HttpFields$Mutable$Wrapper",
				"org.eclipse.jetty.http.HttpFields$MutableHttpFields", "org.eclipse.jetty.http.HttpGenerator",
				"org.eclipse.jetty.http.HttpHeader", "org.eclipse.jetty.http.HttpHeaderValue",
				"org.eclipse.jetty.http.HttpMethod", "org.eclipse.jetty.http.HttpParser",
				"org.eclipse.jetty.http.HttpScheme", "org.eclipse.jetty.http.HttpTester$Message",
				"org.eclipse.jetty.http.HttpTester$Request", "org.eclipse.jetty.http.HttpTester$Response",
				"org.eclipse.jetty.http.HttpTokens", "org.eclipse.jetty.http.HttpURI$Mutable",
				"org.eclipse.jetty.http.HttpVersion", "org.eclipse.jetty.http.MimeTypes",
				"org.eclipse.jetty.http.MimeTypes$Mutable", "org.eclipse.jetty.http.MimeTypes$Type",
				"org.eclipse.jetty.http.MimeTypes$Wrapper", "org.eclipse.jetty.http.MultiPart",
				"org.eclipse.jetty.http.MultiPart$AbstractContentSource",
				"org.eclipse.jetty.http.MultiPart$ByteBufferPart", "org.eclipse.jetty.http.MultiPart$ChunksPart",
				"org.eclipse.jetty.http.MultiPart$ContentSourcePart", "org.eclipse.jetty.http.MultiPart$Parser",
				"org.eclipse.jetty.http.MultiPart$Part", "org.eclipse.jetty.http.MultiPart$PathPart",
				"org.eclipse.jetty.http.MultiPartByteRanges$ContentSource",
				"org.eclipse.jetty.http.MultiPartByteRanges$InputStreamContentSource",
				"org.eclipse.jetty.http.MultiPartByteRanges$Parser", "org.eclipse.jetty.http.MultiPartByteRanges$Part",
				"org.eclipse.jetty.http.MultiPartByteRanges$PathContentSource",
				"org.eclipse.jetty.http.MultiPartFormData$ContentSource",
				"org.eclipse.jetty.http.MultiPartFormData$Parser", "org.eclipse.jetty.http.PreEncodedHttpField",
				"org.eclipse.jetty.http.QuotedCSV", "org.eclipse.jetty.http.QuotedCSVParser",
				"org.eclipse.jetty.http.QuotedQualityCSV", "org.eclipse.jetty.http.Trailers",
				"org.eclipse.jetty.http.content.CachingHttpContentFactory",
				"org.eclipse.jetty.http.content.ValidatingCachingHttpContentFactory",
				"org.eclipse.jetty.http.pathmap.PathMappings", "org.etsi.uri.x01903.v13.GenericTimeStampType",
				"org.h2.fulltext.FullTextLucene", "org.h2.fulltext.FullTextLucene$FullTextTrigger",
				"org.h2.server.web.DbStarter", "org.h2.server.web.JakartaDbStarter",
				"org.h2.server.web.JakartaWebServlet", "org.h2.server.web.WebServlet", "org.h2.util.DbDriverActivator",
				"org.h2.util.OsgiDataSourceFactory", "org.h2.util.geometry.JTSUtils$GeometryTarget",
				"org.junit.jupiter.api.AssertionsKt$assertDoesNotThrow$1",
				"org.junit.jupiter.api.AssertionsKt$assertThrows$2", "org.logevents.observers.SmtpLogEventObserver",
				"org.logevents.optional.azure.ApplicationInsightsLogEventObserver",
				"org.logevents.optional.jakarta.LogEventsConfigurationServlet",
				"org.logevents.optional.jakarta.LogEventsServlet",
				"org.logevents.optional.servlets.LogEventsConfigurationServlet",
				"org.logevents.optional.servlets.LogEventsServlet", "org.mybatis.spring.batch.MyBatisBatchItemWriter",
				"org.mybatis.spring.batch.MyBatisCursorItemReader", "org.mybatis.spring.batch.MyBatisPagingItemReader",
				"org.odftoolkit.odfdom.type.CURIE", "org.odftoolkit.odfdom.type.SafeCURIE",
				"org.odftoolkit.odfdom.type.URIorSafeCURIE", "org.openxmlformats.schemas.drawingml.x2006.chart.CTSerAx",
				"org.openxmlformats.schemas.drawingml.x2006.chart.STDepthPercentUShort",
				"org.openxmlformats.schemas.drawingml.x2006.chart.STDepthPercentWithSymbol",
				"org.openxmlformats.schemas.drawingml.x2006.chart.STHPercentUShort",
				"org.openxmlformats.schemas.drawingml.x2006.chart.STHPercentWithSymbol",
				"org.openxmlformats.schemas.drawingml.x2006.chart.STHoleSizePercent",
				"org.openxmlformats.schemas.drawingml.x2006.chart.STHoleSizeUByte",
				"org.openxmlformats.schemas.drawingml.x2006.chart.STOverlapByte",
				"org.openxmlformats.schemas.drawingml.x2006.chart.STOverlapPercent",
				"org.openxmlformats.schemas.drawingml.x2006.main.CTComplementTransform",
				"org.openxmlformats.schemas.drawingml.x2006.main.CTGroupFillProperties",
				"org.openxmlformats.schemas.drawingml.x2006.main.CTPath2DQuadBezierTo",
				"org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveFixedAngle",
				"org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmarkRange",
				"org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDocumentBase",
				"org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPPrBase",
				"org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblGridBase",
				"org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPrBase",
				"org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblPrExBase",
				"org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPrBase",
				"org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTcPrInner",
				"org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPrBase",
				"org.springframework.aop.aspectj.AbstractAspectJAdvice",
				"org.springframework.aop.aspectj.AspectJAdviceParameterNameDiscoverer",
				"org.springframework.aop.aspectj.AspectJAfterAdvice",
				"org.springframework.aop.aspectj.AspectJAfterReturningAdvice",
				"org.springframework.aop.aspectj.AspectJAfterThrowingAdvice",
				"org.springframework.aop.aspectj.AspectJAroundAdvice",
				"org.springframework.aop.aspectj.AspectJExpressionPointcut",
				"org.springframework.aop.aspectj.AspectJMethodBeforeAdvice",
				"org.springframework.aop.aspectj.AspectJWeaverMessageHandler",
				"org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint",
				"org.springframework.aop.aspectj.annotation.AbstractAspectJAdvisorFactory",
				"org.springframework.aop.aspectj.annotation.AbstractAspectJAdvisorFactory$AspectJAnnotation",
				"org.springframework.aop.aspectj.annotation.ReflectiveAspectJAdvisorFactory",
				"org.springframework.aop.framework.autoproxy.target.QuickTargetSourceCreator",
				"org.springframework.aop.target.CommonsPool2TargetSource",
				"org.springframework.aot.hint.SerializationHintsExtensionsKt$registerType$1",
				"org.springframework.beans.factory.config.ProviderCreatingFactoryBean",
				"org.springframework.beans.factory.config.YamlMapFactoryBean",
				"org.springframework.beans.factory.config.YamlProcessor",
				"org.springframework.beans.factory.config.YamlPropertiesFactoryBean",
				"org.springframework.beans.factory.groovy.GroovyBeanDefinitionReader",
				"org.springframework.context.support.GenericGroovyApplicationContext",
				"org.springframework.core.CoroutinesUtils",
				"org.springframework.core.ReactiveAdapterRegistry$SpringCoreBlockHoundIntegration",
				"org.springframework.core.codec.AbstractCharSequenceDecoder",
				"org.springframework.core.codec.AbstractDataBufferDecoder",
				"org.springframework.core.codec.AbstractDecoder",
				"org.springframework.core.codec.AbstractSingleValueEncoder",
				"org.springframework.core.codec.ByteArrayDecoder", "org.springframework.core.codec.ByteBufferDecoder",
				"org.springframework.core.codec.CharBufferDecoder", "org.springframework.core.codec.DataBufferDecoder",
				"org.springframework.core.codec.Decoder", "org.springframework.core.codec.Netty5BufferDecoder",
				"org.springframework.core.codec.NettyByteBufDecoder", "org.springframework.core.codec.ResourceDecoder",
				"org.springframework.core.codec.ResourceEncoder",
				"org.springframework.core.codec.ResourceRegionEncoder", "org.springframework.core.codec.StringDecoder",
				"org.springframework.core.io.VfsUtils", "org.springframework.core.io.buffer.DataBufferUtils",
				"org.springframework.core.io.buffer.Netty5DataBufferFactory",
				"org.springframework.core.io.buffer.NettyDataBufferFactory",
				"org.springframework.core.type.filter.AspectJTypeFilter",
				"org.springframework.jca.endpoint.AbstractMessageEndpointFactory",
				"org.springframework.jca.endpoint.AbstractMessageEndpointFactory$AbstractMessageEndpoint",
				"org.springframework.jca.endpoint.GenericMessageEndpointFactory",
				"org.springframework.jca.endpoint.GenericMessageEndpointFactory$InternalResourceException",
				"org.springframework.jca.endpoint.GenericMessageEndpointManager",
				"org.springframework.jca.support.ResourceAdapterFactoryBean",
				"org.springframework.jca.support.SimpleBootstrapContext",
				"org.springframework.scripting.bsh.BshScriptEvaluator",
				"org.springframework.scripting.bsh.BshScriptFactory",
				"org.springframework.scripting.bsh.BshScriptUtils$BshExecutionException",
				"org.springframework.scripting.groovy.GroovyScriptEvaluator",
				"org.springframework.scripting.groovy.GroovyScriptFactory",
				"org.springframework.transaction.jta.JtaAfterCompletionSynchronization",
				"org.springframework.transaction.jta.JtaTransactionManager",
				"org.springframework.transaction.jta.JtaTransactionObject",
				"org.springframework.transaction.jta.ManagedTransactionAdapter",
				"org.springframework.transaction.jta.SimpleTransactionFactory",
				"org.springframework.transaction.jta.SpringJtaSynchronizationAdapter",
				"org.springframework.transaction.jta.UserTransactionAdapter",
				"org.springframework.transaction.reactive.AbstractReactiveTransactionManager",
				"org.springframework.transaction.reactive.TransactionalOperator",
				"org.springframework.transaction.reactive.TransactionalOperatorExtensionsKt",
				"org.springframework.validation.beanvalidation.CustomValidatorBean",
				"org.springframework.validation.beanvalidation.LocalValidatorFactoryBean",
				"org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator",
				"org.springframework.validation.beanvalidation.MessageSourceResourceBundleLocator",
				"org.springframework.validation.beanvalidation.MethodValidationAdapter",
				"org.springframework.validation.beanvalidation.MethodValidationInterceptor",
				"org.springframework.validation.beanvalidation.OptionalValidatorFactoryBean",
				"org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory",
				"org.springframework.validation.beanvalidation.SpringValidatorAdapter"
				//
				, "org.eclipse.jetty.http.pathmap.PathSpecSet"
				//
				, "org.eclipse.jetty.http.HttpCompliance"
				//
				, "org.eclipse.jetty.http.DateParser"
		//
		));
		//
		if (GraphicsEnvironment.isHeadless()) {
			//
			classToBeExcluded.add("com.sun.jna.platform.KeyboardUtils");
			//
		} // if
			//
		final FileSystem fs = FileSystems.getDefault();
		//
		if (!Objects.equals("sun.nio.fs.WindowsFileSystemProvider",
				getName(getClass(fs != null ? fs.provider() : null)))) {
			//
			classToBeExcluded.addAll(Arrays.asList("com.sun.jna.platform.win32.Advapi32",
					"com.sun.jna.platform.win32.Kernel32", "com.sun.jna.platform.win32.COM.COMBindingBaseObject",
					"com.sun.jna.platform.win32.COM.COMEarlyBindingObject",
					"com.sun.jna.platform.win32.COM.COMLateBindingObject",
					"com.sun.jna.platform.win32.COM.TypeInfoUtil", "com.sun.jna.platform.win32.COM.TypeLibUtil",
					"com.sun.jna.platform.win32.COM.util.Factory", "com.sun.jna.platform.win32.COM.util.ObjectFactory",
					"com.sun.jna.platform.win32.Cfgmgr32", "com.sun.jna.platform.win32.Crypt32",
					"com.sun.jna.platform.win32.Cryptui", "com.sun.jna.platform.win32.Ddeml",
					"com.sun.jna.platform.win32.DdemlUtil$DdemlException", "com.sun.jna.platform.win32.Dxva2",
					"com.sun.jna.platform.win32.GDI32", "com.sun.jna.platform.win32.IPHlpAPI",
					"com.sun.jna.platform.win32.Mpr", "com.sun.jna.platform.win32.Msi",
					"com.sun.jna.platform.win32.Netapi32", "com.sun.jna.platform.win32.NtDll",
					"com.sun.jna.platform.win32.Ole32", "com.sun.jna.platform.win32.OleAuto",
					"com.sun.jna.platform.win32.OpenGL32", "com.sun.jna.platform.win32.Pdh",
					"com.sun.jna.platform.win32.PowrProf", "com.sun.jna.platform.win32.Psapi",
					"com.sun.jna.platform.win32.Rasapi32", "com.sun.jna.platform.win32.Secur32",
					"com.sun.jna.platform.win32.SetupApi", "com.sun.jna.platform.win32.Shell32",
					"com.sun.jna.platform.win32.Shlwapi", "com.sun.jna.platform.win32.Sspi",
					"com.sun.jna.platform.win32.User32", "com.sun.jna.platform.win32.Version",
					"com.sun.jna.platform.win32.Wevtapi", "com.sun.jna.platform.win32.Wininet",
					"com.sun.jna.platform.win32.Winsock2", "com.sun.jna.platform.win32.Winspool",
					"com.sun.jna.platform.win32.Wtsapi32", "jnafilechooser.win32.Ole32",
					"jnafilechooser.win32.Shell32"));
			//
		} // if
			//
		if (classInfos != null) {
			//
			classInfos.removeIf(x -> {
				//
				if (x == null) {
					//
					return false;
					//
				} // if
					//
				return CollectionUtils.isNotEmpty(classToBeExcluded) && contains(classToBeExcluded, x.getName());
			});
			//
		} // if
			//
		return classInfos;
		//
	}

	private static boolean contains(final Collection<?> items, final Object item) {
		return items != null && items.contains(item);
	}

	private static String getName(final Class<?> instance) {
		return instance != null ? instance.getName() : null;
	}

	private static Class<?> getClass(final Object instance) {
		return instance != null ? instance.getClass() : null;
	}

	private static ClassInfoList getAllClasses(final ScanResult instance) {
		return instance != null ? instance.getAllClasses() : null;
	}

	private static ScanResult scan(final ClassGraph instance) {
		return instance != null ? instance.scan() : null;
	}

}