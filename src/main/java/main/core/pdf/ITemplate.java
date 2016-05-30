package main.core.pdf;

import main.core.exception.GenerationException;

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
    String parse() throws GenerationException;
}