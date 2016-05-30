package main.core.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.Pipeline;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.css.CssFile;
import com.itextpdf.tool.xml.css.StyleAttrCSSResolver;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import main.core.exception.GenerationException;

/**
 * Utility class for generating PDF files.
 *
 * @author Sam
 */
public final class PdfGenerator {

    private PdfGenerator() {
        //Utility class constructor cannot be called.
    }

    /**
     * Create a PDF file from the given data.
     *
     * @param fileName name of the created PDF file.
     * @param template to retrieve the content from.
     */
    public static void generate(String fileName, ITemplate template) {
        OutputStream out = null;
        ByteArrayOutputStream baos = null;
        InputStream is = null;

        try {
            is = new ByteArrayInputStream(template.parse().getBytes());
            baos = new ByteArrayOutputStream();

            Document document = new Document();

            PdfWriter writer = PdfWriter.getInstance(document, baos);
            writer.setInitialLeading(12.5f);

            document.open();

            HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
            htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());

            InputStream cssPathTest = Thread.currentThread().getContextClassLoader().getResourceAsStream("pdf/pdfFiles.css");
            CssFile cssFile = XMLWorkerHelper.getCSS(cssPathTest);
            CSSResolver cssResolver = new StyleAttrCSSResolver();
            cssResolver.addCss(cssFile);

            Pipeline<?> pipeline = new CssResolverPipeline(cssResolver,
                    new HtmlPipeline(htmlContext, new PdfWriterPipeline(
                            document, writer)));

            XMLWorker worker = new XMLWorker(pipeline, true);
            XMLParser parser = new XMLParser(worker);
            parser.parse(is);

            document.close();

            out = new FileOutputStream("C:\\School\\" + fileName + ".pdf");
            baos.writeTo(out);
        } catch (IOException | DocumentException | GenerationException e) {
            Logger.getLogger(PdfGenerator.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            close(out);
            close(baos);
            close(is);
        }
    }

    /**
     * Close a stream.
     *
     * @param stream to close.
     */
    private static void close(Closeable stream) {
        try {
            if (stream != null) {
                stream.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(PdfGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}