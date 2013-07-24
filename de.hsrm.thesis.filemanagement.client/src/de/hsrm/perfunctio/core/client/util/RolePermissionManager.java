package de.hsrm.perfunctio.core.client.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RolePermissionManager {

	private Map<Long, Map<String, Boolean>> permissionState;
	private Long currentRole;
	private Map<String, String> menuPermissionMapping;

	public RolePermissionManager() {
		permissionState = new HashMap<Long, Map<String, Boolean>>();
	}

	public void update(Long role, Map<String, Boolean> permissions) {
		permissionState.put(role, permissions);
	}

	public Map<String, Boolean> getPermissions(Long role) {
		if (permissionState.containsKey(role)) {
			return permissionState.get(role);
		} else {
			return null;
		}
	}

	public Map<Long, ArrayList<String>> getPermissionsForSelectedRoles(
			Long[] roles) {
		Map<Long, ArrayList<String>> map = new HashMap<Long, ArrayList<String>>();
		if (roles != null) {
			for (Long l : roles) {
				if (permissionState.containsKey(l)) {
					Map<String, Boolean> p = permissionState.get(l);
					ArrayList<String> permissions = new ArrayList<String>();
					if (p != null) {
						for (String key : p.keySet()) {
							if (p.get(key)) {
								permissions.add(menuPermissionMapping.get(key));
							}
						}
					}
					map.put(l, permissions);
				}
			}
		}
		return map;
	}

	/**
	 * @return the currentRole
	 */
	public Long getCurrentRole() {
		return currentRole;
	}

	/**
	 * @param currentRole
	 *            the currentRole to set
	 */
	public void setCurrentRole(Long currentRole) {
		this.currentRole = currentRole;
	}

	/**
	 * @return the permissionState
	 */
	public Map<Long, Map<String, Boolean>> getPermissionState() {
		return permissionState;
	}

	/**
	 * @param permissionState
	 *            the permissionState to set
	 */
	public void setPermissionState(
			Map<Long, Map<String, Boolean>> permissionState) {
		this.permissionState = permissionState;
	}

	/**
	 * @return the menuPermissionMapping
	 */
	public Map<String, String> getMenuPermissionMapping() {
		return menuPermissionMapping;
	}

	/**
	 * @param menuPermissionMapping
	 *            the menuPermissionMapping to set
	 */
	public void setMenuPermissionMapping(
			Map<String, String> menuPermissionMapping) {
		this.menuPermissionMapping = menuPermissionMapping;
	}

}
