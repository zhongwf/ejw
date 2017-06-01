package com.octopusthu.ejw.security.component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SecurityProps {
	private String rolePrefix = "";
	private boolean forceHttps;
	private String[] portMappings = { "80:443" };

	private Authentication authentication = new Authentication();
	private Authorization authorization = new Authorization();
	private Default dflt = new Default();
	private Api api = new Api();

	@Getter
	@Setter
	public static class Authentication {
		private String targetUrlParameter = null;
		private String defaultTargetUrl = "/";
		private String failureUrl = "/login";
	}

	@Getter
	@Setter
	public static class Authorization {
		private Default dflt = new Default();

		@Getter
		@Setter
		public static class Default {
		}
	}

	@Getter
	@Setter
	public static class Default {
		private boolean enabled = true;
		private String antPattern;
		private String loginFormUrl = "/login";
		private String accessDeniedPage;
		private Ignoring ignoring = new Ignoring();
		private Logout logout = new Logout();
		private Login login = new Login();
		private boolean rejectPublicInvocations = true;

		@Getter
		@Setter
		public static class Ignoring {
			private String[] antPatterns = { "/favicon", "/res/**" };
		}

		@Getter
		@Setter
		public static class Logout {
			private String url = "/logout";
			private String logoutSuccessUrl = "/";
		}

		@Getter
		@Setter
		public static class Login {
			private String loginProcessingUrl = "/security_check";
			private String usernameParameter = "username";
			private String passwordParameter = "password";
		}
	}

	@Getter
	@Setter
	public static class Api {
		private boolean enabled = false;
		private String antPattern = "/a/**";
		private String accessDeniedPage;
		private Simple simple = new Simple();

		@Getter
		@Setter
		public static class Simple {
			private long timeDeviationAllowed = 300000;
			private boolean appIdWhiteListEnabled = true;
			private String[] appIdWhiteList = {};
			private String[] appRegistry = {};
			private Params params = new Params();

			@Getter
			@Setter
			public static class Params {
				private String app = "app";
				private String key = "key";
				private String timestamp = "timestamp";
			}
		}
	}
}
