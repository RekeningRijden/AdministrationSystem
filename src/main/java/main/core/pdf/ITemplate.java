package main.core.pdf;

/**
 * @author Sam
 *         <p>
 *         Interface declaring predefined PDF file templates.
 *         </p>
 */
public interface ITemplate {

    /**
     * @return PDF file content.
     */
    String parse();
}