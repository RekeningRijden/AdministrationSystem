package main.core.pdf;

/**
 * @author Sam
 *         <p>
 *         Interface declaring predefined PDF file templates.
 *         </p>
 */
@FunctionalInterface
public interface ITemplate {

    /**
     * @return PDF file content.
     */
    String parse();
}