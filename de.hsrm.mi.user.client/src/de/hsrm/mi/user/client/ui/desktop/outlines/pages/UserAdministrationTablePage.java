package de.hsrm.mi.user.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.annotations.Order;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractLongColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.AbstractPageWithTable;
import org.eclipse.scout.rt.client.ui.form.useradmin.DefaultPasswordForm;
import org.eclipse.scout.rt.client.ui.messagebox.MessageBox;
import org.eclipse.scout.rt.extension.client.ui.basic.table.AbstractExtensibleTable;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.services.common.jdbc.SearchFilter;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.mi.user.client.ui.forms.UserForm;
import de.hsrm.thesis.filemanagement.shared.Icons;
import de.hsrm.thesis.filemanagement.shared.security.CreateUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.DeleteUserPermission;
import de.hsrm.thesis.filemanagement.shared.security.ResetPasswordPermission;
import de.hsrm.thesis.filemanagement.shared.security.UpdateUserPermission;
import de.hsrm.thesis.filemanagement.shared.services.IUserProcessService;

public class UserAdministrationTablePage extends AbstractPageWithTable<UserAdministrationTablePage.Table> {

  @Override
  protected boolean getConfiguredLeaf() {
    return true;
  }

  @Override
  protected String getConfiguredTitle() {
    return TEXTS.get("Users");
  }

  @Override
  protected Object[][] execLoadTableData(SearchFilter filter) throws ProcessingException {
    return SERVICES.getService(IUserProcessService.class).getUsers();
  }

  @Order(10.0)
  public class Table extends AbstractExtensibleTable {

    public UsernameColumn getUsernameColumn() {
      return getColumnSet().getColumnByClass(UsernameColumn.class);
    }

    @Override
    protected boolean getConfiguredAutoResizeColumns() {
      return true;
    }

    @Override
    protected String getConfiguredDefaultIconId() {
      return Icons.Eye;
    }

    @Override
    protected void execRowAction(ITableRow row) throws ProcessingException {
      getMenu(ModifyUserMenu.class).execAction();
    }

    public UserIdColumn getUserIdColumn() {
      return getColumnSet().getColumnByClass(UserIdColumn.class);
    }

    @Order(10.0)
    public class UserIdColumn extends AbstractLongColumn {

      @Override
      protected boolean getConfiguredDisplayable() {
        return false;
      }

      @Override
      protected boolean getConfiguredMandatory() {
        return true;
      }

      @Override
      protected boolean getConfiguredPrimaryKey() {
        return true;
      }

      @Override
      protected int getConfiguredWidth() {
        return 0;
      }
    }

    @Order(20.0)
    public class UsernameColumn extends AbstractStringColumn {

      @Override
      protected boolean getConfiguredAutoOptimizeWidth() {
        return true;
      }

      @Override
      protected String getConfiguredHeaderText() {
        return TEXTS.get("Username");
      }

      @Override
      protected int getConfiguredSortIndex() {
        return 0;
      }

      @Override
      protected int getConfiguredWidth() {
        return 230;
      }
    }

    @Order(10.0)
    public class ModifyUserMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ModifyUser");
      }

      @Override
      protected void execPrepareAction() throws ProcessingException {
        setVisiblePermission(new UpdateUserPermission());
      }

      @Override
      protected void execAction() throws ProcessingException {
        UserForm form = new UserForm();
        form.getUsernameField().setValue(getUsernameColumn().getSelectedValue());
        form.setUserId(getUserIdColumn().getSelectedValue());
        form.getRoleField().setValue(SERVICES.getService(IUserProcessService.class).getUserRoles(getUserIdColumn().getSelectedValue()));
        form.startModify();
        form.waitFor();
        if (form.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(20.0)
    public class NewUserMenu extends AbstractMenu {

      @Override
      protected boolean getConfiguredEmptySpaceAction() {
        return true;
      }

      @Override
      protected boolean getConfiguredMultiSelectionAction() {
        return true;
      }

      @Override
      protected boolean getConfiguredSingleSelectionAction() {
        return false;
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("NewUser");
      }

      @Override
      protected void execPrepareAction() throws ProcessingException {
        setVisiblePermission(new CreateUserPermission());
      }

      @Override
      protected void execAction() throws ProcessingException {
        UserForm frm = new UserForm();
        frm.getRoleField().setValue(new Long[]{SERVICES.getService(IUserProcessService.class).getUserRoleId()});
        //TODO set User Role enabled
//        frm.getRoleField().getCheckedLookupRow().setEnabled(false); triggers nullpointer
        frm.startNew();
        frm.waitFor();
        if (frm.isFormStored()) {
          reloadPage();
        }
      }
    }

    @Order(30.0)
    public class ResetPasswordMenu extends AbstractMenu {

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("ResetPassword");
      }

      @Override
      protected void execAction() throws ProcessingException {
        DefaultPasswordForm frm = new DefaultPasswordForm();
        frm.setUserId(getUserIdColumn().getSelectedValue().toString());
        frm.startReset();
        frm.waitFor();
      }

      @Override
      protected void execPrepareAction() throws ProcessingException {
        setVisiblePermission(new ResetPasswordPermission());
      }
    }

    @Order(40.0)
    public class SeparatorMenu extends AbstractMenu {

      @Override
      protected boolean getConfiguredSeparator() {
        return true;
      }

      @Override
      protected void execPrepareAction() throws ProcessingException {
        setVisiblePermission(new DeleteUserPermission());
      }
    }

    @Order(50.0)
    public class DeleteUserMenu extends AbstractMenu {

      @Override
      protected boolean getConfiguredMultiSelectionAction() {
        return true;
      }

      @Override
      protected void execPrepareAction() throws ProcessingException {
        setVisiblePermission(new DeleteUserPermission());
      }

      @Override
      protected String getConfiguredText() {
        return TEXTS.get("DeleteUser");
      }

      @Override
      protected void execAction() throws ProcessingException {
        if (MessageBox.showDeleteConfirmationMessage(TEXTS.get("Users"), getUsernameColumn().getValues(getSelectedRows()))) {
          SERVICES.getService(IUserProcessService.class).deleteUser(getUserIdColumn().getValues(getSelectedRows()));
          reloadPage();
        }
      }
    }

  }
}
