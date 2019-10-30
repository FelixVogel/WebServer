package de.felix.webserver;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Keeps some of the most used mime types, can be extended for any file
 *
 * @author Felix Vogel
 */
public final class MimeType {

	private static final Map<String, String> MIME_TYPES = new HashMap<>();

	static {
		MIME_TYPES.put("html", "text/html");
		MIME_TYPES.put("htm", "text/html");
		MIME_TYPES.put("js", "text/javascript");
		MIME_TYPES.put("css", "text/css");
		MIME_TYPES.put("json", "application/json");
		MIME_TYPES.put("xml", "application/xml");
		MIME_TYPES.put("gif", "image/gif");
		MIME_TYPES.put("png", "image/png");
		MIME_TYPES.put("jpeg", "image/jpeg");
		MIME_TYPES.put("jpg", "image/jpeg");
		MIME_TYPES.put("svg", "image/svg+xml");
		MIME_TYPES.put("ico", "	image/x-icon");
	}

	private final String type;
	private final boolean isReadable;

	private MimeType(final String type, final boolean readable) {
		this.type = type;
		this.isReadable = readable;
	}

	@Override
	public String toString() {
		return type;
	}

	/**
	 * Get the type string, e.g. for a <code>.html</code> file it would be <code>text/html</code>
	 *
	 * @return The type string
	 */
	public String getType() {
		return type;
	}

	/**
	 * If the file is readable, this might seem confusing, but imagine trying to open a <code>.png</code> <br>
	 * with a text editor and trying to read what's inside, doesn't make much sense does it.
	 *
	 * @return If the file is considered to be readable by humans
	 */
	public boolean isReadable() {
		return isReadable;
	}

	/**
	 * Get the mime type that should be used for the defined file
	 *
	 * @param file The file to get the mime type for
	 * @return The mime type of this file
	 */
	public static String getMimeType(final File file) {
		final String[] extensions = file.getName().split("\\.");

		return MIME_TYPES.getOrDefault(extensions[extensions.length - 1], "application/octet-stream");
	}

}
