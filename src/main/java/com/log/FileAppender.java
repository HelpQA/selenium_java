package com.log;

import java.io.IOException;

import org.apache.log4j.Layout;
import org.apache.log4j.RollingFileAppender;

/**
 * This customized Log4j appender will separate the log messages based on
 * message which is to be passed in logging event.
 * @see org.apache.log4j.RollingFileAppender
 */
public class FileAppender extends RollingFileAppender {

	/**
	 * The default constructor simply calls its {@link FileAppender#FileAppender parents constructor}.
	 */
	public FileAppender() {
		super();
	}

	/**
	 *  Instantiate a FileAppender and open the file designated by filename. The opened filename will become the output
	 * destination for this appender.
	 * @param layout
	 * @param filename
	 * @throws IOException
	 */
	public FileAppender(final Layout layout, final String filename)
			throws IOException {
		super(layout, filename);
	}

	/**Instantiate a FileAppender and open the file designated by filename. The opened filename will become the output
	 * destination for this appender.
	 * If the append parameter is true, the file will be appended to.
	 * @param layout
	 * @param filename
	 * @param append
	 * @throws IOException
	 */
	public FileAppender(final Layout layout, final String filename,
			final boolean append) throws IOException {
		super(layout, filename, append);
	}

}
