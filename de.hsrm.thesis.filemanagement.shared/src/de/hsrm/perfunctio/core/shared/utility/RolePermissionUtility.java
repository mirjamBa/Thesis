package de.hsrm.perfunctio.core.shared.utility;

import java.util.ArrayList;
import java.util.Map;

public class RolePermissionUtility {

	/**
	 * Compares every role and permission in the map and removes all old
	 * elements
	 * 
	 * @param oldP  Map<Long, ArrayList<String>>
	 * @param newP  Map<Long, ArrayList<String>>
	 * @return  Map<Long, ArrayList<String>>
	 */
	public static Map<Long, ArrayList<String>> getRolePermissionDif(
			Map<Long, ArrayList<String>> oldP, Map<Long, ArrayList<String>> newP) {

		// extract new roles
		Long[] roles = newP.keySet().toArray(new Long[newP.keySet().size()]);
		
		for(int i = 0; i < roles.length; i++){
			Long role = roles[i];
			if(oldP.containsKey(role)){
				// role may have been modified (permissions)
				if(!newP.get(role).equals(oldP.get(role))){
					// check every permission
					for(int j = 0; j < newP.get(role).size(); j++){
						String permission = newP.get(role).get(j);
						if (oldP.get(role).contains(permission)) {
							newP.get(role).remove(permission);
						}
					}
					if (newP.get(role).size() == 0) {
						// role hasn't changed
						newP.remove(role);
					}
				}
				
			}else{
				// role is completely new, nothing to compare
			}
		}

		
//		for (Long l : newP.keySet()) {
//			if (oldP.containsKey(l)) {
//				// role may have been modified (permissions)
//				if (!newP.get(l).equals(oldP.get(l))) {
//					// check every permission
//					for(int i = 0; i < newP.get(l).size(); i++){
//						String permission = newP.get(l).get(i);
//						if (oldP.get(l).contains(permission)) {
//							newP.get(l).remove(permission);
//						}
//					}
////					if (newP.get(l).size() == 0) {
////						// role hasn't changed
////						newP.remove(l);
////					}
//				}
//			} else {
//				// role is completely new, nothing to compare
//			}
//		}
		
		return newP;
	}

}
