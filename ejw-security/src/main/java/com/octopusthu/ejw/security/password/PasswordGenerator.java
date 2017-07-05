/**
 * 
 */
package com.octopusthu.ejw.security.password;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import lombok.Setter;

public class PasswordGenerator {
	protected final Log log = LogFactory.getLog(getClass());

	private short length;
	private String chars;
	private int maxAttempts;
	private Set<PasswordValidator> validators = new HashSet<PasswordValidator>();

	public PasswordGenerator(Short length, String chars, int maxAttempts, PasswordValidator... validators) {
		Assert.isTrue(length > 0, "length must be positive!");
		Assert.hasText(chars, "chars must have text!");
		Assert.isTrue(maxAttempts > 0, "maxAttempts must be positive!");

		this.length = length;
		this.chars = chars;
		this.maxAttempts = maxAttempts;
		if (ArrayUtils.isNotEmpty(validators)) {
			for (PasswordValidator validator : validators) {
				this.validators.add(validator);
			}
		}
	}

	/**
	 * @return the generated password, or {@code null} if it fails to generate a
	 *         valid password.
	 */
	public String generate() {
		int attempts = 0;
		String ret;
		while (attempts < this.maxAttempts) {
			ret = RandomStringUtils.random(this.length, this.chars);
			attempts++;
			if (this.validate(ret)) {
				log.info("Password generated with " + attempts + " attempt(s).");
				return ret;
			}
		}
		log.warn("Max attempts reached!");
		return null;
	}

	private boolean validate(String pwd) {
		for (PasswordValidator validator : this.validators) {
			if (!validator.validate(pwd)) {
				return false;
			}
		}
		return true;
	}

	public static interface PasswordValidator {
		public boolean validate(String pwd);
	}

	/**
	 * patterns: Set of regex
	 * 
	 * leastOccurrences must not be greater than patterns.size().
	 * 
	 * If leastOccurrences <= 0 then leastOccurrences = patterns.size().
	 * 
	 *
	 */
	public static class RegexPasswordValidator implements PasswordValidator {
		protected final Log log = LogFactory.getLog(getClass());

		private final Set<Pattern> patterns;
		private final int leastOccurrences;

		public RegexPasswordValidator(int leastOccurrences, String... strPatterns) {
			Assert.notEmpty(strPatterns, "strPatterns must not be empty!");
			this.patterns = new HashSet<Pattern>(strPatterns.length);
			for (String strPattern : strPatterns) {
				this.patterns.add(Pattern.compile(strPattern));
			}
			Assert.isTrue(leastOccurrences <= this.patterns.size(),
					"leastOccurrences must not be greater than patterns size!");
			this.leastOccurrences = leastOccurrences <= 0 ? this.patterns.size() : leastOccurrences;
		}

		@Override
		public boolean validate(String pwd) {
			if (pwd == null) {
				return false;
			}
			int occurrences = 0;
			for (Pattern pattern : this.patterns) {
				try {
					if (pattern.matcher(pwd).matches()) {
						occurrences++;
						if (occurrences == this.leastOccurrences) {
							return true;
						}
					}
				} catch (Exception e) {
					log.warn("Error validating pwd! pwd=" + pwd + ", pattern=" + pattern, e);
					return false;
				}
			}
			return false;
		}
	}

	/**
	 * If:
	 * 
	 * charset=UTF_8, length=3, byteRanges={[48, 57], [65, 90], [97, 122]}
	 * 
	 * Then:
	 * 
	 * "123", "321", "ABC", "CBA", "abc", "bca", ... are not allowed.
	 * 
	 * NOTE: Can only deal with single-byte characters.
	 *
	 */
	public static class NoConsecutiveCharactersPasswordValidator implements PasswordValidator {
		protected final Log log = LogFactory.getLog(getClass());

		private int diff = 1;

		@Setter
		private Charset charset = StandardCharsets.UTF_8;

		private int length;
		private Set<Byte[]> byteRanges;

		public NoConsecutiveCharactersPasswordValidator(int length, Set<Byte[]> byteRanges) {
			Assert.isTrue(length > 1, "length must at least be 2!");
			Assert.notEmpty(byteRanges, "byteRanges must not be empty!");
			try {
				for (Byte[] byteRange : byteRanges) {
					Assert.isTrue(byteRange[0] <= byteRange[1],
							"Illegal byte range: [" + byteRange[0] + ", " + byteRange[1] + "]!");
				}
			} catch (Exception e) {
				throw new IllegalArgumentException(e);
			}

			this.length = length;
			this.byteRanges = byteRanges;
		}

		@Override
		public boolean validate(String pwd) {
			if (pwd == null || pwd.length() < this.length) {
				log.warn("No need to validate: " + pwd);
				return true;
			}
			byte[] bytes = pwd.getBytes();
			if (bytes.length != pwd.length()) {
				log.warn("Unable to validate pwd as it contains non-single-byte characters: " + pwd);
				return false;
			}
			try {
				validateRecursively(bytes, 0);
			} catch (Exception e) {
				log.info(e.getMessage());
				return false;
			}
			return true;
		}

		private void validateRecursively(byte[] bytes, int current, byte... pattern) throws Exception {
			int left = (ArrayUtils.isEmpty(pattern)) ? this.length : this.length - pattern.length;

			if (left == 0) {
				throw new Exception("Invalid pattern found at index: " + (current - 1) + "! pwd="
						+ new String(bytes, this.charset));
			}

			if (current >= bytes.length || bytes.length - current < left) {
				return;
			}

			byte currentByte = bytes[current];

			if (ArrayUtils.isEmpty(pattern)) {
				validateRecursively(bytes, current + 1);
				if (this.isInRange(currentByte)) {
					validateRecursively(bytes, current + 1, currentByte);
				}
			} else {
				if (this.isInRange(currentByte) && this.isConsecutive(pattern, currentByte)) {
					byte[] newPattern = Arrays.copyOf(pattern, pattern.length + 1);
					newPattern[newPattern.length - 1] = currentByte;
					validateRecursively(bytes, current + 1, newPattern);
				}
			}

		}

		private boolean isConsecutive(byte[] pattern, byte currentByte) {
			int diff = currentByte - pattern[pattern.length - 1];
			if (Math.abs(diff) == this.diff) {
				if (pattern.length == 1) {
					return true;
				}
				return diff == (pattern[pattern.length - 1] - pattern[pattern.length - 2]);
			}
			return false;
		}

		private boolean isInRange(byte b) {
			for (Byte[] byteRange : this.byteRanges) {
				if (byteRange[0] <= b && b <= byteRange[1]) {
					return true;
				}
			}
			return false;
		}

	}
}
