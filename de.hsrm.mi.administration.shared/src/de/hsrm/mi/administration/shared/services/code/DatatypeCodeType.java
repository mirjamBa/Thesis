package de.hsrm.mi.administration.shared.services.code;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

public class DatatypeCodeType extends AbstractCodeType<Long> {

	private static final long serialVersionUID = 1L;
	public static final Long ID = 20000L;

	public DatatypeCodeType() throws ProcessingException {
		super();
	}

	public static Object getValue(Long codeId, String value) {
		if (value != null) {
			if (codeId.equals(StringCode.ID)) {
				return value;
			} else if (codeId.equals(LongCode.ID)) {
				return Long.parseLong(value);
			} else if (codeId.equals(DoubleCode.ID)) {
				return Double.parseDouble(value);
			} else if (codeId.equals(DateCode.ID)) {
				SimpleDateFormat formatter = new SimpleDateFormat(TEXTS.get("SimpleDateFormat"));
				try {
					return formatter.parse(value);
				} catch (ParseException e) {
					e.printStackTrace();
					return null;
				}
			}
		}
		return null;
	}

	@Override
	protected String getConfiguredText() {
		return TEXTS.get("Datatype");
	}

	@Override
	public Long getId() {
		return ID;
	}

	@Order(10.0)
	public static class StringCode extends AbstractCode<Long> {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 20001L;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("String");
		}

		@Override
		public Long getId() {
			return ID;
		}
	}

	@Order(20.0)
	public static class LongCode extends AbstractCode<Long> {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 20002L;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Long");
		}

		@Override
		public Long getId() {
			return ID;
		}
	}

	@Order(30.0)
	public static class DoubleCode extends AbstractCode<Long> {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 20003L;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Double");
		}

		@Override
		public Long getId() {
			return ID;
		}
	}

	@Order(40.0)
	public static class DateCode extends AbstractCode<Long> {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 20004L;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Date");
		}

		@Override
		public Long getId() {
			return ID;
		}
	}
}
