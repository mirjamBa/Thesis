package de.hsrm.perfunctio.core.server.services;

import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.IServerSession;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.service.AbstractService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.perfunctio.core.shared.services.IStartupService;
import de.hsrm.perfunctio.core.shared.services.IUserProcessService;

/**
 * Service implementation to initialize server session settings
 * 
 * @author Mirjam Bayatloo
 * 
 */
public class StartupService extends AbstractService implements IStartupService {
	private static IScoutLogger logger = ScoutLogManager
			.getLogger(IStartupService.class);

	@Override
	public void initSessionData() throws ProcessingException {
		IServerSession session = ServerJob.getCurrentSession();

		if (session.getUserId() != null) {
			logger.info("created a new session for " + session.getUserId());
			Long userNr = SERVICES.getService(IUserProcessService.class)
					.getUserId(session.getUserId());
			session.setData(IStartupService.USER_NR, userNr);
		}

	}
}
