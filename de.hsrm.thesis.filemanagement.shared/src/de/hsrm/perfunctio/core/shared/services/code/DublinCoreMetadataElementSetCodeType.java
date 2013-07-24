package de.hsrm.perfunctio.core.shared.services.code;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCode;
import org.eclipse.scout.rt.shared.services.common.code.AbstractCodeType;

/**
 * Long-CodeType for the Dublin Core Metadata Element Set 
 * {@link http://dublincore.org/documents/dces/}
 * 
 * @author mba
 * 
 */
public class DublinCoreMetadataElementSetCodeType
		extends
			AbstractCodeType<Long> {

	private static final long serialVersionUID = 1L;
	public static final Long ID = 70000L;

	public DublinCoreMetadataElementSetCodeType() throws ProcessingException {
		super();
	}

	@Override
	protected String getConfiguredText() {
		return TEXTS.get("DublinCoreMetadataElementSet");
	}

	@Override
	public Long getId() {
		return ID;
	}

	@Order(10.0)
	public static class TitleCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70001L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Title");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(20.0)
	public static class CreatorCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70002L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Creator");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(30.0)
	public static class SubjectCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70003L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Subject");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(40.0)
	public static class DescriptionCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70004L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Description");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(50.0)
	public static class PublisherCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70005L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Publisher");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(60.0)
	public static class ContributorCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70006L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Contributor");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(70.0)
	public static class DateCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70007L;
		public static final Long DATATYPE = DatatypeCodeType.DateCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("DateMetadata");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(80.0)
	public static class TypeCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70008L;
		public static final Long DATATYPE = DatatypeCodeType.LongCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("FileType");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(90.0)
	public static class FormatCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70009L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Format");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(100.0)
	public static class IdentifierCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70010L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Identifier");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(110.0)
	public static class SourceCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70011L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Source");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(120.0)
	public static class LanguageCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70012L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Language");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(130.0)
	public static class RelationCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70013L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Relation");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(140.0)
	public static class CoverageCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70014L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Coverage");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}

	@Order(150.0)
	public static class RightsCode extends AbstractCode<Long>
			implements
				ICategorizable {

		private static final long serialVersionUID = 1L;
		public static final Long ID = 70015L;
		public static final Long DATATYPE = DatatypeCodeType.StringCode.ID;

		@Override
		protected String getConfiguredText() {
			return TEXTS.get("Rights");
		}

		@Override
		public Long getId() {
			return ID;
		}

		@Override
		public Long getCategory() {
			return DATATYPE;
		}
	}
}
