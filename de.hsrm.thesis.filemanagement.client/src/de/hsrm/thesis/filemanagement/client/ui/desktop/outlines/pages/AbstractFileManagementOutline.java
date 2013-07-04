package de.hsrm.thesis.filemanagement.client.ui.desktop.outlines.pages;

import org.eclipse.scout.commons.dnd.JavaTransferObject;
import org.eclipse.scout.commons.dnd.TransferObject;
import org.eclipse.scout.commons.exception.ProcessingException;
import org.eclipse.scout.rt.client.ui.basic.tree.ITree;
import org.eclipse.scout.rt.client.ui.basic.tree.ITreeNode;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.IPage;
import org.eclipse.scout.rt.client.ui.desktop.outline.pages.VirtualPage;
import org.eclipse.scout.rt.extension.client.ui.desktop.outline.AbstractExtensibleOutline;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.service.SERVICES;

import de.hsrm.thesis.filemanagement.shared.services.IFolderService;

public abstract class AbstractFileManagementOutline extends AbstractExtensibleOutline {
	  private boolean m_resolved = false;

	  @Override
	  protected String getConfiguredTitle() {
	    return TEXTS.get("FileManagement");
	  }

	  @Override
	  protected TransferObject execDrag(ITreeNode node) throws ProcessingException {
	    if (node instanceof FilePage || node instanceof FolderTablePage) {
	      return new JavaTransferObject(node);
	    }
	    return null;
	  }

	  @Override
	  protected void execDrop(ITreeNode node, TransferObject t) {
	    //only drop into folders
	    if (t instanceof JavaTransferObject && node instanceof FolderTablePage) {
	      IPage page = null;
	      Long id = null;
	      //dropped node is File
	      if (((JavaTransferObject) t).getLocalObject() instanceof FilePage) {
	        page = (FilePage) ((JavaTransferObject) t).getLocalObject();
	        id = ((FilePage) page).getFileId();
	      }
	      //dropped node is folder
	      else if (((JavaTransferObject) t).getLocalObject() instanceof FolderTablePage) {
	        page = (FolderTablePage) ((JavaTransferObject) t).getLocalObject();
	        id = ((FolderTablePage) page).getFolderId();
	      }

	      if (page != null && page.getParentNode() instanceof FolderTablePage) {
	        Long parentFolderId = ((FolderTablePage) page.getParentNode()).getFolderId();
	        Long newFolderId = ((FolderTablePage) node).getFolderId();
	        try {
	          SERVICES.getService(IFolderService.class).slideFileOrFolder(id, parentFolderId, newFolderId);
	          ((IPage) page.getTree().getRootNode()).reloadPage();
	          //TODO open child pages
	          setTreeNodeState(true, page.getTree());
	        }
	        catch (ProcessingException e) {
	          e.printStackTrace();
	        }
	      }

	    }
	  }

	  private void setTreeNodeState(boolean state, ITree tree) {
	    ITreeNode root = tree.getRootNode();
	    if (state) {
	      if (!m_resolved) {
	        resolveAllTreeNodes(root, state);
	        m_resolved = true;
	      }
	      else {
	        tree.expandAll(root);
	      }
	    }
	    else {
	      tree.collapseAll(root);
	    }
	  }

	  private void resolveAllTreeNodes(ITreeNode root, boolean state) {
	    for (ITreeNode child : root.getChildNodes()) {
	      if (child instanceof VirtualPage) {
	        try {
	          root.resolveVirtualChildNode(child);
	        }
	        catch (ProcessingException e) {
	          e.printStackTrace();
	        }
	      }
	    }
	    root.getTree().expandAll(root);
	    for (ITreeNode child : root.getChildNodes()) {
	      resolveAllTreeNodes(child, state);
	    }
	  }

	  @Override
	  protected boolean getConfiguredDragEnabled() {
	    return true;
	  }

	  @Override
	  protected int getConfiguredDragType() {
	    return TYPE_JAVA_ELEMENT_TRANSFER;
	  }

	  @Override
	  protected int getConfiguredDropType() {
	    return TYPE_JAVA_ELEMENT_TRANSFER;
	  }

	}

