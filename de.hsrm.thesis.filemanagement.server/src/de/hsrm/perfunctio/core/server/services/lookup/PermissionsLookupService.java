package de.hsrm.perfunctio.core.server.services.lookup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.osgi.BundleClassDescriptor;
import org.eclipse.scout.rt.server.services.lookup.AbstractLookupService;
import org.eclipse.scout.rt.shared.services.common.security.IPermissionService;
import org.eclipse.scout.rt.shared.services.lookup.LookupCall;
import org.eclipse.scout.rt.shared.services.lookup.LookupRow;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.server.util.LookupUtility;
import de.hsrm.perfunctio.core.shared.services.lookup.IPermissionsLookupService;

/**
 * Fetches all permissions as lookup-rows from the bundles
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class PermissionsLookupService extends AbstractLookupService
		implements
			IPermissionsLookupService {

	@Override
	public LookupRow[] getDataByAll(LookupCall call) throws ProcessingException {
		List<LookupRow> rows = new ArrayList<LookupRow>();

		BundleClassDescriptor[] permissions = SERVICES.getService(
				IPermissionService.class).getAllPermissionClasses();

		for (int i = 0; i < permissions.length; i++) {
			String bundleName = permissions[i].getBundleSymbolicName();
			if (bundleName.contains("perfunctio")) {
				rows.add(new LookupRow(permissions[i].getSimpleClassName(),
						permissions[i].getSimpleClassName()));
			}
		}

		return LookupUtility.sortLookupRows(rows);
	}

	@Override
	public LookupRow[] getDataByKey(LookupCall call) throws ProcessingException {
		return getDataByAll(call);
	}

	@Override
	public LookupRow[] getDataByRec(LookupCall call) throws ProcessingException {
		return getDataByAll(call);
	}

	@Override
	public LookupRow[] getDataByText(LookupCall call)
			throws ProcessingException {
		return getDataByAll(call);
	}
}
