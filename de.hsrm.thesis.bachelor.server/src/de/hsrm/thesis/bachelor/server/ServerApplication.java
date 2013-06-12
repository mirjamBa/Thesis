package de.hsrm.thesis.bachelor.server;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.equinox.app.IApplication;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.scout.commons.logger.IScoutLogger;
import org.eclipse.scout.commons.logger.ScoutLogManager;
import org.eclipse.scout.rt.server.ServerJob;
import org.eclipse.scout.rt.server.services.common.session.IServerSessionRegistryService;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.shared.services.IStorageService;

/**
 * Dummy application in order to manage server side product configurations in *.product files.
 * A typical config.ini for such a product has (among others) the following properties:
 * osgi.clean=true
 * osgi.console=
 * eclipse.consoleLog=true
 * org.eclipse.equinox.http.jetty.http.port=8080
 * org.eclipse.equinox.http.jetty.context.path=/bachelor
 * osgi.bundles=org.eclipse.equinox.common@2:start, org.eclipse.update.configurator@start,
 * org.eclipse.equinox.http.jetty@start, org.eclipse.equinox.http.registry@start, org.eclipse.core.runtime@start
 * osgi.bundles.defaultStartLevel=4
 * osgi.noShutdown=true
 * eclipse.ignoreApp=false
 * eclipse.product=de.hsrm.thesis.bachelor.server.product
 */
public class ServerApplication implements IApplication {
  private static IScoutLogger logger = ScoutLogManager.getLogger(ServerApplication.class);

  @Override
  public Object start(IApplicationContext context) throws Exception {
    //start the scheduler
    /*
    Scheduler scheduler=new Scheduler(Activator.getDefault().getBackendSubject(),ServerSession.class);
    scheduler.addJob(new LoadJobs());
    scheduler.start();
    Activator.getDefault().setScheduler(scheduler);
    */

    // create session using server principal
    ServerSession serverSession = SERVICES.getService(IServerSessionRegistryService.class).newServerSession(ServerSession.class, Activator.getDefault().getBackendSubject());

    // start a job that installs the database and creates all tables (if they do not exist already).
    ServerJob dbInstallJob = new ServerJob("Install Db schema if necessary", serverSession) {
      @Override
      protected IStatus runTransaction(IProgressMonitor monitor) {
        try {
          SERVICES.getService(IStorageService.class).installStorage();
        }
        catch (Throwable t) {
          return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error while installing the bachelor server Db schema", t);
        }

        try {
        }
        catch (Throwable t) {
          return new Status(IStatus.ERROR, Activator.PLUGIN_ID, "Error while initiating filetypes", t);
        }

        return Status.OK_STATUS;
      }
    };
    dbInstallJob.schedule();
    dbInstallJob.join(20000);

    logger.info("bachelor server initialized");
    return EXIT_OK;
  }

  @Override
  public void stop() {

  }
}
